package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.library.interfaces.IBackPressed;
import com.github.liosha2007.android.recipes.craft.ApplicationActivity;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.fragment.ItemsFragment;
import com.github.liosha2007.android.recipes.craft.fragment.RecipeFragment;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ItemsController extends BaseController<ItemsFragment> {

    @Override
    public void onShow() {
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
        List<Item> items = DBHelper.getItemDAO().getAllItems();
        fragment.clearItems();
        fragment.showItems(items);
    }

    public void onItemClicked(Integer itemId) {
        Item item = DBHelper.getItemDAO().queryForId(itemId);
        if (item != null) {
            FragmentManager.adapter.addFragment(Fragments.RECIPE_FRAGMENT, new RecipeFragment(item));
            FragmentManager.adapter.setCurrentItem(Fragments.RECIPE_FRAGMENT);
        } else {
            Utils.deb("item is null when try to load and show it");
        }
    }
}
