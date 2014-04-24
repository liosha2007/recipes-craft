package com.github.liosha2007.android.recipes.craft.fragment;

import android.content.Context;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.application.ApplicationActivity;
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
        TabHost tabHost = view(R.id.recipe_tab_host);

        tabHost.setup();

        TabHost.TabSpec spec;

        spec = tabHost.newTabSpec(RecipeController.TAB_RECIPE);
        spec.setContent(R.id.recipe_tab_recipe);
        spec.setIndicator("Рецепты");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(RecipeController.TAB_DESCRIPTION);
        spec.setContent(R.id.recipe_tab_description);
        spec.setIndicator("Описание");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(RecipeController.TAB_MATERIALS);
        spec.setContent(R.id.recipe_tab_materials);
        spec.setIndicator("Материалы");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(RecipeController.TAB_NOTES);
        spec.setContent(R.id.recipe_tab_notes);
        spec.setIndicator("Примечание");
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (RecipeController.TAB_RECIPE.equalsIgnoreCase(tabId)) {
                    controller.onRecipeTabSelected();
                } else if (RecipeController.TAB_DESCRIPTION.equalsIgnoreCase(tabId)) {
                    controller.onDescriptionTabSelected();
                } else if (RecipeController.TAB_MATERIALS.equalsIgnoreCase(tabId)) {
                    controller.onMaterialsTabSelected();
                } else if (RecipeController.TAB_NOTES.equalsIgnoreCase(tabId)) {
                    controller.onNotesTabSelected();
                }
            }
        });
    }
}
