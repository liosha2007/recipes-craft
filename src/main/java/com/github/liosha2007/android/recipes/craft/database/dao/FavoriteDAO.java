package com.github.liosha2007.android.recipes.craft.database.dao;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
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

    /**
     * Select first found entity object with specified values of fields
     *
     * @param fieldNames  list of field names (Recommend to use constants from entity class)
     * @param fieldValues array of filed values according and in the same order to fieldNames
     * @return first found entity object
     */
    public final Favorite selectBy(List<String> fieldNames, Object... fieldValues) throws SQLException {
        QueryBuilder<Favorite, Integer> qb = super.queryBuilder();
        int size = Math.min(fieldNames.size(), fieldValues.length);
        if (size > 0 && fieldValues[0] != null) {
            Where where = qb.where();
            for (int n = 0; n < size; n++) {
                if (fieldValues[n] != null) {
                    where.eq(fieldNames.get(n), fieldValues[n]);
                }
                if (n < size - 1 && fieldValues[n + 1] != null) {
                    where.and();
                }
            }
            PreparedQuery<Favorite> preparedQuery = qb.prepare();
            return super.queryForFirst(preparedQuery);
        }
        return null;
    }
}
