package com.github.liosha2007.android.recipes.craft.controller;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.github.liosha2007.android.recipes.craft.fragment.FavoritesView;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class FavoritesController extends BaseController<FavoritesView> {
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
}
