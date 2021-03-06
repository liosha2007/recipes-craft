package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.view.DashboardView;

/**
 * Created by liosha on 22.04.2014.
 */
public class DashboardController extends BaseActivityController<DashboardView> {
    public DashboardController() {
        super(new DashboardView());
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    public void onItemsClicked() {
        run(ItemsController.class);
    }

    public void onCategoriesClicked() {
        run(CategoriesController.class);
    }

    public void onModsClicked() {
        run(ModsController.class);
    }

    public void onFavoritesClicked() {
        Bundle bundle = new Bundle();
        bundle.putString(ItemsController.FAVORITES_TITLE, "Избранное");
        run(ItemsController.class, bundle);
    }

    public void onSearchClick(String searchText) {
        if (Utils.isNullOrBlank(searchText)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(ItemsController.SEARCH_TEXT, searchText);
        bundle.putString(ItemsController.SEARCH_TITLE, "Поиск: '" + searchText + "'");
        run(ItemsController.class, bundle);
    }

    public void onSettingsClicked() {
        run(SettingsController.class);
    }

    public void onVersionClicked() {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean editMode = preferences.getBoolean(Constants.ADD_DELETE_RECIPES, false);
//        // Export database file
//        if (editMode) {
//            File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            if (DBHelper.exportDatabaseFileTo(this, downloadsDirectory.getAbsolutePath())) {
//                Toast.makeText(this, "Database exported to '" + downloadsDirectory.getAbsolutePath() + "'!", Toast.LENGTH_LONG).show();
//            }
//        }
    }
}
