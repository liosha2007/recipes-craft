package com.github.liosha2007.android.recipes.craft.controller;

import android.content.Intent;
import android.widget.Toast;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.dao.IconDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ModDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;
import com.github.liosha2007.android.recipes.craft.view.CreateModView;

import static java.util.Arrays.asList;

/**
 * @author liosha on 30.09.2014.
 */
public class CreateModController extends BaseActivityController<CreateModView> {
    private static final int SELECT_ICON_EVENT = Utils.makeID();
    private int iconId;

    public CreateModController() {
        super(new CreateModView());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void onIconClicked() {
        Intent intent = new Intent(BrowserController.INTENT_ACTION_SELECT_DIR,
                null,
                this,
                IconsController.class);
        startActivityForResult(intent, SELECT_ICON_EVENT);
    }

    public void onCreateModClicked() {
        String categoryName = view.getCategoryName();
        if (categoryName == null || categoryName.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show();
        } else {
            final ModDAO modDAO = daoFor(Mod.class);
            final IconDAO iconDAO = daoFor(Icon.class);
            Mod mod = new Mod();
            mod.setIcon(iconId == -1 ? null : iconDAO.queryForId(iconId));
            mod.setName(categoryName);
            try {
                if (modDAO.selectBy(asList(Category.FIELD_NAME), categoryName) != null) {
                    throw new Exception("Мод уже существует!");
                }
                modDAO.create(mod);
                Toast.makeText(this, "Мод создан!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                Utils.err("Error during create mod: " + e.getMessage());
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
        final IconDAO iconDAO = daoFor(Icon.class);
        this.iconId = iconId;
        Icon icon = (iconId == -1 ? null : iconDAO.queryForId(iconId));
        view.setCategoryIcon(icon == null ? null : Utils.bytes2bitmap(icon.getIcon()));
    }
}