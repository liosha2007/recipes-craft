package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;
import com.github.liosha2007.android.recipes.craft.view.ModsView;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ModsController extends BaseActivityController<ModsView> {
    public ModsController() {
        super(new ModsView());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //
        List<Mod> mods = DBHelper.getModDAO().getAllMods();
        view.clearMods();
        for (Mod category : mods){
            try {
                DBHelper.getIconDAO().refresh(category.getIcon());
            } catch (SQLException e) {
                Utils.err("Can't refresh icon for mod: " + e.getMessage());
            }
        }
        view.showMods(mods);
    }

    public void onModClicked(Integer modId, String title) {
        if (modId != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(ItemsController.MOD_ID, modId);
            bundle.putString(ItemsController.MOD_TITLE, title);
            run(ItemsController.class, bundle);
        } else {
            Utils.deb("modId is null when try to load and show it");
        }
    }
}
