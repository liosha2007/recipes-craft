package com.github.liosha2007.android.recipes.craft.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.controller.ItemsController;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ItemsView extends BaseActivityView<ItemsController> {
    public ItemsView() {
        super(R.layout.layout_items);
    }
    private ItemsArrayAdapter adapter;

    @Override
    public void onCreate() {
        super.onCreate();

        this.<EditText>view(R.id.items_quick_search).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                controller.onSearchTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Remove all showed items
     */
    public void clearItems() {
        if (adapter != null) {
            adapter.clear();
        }
    }

    /**
     * Show items on list view
     *
     * @param items
     * @param favorites
     */
    public void showItems(List<Item> items, List<Favorite> favorites) {
        final ListView listview = view(R.id.items_list);
        adapter = new ItemsArrayAdapter(controller, items, favorites);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Object o = Utils.view(view, R.id.items_item_title).getTag();
                if (o instanceof Integer) {
                    controller.onItemClicked((Integer) o);
                } else {
                    Utils.err("not found ID in item tag");
                }
            }

        });
    }

    public void setTitle(String title) {
        if (title != null){
            this.<TextView>view(R.id.items_title).setText(title);
        }
    }

    public void updateListViewStack(boolean showed) {
        this.<ListView>view(R.id.items_list).setStackFromBottom(showed);
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public ImageView favoritesImageView;
        public boolean isFavorite;
        public int position;
    }

    private class ItemsArrayAdapter extends ArrayAdapter<Item> {
        protected List<Item> items;
        protected List<Favorite> favorites;

        public ItemsArrayAdapter(Context context, List<Item> items, List<Favorite> favorites) {
            super(context, R.layout.layout_items_row, items);
            this.items = items;
            this.favorites = favorites;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = controller.getLayoutInflater();
                rowView = inflater.inflate(R.layout.layout_items_row, null, true);
                holder = new ViewHolder();
                holder.textView = Utils.view(rowView, R.id.items_item_title);
                holder.imageView = Utils.view(rowView, R.id.items_item_icon);
                holder.favoritesImageView = Utils.view(rowView, R.id.items_item_favorites);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            holder.isFavorite = isFavorite(items.get(position).getId());
            holder.position = position;

            holder.textView.setText(items.get(position).getName());
            holder.textView.setTag(items.get(position).getId());
            holder.imageView.setImageDrawable(Utils.loadImageFromAssets(controller, items.get(position).getIcon()));
            holder.favoritesImageView.setImageResource(holder.isFavorite ? R.drawable.favorites_active : R.drawable.favorites_passive);


            holder.favoritesImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // !!! Change this code in move ImageView !!!
                    if (v.getParent() instanceof RelativeLayout && ((RelativeLayout) v.getParent()).getTag() instanceof ViewHolder) {
                        RelativeLayout relativeLayout = (RelativeLayout) v.getParent();
                        ViewHolder viewHolder = (ViewHolder) relativeLayout.getTag();
                        controller.onFavoriteClicked(items.get(viewHolder.position), viewHolder.isFavorite, viewHolder.position);
                    }
                }
            });

            return rowView;
        }

        public void updateFavorites(List<Favorite> favorites) {
            this.favorites = favorites;
        }

        protected boolean isFavorite(int itemId){
            for (Favorite favorite : favorites){
                int favoriteItemId = favorite.getItem().getId();
                if (favoriteItemId == itemId){
                    return true;
                }
            }
            return false;
        }
    }

    public void updateFavorites(List<Favorite> favorites) {
//        ImageView favoritesImageView = Utils.view(this.<ListView>view(R.id.items_list).getChildAt(position), R.id.items_item_favorites);
//        if (favoritesImageView != null) {
//            favoritesImageView.setImageDrawable(controller.getResources().getDrawable(isAdded ? R.drawable.favorites_active : R.drawable.favorites_passive));
//            favoritesImageView.setTag(isAdded);
//        }
        ItemsArrayAdapter itemsArrayAdapter = (ItemsArrayAdapter) this.<ListView>view(R.id.items_list).getAdapter();
        itemsArrayAdapter.updateFavorites(favorites);
        itemsArrayAdapter.notifyDataSetChanged();
    }

}
