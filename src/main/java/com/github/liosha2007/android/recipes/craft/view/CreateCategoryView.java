package com.github.liosha2007.android.recipes.craft.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.recipes.craft.controller.BrowserController;
import com.github.liosha2007.android.recipes.craft.controller.CreateCategoryController;
import com.github.liosha2007.android.recipes.craft.controller.SettingsController;

/**
 * @author liosha on 30.09.2014.
 */
public class CreateCategoryView extends BaseActivityView<CreateCategoryController> {
    public CreateCategoryView() {
        super(R.layout.layout_create_category);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        view(R.id.create_category_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onIconClicked();
            }
        });
        view(R.id.create_category_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onCreateCategoryClicked();
            }
        });
    }

    public void setCategoryIcon(Bitmap bitmap) {
        this.<ImageButton>view(R.id.create_category_icon).setImageBitmap(bitmap);
    }

    public String getCategoryName() {
        return this.<EditText>view(R.id.create_category_title).getText().toString();
    }
}