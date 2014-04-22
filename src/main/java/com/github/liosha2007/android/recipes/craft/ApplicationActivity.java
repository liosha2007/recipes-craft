package com.github.liosha2007.android.recipes.craft;

import android.os.Bundle;
import android.view.Menu;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.fragment.CategoriesFragment;
import com.github.liosha2007.android.recipes.craft.fragment.DashboardFragment;

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
        adapter.addFragment(Fragments.DASHBOARD_FRAGMENT, new DashboardFragment());
        adapter.addFragment(Fragments.CATEGORIES_FRAGMENT, new CategoriesFragment());
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
}
