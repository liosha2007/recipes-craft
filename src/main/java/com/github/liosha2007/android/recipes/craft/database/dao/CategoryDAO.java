package com.github.liosha2007.android.recipes.craft.database.dao;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.j256.ormlite.dao.BaseDaoImpl;
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
            Utils.err(e.getMessage());
        }
        return null;
    }
}
