package com.github.liosha2007.android.recipes.craft.view;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.recipes.craft.controller.BrowserController;

import java.util.List;

/**
 * @author liosha on 29.09.2014.
 */
public class BrowserView extends BaseActivityView<BrowserController> {
    private ArrayAdapter<Item> adapter;

    public BrowserView() {
        super(R.layout.layout_file_browser);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void createFileListAdapter(final List<Item> fileList, final int compoundDrawablePadding) {
        adapter = new ArrayAdapter<Item>(controller, android.R.layout.select_dialog_item, android.R.id.text1, fileList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                int drawableID = 0;
                if (fileList.get(position).icon != -1) {
                    drawableID = fileList.get(position).icon;
                }
                textView.setCompoundDrawablesWithIntrinsicBounds(drawableID, 0, 0, 0);
                textView.setEllipsize(null);
                textView.setCompoundDrawablePadding(compoundDrawablePadding);
                textView.setBackgroundColor(Color.LTGRAY);
                return view;
            }
        };
    }

    public void initializeButtons() {
        Button upDirButton = view(R.id.upDirectoryButton);
        upDirButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                controller.loadDirectoryUp();
                controller.loadFileList();
                adapter.notifyDataSetChanged();
                controller.updateCurrentDirectoryTextView();
            }
        });
        Button selectFolderButton = view(R.id.selectCurrentDirectoryButton);
        if (controller.isSelectDirectory()) {
            selectFolderButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    controller.returnDirectoryFinishActivity();
                }
            });
        } else {
            selectFolderButton.setVisibility(View.GONE);
        }
    }

    public void enableUpButton(boolean enable) {
        view(R.id.upDirectoryButton).setEnabled(enable);
    }

    public void setCurrentDirectoryText(String formattedSpaceString, String curDirString) {
        this.<Button>view(R.id.selectCurrentDirectoryButton).setText("Select\n[" + formattedSpaceString + "]");
        this.<TextView>view(R.id.currentDirectoryTextView).setText("Current directory: " + curDirString);
    }

    public void initializeFileListView() {
        ListView lView = view(R.id.fileListView);
        lView.setBackgroundColor(Color.LTGRAY);
        LinearLayout.LayoutParams lParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lParam.setMargins(15, 5, 15, 5);
        lView.setAdapter(this.adapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                controller.onItemClicked(position, adapter);
            }
        });
    }

    public static class Item {
        public String file;
        public int icon;

        public Item(String file, Integer icon) {
            this.file = file;
            this.icon = icon;
        }

        @Override
        public String toString() {
            return file;
        }
    }

}
