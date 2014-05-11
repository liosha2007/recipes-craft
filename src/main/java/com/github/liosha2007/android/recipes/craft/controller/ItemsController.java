package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.fragment.ItemsView;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ItemsController extends BaseController<ItemsView> {
    public ItemsController() {
        super(new ItemsView());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //
        List<Item> items = DBHelper.getItemDAO().getAllItems();
        view.clearItems();
        view.showItems(items);
    }

    public void onItemClicked(Integer itemId) {
        if (itemId != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(RecipeController.ITEM_ID, itemId);
            run(RecipeController.class, bundle);
        } else {
            Utils.deb("itemId is null when try to load and show it");
        }
    }
}
