package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.fragment.DashboardFragment;

/**
 * Created by liosha on 22.04.2014.
 */
public class DashboardController extends BaseController<DashboardFragment> {
    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        // TODO: Initialize here
        return;
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
}
