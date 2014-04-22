package com.github.liosha2007.android.recipes.craft.fragment;

import android.view.View;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.BaseFragment;
import com.github.liosha2007.android.recipes.craft.controller.DashboardController;

/**
 * Created by liosha on 21.04.2014.
 */
public class DashboardFragment extends BaseFragment {
    public DashboardFragment() {
        super(R.layout.layout_dashboard, new DashboardController());
    }

    @Override
    public void onViewCreated(View view) {
        // TODO: Initialize here
        return;
    }
}
