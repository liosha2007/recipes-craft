package com.github.liosha2007.android.recipes.craft.fragment.recipe.controller;

import com.github.liosha2007.android.library.fragment.controller.BaseFragmentController;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.fragment.recipe.view.DescriptionFragment;

/**
 * Created by liosha on 13.05.2014.
 */
public class DescriptionController extends BaseFragmentController<DescriptionFragment> {
    public DescriptionController() {
        super(new DescriptionFragment());
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        final ItemDAO itemDAO = daoFor(Item.class);
        int itemId = getArguments().getInt(com.github.liosha2007.android.recipes.craft.controller.RecipeController.ITEM_ID, -1);
        Item item = itemDAO.queryForId(itemId);
        view.showDescription(item.getDescription());
    }
}
