package com.github.liosha2007.android.recipes.craft.database.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * Created by liosha on 24.04.2014.
 */
@DatabaseTable(tableName = Recipe.TABLE_NAME)
public class Recipe {
    public static final String TABLE_NAME = "Recipes";
    public static final String FIELD_ID = "_Id";
    public static final String FIELD_P1X1 = "P1x1";
    public static final String FIELD_P1X2 = "P1x2";
    public static final String FIELD_P1X3 = "P1x3";
    public static final String FIELD_P2X1 = "P2x1";
    public static final String FIELD_P2X2 = "P2x2";
    public static final String FIELD_P2X3 = "P2x3";
    public static final String FIELD_P3X1 = "P3x1";
    public static final String FIELD_P3X2 = "P3x2";
    public static final String FIELD_P3X3 = "P3x3";
    public static final String FIELD_RESULT = "Result";

    @DatabaseField(generatedId = true, columnName = FIELD_ID, unique = true, canBeNull = false)
    private Integer id;
    @DatabaseField(foreign = true, columnName = FIELD_P1X1)
    private Item p1x1;
    @DatabaseField(foreign = true, columnName = FIELD_P1X2)
    private Item p1x2;
    @DatabaseField(foreign = true, columnName = FIELD_P1X3)
    private Item p1x3;
    @DatabaseField(foreign = true, columnName = FIELD_P2X1)
    private Item p2x1;
    @DatabaseField(foreign = true, columnName = FIELD_P2X2)
    private Item p2x2;
    @DatabaseField(foreign = true, columnName = FIELD_P2X3)
    private Item p2x3;
    @DatabaseField(foreign = true, columnName = FIELD_P3X1)
    private Item p3x1;
    @DatabaseField(foreign = true, columnName = FIELD_P3X2)
    private Item p3x2;
    @DatabaseField(foreign = true, columnName = FIELD_P3X3)
    private Item p3x3;
    @DatabaseField(foreign = true, columnName = FIELD_RESULT, canBeNull = false)
    private Item result;

    public Recipe() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getP1x1() {
        return p1x1;
    }

    public void setP1x1(Item p1x1) {
        this.p1x1 = p1x1;
    }

    public Item getP1x2() {
        return p1x2;
    }

    public void setP1x2(Item p1x2) {
        this.p1x2 = p1x2;
    }

    public Item getP1x3() {
        return p1x3;
    }

    public void setP1x3(Item p1x3) {
        this.p1x3 = p1x3;
    }

    public Item getP2x1() {
        return p2x1;
    }

    public void setP2x1(Item p2x1) {
        this.p2x1 = p2x1;
    }

    public Item getP2x2() {
        return p2x2;
    }

    public void setP2x2(Item p2x2) {
        this.p2x2 = p2x2;
    }

    public Item getP2x3() {
        return p2x3;
    }

    public void setP2x3(Item p2x3) {
        this.p2x3 = p2x3;
    }

    public Item getP3x1() {
        return p3x1;
    }

    public void setP3x1(Item p3x1) {
        this.p3x1 = p3x1;
    }

    public Item getP3x2() {
        return p3x2;
    }

    public void setP3x2(Item p3x2) {
        this.p3x2 = p3x2;
    }

    public Item getP3x3() {
        return p3x3;
    }

    public void setP3x3(Item p3x3) {
        this.p3x3 = p3x3;
    }

    public Item getResult() {
        return result;
    }

    public void setResult(Item result) {
        this.result = result;
    }
}
