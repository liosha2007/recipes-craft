package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.manager.FragmentManager;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;
import com.github.liosha2007.android.recipes.craft.view.RecipeView;

import java.util.List;
import java.util.UUID;

/**
 * Created by liosha on 22.04.2014.
 */
public class RecipeController extends BaseActivityController<RecipeView> {
    public static final String ITEM_ID = "itemId";

    public RecipeController() {
        super(new RecipeView());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void onViewPagerCreated(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putInt(ITEM_ID, getIntent().getExtras().getInt(ITEM_ID, -1));
        FragmentManager fragmentManager = FragmentManager.prepareViewPager(
                viewPager,
                this,
                new com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.RecipeController().withArguments(bundle),
                new com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.DescriptionController().withArguments(bundle),
                new com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.MaterialsController().withArguments(bundle),
                new com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.NotesController().withArguments(bundle)
        );
    }

    public void onRecipeTabSelected() {

    }

    public void onDescriptionTabSelected() {

    }

    public void onMaterialsTabSelected() {

    }

    public void onNotesTabSelected() {

    }
}
