package com.github.liosha2007.android.recipes.craft.fragment.recipe.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.fragment.view.BaseFragmentView;
import com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.NotesController;

/**
 * Created by liosha on 13.05.2014.
 */
public class NotesFragment extends BaseFragmentView<NotesController> {
    public NotesFragment() {
        super(R.layout.layout_recipe_fragment_notes);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        view(R.id.recipe_tab_note_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view(R.id.recipe_tab_note).getVisibility() == View.VISIBLE){
                    controller.onEditClicked();
                } else {
                    controller.onSaveClicked(
                            NotesFragment.this.<EditText>view(R.id.recipe_tab_note_edit).getText().toString(),
                            (Integer) NotesFragment.this.view(R.id.recipe_tab_note).getTag()
                    );
                }
            }
        });
    }

    public void showNote(String note, int itemId) {
        TextView noteView = this.view(R.id.recipe_tab_note);
        noteView.setText(note);
        noteView.setTag(itemId);
    }

    public void switchToEdit() {
        TextView noteText = view(R.id.recipe_tab_note);
        String noteString = noteText.getText().toString();
        EditText noteEditor = view(R.id.recipe_tab_note_edit);
        noteEditor.setText(noteString);
        noteEditor.setVisibility(View.VISIBLE);
        noteText.setVisibility(View.GONE);
        this.<Button>view(R.id.recipe_tab_note_save).setText("Сохранить");
    }

    public void switchToView(String noteText) {
        this.<TextView>view(R.id.recipe_tab_note).setText(noteText);
        view(R.id.recipe_tab_note_edit).setVisibility(View.GONE);
        view(R.id.recipe_tab_note).setVisibility(View.VISIBLE);
        this.<Button>view(R.id.recipe_tab_note_save).setText("Редактировать");
    }
}
