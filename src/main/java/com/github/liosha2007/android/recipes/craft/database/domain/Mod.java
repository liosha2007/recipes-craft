package com.github.liosha2007.android.recipes.craft.database.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * Created by liosha on 24.04.2014.
 */
@DatabaseTable(tableName = Mod.TABLE_NAME)
public class Mod {
    public static final String TABLE_NAME = "Mods";
    public static final String FIELD_ID = "_Id";
    public static final String FIELD_NAME = "Name";
    public static final String FIELD_ICON = "Icon";

    @DatabaseField(generatedId = true, columnName = FIELD_ID, unique = true, canBeNull = false)
    private Integer id;
    @DatabaseField(dataType = DataType.STRING, columnName = FIELD_NAME, unique = true, canBeNull = false)
    private String name;
    @DatabaseField(dataType = DataType.STRING, columnName = FIELD_ICON)
    private String icon;

    public Mod() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}