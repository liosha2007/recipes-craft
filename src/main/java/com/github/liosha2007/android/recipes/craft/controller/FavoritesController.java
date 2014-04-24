package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.library.interfaces.IBackPressed;
import com.github.liosha2007.android.recipes.craft.ApplicationActivity;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.github.liosha2007.android.recipes.craft.fragment.FavoritesFragment;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class FavoritesController extends BaseController<FavoritesFragment> {

    @Override
    public void onShowed() {
        // Update back pressed
        ApplicationActivity.setBackPressed(new IBackPressed() {
            @Override
            public boolean onBackPressed() {
                FragmentManager.adapter.setCurrentItem(Fragments.DASHBOARD_FRAGMENT);
                return true;
            }
        });
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        //
        List<Favorite> favorites = DBHelper.getFavoriteDAO().getAllFavorites();
        fragment.clearFavorites();
        for (Favorite favorite : favorites){
            DBHelper.getItemDAO().refresh(favorite.getItem());
        }
        fragment.showFavorites(favorites);
    }
}
