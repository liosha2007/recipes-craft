package com.github.liosha2007.android.recipes.craft;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.fragment.CategoriesFragment;
import com.github.liosha2007.android.recipes.craft.fragment.DashboardFragment;
import com.github.liosha2007.android.recipes.craft.fragment.FavoritesFragment;
import com.github.liosha2007.android.recipes.craft.fragment.ItemsFragment;
import com.github.liosha2007.android.recipes.craft.fragment.ModsFragment;
import com.github.liosha2007.android.recipes.craft.fragment.SearchFragment;
import com.github.liosha2007.android.recipes.craft.fragment.SettingsFragment;

import java.io.InputStream;

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
        adapter.addFragment(Fragments.FAVORITES_FRAGMENT, new FavoritesFragment());
        adapter.addFragment(Fragments.DASHBOARD_FRAGMENT, new DashboardFragment());
        adapter.addFragment(Fragments.ITEMS_FRAGMENT, new ItemsFragment());
        adapter.addFragment(Fragments.CATEGORIES_FRAGMENT, new CategoriesFragment());
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

    @Override
    protected void onDestroy() {
        DBHelper.releaseHelper();
        super.onDestroy();
    }

    public static Drawable loadImageFromAssets(String assetsPath){
        InputStream inputStream = null;
        try {
            // get input stream
            inputStream = activity.getAssets().open(assetsPath);
            // load image as Drawable
            return Drawable.createFromStream(inputStream, null);
        } catch (Exception e) {
            Utils.err(e.getMessage());
        } finally {
            Utils.closeStreams(inputStream);
        }
        return null;
    }
}
