package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;
import com.github.liosha2007.android.recipes.craft.fragment.RecipeView;

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
    public static final String TAB_RECIPE = UUID.randomUUID().toString();
    public static final String TAB_DESCRIPTION = UUID.randomUUID().toString();
    public static final String TAB_MATERIALS = UUID.randomUUID().toString();
    public static final String TAB_NOTES = UUID.randomUUID().toString();
    private int itemId;

    @Override
    public void onCreate() {
        super.onCreate();
        // Restore parameters
        Bundle bundle = getIntent().getExtras();
        this.itemId = bundle.getInt(ITEM_ID, -1);

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
            view.showAdditionalItemInformation(item);
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
