package com.github.liosha2007.android.recipes.craft.database.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author liosha on 30.09.2014.
 */
@DatabaseTable(tableName = Icon.TABLE_NAME)
public class Icon {
    public static final String TABLE_NAME = "Icons";
    public static final String FIELD_ID = "_Id";
    public static final String FIELD_ICON = "Icon";

    @DatabaseField(generatedId = true, columnName = FIELD_ID, unique = true, canBeNull = false)
    private Integer id;
    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = FIELD_ICON)
    private byte[] icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
}
