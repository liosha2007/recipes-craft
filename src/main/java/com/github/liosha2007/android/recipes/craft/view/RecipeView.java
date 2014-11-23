package com.github.liosha2007.android.recipes.craft.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.controller.RecipeController;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;

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
        view(R.id.recipe_tab_recipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onRecipeTabClicked(RecipeView.this.<ViewPager>view(R.id.recipe_viewpager));
            }
        });
        view(R.id.recipe_tab_description).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onDescriptionTabClicked(RecipeView.this.<ViewPager>view(R.id.recipe_viewpager));
            }
        });
        view(R.id.recipe_tab_materials).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onMaterialsTabClicked(RecipeView.this.<ViewPager>view(R.id.recipe_viewpager));
            }
        });
        view(R.id.recipe_tab_notes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onNotesTabClicked(RecipeView.this.<ViewPager>view(R.id.recipe_viewpager));
            }
        });
    }

    public void onRecipeShow(boolean showed) {
        TextView textView = this.view(R.id.recipe_tab_recipe);
        if (textView != null) {
            int textColor = controller.getResources().getColor(R.color.TextLight);
            Drawable backgroundDrawable = controller.getResources().getDrawable(R.drawable.tabselected_background);
            textView.setTextColor(showed ? textColor : Color.BLACK);
            textView.setBackground(showed ? backgroundDrawable : null);
        }
    }

    public void onDescriptionShow(boolean showed) {
        TextView textView = this.view(R.id.recipe_tab_description);
        if (textView != null) {
            int textColor = controller.getResources().getColor(R.color.TextLight);
            Drawable backgroundDrawable = controller.getResources().getDrawable(R.drawable.tabselected_background);
            textView.setTextColor(showed ? textColor : Color.BLACK);
            textView.setBackground(showed ? backgroundDrawable : null);
        }
    }

    public void onMaterialsShow(boolean showed) {
        TextView textView = this.view(R.id.recipe_tab_materials);
        if (textView != null) {
            int textColor = controller.getResources().getColor(R.color.TextLight);
            Drawable backgroundDrawable = controller.getResources().getDrawable(R.drawable.tabselected_background);
            textView.setTextColor(showed ? textColor : Color.BLACK);
            textView.setBackground(showed ? backgroundDrawable : null);
        }
    }

    public void onNotesShow(boolean showed) {
        TextView textView = this.view(R.id.recipe_tab_notes);
        if (textView != null) {
            int textColor = controller.getResources().getColor(R.color.TextLight);
            Drawable backgroundDrawable = controller.getResources().getDrawable(R.drawable.tabselected_background);
            textView.setTextColor(showed ? textColor : Color.BLACK);
            textView.setBackground(showed ? backgroundDrawable : null);
        }
    }

    public void showItemInfo(Item item) {
        if (item != null) {
            this.<TextView>view(R.id.recipe_result_title).setText(item.getName());
            Icon icon = item.getIcon();
            this.<ImageView>view(R.id.recipe_result_icon).setImageBitmap(icon == null ? null : Utils.bytes2bitmap(icon.getIcon())
            );
        }
    }
}
