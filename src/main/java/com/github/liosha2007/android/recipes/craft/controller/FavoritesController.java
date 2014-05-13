package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.github.liosha2007.android.recipes.craft.fragment.FavoritesView;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class FavoritesController extends BaseActivityController<FavoritesView> {
    public FavoritesController() {
        super(new FavoritesView());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //
        List<Favorite> favorites = DBHelper.getFavoriteDAO().getAllFavorites();
        view.clearFavorites();
        for (Favorite favorite : favorites) {
            DBHelper.getItemDAO().refresh(favorite.getItem());
        }
        view.showFavorites(favorites);
    }

    public void onFavoriteClicked(Integer itemId) {
        if (itemId != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(RecipeController.ITEM_ID, itemId);
            run(RecipeController.class, bundle);
        } else {
            Utils.deb("itemId is null when try to load and show it from favorites");
        }
    }
}
