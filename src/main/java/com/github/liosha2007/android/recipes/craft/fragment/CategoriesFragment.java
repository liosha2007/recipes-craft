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
import com.github.liosha2007.android.recipes.craft.controller.CategoriesController;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;

import java.util.List;

/**
 * Created by liosha on 21.04.2014.
 */
public class CategoriesFragment extends BaseFragment<CategoriesController> {
    private CategoriesArrayAdapter adapter;

    public CategoriesFragment() {
        super(R.layout.layout_categories, new CategoriesController());
    }

    @Override
    public void onViewCreated(View view) {
        // Initialize fragment UI elements
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
    public void showCategories(List<Category> categories) {
        final ListView listview = (ListView) view.findViewById(R.id.mods_list);
        adapter = new CategoriesArrayAdapter(ApplicationActivity.activity, categories);
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

    private class CategoriesArrayAdapter extends ArrayAdapter<Category> {
        protected List<Category> categories;

        public CategoriesArrayAdapter(Context context, List<Category> categories) {
            super(context, R.layout.layout_categories_row, categories);
            this.categories = categories;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = com.github.liosha2007.android.recipes.craft.ApplicationActivity.activity.getLayoutInflater();
                rowView = inflater.inflate(R.layout.layout_categories_row, null, true);
                holder = new ViewHolder();
                holder.textView = Utils.view(rowView, R.id.second_line);
                holder.imageView = Utils.view(rowView, R.id.mods_icon);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            holder.textView.setText(categories.get(position).getName());
            holder.imageView.setImageDrawable(Utils.loadImageFromAssets(ApplicationActivity.activity, categories.get(position).getIcon()));

            return rowView;
        }
    }
}

