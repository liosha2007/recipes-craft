package com.github.liosha2007.android.recipes.craft.controller;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.dao.FavoriteDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.view.ItemsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ItemsController extends BaseActivityController<ItemsView> {
    public static final String MOD_ID = "modId";
    public static final String MOD_TITLE = "modTitle";
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_TITLE = "categoryTitle";
    public static final String SEARCH_TEXT = "searchText";
    public static final String SEARCH_TITLE = "searchTitle";
    public static final String FAVORITES_TITLE = "favoritesTitle";
    public static final String CREATE_RECIPE_TITLE = "createRecipeTitle";
    public static final String CREATE_RECIPE_IMAGE_VIEW_ID = "createRecipeImageView";
    public static final String CREATE_RECIPE_RESULT_ITEM_ID = "CREATE_RECIPE_RESULT_ITEM_ID";

    protected int modId = -1;
    protected String modTitle;

    protected int categoryId = -1;
    protected String categoryTitle;

    protected String searchText;
    protected String searchTitle;

    protected String favoritesTitle;

    protected String selectItemTitle;
    protected int imageViewId;

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

            this.categoryId = bundle.getInt(CATEGORY_ID, -1);
            this.categoryTitle = bundle.getString(CATEGORY_TITLE, "");

            this.searchText = bundle.getString(SEARCH_TEXT, null);
            this.searchTitle = bundle.getString(SEARCH_TITLE, "");

            this.favoritesTitle = bundle.getString(FAVORITES_TITLE, null);

            this.imageViewId = bundle.getInt(CREATE_RECIPE_IMAGE_VIEW_ID, -1);
            this.selectItemTitle = bundle.getString(CREATE_RECIPE_TITLE, null);
        }
        //
        view.clearItems();
        List<Item> items = loadItems();
        List<Favorite> favorites = DBHelper.getFavoriteDAO().getAllFavorites();
        try {
            for (Item item : items) {
                DBHelper.getIconDAO().refresh(item.getIcon());
            }
            for (Favorite favorite : favorites) {
                DBHelper.getIconDAO().refresh(favorite.getItem().getIcon());
            }
        } catch (Exception e) {
            Utils.err("Can't update icon: " + e.getMessage());
        }

        view.showItems(items, favorites);
        if (items.size() == 0) {
            view.showNotFound();
        }

        // Keyboard hidden/showed event
        final View activityRootView = this.view.view(R.id.items_root);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private boolean isSoftKeyboardOpened = false;

            @Override
            public void onGlobalLayout() {
                final Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                final int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (!isSoftKeyboardOpened && heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    view.updateListViewStack(isSoftKeyboardOpened = true);
                } else if (isSoftKeyboardOpened && heightDiff < 100) {
                    view.updateListViewStack(isSoftKeyboardOpened = false);
                }
            }
        });
    }

    protected List<Item> loadItems() {
        List<Item> items = new ArrayList<Item>();
        if (modId != -1) {
            items = DBHelper.getItemDAO().queryForEq(Item.FIELD_MOD, modId);
            view.setTitle(modTitle);
        } else if (categoryId != -1) {
            items = DBHelper.getItemDAO().queryForEq(Item.FIELD_CATEGORY, categoryId);
            view.setTitle(categoryTitle);
        } else if (searchText != null) {
            // Search in title
            searchAndAddUnique(Item.FIELD_NAME, searchText, items);
            // Search in description
            searchAndAddUnique(Item.FIELD_DESCRIPTION, searchText, items);
            // Search in note
            searchAndAddUnique(Item.FIELD_NOTE, searchText, items);

            view.setTitle(searchTitle);
        } else if (favoritesTitle != null) {
            List<Favorite> favorites = DBHelper.getFavoriteDAO().getAllFavorites();
            if (favorites != null) {
                for (Favorite favorite : favorites) {
                    Item item = favorite.getItem();
                    DBHelper.getItemDAO().refresh(item);
                    items.add(item);
                }
                view.setTitle(favoritesTitle);
            }
        } else if (selectItemTitle != null) {
            List<Item> allItems = DBHelper.getItemDAO().getAllItems();

            Item emptyItem = new Item();
            emptyItem.setId(-1);
            emptyItem.setName("Пустая ячейка");
//            emptyItem.setIcon("empty.png"); // TODO: Utils.bytes2bitmap(item.getIcon()
            items.add(emptyItem);
            items.addAll(allItems);
            view.setTitle(selectItemTitle);
        } else {
            items = DBHelper.getItemDAO().getAllItems();
        }
        return items;
    }

    private void searchAndAddUnique(String fieldName, String searchText, List<Item> items) {
        for (Item foundItem : DBHelper.getItemDAO().queryForLike(fieldName, searchText)) {
            boolean added = false;
            for (Item item : items) {
                if (foundItem.getId().equals(item.getId())) {
                    added = true;
                    break;
                }
            }
            if (!added) {
                items.add(foundItem);
            }
        }
    }

    public void onItemClicked(Integer itemId) {
        if (selectItemTitle != null) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(CREATE_RECIPE_RESULT_ITEM_ID, itemId);
            returnIntent.putExtra(CREATE_RECIPE_IMAGE_VIEW_ID, imageViewId);
            setResult(RESULT_OK, returnIntent);
            finish();
        } else if (itemId != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(RecipeController.ITEM_ID, itemId);
            run(RecipeController.class, bundle);
        } else {
            Utils.deb("itemId is null when try to load and show it");
        }
    }

    public void onFavoriteClicked(Item item, Boolean isAdded, int position) {
        FavoriteDAO favoriteDAO = DBHelper.getFavoriteDAO();
        List<Favorite> favorites = favoriteDAO.queryForEq(Favorite.FIELD_ITEM, item.getId());
        if (favorites.size() == 0) {
            Favorite favorite = new Favorite();
            favorite.setItem(item);
            favoriteDAO.create(favorite);
        } else {
            for (Favorite favorite : favorites) {
                favoriteDAO.delete(favorite);
            }
        }
        view.updateFavorites(favoriteDAO.getAllFavorites());
    }

    public void onSearchTextChanged(String searchText) {
        List<Item> items = loadItems();
        List<Item> filteredItems = new ArrayList<Item>(items.size());
        for (Item item : items) {
            if (item.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        view.clearItems();
        view.showItems(filteredItems, DBHelper.getFavoriteDAO().getAllFavorites());
    }
}
