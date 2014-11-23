package com.github.liosha2007.android.recipes.craft.controller;

import android.content.Intent;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.recipes.craft.database.dao.IconDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.view.IconsView;

import java.util.List;

/**
 * @author liosha on 30.09.2014.
 */
public class IconsController extends BaseActivityController<IconsView> {
    public static final String RETURN_ICON_PARAMETER = "com.github.liosha2007.android.recipes.craft.controller.IconsController.RETURN_ICON_PARAMETER";

    public IconsController() {
        super(new IconsView());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final IconDAO iconDAO = daoFor(Icon.class);
        List<Icon> icons = iconDAO.getAllIcons();
        if (icons.size() == 0) {
            view.showNotFound();
        }
        for (Icon icon : icons) {
            view.showIcon(icon);
        }
    }

    public void onIconClicked(Integer iconId) {
        Intent retIntent = new Intent();
        retIntent.putExtra(RETURN_ICON_PARAMETER, iconId);
        this.setResult(RESULT_OK, retIntent);
        this.finish();
    }
}