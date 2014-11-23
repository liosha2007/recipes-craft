package com.github.liosha2007.android.recipes.craft.common;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.database.DaoFactory;
import com.github.liosha2007.android.library.database.IDaoCallback;
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
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Aleksey Permyakov (23.11.2014).
 */
public class DatabaseApplication extends Application {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RecipesCraft.db";
    private static final String STANDARD_DATABASE_PATH = "/data/data/%s/databases/";

    private static Map<Class, BaseDaoImpl> clazz2dao = new HashMap<Class, BaseDaoImpl>(6);

    @Override
    public void onCreate() {
        DaoFactory.initialize(new IDaoCallback() {
            @Override
            public int getDatabaseVersion() {
                return DATABASE_VERSION;
            }

            @Override
            public <T extends BaseDaoImpl> String getDatabaseName() {
                return DATABASE_NAME;
            }

            @Override
            public String getDatabaseDirectory() {
                return String.format(STANDARD_DATABASE_PATH, DatabaseApplication.this.getApplicationContext().getPackageName());
            }

            @Override
            public <T extends BaseDaoImpl> T getDao(ConnectionSource connectionSource, Class clazz) throws Exception {
                if (!clazz2dao.containsKey(clazz)) {
                    if (clazz.equals(Category.class)) {
                        clazz2dao.put(clazz, new CategoryDAO(connectionSource, clazz));
                    } else if (clazz.equals(Favorite.class)) {
                        clazz2dao.put(clazz, new FavoriteDAO(connectionSource, clazz));
                    } else if (clazz.equals(Icon.class)) {
                        clazz2dao.put(clazz, new IconDAO(connectionSource, clazz));
                    } else if (clazz.equals(Item.class)) {
                        clazz2dao.put(clazz, new ItemDAO(connectionSource, clazz));
                    } else if (clazz.equals(Mod.class)) {
                        clazz2dao.put(clazz, new ModDAO(connectionSource, clazz));
                    } else if (clazz.equals(Recipe.class)) {
                        clazz2dao.put(clazz, new RecipeDAO(connectionSource, clazz));
                    }
                }
                return (T) clazz2dao.get(clazz);
            }

            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
                Context context = getApplicationContext();
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
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
                try {
                    TableUtils.dropTable(connectionSource, Item.class, true);
                    TableUtils.dropTable(connectionSource, Category.class, true);
                    TableUtils.dropTable(connectionSource, Mod.class, true);
                    TableUtils.dropTable(connectionSource, Recipe.class, true);
                    TableUtils.dropTable(connectionSource, Favorite.class, true);
                    TableUtils.dropTable(connectionSource, Icon.class, true);
                    onCreate(sqLiteDatabase, connectionSource);
                } catch (SQLException e) {
                    Utils.err("error upgrading db " + DATABASE_NAME + "from ver " + oldVersion + " to ver " + newVersion + ": " + e.getMessage());
                }
            }
        });
        super.onCreate();
    }
}
