package com.github.liosha2007.android.recipes.craft.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.database.dao.CategoryDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.FavoriteDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.IconDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.ModDAO;
import com.github.liosha2007.android.recipes.craft.database.dao.RecipeDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Category;
import com.github.liosha2007.android.recipes.craft.database.domain.Favorite;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Mod;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by liosha on 23.04.2014.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String STANDARD_DATABASE_PATH = "/data/data/%s/databases/";
    private static final String DATABASE_NAME = "RecipesCraft.db";

    private static DBHelper dbHelper;
    private static ItemDAO itemDAO = null;
    private static CategoryDAO categoryDAO = null;
    private static ModDAO modDAO = null;
    private static RecipeDAO recipeDAO = null;
    private static IconDAO iconDAO = null;
    private static FavoriteDAO favoriteDAO = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void setHelper(Context context) {
        String databasePath = String.format(STANDARD_DATABASE_PATH, context.getPackageName());
        File dbFile = new File(databasePath + DATABASE_NAME);
        if ((!dbFile.exists()) && dbFile.getParentFile().mkdirs()) {
            try {
                // Create folders

                //Открываем локальную БД как входящий поток
                InputStream myInput = context.getAssets().open(DATABASE_NAME);

                //Путь ко вновь созданной БД
                String outFileName = databasePath + DATABASE_NAME;

                //Открываем пустую базу данных как исходящий поток
                OutputStream myOutput = new FileOutputStream(outFileName);

                //перемещаем байты из входящего файла в исходящий
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                //закрываем потоки
                myOutput.flush();
                Utils.closeStreams(myOutput, myInput);
            } catch (Exception e) {
                Utils.err("Can't copy database file! " + e.getMessage());
            }
        } else {
            Utils.err("Can't create database directory");
        }
        dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        dbHelper = null;
    }

    public static ItemDAO getItemDAO() {
        try {
            return (itemDAO == null) ? (itemDAO = new ItemDAO(dbHelper.getConnectionSource(), Item.class)) : itemDAO;
        } catch (Exception e) {
            Utils.err("error creating item dao: " + e.getMessage());
            return null;
        }
    }

    public static CategoryDAO getCategoryDAO() {
        try {
            return (categoryDAO == null) ? (categoryDAO = new CategoryDAO(dbHelper.getConnectionSource(), Category.class)) : categoryDAO;
        } catch (Exception e) {
            Utils.err("error creating item dao: " + e.getMessage());
            return null;
        }
    }

    public static ModDAO getModDAO() {
        try {
            return (modDAO == null) ? (modDAO = new ModDAO(dbHelper.getConnectionSource(), Mod.class)) : modDAO;
        } catch (Exception e) {
            Utils.err("error creating mod dao: " + e.getMessage());
            return null;
        }
    }

    public static RecipeDAO getRecipeDAO() {
        try {
            return (recipeDAO == null) ? (recipeDAO = new RecipeDAO(dbHelper.getConnectionSource(), Recipe.class)) : recipeDAO;
        } catch (Exception e) {
            Utils.err("error creating recipe dao: " + e.getMessage());
            return null;
        }
    }

    public static FavoriteDAO getFavoriteDAO() {
        try {
            return (favoriteDAO == null) ? (favoriteDAO = new FavoriteDAO(dbHelper.getConnectionSource(), Favorite.class)) : favoriteDAO;
        } catch (Exception e) {
            Utils.err("error creating favorite dao: " + e.getMessage());
            return null;
        }
    }

    public static IconDAO getIconDAO() {
        try {
            return (iconDAO == null) ? (iconDAO = new IconDAO(dbHelper.getConnectionSource(), Icon.class)) : iconDAO;
        } catch (Exception e) {
            Utils.err("error creating icon dao: " + e.getMessage());
            return null;
        }
    }

    public static boolean exportDatabaseFileTo(Context context, String path) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(String.format(STANDARD_DATABASE_PATH, context.getPackageName()) + DATABASE_NAME);
            fileOutputStream = new FileOutputStream(path + File.separator + DATABASE_NAME);
            IOUtils.copy(fileInputStream, fileOutputStream);
            return true;
        } catch (Exception e) {
            Utils.err("Can't export database: " + e.getMessage());
        } finally {
            Utils.closeStreams(fileInputStream, fileOutputStream);
        }
        return false;
    }

    public static boolean importDatabaseFileFrom(Context context, String path) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            fileOutputStream = new FileOutputStream(String.format(STANDARD_DATABASE_PATH, context.getPackageName()) + DATABASE_NAME);
            IOUtils.copy(fileInputStream, fileOutputStream);
            return true;
        } catch (Exception e) {
            Utils.err("Can't import database: " + e.getMessage());
        } finally {
            Utils.closeStreams(fileInputStream, fileOutputStream);
        }
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
//        new File(String.format(STANDARD_DATABASE_PATH, context.getPackageName() + DATABASE_NAME)).delete();

//            TableUtils.createTable(connectionSource, Item.class);
//            TableUtils.createTable(connectionSource, Category.class);
//            TableUtils.createTable(connectionSource, Mod.class);
//            TableUtils.createTable(connectionSource, Recipe.class);
//            TableUtils.createTable(connectionSource, Favorite.class);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, Item.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            TableUtils.dropTable(connectionSource, Mod.class, true);
            TableUtils.dropTable(connectionSource, Recipe.class, true);
            TableUtils.dropTable(connectionSource, Favorite.class, true);
            TableUtils.dropTable(connectionSource, Icon.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Utils.err("error upgrading db " + DATABASE_NAME + "from ver " + oldVer + " to ver " + newVer + ": " + e.getMessage());
        }
    }

    @Override
    public void close() {
        super.close();
        itemDAO = null;
    }
}
