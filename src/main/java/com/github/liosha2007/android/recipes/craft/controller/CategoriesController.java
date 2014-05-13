package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.github.liosha2007.android.recipes.craft.view.CategoriesView;

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
        List<Category> items = DBHelper.getCategoryDAO().getAllCategories();
        view.clearCategories();
        view.showCategories(items);
    }

    public void onCategoryClicked(final Integer categoryId) {
        if (categoryId != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(CategoryController.CATEGORY_ID, categoryId);
            run(CategoryController.class, bundle);
        } else {
            Utils.deb("categoryId is null");
        }
    }
}
