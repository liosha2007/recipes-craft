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


    public Favorite queryForId(Integer id) {
        try {
            return super.queryForId(id);
        } catch (Exception e) {
            Utils.err("can't load favorite by id " + id + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Favorite> queryForEq(String fieldName, Object value) {
        try {
            return super.queryForEq(fieldName, value);
        } catch (Exception e) {
            Utils.err("can't load favorites by statement " + fieldName + "=" + value + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public int delete(Favorite data) {
        try {
            return super.delete(data);
        } catch (Exception e) {
            Utils.err("can't delete favorite by data " + data + ", error: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public int create(Favorite data) {
        try {
            return super.create(data);
        } catch (Exception e) {
            Utils.err("can't insert favorite " + data + ", error: " + e.getMessage());
        }
        return -1;
    }
}
