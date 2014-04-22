package com.github.liosha2007.android.recipes.craft.fragment;

import android.view.View;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.BaseFragment;
import com.github.liosha2007.android.recipes.craft.controller.RecipeController;

/**
 * Created by liosha on 22.04.2014.
 */
public class RecipeFragment extends BaseFragment<RecipeController> {
    public RecipeFragment() {
        super(R.layout.layout_recipe, new RecipeController());
    }

    @Override
    public void onViewCreated(View view) {

    }
}
