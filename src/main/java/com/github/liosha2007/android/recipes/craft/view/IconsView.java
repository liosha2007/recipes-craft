package com.github.liosha2007.android.recipes.craft.view;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.controller.IconsController;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.layout.FlowLayout;

/**
 * @author liosha on 30.09.2014.
 */
public class IconsView extends BaseActivityView<IconsController> {
    public IconsView() {
        super(R.layout.layout_icons);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void showIcon(Icon icon) {
        ImageButton imageButton = new ImageButton(controller);
        imageButton.setBackgroundColor(Color.TRANSPARENT);
        imageButton.setImageBitmap(Utils.bytes2bitmap(icon.getIcon()));
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(80, 80);
        imageButton.setLayoutParams(layoutParams);
        imageButton.setTag(icon.getId());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onIconClicked((Integer) view.getTag());
            }
        });
        this.<FlowLayout>view(R.id.icons_container).addView(imageButton);
    }

    public void showNotFound() {
        view(R.id.not_found).setVisibility(View.VISIBLE);
    }
}