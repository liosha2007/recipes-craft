package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.view.ItemsView;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ItemsController extends BaseActivityController<ItemsView> {
    public static final String MOD_ID = "modId";
    public static final String MOD_TITLE = "modTitle";
    protected int modId = -1;
    protected String modTitle;

    public ItemsController() {
        super(new ItemsView());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.modId = bundle.getInt(MOD_ID, -1);
            this.modTitle = bundle.getString(MOD_TITLE, "");
        }
        //
        List<Item> items = null;
        if (modId == -1) {
            items = DBHelper.getItemDAO().getAllItems();
        } else {
            items = DBHelper.getItemDAO().queryForEq(Item.FIELD_MOD, modId);
        }

        view.clearItems();
        view.showItems(items);
        view.setTitle(modTitle);
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
