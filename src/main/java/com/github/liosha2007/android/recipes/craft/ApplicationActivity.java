package com.github.liosha2007.android.recipes.craft;

import android.os.Bundle;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.controller.CategoriesController;
import com.github.liosha2007.android.recipes.craft.controller.DashboardController;
import com.github.liosha2007.android.recipes.craft.controller.FavoritesController;
import com.github.liosha2007.android.recipes.craft.controller.ItemsController;
import com.github.liosha2007.android.recipes.craft.controller.ModsController;
import com.github.liosha2007.android.recipes.craft.controller.SearchController;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;

/**
 * Created by liosha on 21.04.2014.
 */
public class ApplicationActivity extends com.github.liosha2007.android.library.application.ApplicationActivity {
    public ApplicationActivity() {
        super(R.layout.layout_main, R.id.viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBHelper.setHelper(getApplicationContext());
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onFragmentCreate(FragmentManager adapter) {
        adapter.addFragment(Fragments.FAVORITES_FRAGMENT, new FavoritesController().getFragment());
        adapter.addFragment(Fragments.DASHBOARD_FRAGMENT, new DashboardController().getFragment());
        adapter.addFragment(Fragments.ITEMS_FRAGMENT, new ItemsController().getFragment());
        adapter.addFragment(Fragments.CATEGORIES_FRAGMENT, new CategoriesController().getFragment());
        adapter.addFragment(Fragments.MODS_FRAGMENT, new ModsController().getFragment());
        adapter.addFragment(Fragments.SEARCH_FRAGMENT, new SearchController().getFragment());
//        adapter.addFragment(Fragments.SETTINGS_FRAGMENT, new SettingsController().getFragment());

        adapter.setCurrentItem(Fragments.DASHBOARD_FRAGMENT);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    protected void onDestroy() {
        DBHelper.releaseHelper();
        super.onDestroy();
    }
}
