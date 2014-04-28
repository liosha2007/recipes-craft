package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.common.Utils;
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
public class CategoriesController extends BaseController<CategoriesFragment> {

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
        List<Category> items = DBHelper.getCategoryDAO().getAllCategories();
        fragment.clearCategories();
        fragment.showCategories(items);
    }

    public void onCategoryClicked(Integer categoryId) {
        Category category = DBHelper.getCategoryDAO().queryForId(categoryId);
        if (category != null) {
            List<Item> items = DBHelper.getItemDAO().queryForEq(Item.FIELD_CATEGORY, category);

            FragmentManager.adapter.addFragment(Fragments.CATEGORY_FRAGMENT, new CategoryFragment(items));
            FragmentManager.adapter.setCurrentItem(Fragments.CATEGORY_FRAGMENT);
        } else {
            Utils.deb("category is null when try to load it and display associated items");
        }
    }
}
