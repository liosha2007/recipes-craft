package com.github.liosha2007.android.recipes.craft.fragment;

import android.view.View;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.BaseFragment;
import com.github.liosha2007.android.recipes.craft.controller.ItemsController;

/**
 * Created by liosha on 22.04.2014.
 */
public class ItemsFragment extends BaseFragment<ItemsController> {
    public ItemsFragment() {
        super(R.layout.layout_items, new ItemsController());
    }

    @Override
    public void onViewCreated(View view) {

    }
}
