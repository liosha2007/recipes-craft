package com.github.liosha2007.android.recipes.craft.controller;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Environment;
import android.os.StatFs;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.controller.BaseActivityController;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.recipes.craft.view.BrowserView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author liosha on 29.09.2014.
 */
public class BrowserController extends BaseActivityController<BrowserView> {
    public static final String INTENT_ACTION_SELECT_DIR = "com.github.liosha2007.android.recipes.craft.controller.SELECT_DIRECTORY_ACTION";
    public static final String INTENT_ACTION_SELECT_FILE = "com.github.liosha2007.android.recipes.craft.controller.SELECT_FILE_ACTION";
    public static final String SHOW_CANNOT_READ_PARAMETER = "com.github.liosha2007.android.recipes.craft.controller.SHOW_CANNOT_READ_PARAMETER";
    public static final String FILTER_EXTENSION = "com.github.liosha2007.android.recipes.craft.controller.FILTER_EXTENSION";
    public static final String START_DIRECTORY_PARAMETER = "com.github.liosha2007.android.recipes.craft.controller.START_DIRECTORY_PARAMETER";
    public static final String RETURN_DIRECTORY_PARAMETER = "com.github.liosha2007.android.recipes.craft.controller.RETURN_DIRECTORY_PARAMETER";
    public static final String RETURN_FILE_PARAMETER = "com.github.liosha2007.android.recipes.craft.controller.RETURN_FILE_PARAMETER";
    private static final int SELECT_DIRECTORY = 1;
    private static final int SELECT_FILE = 2;
    private static int currentAction = -1;
    private ArrayList<String> pathDirsList = new ArrayList<String>();
    private List<BrowserView.Item> fileList = new ArrayList<BrowserView.Item>();
    private File path = null;
    private boolean directoryShownIsEmpty = false;
    private boolean showHiddenFilesAndDirs = true;
    private String filterFileExtension = null;

    public BrowserController() {
        super(new BrowserView());
    }

    public static long getFreeSpace(String path) {
        StatFs stat = new StatFs(path);
        return (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
    }

    public static String formatBytes(long bytes) {
        String retStr = "";
        if (bytes > 1073741824) {// Add GB
            long gbs = bytes / 1073741824;
            retStr += (Long.valueOf(gbs)).toString() + "GB ";
            bytes = bytes - (gbs * 1073741824);
        }
        if (bytes > 1048576) {// Add GB
            long mbs = bytes / 1048576;
            retStr += (Long.valueOf(mbs)).toString() + "MB ";
            bytes = bytes - (mbs * 1048576);
        }
        if (bytes > 1024) {
            long kbs = bytes / 1024;
            retStr += (Long.valueOf(kbs)).toString() + "KB";
            bytes = bytes - (kbs * 1024);
        } else {
            retStr += (Long.valueOf(bytes)).toString() + " bytes";
        }
        return retStr;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Intent thisInt = this.getIntent();
        currentAction = SELECT_DIRECTORY;
        if (thisInt.getAction().equalsIgnoreCase(INTENT_ACTION_SELECT_FILE)) {
            currentAction = SELECT_FILE;
        }
        showHiddenFilesAndDirs = thisInt.getBooleanExtra(
                SHOW_CANNOT_READ_PARAMETER, true);
        filterFileExtension = thisInt.getStringExtra(FILTER_EXTENSION);

        setInitialDirectory();
        parseDirectoryPath();
        loadFileList();
        int compoundDrawablePadding = (int) (3 * getResources().getDisplayMetrics().density + 0.5f);
        view.createFileListAdapter(fileList, compoundDrawablePadding);
        view.initializeButtons();
        view.initializeFileListView();
        updateCurrentDirectoryTextView();
    }

    private void setInitialDirectory() {
        Intent thisInt = this.getIntent();
        String requestedStartDir = thisInt
                .getStringExtra(START_DIRECTORY_PARAMETER);
        if (requestedStartDir != null && requestedStartDir.length() > 0) {
            File tempFile = new File(requestedStartDir);
            if (tempFile.isDirectory())
                this.path = tempFile;
        }
        if (this.path == null) {
            if (Environment.getExternalStorageDirectory().isDirectory()
                    && Environment.getExternalStorageDirectory().canRead())
                path = Environment.getExternalStorageDirectory();
            else
                path = new File("/");
        }
    }

    private void parseDirectoryPath() {
        pathDirsList.clear();
        String pathString = path.getAbsolutePath();
        String[] parts = pathString.split("/");
        int i = 0;
        while (i < parts.length) {
            pathDirsList.add(parts[i]);
            i++;
        }
    }


    public void loadDirectoryUp() {
        String s = pathDirsList.remove(pathDirsList.size() - 1);
        path = new File(path.toString().substring(0,
                path.toString().lastIndexOf(s)));
        fileList.clear();
    }

    public void updateCurrentDirectoryTextView() {
        int i = 0;
        String curDirString = "";
        while (i < pathDirsList.size()) {
            curDirString += pathDirsList.get(i) + "/";
            i++;
        }
        if (pathDirsList.size() == 0) {
            view.enableUpButton(false);
            curDirString = "/";
        } else
            view.enableUpButton(true);
        long freeSpace = getFreeSpace(curDirString);
        String formattedSpaceString = formatBytes(freeSpace);
        if (freeSpace == 0) {
            File currentDir = new File(curDirString);
            if (!currentDir.canWrite())
                formattedSpaceString = "NON Writable";
        }
        view.setCurrentDirectoryText(formattedSpaceString, curDirString);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void returnDirectoryFinishActivity() {
        Intent retIntent = new Intent();
        retIntent.putExtra(RETURN_DIRECTORY_PARAMETER, path.getAbsolutePath());
        this.setResult(RESULT_OK, retIntent);
        this.finish();
    }

    private void returnFileFinishActivity(String filePath) {
        Intent retIntent = new Intent();
        retIntent.putExtra(RETURN_FILE_PARAMETER, filePath);
        this.setResult(RESULT_OK, retIntent);
        this.finish();
    }

    public void loadFileList() {
        try {
            path.mkdirs();
        } catch (SecurityException e) {
            Utils.err("unable to write on the sd card ");
        }
        fileList.clear();
        if (path.exists() && path.canRead()) {
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    boolean showReadableFile = showHiddenFilesAndDirs
                            || sel.canRead();
                    if (currentAction == SELECT_DIRECTORY) {
                        return (sel.isDirectory() && showReadableFile);
                    }
                    if (currentAction == SELECT_FILE) {
                        if (sel.isFile() && filterFileExtension != null) {
                            return (showReadableFile && sel.getName().endsWith(
                                    filterFileExtension));
                        }
                        return (showReadableFile);
                    }
                    return true;
                }
            };
            String[] fList = path.list(filter);
            this.directoryShownIsEmpty = false;
            for (int i = 0; i < fList.length; i++) {
                File sel = new File(path, fList[i]);
                Utils.err("File:" + fList[i] + " readable:" + (Boolean.valueOf(sel.canRead())).toString());
                int drawableID = R.drawable.file_icon;
                boolean canRead = sel.canRead();
                if (sel.isDirectory()) {
                    if (canRead) {
                        drawableID = R.drawable.folder_icon;
                    } else {
                        drawableID = R.drawable.folder_icon_light;
                    }
                }
                fileList.add(i, new BrowserView.Item(fList[i], drawableID));
            }
            if (fileList.size() == 0) {
                this.directoryShownIsEmpty = true;
                fileList.add(0, new BrowserView.Item("Directory is empty", -1));
            } else {
                Collections.sort(fileList, new ItemFileNameComparator());
            }
        } else {
            Utils.err("path does not exist or cannot be read");
        }
    }


    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Log.d(LOGTAG, "ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            Log.d(LOGTAG, "ORIENTATION_PORTRAIT");
        }
    }

    public boolean isSelectDirectory() {
        return currentAction == SELECT_DIRECTORY;
    }

    public void onItemClicked(int position, ArrayAdapter<BrowserView.Item> adapter) {
        String chosenFile = fileList.get(position).file;
        File sel = new File(path + "/" + chosenFile);
        if (sel.isDirectory()) {
            if (sel.canRead()) {
                pathDirsList.add(chosenFile);
                path = new File(sel + "");
                loadFileList();
                adapter.notifyDataSetChanged();
                updateCurrentDirectoryTextView();
            } else {
                showToast("Path does not exist or cannot be read");
            }
        } else {
            if (!directoryShownIsEmpty) {
                returnFileFinishActivity(sel.getAbsolutePath());
            }
        }
    }

    private class ItemFileNameComparator implements Comparator<BrowserView.Item> {
        public int compare(BrowserView.Item lhs, BrowserView.Item rhs) {
            return lhs.file.toLowerCase().compareTo(rhs.file.toLowerCase());
        }
    }
}
