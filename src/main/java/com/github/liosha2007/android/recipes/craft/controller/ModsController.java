package com.github.liosha2007.android.recipes.craft.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.common.Constants;
import com.github.liosha2007.android.recipes.craft.database.dao.IconDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ModDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;
import com.github.liosha2007.android.recipes.craft.view.ModsView;

import java.sql.SQLException;
import java.util.Arrays;
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
        showMods();
    }

    private void showMods() {
        final ModDAO modDAO = daoFor(Mod.class);
        final IconDAO iconDAO = daoFor(Icon.class);
        //
        List<Mod> mods = modDAO.getAllMods();
        view.clearMods();
        for (Mod category : mods) {
            try {
                iconDAO.refresh(category.getIcon());
            } catch (SQLException e) {
                Utils.err("Can't refresh icon for mod: " + e.getMessage());
            }
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean editMode = preferences.getBoolean(Constants.ADD_DELETE_RECIPES, false);

        view.showMods(mods, editMode);
        if (mods.size() == 0) {
            view.showNotFound();
        }
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

    public void onDeleteClicked(final int modId) {
        new AlertDialog.Builder(this)
                .setTitle("Удаление")
                .setMessage("Вы действительно хотите удалить?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        final ItemDAO itemDAO = daoFor(Item.class);
                        final ModDAO modDAO = daoFor(Mod.class);
                        try {
                            if (itemDAO.selectBy(Arrays.asList(Item.FIELD_CATEGORY), modId) != null) {
                                Toast.makeText(ModsController.this, "Мод используется!", Toast.LENGTH_LONG).show();
                            } else {
                                modDAO.delete(modDAO.queryForId(modId));
                                Toast.makeText(ModsController.this, "Мод удален!", Toast.LENGTH_LONG).show();
                                showMods();
                            }
                        } catch (SQLException e) {
                            Toast.makeText(ModsController.this, "Не удалось удалить мод!", Toast.LENGTH_LONG).show();
                            Utils.err("Can't delete mod: " + e.getMessage());
                        }
                    }
                }).create().show();
    }
}
