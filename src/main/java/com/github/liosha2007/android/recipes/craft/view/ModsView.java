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
import com.github.liosha2007.android.recipes.craft.controller.ModsController;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ModsView extends BaseActivityView<ModsController> {
    public ModsView() {
        super(R.layout.layout_mods);
    }
    private ModsArrayAdapter adapter;

    public void clearMods() {
        if (adapter != null) {
            adapter.clear();
        }
    }

    public void showMods(List<Mod> mods) {
        final ListView listview = (ListView) view.findViewById(R.id.mods_list);
        adapter = new ModsArrayAdapter(controller, mods);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Object o = Utils.view(view, R.id.mods_item_title).getTag();
                if (o instanceof Integer) {
                    controller.onModClicked((Integer) o, String.valueOf(Utils.<TextView>view(view, R.id.mods_item_title).getText()));
                } else {
                    Utils.err("not found ID in item tag");
                }
            }
        });
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }

    private class ModsArrayAdapter extends ArrayAdapter<Mod> {
        protected List<Mod> mods;

        public ModsArrayAdapter(Context context, List<Mod> mods) {
            super(context, R.layout.layout_mods_row, mods);
            this.mods = mods;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = controller.getLayoutInflater();
                rowView = inflater.inflate(R.layout.layout_mods_row, null, true);
                holder = new ViewHolder();
                holder.textView = Utils.view(rowView, R.id.mods_item_title);
                holder.imageView = Utils.view(rowView, R.id.mods_item_icon);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            holder.textView.setText(mods.get(position).getName());
            holder.textView.setTag(mods.get(position).getId());
            holder.imageView.setImageDrawable(Utils.loadImageFromAssets(controller, mods.get(position).getIcon()));

            return rowView;
        }
    }
}
