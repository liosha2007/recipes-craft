package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.library.interfaces.IBackPressed;
import com.github.liosha2007.android.recipes.craft.ApplicationActivity;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;
import com.github.liosha2007.android.recipes.craft.fragment.ModsFragment;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ModsController extends BaseController<ModsFragment> {
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
        //
        List<Mod> items = DBHelper.getModDAO().getAllMods();
        fragment.clearMods();
        fragment.showMods(items);
    }
}
