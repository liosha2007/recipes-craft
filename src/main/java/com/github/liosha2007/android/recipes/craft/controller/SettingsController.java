package com.github.liosha2007.android.recipes.craft.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.database.DaoFactory;
import com.github.liosha2007.android.recipes.craft.common.Constants;
import com.github.liosha2007.android.recipes.craft.database.dao.IconDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Icon;
import com.github.liosha2007.android.recipes.craft.view.SettingsView;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;

/**
 * Created by liosha on 22.04.2014.
 */
public class SettingsController extends BaseActivityController<SettingsView> {
    private static final int EXPORT_DATABASE_EVENT = Utils.makeID();
    private static final int IMPORT_DATABASE_EVENT = Utils.makeID();
    private static final int UPLOAD_TEXTURES_EVENT = Utils.makeID();

    public SettingsController() {
        super(new SettingsView());
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean(Constants.ADD_DELETE_RECIPES, false)) {
            view.enableEditMode();
        }
    }

    public void onEditRecipesChecked(boolean checked) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.ADD_DELETE_RECIPES, checked);
        editor.apply();
    }

    public void onExportDatabaseClicked() {
        Intent intent = new Intent(BrowserController.INTENT_ACTION_SELECT_DIR,
                null,
                this,
                BrowserController.class);
        startActivityForResult(intent, EXPORT_DATABASE_EVENT);
    }

    public void onImportDatabaseClicked() {
        Intent intent = new Intent(BrowserController.INTENT_ACTION_SELECT_FILE,
                null,
                this,
                BrowserController.class);
        startActivityForResult(intent, IMPORT_DATABASE_EVENT);
    }

    public void onUploadTexturesClicked() {
        Intent intent = new Intent(BrowserController.INTENT_ACTION_SELECT_DIR,
                null,
                this,
                BrowserController.class);
        startActivityForResult(intent, UPLOAD_TEXTURES_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == EXPORT_DATABASE_EVENT) {
                exportDatabase(data.getStringExtra(BrowserController.RETURN_DIRECTORY_PARAMETER));
            } else if (requestCode == IMPORT_DATABASE_EVENT) {
                importDatabase(data.getStringExtra(BrowserController.RETURN_FILE_PARAMETER));
            } else if (requestCode == UPLOAD_TEXTURES_EVENT) {
                uploadTextures(data.getStringExtra(BrowserController.RETURN_DIRECTORY_PARAMETER));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadTextures(String directory) {
        final IconDAO iconDAO = daoFor(Icon.class);
        File[] listFiles = new File(directory).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.endsWith(".png");
            }
        });
        for (File file : listFiles) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[fileInputStream.available()];
                IOUtils.readFully(fileInputStream, bytes);
                Icon icon = new Icon();
                icon.setIcon(bytes);
                iconDAO.create(icon);
            } catch (Exception e) {
                Utils.war("Image skipped: " + file.getName());
            } finally {
                Utils.closeStreams(fileInputStream);
            }
        }
        Toast.makeText(this, "Иконки загружены!", Toast.LENGTH_LONG).show();
    }

    private void importDatabase(String file) {
        DaoFactory.importDatabaseFileFrom(this, file);
        Toast.makeText(this, "Импорт завершен!", Toast.LENGTH_LONG).show();
    }

    private void exportDatabase(String directory) {
        DaoFactory.exportDatabaseToFile(this, directory);
        Toast.makeText(this, "Экспорт завершен!", Toast.LENGTH_LONG).show();
    }

    public void onCreateCategoryClicked() {
        run(CreateCategoryController.class);
    }

    public void onCreateModClicked() {
        run(CreateModController.class);
    }

    public void onCreateItemClicked() {
        run(CreateItemController.class);
    }
}
