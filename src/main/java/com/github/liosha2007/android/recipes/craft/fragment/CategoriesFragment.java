package com.github.liosha2007.android.recipes.craft.fragment;

import android.view.View;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.BaseFragment;
import com.github.liosha2007.android.recipes.craft.controller.CategoriesController;

/**
 * Created by liosha on 21.04.2014.
 */
public class CategoriesFragment extends BaseFragment<CategoriesController> {
    public CategoriesFragment() {
        super(R.layout.layout_categories, new CategoriesController());
    }

    @Override
    public void onViewCreated(View view) {
        // TODO: Initialize here
        return;
    }
}
