package com.github.liosha2007.android.recipes.craft.database.dao;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
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
public class ItemDAO extends BaseDaoImpl<Item, Integer> {
    public ItemDAO(ConnectionSource connectionSource, Class<Item> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Item> getAllItems() {
        try {
            return this.queryForAll();
        } catch (Exception e) {
            Utils.err("error getting all items: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int refresh(Item data) {
        try {
            return super.refresh(data);
        } catch (Exception e) {
            Utils.err("can not resresh item: " + e.getMessage());
            return -1;
        }
    }

    public Item queryForId(Integer id) {
        try {
            return super.queryForId(id);
        } catch (Exception e) {
            Utils.err("can't load item by id " + id + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Item> queryForEq(String fieldName, Object value) {
        try {
            return super.queryForEq(fieldName, value);
        } catch (Exception e) {
            Utils.err("can't load items by statement " + fieldName + "=" + value + ": " + e.getMessage());
        }
        return null;
    }

    public List<Item> queryForLike(String fieldName, Object value) {
        try {
            QueryBuilder<Item, Integer> qb = this.queryBuilder();
            qb.where().like(fieldName, "%" + value + "%");
            PreparedQuery<Item> pq = qb.prepare();
            return this.query(pq);
        } catch (SQLException e) {
            Utils.err("can't load items by like " + fieldName + "=%" + value + "%: " + e.getMessage());
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
    public final Item selectBy(List<String> fieldNames, Object... fieldValues) throws SQLException {
        QueryBuilder<Item, Integer> qb = super.queryBuilder();
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
            PreparedQuery<Item> preparedQuery = qb.prepare();
            return super.queryForFirst(preparedQuery);
        }
        return null;
    }
//    // http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_3.html#SEC37
//    public List<Goal> getGoalByName(String name)  throws SQLException{
//        QueryBuilder<Goal, String> queryBuilder = queryBuilder();
//        queryBuilder.where().eq(Goal.GOAL_NAME_FIELD_NAME, "First goal");
//        PreparedQuery<Goal> preparedQuery = queryBuilder.prepare();
//        List<Goal> goalList =query(preparedQuery);
//        return goalList;
//    }
}
