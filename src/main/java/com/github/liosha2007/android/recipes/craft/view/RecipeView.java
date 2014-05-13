package com.github.liosha2007.android.recipes.craft.view;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.controller.RecipeController;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class RecipeView extends BaseActivityView<RecipeController> {

    public RecipeView() {
        super(R.layout.layout_recipe);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Tabs functional
        controller.onViewPagerCreated(this.<ViewPager>view(R.id.recipe_viewpager));
    }

    public void onRecipeShow(boolean showed) {
        this.<TextView>view(R.id.recipe_tab_recipe).setTextColor(showed ? Color.RED : Color.BLACK);
    }

    public void onDescriptionShow(boolean showed) {
        this.<TextView>view(R.id.recipe_tab_description).setTextColor(showed ? Color.RED : Color.BLACK);
    }

    public void onMaterialsShow(boolean showed) {
        this.<TextView>view(R.id.recipe_tab_materials).setTextColor(showed ? Color.RED : Color.BLACK);
    }

    public void onNotesShow(boolean showed) {
        this.<TextView>view(R.id.recipe_tab_notes).setTextColor(showed ? Color.RED : Color.BLACK);
    }

    public void showItemInfo(Item item) {
        if (item != null) {
            this.<TextView>view(R.id.recipe_result_title).setText(item.getName());
            this.<ImageView>view(R.id.recipe_result_icon).setImageDrawable(Utils.loadImageFromAssets(controller, item.getIcon())
            );
        }
    }
}
