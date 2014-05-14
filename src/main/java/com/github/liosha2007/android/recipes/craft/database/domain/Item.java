package com.github.liosha2007.android.recipes.craft.database.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by liosha on 23.04.2014.
 */
@DatabaseTable(tableName = Item.TABLE_NAME)
public class Item {
    public static final String TABLE_NAME = "Items";
    public static final String FIELD_ID = "_Id";
    public static final String FIELD_NAME = "Name";
    public static final String FIELD_ICON = "Icon";
    public static final String FIELD_CATEGORY = "Category";
    public static final String FIELD_MOD = "Mod";
    public static final String FIELD_DESCRIPTION = "Description";
    public static final String FIELD_NOTE = "Note";

    @DatabaseField(generatedId = true, columnName = FIELD_ID, unique = true, canBeNull = false)
    private Integer id;
    @DatabaseField(dataType = DataType.STRING, columnName = FIELD_NAME, unique = true, canBeNull = false)
    private String name;
    @DatabaseField(dataType = DataType.STRING, columnName = FIELD_ICON)
    private String icon;
    @DatabaseField(foreign = true, columnName = FIELD_CATEGORY)
    private Category category;
    @DatabaseField(foreign = true, columnName = FIELD_MOD)
    private Mod mod;
    @DatabaseField(dataType = DataType.STRING, columnName = FIELD_DESCRIPTION)
    private String description;
    @DatabaseField(dataType = DataType.STRING, columnName = FIELD_NOTE)
    private String note;

    public Item() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Mod getMod() {
        return mod;
    }

    public void setMod(Mod mod) {
        this.mod = mod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
