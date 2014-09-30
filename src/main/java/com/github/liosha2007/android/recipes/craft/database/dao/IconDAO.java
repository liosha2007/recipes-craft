package com.github.liosha2007.android.recipes.craft.database.dao;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by liosha on 24.04.2014.
 */
public class IconDAO extends BaseDaoImpl<Icon, Integer> {
    public IconDAO(ConnectionSource connectionSource, Class<Icon> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Icon> getAllIcons() {
        try {
            return this.queryForAll();
        } catch (Exception e) {
            Utils.err("error getting all icons: " + e.getMessage());
        }
        return null;
    }

    public Icon queryForId(Integer id) {
        try {
            return super.queryForId(id);
        } catch (Exception e) {
            Utils.err("can't load icon by id " + id + ": " + e.getMessage());
        }
        return null;
    }
}
