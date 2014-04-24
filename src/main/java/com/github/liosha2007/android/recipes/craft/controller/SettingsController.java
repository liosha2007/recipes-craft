package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.library.interfaces.IBackPressed;
import com.github.liosha2007.android.recipes.craft.ApplicationActivity;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.fragment.SettingsFragment;

/**
 * Created by liosha on 22.04.2014.
 */
public class SettingsController extends BaseController<SettingsFragment> {
    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        // Update back pressed
        ApplicationActivity.setBackPressed(new IBackPressed() {
            @Override
            public boolean onBackPressed() {
                FragmentManager.adapter.setCurrentItem(Fragments.DASHBOARD_FRAGMENT);
                return true;
            }
        });

    }
}
