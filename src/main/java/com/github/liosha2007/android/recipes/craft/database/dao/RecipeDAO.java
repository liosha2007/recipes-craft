package com.github.liosha2007.android.recipes.craft.database.dao;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by liosha on 24.04.2014.
 */
public class RecipeDAO extends BaseDaoImpl<Recipe, Integer> {
    public RecipeDAO(ConnectionSource connectionSource, Class<Recipe> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Recipe> getAllRecipes() {
        try {
            return this.queryForAll();
        } catch (Exception e) {
            Utils.err("error getting all recipes: " + e.getMessage());
        }
        return null;
    }

    public List<Recipe> getAllRecipesForItemId(Integer itemId) {
        try {
            return queryForEq(Recipe.FIELD_RESULT, itemId);
        } catch (Exception e) {
            Utils.err("Can't load recipes for item with id " + itemId + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public int deleteById(Integer recipeId) {
        try {
            return super.deleteById(recipeId);
        } catch (Exception e) {
            Utils.err("Can't delete recipe by id " + recipeId + ": " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int create(Recipe recipe) {
        try {
            return super.create(recipe);
        } catch (Exception e) {
            Utils.err("Can't create recipe with id " + recipe.getId() + ": " + e.getMessage());
        }
        return 0;
    }
}
