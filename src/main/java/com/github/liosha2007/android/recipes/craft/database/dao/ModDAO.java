package com.github.liosha2007.android.recipes.craft.database.dao;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by liosha on 24.04.2014.
 */
public class ModDAO extends BaseDaoImpl<Mod, Integer> {
    public ModDAO(ConnectionSource connectionSource, Class<Mod> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Mod> getAllMods() {
        try {
            return this.queryForAll();
        } catch (Exception e) {
            Utils.err("error getting all mods: " + e.getMessage());
        }
        return null;
    }
}
