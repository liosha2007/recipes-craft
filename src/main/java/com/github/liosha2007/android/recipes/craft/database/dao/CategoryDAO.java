package com.github.liosha2007.android.recipes.craft.database.dao;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by liosha on 23.04.2014.
 */
public class CategoryDAO extends BaseDaoImpl<Category, Integer> {
    public CategoryDAO(ConnectionSource connectionSource, Class<Category> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Category> getAllCategories() {
        try {
            return this.queryForAll();
        } catch (Exception e) {
            Utils.err("error getting all categories: " + e.getMessage());
        }
        return null;
    }

    public Category queryForId(Integer id) {
        try {
            return super.queryForId(id);
        } catch (Exception e) {
            Utils.err("can't load category by id " + id + ": " + e.getMessage());
        }
        return null;
    }

    /**
     * Select first found entity object with specified values of fields
     *
     * @param fieldNames  list of field names (Recommend to use constants from entity class)
     * @param fieldValues array of filed values according and in the same order to fieldNames
     * @return first found entity object
     */
    public final Category selectBy(List<String> fieldNames, Object... fieldValues) throws SQLException {
        QueryBuilder<Category, Integer> qb = super.queryBuilder();
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
            PreparedQuery<Category> preparedQuery = qb.prepare();
            return super.queryForFirst(preparedQuery);
        }
        return null;
    }
}
