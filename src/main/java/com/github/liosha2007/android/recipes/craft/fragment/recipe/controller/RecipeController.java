package com.github.liosha2007.android.recipes.craft.fragment.recipe.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.fragment.controller.BaseFragmentController;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;
import com.github.liosha2007.android.recipes.craft.fragment.recipe.view.RecipeFragment;

import java.util.List;

/**
 * Created by liosha on 13.05.2014.
 */
public class RecipeController extends BaseFragmentController<RecipeFragment> {
    protected int itemId;
    public RecipeController() {
        super(new RecipeFragment());
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        // Restore parameters
        this.itemId = getArguments().getInt(com.github.liosha2007.android.recipes.craft.controller.RecipeController.ITEM_ID, -1);

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
            view.createAccordion(recipe.getResult(), recipes);
        }
        if (recipes.size() == 0) {
            view.showRecipeNotFound();
        }
    }
}
