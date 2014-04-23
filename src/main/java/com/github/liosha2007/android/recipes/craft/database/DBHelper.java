package com.github.liosha2007.android.recipes.craft.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.liosha2007.android.library.application.ApplicationActivity;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.dao.CategoryDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.apache.log4j.lf5.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by liosha on 23.04.2014.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RecipesCraft.db";

    private static DBHelper dbHelper;
    private static ItemDAO itemDAO = null;
    private static CategoryDAO categoryDAO = null;

    public DBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try {
            TableUtils.createTable(connectionSource, Item.class);
            TableUtils.createTable(connectionSource, Category.class);

            // TODO: Create test data

            Category materials = new Category();
            materials.setName("Материалы");
//            materials.setIcon("mods/common/icon.png");
            materials.setUuid(UUID.randomUUID());
            getCategoryDAO().create(materials);

            Category instruments = new Category();
            instruments.setName("Инструменты");
//            instruments.setIcon("mods/common/icon.png");
            instruments.setUuid(UUID.randomUUID());
            getCategoryDAO().create(instruments);

            Item dub = new Item();
            dub.setName("Дуб");
            dub.setIcon("mods/common/items/5.png");
            dub.setUuid(UUID.randomUUID());
            dub.setCategory(materials);
            getItemDAO().create(dub);

            Item craftingTable = new Item();
            craftingTable.setName("Верстак");
            craftingTable.setIcon("mods/common/items/6.png");
            craftingTable.setUuid(UUID.randomUUID());
            craftingTable.setCategory(instruments);
            getItemDAO().create(craftingTable);

        } catch (SQLException e){
            Utils.err("error creating DB " + DATABASE_NAME + ": " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer, int newVer){
        try{
            TableUtils.dropTable(connectionSource, Item.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e){
            Utils.err("error upgrading db " + DATABASE_NAME + "from ver " + oldVer + " to ver " + newVer + ": " + e.getMessage());
        }
    }
    public static void setHelper(Context context) {
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        dbHelper = null;
    }

    @Override
    public void close(){
        super.close();
        itemDAO = null;
    }

    public static ItemDAO getItemDAO() {
        try {
            return (itemDAO == null) ? (itemDAO = new ItemDAO(dbHelper.getConnectionSource(), Item.class)) : itemDAO;
        } catch (Exception e){
            Utils.err("error creating item dao: " + e.getMessage());
            return null;
        }
    }

    public static CategoryDAO getCategoryDAO() {
        try {
            return (categoryDAO == null) ? (categoryDAO = new CategoryDAO(dbHelper.getConnectionSource(), Category.class)) : categoryDAO;
        } catch (Exception e){
            Utils.err("error creating item dao: " + e.getMessage());
            return null;
        }
    }
}
