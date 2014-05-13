package com.github.liosha2007.android.recipes.craft.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.controller.CategoryController;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;

import java.util.List;

/**
 * Created by liosha on 21.04.2014.
 */
public class CategoryView extends BaseActivityView<CategoryController> {
    public CategoryView() {
        super(R.layout.layout_category);
    }
    private CategoryArrayAdapter adapter;

    /**
     * Remove all showed category items
     */
    public void clearCategoryItems() {
        if (adapter != null) {
            adapter.clear();
        }
    }

    /**
     * Show categories on list view
     *
     * @param items
     */
    public void showCategoryItems(List<Item> items) {
        final ListView listview = (ListView) view.findViewById(R.id.category_list);
        adapter = new CategoryArrayAdapter(controller, items);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Object o = Utils.view(view, R.id.category_item_title).getTag();
                if (o instanceof Integer) {
                    controller.onItemClicked((Integer) o);
                } else {
                    Utils.err("not found ID in item tag");
                }
            }

        });
    }

    public void showCategoryTitle(String title) {
        this.<TextView>view(R.id.category_title).setText(title);
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }

    private class CategoryArrayAdapter extends ArrayAdapter<Item> {
        protected List<Item> items;

        public CategoryArrayAdapter(Context context, List<Item> items) {
            super(context, R.layout.layout_category_row, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = controller.getLayoutInflater();
                rowView = inflater.inflate(R.layout.layout_category_row, null, true);
                holder = new ViewHolder();
                holder.textView = Utils.view(rowView, R.id.category_item_title);
                holder.imageView = Utils.view(rowView, R.id.category_item_icon);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            holder.textView.setText(items.get(position).getName());
            holder.textView.setTag(items.get(position).getId());
            holder.imageView.setImageDrawable(Utils.loadImageFromAssets(controller, items.get(position).getIcon()));

            return rowView;
        }
    }
}

