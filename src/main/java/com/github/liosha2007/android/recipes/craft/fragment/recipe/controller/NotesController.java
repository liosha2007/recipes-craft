package com.github.liosha2007.android.recipes.craft.fragment.recipe.controller;

import android.widget.Toast;

import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.fragment.controller.BaseFragmentController;
import com.github.liosha2007.android.recipes.craft.database.dao.ItemDAO;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.fragment.recipe.view.NotesFragment;

import java.sql.SQLException;

/**
 * Created by liosha on 13.05.2014.
 */
public class NotesController extends BaseFragmentController<NotesFragment> {
    public NotesController() {
        super(new NotesFragment());
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        final ItemDAO itemDAO = daoFor(Item.class);
        int itemId = getArguments().getInt(com.github.liosha2007.android.recipes.craft.controller.RecipeController.ITEM_ID, -1);
        Item item = itemDAO.queryForId(itemId);
        view.showNote(item.getNote(), itemId);
    }

    public void onEditClicked() {
        view.switchToEdit();
    }

    public void onSaveClicked(String noteText, Integer itemId) {
        if (itemId != null) {
            final ItemDAO itemDAO = daoFor(Item.class);
            Item item = itemDAO.queryForId(itemId);
            item.setNote(noteText);
            try {
                itemDAO.update(item);
                view.switchToView(noteText);
            } catch (SQLException e) {
                Utils.err("Can't save item note: " + e.getMessage());
                Toast.makeText(getActivity(), "Ошибка при сохранении заметки: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
