package com.github.liosha2007.android.recipes.craft.fragment.recipe.view;

import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.view.BaseFragmentView;
import com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.DescriptionController;

/**
 * Created by liosha on 13.05.2014.
 */
public class DescriptionFragment extends BaseFragmentView<DescriptionController> {
    public DescriptionFragment() {
        super(R.layout.layout_recipe_fragment_description);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void showDescription(String description) {
        this.<TextView>view(R.id.recipe_tab_description).setText(description);
    }
}
