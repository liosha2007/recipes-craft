package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.library.interfaces.IBackPressed;
import com.github.liosha2007.android.recipes.craft.ApplicationActivity;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.fragment.RecipeFragment;

import java.util.UUID;

/**
 * Created by liosha on 22.04.2014.
 */
public class RecipeController extends BaseController<RecipeFragment> {
    public static final String TAB_RECIPE = UUID.randomUUID().toString();
    public static final String TAB_DESCRIPTION = UUID.randomUUID().toString();
    public static final String TAB_MATERIALS = UUID.randomUUID().toString();
    public static final String TAB_NOTES = UUID.randomUUID().toString();

    @Override
    public void onShow() {
        // Update back pressed
        ApplicationActivity.setBackPressed(new IBackPressed() {
            @Override
            public boolean onBackPressed() {
                FragmentManager.adapter.removeFragment(Fragments.RECIPE_FRAGMENT);
                FragmentManager.adapter.setCurrentItem(Fragments.DASHBOARD_FRAGMENT);
                return true;
            }
        });
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
    }

    public void onRecipeTabSelected() {

    }

    public void onDescriptionTabSelected() {

    }

    public void onMaterialsTabSelected() {

    }

    public void onNotesTabSelected() {

    }
}
