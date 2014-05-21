package com.github.liosha2007.android.recipes.craft.controller;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.recipes.craft.common.Constants;
import com.github.liosha2007.android.recipes.craft.view.SettingsView;

import java.awt.Checkbox;

/**
 * Created by liosha on 22.04.2014.
 */
public class SettingsController extends BaseActivityController<SettingsView> {
    public SettingsController() {
        super(new SettingsView());
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean(Constants.ADD_DELETE_RECIPES, false)){
            view.checkAddDelete();
        }
    }

    public void onEditRecipesChecked(boolean checked) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.ADD_DELETE_RECIPES, checked);
        editor.commit();
    }
}
