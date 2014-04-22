package com.github.liosha2007.android.recipes.craft.fragment;

import android.view.View;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.BaseFragment;
import com.github.liosha2007.android.recipes.craft.controller.ModsController;

/**
 * Created by liosha on 22.04.2014.
 */
public class ModsFragment extends BaseFragment<ModsController> {
    public ModsFragment() {
        super(R.layout.layout_mods, new ModsController());
    }

    @Override
    public void onViewCreated(View view) {

    }
}
