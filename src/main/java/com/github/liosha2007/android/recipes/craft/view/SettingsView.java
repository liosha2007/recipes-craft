package com.github.liosha2007.android.recipes.craft.view;

import android.view.View;
import android.widget.Button;
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
        this.<Button>view(R.id.export_database).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onExportDatabaseClicked();
            }
        });
        this.<Button>view(R.id.import_database).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onImportDatabaseClicked();
            }
        });
        this.<Button>view(R.id.upload_textures).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onUploadTexturesClicked();
            }
        });
        this.<Button>view(R.id.create_category).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onCreateCategoryClicked();
            }
        });
        this.<Button>view(R.id.create_mod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onCreateModClicked();
            }
        });
        this.<Button>view(R.id.create_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.onCreateItemClicked();
            }
        });
    }

    public void enableEditMode() {
        this.<CheckBox>view(R.id.add_delete_recipes).setChecked(true);
        this.<Button>view(R.id.upload_textures).setVisibility(View.VISIBLE);
        this.<Button>view(R.id.create_category).setVisibility(View.VISIBLE);
        this.<Button>view(R.id.create_mod).setVisibility(View.VISIBLE);
        this.<Button>view(R.id.create_item).setVisibility(View.VISIBLE);
    }
}
