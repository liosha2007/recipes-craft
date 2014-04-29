package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.library.interfaces.IBackPressed;
import com.github.liosha2007.android.recipes.craft.ApplicationActivity;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.fragment.DashboardFragment;

/**
 * Created by liosha on 22.04.2014.
 */
public class DashboardController extends BaseController<DashboardFragment> {

    public DashboardController() {
        super(new DashboardFragment());
    }

    @Override
    public void onShow() {
        // Update back pressed
        ApplicationActivity.setBackPressed(new IBackPressed() {
            @Override
            public boolean onBackPressed() {
                ApplicationActivity.activity.finish();
                return true;
            }
        });
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
    }

    public void onItemsClicked() {
        FragmentManager.adapter.setCurrentItem(Fragments.ITEMS_FRAGMENT);
    }

    public void onCategoriesClicked() {
        FragmentManager.adapter.setCurrentItem(Fragments.CATEGORIES_FRAGMENT);
    }

    public void onModsClicked() {
        FragmentManager.adapter.setCurrentItem(Fragments.MODS_FRAGMENT);
    }

    public void onFavoritesClicked() {
        FragmentManager.adapter.setCurrentItem(Fragments.FAVORITES_FRAGMENT);
    }

    public void onSearchClick() {

    }
}
