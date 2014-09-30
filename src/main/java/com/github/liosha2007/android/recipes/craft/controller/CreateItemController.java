package com.github.liosha2007.android.recipes.craft.controller;

import android.content.Intent;
import android.widget.Toast;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
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
        List<Category> categories = DBHelper.getCategoryDAO().getAllCategories();
        view.showCategories(categories);
        List<Mod> mods = DBHelper.getModDAO().getAllMods();
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
            Category category = null;
            try {
                category = DBHelper.getCategoryDAO().selectBy(asList(Category.FIELD_NAME), categoryName);
            } catch (SQLException e) {
                Utils.err("Can't find category for name: " + e.getMessage());
            }
            Mod mod = null;
            try {
                mod = DBHelper.getModDAO().selectBy(asList(Category.FIELD_NAME), modName);
            } catch (SQLException e) {
                Utils.err("Can't find category for name: " + e.getMessage());
            }

            Item item = new Item();
            item.setIcon(iconId == -1 ? null : DBHelper.getIconDAO().queryForId(iconId));
            item.setName(title);
            item.setDescription(description);
            item.setNote(note);
            item.setCategory(category);
            item.setMod(mod);

            ItemDAO itemDAO = DBHelper.getItemDAO();
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
        Icon icon = (iconId == -1 ? null : DBHelper.getIconDAO().queryForId(iconId));
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
