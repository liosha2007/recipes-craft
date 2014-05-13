package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.view.CategoryView;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class CategoryController extends BaseActivityController<CategoryView> {
    public CategoryController() {
        super(new CategoryView());
    }

    public static final String CATEGORY_ID = "category_id";
    protected int categoryId;

    @Override
    public void onCreate() {
        super.onCreate();
        // Restore parameters
        Bundle storage = getIntent().getExtras();
        this.categoryId = storage.getInt(CATEGORY_ID, -1);

        view.clearCategoryItems();
        Category category = DBHelper.getCategoryDAO().queryForId(categoryId);
        if (category != null) {
            view.showCategoryTitle(category.getName());
            List<Item> items = DBHelper.getItemDAO().queryForEq(Item.FIELD_CATEGORY, category);
            view.showCategoryItems(items);
        } else {
            Utils.deb("category is null when try to load it");
        }
    }

    public void onItemClicked(Integer itemId) {
        if (itemId != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(RecipeController.ITEM_ID, itemId);
            run(RecipeController.class, bundle);
//            FragmentManager.addFragment(Fragments.RECIPE_FRAGMENT, new RecipeController(itemId, new IBackPressed() {
//                @Override
//                public boolean onBackPressed() {
//                    FragmentManager.removeFragment(Fragments.RECIPE_FRAGMENT);
//                    FragmentManager.setCurrentFragment(Fragments.CATEGORY_FRAGMENT);
//                    return true;
//                }
//            }).getFragment());
//            FragmentManager.setCurrentFragment(Fragments.RECIPE_FRAGMENT);
        } else {
            Utils.deb("itemId is null when try to load and show it");
        }
    }
}
