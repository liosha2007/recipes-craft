package com.github.liosha2007.android.recipes.craft;

import android.os.Bundle;
import android.view.Menu;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.fragment.CategoriesFragment;
import com.github.liosha2007.android.recipes.craft.fragment.DashboardFragment;
import com.github.liosha2007.android.recipes.craft.fragment.FavoritesFragment;
import com.github.liosha2007.android.recipes.craft.fragment.ItemsFragment;
import com.github.liosha2007.android.recipes.craft.fragment.ModsFragment;
import com.github.liosha2007.android.recipes.craft.fragment.SearchFragment;
import com.github.liosha2007.android.recipes.craft.fragment.SettingsFragment;

/**
 * Created by liosha on 21.04.2014.
 */
public class ApplicationActivity extends com.github.liosha2007.android.library.application.ApplicationActivity {
    public ApplicationActivity() {
        super(R.layout.layout_main, R.id.viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onFragmentCreate(FragmentManager adapter) {
        adapter.addFragment(Fragments.FAVORITES_FRAGMENT, new FavoritesFragment());
        adapter.addFragment(Fragments.DASHBOARD_FRAGMENT, new DashboardFragment());
        adapter.addFragment(Fragments.CATEGORIES_FRAGMENT, new CategoriesFragment());
        adapter.addFragment(Fragments.ITEMS_FRAGMENT, new ItemsFragment());
        adapter.addFragment(Fragments.MODS_FRAGMENT, new ModsFragment());
        adapter.addFragment(Fragments.SEARCH_FRAGMENT, new SearchFragment());
//        adapter.addFragment(Fragments.SETTINGS_FRAGMENT, new SettingsFragment());

        adapter.setCurrentItem(Fragments.DASHBOARD_FRAGMENT);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
}
