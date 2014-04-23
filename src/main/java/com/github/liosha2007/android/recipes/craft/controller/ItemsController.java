package com.github.liosha2007.android.recipes.craft.controller;

import android.os.Bundle;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.recipes.craft.ApplicationActivity;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.fragment.ItemsFragment;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ItemsController extends BaseController<ItemsFragment> {
    @Override
    public void onViewCreated(Bundle savedInstanceState) {
        List<Item> items = DBHelper.getItemDAO().getAllItems();
        fragment.clearItems();
        fragment.showItems(items);
    }


}
