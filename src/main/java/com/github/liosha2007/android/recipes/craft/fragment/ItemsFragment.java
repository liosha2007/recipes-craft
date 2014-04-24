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
import com.github.liosha2007.android.library.fragment.BaseFragment;
import com.github.liosha2007.android.recipes.craft.ApplicationActivity;
import com.github.liosha2007.android.recipes.craft.controller.ItemsController;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ItemsFragment extends BaseFragment<ItemsController> {
    private ItemsArrayAdapter adapter;

    public ItemsFragment() {
        super(R.layout.layout_items, new ItemsController());
    }

    @Override
    public void onViewCreated(View view) {
        // Initialize fragment UI elements
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
     * @param items
     */
    public void showItems(List<Item> items) {
        final ListView listview = Utils.view(view, R.id.items_list);
        adapter = new ItemsArrayAdapter(ApplicationActivity.activity, items);
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

    private class ItemsArrayAdapter extends ArrayAdapter<Item> {
        protected List<Item> items;

        public ItemsArrayAdapter(Context context, List<Item> items) {
            super(context, R.layout.layout_items_row, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = com.github.liosha2007.android.recipes.craft.ApplicationActivity.activity.getLayoutInflater();
                rowView = inflater.inflate(R.layout.layout_items_row, null, true);
                holder = new ViewHolder();
                holder.textView = Utils.view(rowView, R.id.second_line);
                holder.imageView = Utils.view(rowView, R.id.items_icon);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            holder.textView.setText(items.get(position).getName());
            holder.imageView.setImageDrawable(Utils.loadImageFromAssets(ApplicationActivity.activity, items.get(position).getIcon()));

            return rowView;
        }
    }
}
