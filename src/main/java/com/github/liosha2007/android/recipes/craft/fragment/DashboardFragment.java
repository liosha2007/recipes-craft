package com.github.liosha2007.android.recipes.craft.fragment;

import android.view.View;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.BaseFragment;
import com.github.liosha2007.android.recipes.craft.controller.DashboardController;

/**
 * Created by liosha on 21.04.2014.
 */
public class DashboardFragment extends BaseFragment<DashboardController> {
    public DashboardFragment() {
        super(R.layout.layout_dashboard);
    }

    @Override
    public void onViewCreated(View view) {
        view(R.id.items).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onItemsClicked();
            }
        });
        view(R.id.categories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onCategoriesClicked();
            }
        });
        view(R.id.mods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onModsClicked();
            }
        });
        view(R.id.favorites).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onFavoritesClicked();
            }
        });

        view(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onSearchClick();
            }
        });
    }
}
