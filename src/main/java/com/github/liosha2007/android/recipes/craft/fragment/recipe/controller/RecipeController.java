package com.github.liosha2007.android.recipes.craft.fragment.recipe.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.ContextThemeWrapper;
import android.widget.Button;
import android.widget.Toast;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.fragment.controller.BaseFragmentController;
import com.github.liosha2007.android.recipes.craft.common.Constants;
import com.github.liosha2007.android.recipes.craft.controller.ItemsController;
import com.github.liosha2007.android.recipes.craft.database.dao.FavoriteDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.RecipeDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;
import com.github.liosha2007.android.recipes.craft.fragment.recipe.view.RecipeFragment;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author liosha on 13.05.2014.
 */
public class RecipeController extends BaseFragmentController<RecipeFragment> {
    protected int itemId;
    protected boolean editMode = false;

    public RecipeController() {
        super(new RecipeFragment());
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        editMode = preferences.getBoolean(Constants.ADD_DELETE_RECIPES, false);

        // Restore parameters
        this.itemId = getArguments().getInt(com.github.liosha2007.android.recipes.craft.controller.RecipeController.ITEM_ID, -1);

        final ItemDAO itemDAO = daoFor(Item.class);
        final RecipeDAO recipeDAO = daoFor(Recipe.class);
        Item item = itemDAO.queryForId(itemId);
        List<Recipe> recipes = recipeDAO.getAllRecipesForItemId(item.getId());
        for (Recipe recipe : recipes) {
            itemDAO.refresh(recipe.getP1x1());
            itemDAO.refresh(recipe.getP1x2());
            itemDAO.refresh(recipe.getP1x3());
            itemDAO.refresh(recipe.getP2x1());
            itemDAO.refresh(recipe.getP2x2());
            itemDAO.refresh(recipe.getP2x3());
            itemDAO.refresh(recipe.getP3x1());
            itemDAO.refresh(recipe.getP3x2());
            itemDAO.refresh(recipe.getP3x3());
            itemDAO.refresh(recipe.getResult());
        }
        view.createAccordion(item, recipes, editMode);
        if (editMode) {
            view.createRecipeControls();
        }
        if (recipes.size() == 0) {
            view.showRecipeNotFound();
        }
    }

    public void onAccordionClicked(Button button) {
        view.switchAccordion(button);
    }

    /**
     * Нажата кнопка удаления рецепта
     *
     * @param recipeId
     */
    public void onDeleteClicked(final int recipeId) {
        new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom))
                .setTitle("Удаление")
                .setMessage("Вы действительно хотите удалить?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        final RecipeDAO recipeDAO = daoFor(Recipe.class);
                        recipeDAO.deleteById(recipeId);
                        Toast.makeText(getActivity(), "Рецепт удален!", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                }).create().show();
    }

    public void onCreateClicked() {
        view.showCreateRecipePopup();
    }

    /**
     * Клик по ячейке выбора предмета для создания предмета
     *
     * @param imageViewId
     */
    public void onCraftingPointSelected(int imageViewId) {
        Intent intent = new Intent(this.getActivity(), ItemsController.class);
        intent.putExtra(ItemsController.CREATE_RECIPE_TITLE, "Выберите предмет");
        intent.putExtra(ItemsController.CREATE_RECIPE_IMAGE_VIEW_ID, imageViewId);
        startActivityForResult(intent, 1);
    }

    /**
     * Коллбэк при выборе предмета для ячейки рецепта
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data != null) {
            final ItemDAO itemDAO = daoFor(Item.class);
            int imageViewId = data.getIntExtra(ItemsController.CREATE_RECIPE_IMAGE_VIEW_ID, -1);
            int itemId = data.getIntExtra(ItemsController.CREATE_RECIPE_RESULT_ITEM_ID, -1);
            Item item = null;
            if (itemId != -1) {
                item = itemDAO.queryForId(itemId);
            }
            view.showNewRecipeItem(item, imageViewId);
        }
    }

    public void onAddRecipeClicked(
            Integer p1x1, Integer p1x2, Integer p1x3,
            Integer p2x1, Integer p2x2, Integer p2x3,
            Integer p3x1, Integer p3x2, Integer p3x3) {
        final ItemDAO itemDAO = daoFor(Item.class);
        Item resultItem = itemDAO.queryForId(itemId);

        if (
                p1x1 == null &&
                        p1x2 == null &&
                        p1x3 == null &&
                        p2x1 == null &&
                        p2x2 == null &&
                        p2x3 == null &&
                        p3x1 == null &&
                        p3x2 == null &&
                        p3x3 == null) {
            return;
        }

        Recipe recipe = new Recipe();
        recipe.setResult(resultItem);

        if (p1x1 != null) {
            recipe.setP1x1(itemDAO.queryForId(p1x1));
        }
        if (p1x2 != null) {
            recipe.setP1x2(itemDAO.queryForId(p1x2));
        }
        if (p1x3 != null) {
            recipe.setP1x3(itemDAO.queryForId(p1x3));
        }

        if (p2x1 != null) {
            recipe.setP2x1(itemDAO.queryForId(p2x1));
        }
        if (p2x2 != null) {
            recipe.setP2x2(itemDAO.queryForId(p2x2));
        }
        if (p2x3 != null) {
            recipe.setP2x3(itemDAO.queryForId(p2x3));
        }

        if (p3x1 != null) {
            recipe.setP3x1(itemDAO.queryForId(p3x1));
        }
        if (p3x2 != null) {
            recipe.setP3x2(itemDAO.queryForId(p3x2));
        }
        if (p3x3 != null) {
            recipe.setP3x3(itemDAO.queryForId(p3x3));
        }

        final RecipeDAO recipeDAO = daoFor(Recipe.class);
        recipeDAO.create(recipe);
        Toast.makeText(getActivity(), "Рецепт для '" + resultItem.getName() + "' создан!", Toast.LENGTH_LONG).show();
        getActivity().finish();
    }
}
