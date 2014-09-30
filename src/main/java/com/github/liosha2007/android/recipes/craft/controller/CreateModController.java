package com.github.liosha2007.android.recipes.craft.controller;

import android.content.Intent;
import android.widget.Toast;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;
import com.github.liosha2007.android.recipes.craft.view.CreateCategoryView;
import com.github.liosha2007.android.recipes.craft.view.CreateModView;

import java.sql.SQLException;

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
        if (categoryName == null || categoryName.isEmpty()){
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_LONG).show();
        } else {
            Mod mod = new Mod();
            mod.setIcon(iconId == -1 ? null : DBHelper.getIconDAO().queryForId(iconId));
            mod.setName(categoryName);
            try {
                DBHelper.getModDAO().create(mod);
                Toast.makeText(this, "Мод создан!", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(this, "Ошибка при создании мода!", Toast.LENGTH_LONG).show();
                Utils.err("Error during create category: " + e.getMessage());
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
}