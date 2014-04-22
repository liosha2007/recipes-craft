package com.github.liosha2007.android.recipes.craft.fragment;

import android.view.View;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.BaseFragment;
import com.github.liosha2007.android.recipes.craft.controller.FavoritesController;

/**
 * Created by liosha on 22.04.2014.
 */
public class FavoritesFragment extends BaseFragment<FavoritesController> {
    public FavoritesFragment() {
        super(R.layout.layout_favorites, new FavoritesController());
    }

    @Override
    public void onViewCreated(View view) {

    }
}
