package com.github.liosha2007.android.recipes.craft.view;

import android.support.v4.view.ViewPager;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.recipes.craft.controller.RecipeController;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class RecipeView extends BaseActivityView<RecipeController> {
//    private static final Integer ACCORDION_OPENED = Utils.makeID();
//    private static final Integer ACCORDION_CLOSED = Utils.makeID();

    public RecipeView() {
        super(R.layout.layout_recipe);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Tabs functional
        controller.onViewPagerCreated(this.<ViewPager>view(R.id.recipe_viewpager));
    }
}
