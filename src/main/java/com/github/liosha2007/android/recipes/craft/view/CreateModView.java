package com.github.liosha2007.android.recipes.craft.view;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.recipes.craft.controller.CreateModController;

/**
 * @author liosha on 30.09.2014.
 */
public class CreateModView extends BaseActivityView<CreateModController> {
    public CreateModView() {
        super(R.layout.layout_create_mod);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        view(R.id.create_mod_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onIconClicked();
            }
        });
        view(R.id.create_mod_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onCreateModClicked();
            }
        });
    }

    public void setCategoryIcon(Bitmap bitmap) {
        this.<ImageButton>view(R.id.create_mod_icon).setImageBitmap(bitmap);
    }

    public String getCategoryName() {
        return this.<EditText>view(R.id.create_mod_title).getText().toString();
    }
}