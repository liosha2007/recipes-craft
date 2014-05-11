package com.github.liosha2007.android.recipes.craft.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.view.BaseView;
import com.github.liosha2007.android.recipes.craft.controller.FavoritesController;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class FavoritesView extends BaseView<FavoritesController> {
    public FavoritesView() {
        super(R.layout.layout_favorites);
    }
    private FavoritesArrayAdapter adapter;

    /**
     * Remove all showed favorites
     */
    public void clearFavorites() {
        if (adapter != null) {
            adapter.clear();
        }
    }

    /**
     * Show favorites on list view
     *
     * @param favorites
     */
    public void showFavorites(List<Favorite> favorites) {
        final ListView listview = Utils.view(view, R.id.favorites_list);
        adapter = new FavoritesArrayAdapter(controller, favorites);
        listview.setAdapter(adapter);

//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view,
//                                    int position, long id) {
//                final String item = (String) parent.getItemAtPosition(position);
//                view.animate().setDuration(2000).alpha(0)
//                        .withEndAction(new Runnable() {
//                            @Override
//                            public void run() {
//                                list.remove(item);
//                                adapter.notifyDataSetChanged();
//                                view.setAlpha(1);
//                            }
//                        });
//            }
//
//        });
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }

    private class FavoritesArrayAdapter extends ArrayAdapter<Favorite> {
        protected List<Favorite> favorites;

        public FavoritesArrayAdapter(Context context, List<Favorite> favorites) {
            super(context, R.layout.layout_favorites_row, favorites);
            this.favorites = favorites;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = controller.getLayoutInflater();
                rowView = inflater.inflate(R.layout.layout_favorites_row, null, true);
                holder = new ViewHolder();
                holder.textView = Utils.view(rowView, R.id.second_line);
                holder.imageView = Utils.view(rowView, R.id.favorites_icon);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            Item item = favorites.get(position).getItem();
            holder.textView.setText(item.getName());
            holder.imageView.setImageDrawable(Utils.loadImageFromAssets(controller, item.getIcon()));

            return rowView;
        }
    }
}
