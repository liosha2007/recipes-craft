package com.github.liosha2007.android.recipes.craft.controller;

import com.github.liosha2007.android.library.controller.BaseController;
import com.github.liosha2007.android.recipes.craft.database.DBHelper;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;
import com.github.liosha2007.android.recipes.craft.fragment.ModsView;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class ModsController extends BaseController<ModsView> {
    public ModsController() {
        super(new ModsView());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //
        List<Mod> items = DBHelper.getModDAO().getAllMods();
        view.clearMods();
        view.showMods(items);
    }
}
