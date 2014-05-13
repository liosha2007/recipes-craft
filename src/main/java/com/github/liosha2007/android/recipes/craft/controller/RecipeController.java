package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.manager.FragmentManager;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
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
        int itemId = -1;
        Bundle bundle = new Bundle();
        bundle.putInt(ITEM_ID, itemId = getIntent().getExtras().getInt(ITEM_ID, -1));
        FragmentManager fragmentManager = FragmentManager.prepareViewPager(
                viewPager,
                this,
                new com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.RecipeController(){
                    @Override
                    public void onShow() {
                        super.onShow();
                        RecipeController.this.view.onRecipeShow(true);
                    }

                    @Override
                    public void onHide() {
                        super.onHide();
                        RecipeController.this.view.onRecipeShow(false);
                    }
                }.withArguments(bundle),
                new com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.DescriptionController(){
                    @Override
                    public void onShow() {
                        super.onShow();
                        RecipeController.this.view.onDescriptionShow(true);
                    }

                    @Override
                    public void onHide() {
                        super.onHide();
                        RecipeController.this.view.onDescriptionShow(false);
                    }
                }.withArguments(bundle),
                new com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.MaterialsController(){
                    @Override
                    public void onShow() {
                        super.onShow();
                        RecipeController.this.view.onMaterialsShow(true);
                    }

                    @Override
                    public void onHide() {
                        super.onHide();
                        RecipeController.this.view.onMaterialsShow(false);
                    }
                }.withArguments(bundle),
                new com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.NotesController(){
                    @Override
                    public void onShow() {
                        super.onShow();
                        RecipeController.this.view.onNotesShow(true);
                    }

                    @Override
                    public void onHide() {
                        super.onHide();
                        RecipeController.this.view.onNotesShow(false);
                    }
                }.withArguments(bundle)
        );
        if (itemId != -1) {
            view.showItemInfo(DBHelper.getItemDAO().queryForId(itemId));
        }
    }
}
