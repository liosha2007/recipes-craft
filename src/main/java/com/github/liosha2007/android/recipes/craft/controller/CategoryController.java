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
import com.github.liosha2007.android.recipes.craft.fragment.CategoryFragment;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class CategoryController extends BaseController<CategoryFragment> {
    protected int categoryId;

    public CategoryController(int categoryId) {
        super(new CategoryFragment());
        this.categoryId = categoryId;
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
        Category category = DBHelper.getCategoryDAO().queryForId(categoryId);
        if (category != null) {
            List<Item> items = DBHelper.getItemDAO().queryForEq(Item.FIELD_CATEGORY, category);
            fragment.showCategoryItems(items);
        } else {
            Utils.deb("category is null when try to load it");
        }
    }

    public void onItemClicked(Integer itemId) {
        if (itemId != null) {
            FragmentManager.adapter.addFragment(Fragments.RECIPE_FRAGMENT, new RecipeController(itemId, new IBackPressed() {
                @Override
                public boolean onBackPressed() {
                    FragmentManager.adapter.removeFragment(Fragments.RECIPE_FRAGMENT);
                    FragmentManager.adapter.setCurrentItem(Fragments.CATEGORY_FRAGMENT);
                    return true;
                }
            }).getFragment());
            FragmentManager.adapter.setCurrentItem(Fragments.RECIPE_FRAGMENT);
        } else {
            Utils.deb("itemId is null when try to load and show it");
        }
    }
}
