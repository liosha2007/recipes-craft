package com.github.liosha2007.android.recipes.craft.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.common.Constants;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.view.DashboardView;

import java.io.File;

/**
 * Created by liosha on 22.04.2014.
 */
public class DashboardController extends BaseActivityController<DashboardView> {
    public DashboardController() {
        super(new DashboardView());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.setHelper(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        this.finish();
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
        if (Utils.isNullOrBlank(searchText)){
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(ItemsController.SEARCH_TEXT, searchText);
        bundle.putString(ItemsController.SEARCH_TITLE, "Поиск: '" + searchText + "'");
        run(ItemsController.class, bundle);
    }

    @Override
    protected void onDestroy() {
        DBHelper.releaseHelper();
        super.onDestroy();
    }

    public void onSettingsClicked() {
        run(SettingsController.class);
    }

    public void onVersionClicked() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean addDeleteMode = preferences.getBoolean(Constants.ADD_DELETE_RECIPES, false);
        // Export database file
        if (addDeleteMode) {
            File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (DBHelper.exportDatabaseFileTo(this, downloadsDirectory.getAbsolutePath())) {
                Toast.makeText(this, "Database exported to '" + downloadsDirectory.getAbsolutePath() + "'!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
