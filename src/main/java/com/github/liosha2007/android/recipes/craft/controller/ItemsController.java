package com.github.liosha2007.android.recipes.craft.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.common.Constants;
import com.github.liosha2007.android.recipes.craft.database.dao.FavoriteDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.IconDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.view.ItemsView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        showItems();

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

    private void showItems() {
        final FavoriteDAO favoriteDAO = daoFor(Favorite.class);
        final IconDAO iconDAO = daoFor(Icon.class);
        //
        view.clearItems();
        List<Item> items = loadItems();
        List<Favorite> favorites = favoriteDAO.getAllFavorites();
        try {
            for (Item item : items) {
                iconDAO.refresh(item.getIcon());
            }
            for (Favorite favorite : favorites) {
                iconDAO.refresh(favorite.getItem().getIcon());
            }
        } catch (Exception e) {
            Utils.err("Can't update icon: " + e.getMessage());
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean editMode = preferences.getBoolean(Constants.ADD_DELETE_RECIPES, false);

        view.showItems(items, favorites, editMode);
        if (items.size() == 0) {
            view.showNotFound();
        }
    }

    protected List<Item> loadItems() {
        final FavoriteDAO favoriteDAO = daoFor(Favorite.class);
        final ItemDAO itemDAO = daoFor(Item.class);
        List<Item> items = new ArrayList<Item>();
        if (modId != -1) {
            items = itemDAO.queryForEq(Item.FIELD_MOD, modId);
            view.setTitle(modTitle);
        } else if (categoryId != -1) {
            items = itemDAO.queryForEq(Item.FIELD_CATEGORY, categoryId);
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
            List<Favorite> favorites = favoriteDAO.getAllFavorites();
            if (favorites != null) {
                for (Favorite favorite : favorites) {
                    Item item = favorite.getItem();
                    itemDAO.refresh(item);
                    items.add(item);
                }
                view.setTitle(favoritesTitle);
            }
        } else if (selectItemTitle != null) {
            List<Item> allItems = itemDAO.getAllItems();

            Item emptyItem = new Item();
            emptyItem.setId(-1);
            emptyItem.setName("Пустая ячейка");
//            emptyItem.setIcon("empty.png"); // TODO: Utils.bytes2bitmap(item.getIcon()
            items.add(emptyItem);
            items.addAll(allItems);
            view.setTitle(selectItemTitle);
        } else {
            items = itemDAO.getAllItems();
        }
        return items;
    }

    private void searchAndAddUnique(String fieldName, String searchText, List<Item> items) {
        final ItemDAO itemDAO = daoFor(Item.class);
        for (Item foundItem : itemDAO.queryForLike(fieldName, searchText)) {
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
        final FavoriteDAO favoriteDAO = daoFor(Favorite.class);
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
        final FavoriteDAO favoriteDAO = daoFor(Favorite.class);
        List<Item> items = loadItems();
        List<Item> filteredItems = new ArrayList<Item>(items.size());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean editMode = preferences.getBoolean(Constants.ADD_DELETE_RECIPES, false);
        for (Item item : items) {
            if (item.getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        view.clearItems();
        view.showItems(filteredItems, favoriteDAO.getAllFavorites(), editMode);
    }

    public void onDeleteClicked(final Item item) {
        if (item != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Удаление")
                    .setMessage("Вы действительно хотите удалить?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            final FavoriteDAO favoriteDAO = daoFor(Favorite.class);
                            final ItemDAO itemDAO = daoFor(Item.class);
                            try {
                                Favorite favorite = favoriteDAO.selectBy(Arrays.asList(Favorite.FIELD_ITEM), item.getId());
                                if (favorite != null) {
                                    favoriteDAO.delete(favorite);
                                }
                                itemDAO.delete(item);
                                Toast.makeText(ItemsController.this, "Предмет удален!", Toast.LENGTH_LONG).show();
                                showItems();
                            } catch (SQLException e) {
                                Toast.makeText(ItemsController.this, "Не удалось удалить предмет!", Toast.LENGTH_LONG).show();
                                Utils.err("Can't delete item: " + e.getMessage());
                            }
                        }
                    }).create().show();
        }
    }
}
