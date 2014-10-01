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
import com.github.liosha2007.android.recipes.craft.controller.ModsController;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ModsView extends BaseActivityView<ModsController> {
    private ModsArrayAdapter adapter;

    public ModsView() {
        super(R.layout.layout_mods);
    }

    public void clearMods() {
        if (adapter != null) {
            adapter.clear();
        }
    }

    public void showMods(List<Mod> mods, boolean editMode) {
        final ListView listview = (ListView) view.findViewById(R.id.mods_list);
        adapter = new ModsArrayAdapter(controller, mods, editMode);
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

    public void showNotFound() {
        view(R.id.not_found).setVisibility(View.VISIBLE);
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public ImageView deleteButton;
    }

    private class ModsArrayAdapter extends ArrayAdapter<Mod> {
        protected List<Mod> mods;
        private boolean editMode;

        public ModsArrayAdapter(Context context, List<Mod> mods, boolean editMode) {
            super(context, R.layout.layout_mods_row, mods);
            this.mods = mods;
            this.editMode = editMode;
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
                holder.deleteButton = Utils.view(rowView, R.id.mods_item_delete);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            holder.textView.setText(mods.get(position).getName());
            holder.textView.setTag(mods.get(position).getId());
            holder.imageView.setImageBitmap(Utils.bytes2bitmap(mods.get(position).getIcon().getIcon()));
            if (editMode) {
                holder.deleteButton.setVisibility(View.VISIBLE);
                holder.deleteButton.setTag(mods.get(position).getId());
                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getParent() instanceof RelativeLayout && ((RelativeLayout) v.getParent()).getTag() instanceof ViewHolder) {
                            RelativeLayout relativeLayout = (RelativeLayout) v.getParent();
                            ViewHolder viewHolder = (ViewHolder) relativeLayout.getTag();
                            Integer modId = (Integer) viewHolder.deleteButton.getTag();
                            if (modId != null && modId != 0) {
                                controller.onDeleteClicked(modId);
                            }
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
