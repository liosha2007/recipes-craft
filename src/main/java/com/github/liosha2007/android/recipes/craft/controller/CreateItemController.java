package com.github.liosha2007.android.recipes.craft.controller;

import android.content.Intent;
import android.widget.Toast;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.dao.CategoryDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.IconDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ModDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;
import com.github.liosha2007.android.recipes.craft.view.CreateItemView;

import java.sql.SQLException;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author liosha on 30.09.2014.
 */
public class CreateItemController extends BaseActivityController<CreateItemView> {
    private static final int SELECT_ICON_EVENT = Utils.makeID();
    private int iconId;
    private String categoryName;
    private String modName;

    public CreateItemController() {
        super(new CreateItemView());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final CategoryDAO categoryDAO = daoFor(Category.class);
        final ModDAO modDAO = daoFor(Mod.class);
        List<Category> categories = categoryDAO.getAllCategories();
        view.showCategories(categories);
        List<Mod> mods = modDAO.getAllMods();
        view.showMods(mods);
    }

    public void onIconClicked() {
        Intent intent = new Intent(BrowserController.INTENT_ACTION_SELECT_DIR,
                null,
                this,
                IconsController.class);
        startActivityForResult(intent, SELECT_ICON_EVENT);
    }

    public void onCreateItemClicked(String title, String description, String note) {
        if (title == null || title.isEmpty()
                || description == null || description.isEmpty()
                || categoryName == null || categoryName.isEmpty()
                || modName == null || modName.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show();
        } else {
            final CategoryDAO categoryDAO = daoFor(Category.class);
            final ModDAO modDAO = daoFor(Mod.class);
            final IconDAO iconDAO = daoFor(Icon.class);
            final ItemDAO itemDAO = daoFor(Item.class);
            Category category = null;
            try {
                category = categoryDAO.selectBy(asList(Category.FIELD_NAME), categoryName);
            } catch (SQLException e) {
                Utils.err("Can't find category for name: " + e.getMessage());
            }
            Mod mod = null;
            try {
                mod = modDAO.selectBy(asList(Category.FIELD_NAME), modName);
            } catch (SQLException e) {
                Utils.err("Can't find category for name: " + e.getMessage());
            }

            Item item = new Item();
            item.setIcon(iconId == -1 ? null : iconDAO.queryForId(iconId));
            item.setName(title);
            item.setDescription(description);
            item.setNote(note);
            item.setCategory(category);
            item.setMod(mod);

            try {
                if (itemDAO.selectBy(asList(Item.FIELD_NAME), categoryName) != null) {
                    throw new Exception("Предмет уже существует!");
                }
                itemDAO.create(item);
                Toast.makeText(this, "Предмет создан!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                Utils.err("Error during create item: " + e.getMessage());
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_ICON_EVENT) {
                onIconSelected(data.getIntExtra(IconsController.RETURN_ICON_PARAMETER, -1));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onIconSelected(int iconId) {
        this.iconId = iconId;
        IconDAO iconDAO = daoFor(Icon.class);
        Icon icon = (iconId == -1 ? null : iconDAO.queryForId(iconId));
        view.setCategoryIcon(icon == null ? null : Utils.bytes2bitmap(icon.getIcon()));
    }

    public void onCategorySelected(String categoryName) {
        this.categoryName = categoryName;
    }

    public void onModSelected(String modName) {
        this.modName = modName;
    }
}


//for (Mod category : mods){
//        try {
//        DBHelper.getIconDAO().refresh(category.getIcon());
//        } catch (SQLException e) {
//        Utils.err("Can't refresh icon for mod: " + e.getMessage());
//        }
//        }
