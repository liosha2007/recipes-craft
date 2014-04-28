package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.library.interfaces.IBackPressed;
import com.github.liosha2007.android.recipes.craft.ApplicationActivity;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.fragment.CategoriesFragment;
import com.github.liosha2007.android.recipes.craft.fragment.CategoryFragment;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class CategoryController extends BaseController<CategoryFragment> {
    protected List<Item> items;

    public CategoryController(List<Item> items) {
        this.items = items;
    }

    @Override
    public void onShow() {
        // Update back pressed
        ApplicationActivity.setBackPressed(new IBackPressed() {
            @Override
            public boolean onBackPressed() {
                FragmentManager.adapter.removeFragment(Fragments.CATEGORY_FRAGMENT);
                FragmentManager.adapter.setCurrentItem(Fragments.CATEGORIES_FRAGMENT);
                return true;
            }
        });
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        fragment.clearCategoryItems();
        fragment.showCategoryItems(items);
    }
}
