package com.github.liosha2007.android.recipes.craft.view;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.recipes.craft.controller.SettingsController;


/**
 * Created by liosha on 22.04.2014.
 */
public class SettingsView extends BaseActivityView<SettingsController> {
    public SettingsView() {
        super(R.layout.layout_settings);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.<CheckBox>view(R.id.add_delete_recipes).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                controller.onEditRecipesChecked(checked);
            }
        });
    }

    public void checkAddDelete() {
        this.<CheckBox>view(R.id.add_delete_recipes).setChecked(true);
    }
}
