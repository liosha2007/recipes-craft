package com.github.liosha2007.android.recipes.craft.view;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.controller.CreateItemController;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;

import java.util.List;

/**
 * @author liosha on 30.09.2014.
 */
public class CreateItemView extends BaseActivityView<CreateItemController> {
    public CreateItemView() {
        super(R.layout.layout_create_item);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        view(R.id.create_item_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onIconClicked();
            }
        });
        view(R.id.create_item_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = CreateItemView.this.<EditText>view(R.id.create_item_title).getText().toString();
                String description = CreateItemView.this.<EditText>view(R.id.create_item_description).getText().toString();
                String note = CreateItemView.this.<EditText>view(R.id.create_item_note).getText().toString();

                controller.onCreateItemClicked(title, description, note);
            }
        });
    }

    public void showCategories(List<Category> categories) {
        Spinner spinner = view(R.id.create_item_category);
        List<String> categoryNames = Utils.select(categories, new Utils.ISelect<Category, String>() {
            @Override
            public String toAdd(Category category) {
                return category.getName();
            }
        });
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this.controller, android.R.layout.simple_spinner_item, categoryNames.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                controller.onCategorySelected((String) adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setCategoryIcon(Bitmap bitmap) {
        this.<ImageButton>view(R.id.create_item_icon).setImageBitmap(bitmap);
    }

    public void showMods(List<Mod> mods) {
        Spinner spinner = view(R.id.create_item_mod);
        List<String> modNames = Utils.select(mods, new Utils.ISelect<Mod, String>() {
            @Override
            public String toAdd(Mod category) {
                return category.getName();
            }
        });
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this.controller, android.R.layout.simple_spinner_item, modNames.toArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                controller.onModSelected((String) adapterView.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}