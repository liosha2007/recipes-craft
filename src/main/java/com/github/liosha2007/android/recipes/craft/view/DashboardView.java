package com.github.liosha2007.android.recipes.craft.view;

import android.view.View;
import android.widget.EditText;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.recipes.craft.controller.DashboardController;

/**
 * Created by liosha on 21.04.2014.
 */
public class DashboardView extends BaseActivityView<DashboardController> {
    public DashboardView() {
        super(R.layout.layout_dashboard);
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
                controller.onSearchClick(DashboardView.this.<EditText>view(R.id.search_text).getText().toString());
            }
        });
        view(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onSettingsClicked();
            }
        });
        view(R.id.app_version).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onVersionClicked();
            }
        });
    }
}
