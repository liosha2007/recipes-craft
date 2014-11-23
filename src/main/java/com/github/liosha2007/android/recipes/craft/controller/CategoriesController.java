package com.github.liosha2007.android.recipes.craft.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.common.Constants;
import com.github.liosha2007.android.recipes.craft.database.dao.CategoryDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.IconDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.view.CategoriesView;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class CategoriesController extends BaseActivityController<CategoriesView> {
    public static final String CATEGORY_ID = "categoryId";

    public CategoriesController() {
        super(new CategoriesView());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showCategories();
    }

    private void showCategories() {
        final IconDAO iconDAO = daoFor(Icon.class);
        final CategoryDAO categoryDAO = daoFor(Category.class);

        List<Category> categories = categoryDAO.getAllCategories();
        if (categories == null) {
            return;
        }
        if (categories.size() == 0) {
            view.showNotFound();
        }

        view.clearCategories();
        for (Category category : categories) {
            try {
                iconDAO.refresh(category.getIcon());
            } catch (SQLException e) {
                Utils.err("Can't refresh icon for category: " + e.getMessage());
            }
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean editMode = preferences.getBoolean(Constants.ADD_DELETE_RECIPES, false);
        view.showCategories(categories, editMode);
    }

    public void onCategoryClicked(final Integer categoryId) {
        if (categoryId != null) {
            final CategoryDAO categoryDAO = daoFor(Category.class);
            Bundle bundle = new Bundle();
            bundle.putInt(ItemsController.CATEGORY_ID, categoryId);
            bundle.putString(ItemsController.CATEGORY_TITLE, categoryDAO.queryForId(categoryId).getName());
            run(ItemsController.class, bundle);
        } else {
            Utils.deb("categoryId is null");
        }
    }

    public void onDeleteClicked(final Category category) {
        if (category != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Удаление")
                    .setMessage("Вы действительно хотите удалить?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            final CategoryDAO categoryDAO = daoFor(Category.class);
                            final ItemDAO itemDAO = daoFor(Item.class);
                            try {
                                if (itemDAO.selectBy(Arrays.asList(Item.FIELD_CATEGORY), category.getId()) != null) {
                                    Toast.makeText(CategoriesController.this, "Категория не пуста!", Toast.LENGTH_LONG).show();
                                } else {
                                    categoryDAO.delete(category);
                                    Toast.makeText(CategoriesController.this, "Категория удалена!", Toast.LENGTH_LONG).show();
                                    showCategories();
                                }
                            } catch (SQLException e) {
                                Toast.makeText(CategoriesController.this, "Не удалось удалить категорию!", Toast.LENGTH_LONG).show();
                                Utils.err("Can't delete item: " + e.getMessage());
                            }
                        }
                    }).create().show();
        }
    }
}
