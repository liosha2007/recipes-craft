package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;
import android.widget.EditText;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.view.DashboardView;

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
        run(FavoritesController.class);
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
}
