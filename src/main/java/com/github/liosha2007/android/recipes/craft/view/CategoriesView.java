package com.github.liosha2007.android.recipes.craft.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.controller.CategoriesController;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;

import java.util.List;

/**
 * Created by liosha on 21.04.2014.
 */
public class CategoriesView extends BaseActivityView<CategoriesController> {
    private CategoriesArrayAdapter adapter;

    public CategoriesView() {
        super(R.layout.layout_categories);
    }

    /**
     * Remove all showed categories
     */
    public void clearCategories() {
        if (adapter != null) {
            adapter.clear();
        }
    }

    /**
     * Show categories on list view
     *
     * @param categories
     */
    public void showCategories(List<Category> categories, boolean editMode) {
        final ListView listview = (ListView) view.findViewById(R.id.categories_list);
        adapter = new CategoriesArrayAdapter(controller, categories, editMode);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Object o = Utils.view(view, R.id.categories_item_title).getTag();
                if (o instanceof Integer) {
                    controller.onCategoryClicked((Integer) o);
                } else {
                    Utils.err("not found ID in categories item tag");
                }
            }

        });
    }

    public void showNotFound() {
        view(R.id.not_found).setVisibility(View.VISIBLE);
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public ImageView deleteButton;
        public int position;
    }

    private class CategoriesArrayAdapter extends ArrayAdapter<Category> {
        private final boolean editMode;
        protected List<Category> categories;

        public CategoriesArrayAdapter(Context context, List<Category> categories, boolean editMode) {
            super(context, R.layout.layout_categories_row, categories);
            this.categories = categories;
            this.editMode = editMode;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = controller.getLayoutInflater();
                rowView = inflater.inflate(R.layout.layout_categories_row, null, true);
                holder = new ViewHolder();
                holder.textView = Utils.view(rowView, R.id.categories_item_title);
                holder.imageView = Utils.view(rowView, R.id.categories_item_icon);
                holder.deleteButton = Utils.view(rowView, R.id.categories_item_delete);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            holder.position = position;
            holder.textView.setText(categories.get(position).getName());
            holder.textView.setTag(categories.get(position).getId());
            Icon icon = categories.get(position).getIcon();
            holder.imageView.setImageBitmap(icon == null || icon.getIcon() == null ? null : Utils.bytes2bitmap(icon.getIcon()));
            if (editMode) {
                holder.deleteButton.setVisibility(View.VISIBLE);
                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getParent() instanceof RelativeLayout && ((RelativeLayout) v.getParent()).getTag() instanceof ViewHolder) {
                            RelativeLayout relativeLayout = (RelativeLayout) v.getParent();
                            ViewHolder viewHolder = (ViewHolder) relativeLayout.getTag();
                            controller.onDeleteClicked(categories.get(viewHolder.position));
                        }
                    }
                });
            } else {
                holder.deleteButton.setVisibility(View.GONE);
            }

            return rowView;
        }
    }
}

