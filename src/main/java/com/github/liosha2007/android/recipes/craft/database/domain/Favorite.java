package com.github.liosha2007.android.recipes.craft.database.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by liosha on 24.04.2014.
 */
@DatabaseTable(tableName = Favorite.TABLE_NAME)
public class Favorite {
    public static final String TABLE_NAME = "Favorites";
    public static final String FIELD_ID = "_Id";
    public static final String FIELD_ITEM = "Item";

    @DatabaseField(generatedId = true, columnName = FIELD_ID, unique = true, canBeNull = false)
    private Integer id;
    @DatabaseField(foreign = true, columnName = FIELD_ITEM, canBeNull = false, foreignAutoRefresh = true)
    private Item item;

    public Favorite() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
