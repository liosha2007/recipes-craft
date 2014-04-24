package com.github.liosha2007.android.recipes.craft.database.dao;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by liosha on 24.04.2014.
 */
public class FavoriteDAO extends BaseDaoImpl<Favorite, Integer> {
    public FavoriteDAO(ConnectionSource connectionSource, Class<Favorite> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Favorite> getAllFavorites() {
        try {
            return this.queryForAll();
        } catch (Exception e) {
            Utils.err("error getting all favorites: " + e.getMessage());
        }
        return null;
    }
}
