package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.library.fragment.FragmentManager;
import com.github.liosha2007.android.library.interfaces.IBackPressed;
import com.github.liosha2007.android.recipes.craft.ApplicationActivity;
import com.github.liosha2007.android.recipes.craft.common.Fragments;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;
import com.github.liosha2007.android.recipes.craft.fragment.RecipeFragment;

import java.util.List;
import java.util.UUID;

/**
 * Created by liosha on 22.04.2014.
 */
public class RecipeController extends BaseController<RecipeFragment> {
    public static final String TAB_RECIPE = UUID.randomUUID().toString();
    public static final String TAB_DESCRIPTION = UUID.randomUUID().toString();
    public static final String TAB_MATERIALS = UUID.randomUUID().toString();
    public static final String TAB_NOTES = UUID.randomUUID().toString();
    private final IBackPressed backPressed;
    private int itemId;

    public RecipeController(int itemId, IBackPressed backPressed) {
        super(new RecipeFragment());
        this.itemId = itemId;
        this.backPressed = backPressed;
    }

    @Override
    public void onShow() {
        // Back pressed
        ApplicationActivity.setBackPressed(backPressed);
    }

    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        Item item = DBHelper.getItemDAO().queryForId(itemId);
        List<Recipe> recipes = DBHelper.getRecipeDAO().getAllRecipesForItemId(item.getId());
        for (Recipe recipe : recipes) {
            DBHelper.getItemDAO().refresh(recipe.getP1x1());
            DBHelper.getItemDAO().refresh(recipe.getP1x2());
            DBHelper.getItemDAO().refresh(recipe.getP1x3());
            DBHelper.getItemDAO().refresh(recipe.getP2x1());
            DBHelper.getItemDAO().refresh(recipe.getP2x2());
            DBHelper.getItemDAO().refresh(recipe.getP2x3());
            DBHelper.getItemDAO().refresh(recipe.getP3x1());
            DBHelper.getItemDAO().refresh(recipe.getP3x2());
            DBHelper.getItemDAO().refresh(recipe.getP3x3());
            DBHelper.getItemDAO().refresh(recipe.getResult());
            fragment.createAccordion(recipe.getResult(), recipes);
        }
        if (recipes.size() == 0) {
            fragment.showRecipeNotFound();
            fragment.showAdditionalItemInformation(item);
        }
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
