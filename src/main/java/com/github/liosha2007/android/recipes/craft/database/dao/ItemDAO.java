package com.github.liosha2007.android.recipes.craft.database.dao;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liosha on 23.04.2014.
 */
public class ItemDAO extends BaseDaoImpl<Item, Integer> {
    public ItemDAO(ConnectionSource connectionSource, Class<Item> dataClass) throws SQLException{
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
        } catch (Exception e){
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
//    // http://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_3.html#SEC37
//    public List<Goal> getGoalByName(String name)  throws SQLException{
//        QueryBuilder<Goal, String> queryBuilder = queryBuilder();
//        queryBuilder.where().eq(Goal.GOAL_NAME_FIELD_NAME, "First goal");
//        PreparedQuery<Goal> preparedQuery = queryBuilder.prepare();
//        List<Goal> goalList =query(preparedQuery);
//        return goalList;
//    }
}
