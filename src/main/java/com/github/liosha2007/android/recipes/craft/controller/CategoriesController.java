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
import com.github.liosha2007.android.recipes.craft.fragment.CategoriesFragment;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class CategoriesController extends BaseController<CategoriesFragment> {

    public CategoriesController() {
        super(new CategoriesFragment());
    }

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
        if (categoryId != null) {
            FragmentManager.adapter.addFragment(Fragments.CATEGORY_FRAGMENT, new CategoryController(categoryId).getFragment());
            FragmentManager.adapter.setCurrentItem(Fragments.CATEGORY_FRAGMENT);
        } else {
            Utils.deb("categoryId is null");
        }
    }
}
