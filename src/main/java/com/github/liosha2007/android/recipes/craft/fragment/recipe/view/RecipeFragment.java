package com.github.liosha2007.android.recipes.craft.fragment.recipe.view;

import android.app.ActionBar;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.fragment.view.BaseFragmentView;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;
import com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.RecipeController;

import java.util.List;
import java.util.UUID;

/**
 * Created by liosha on 13.05.2014.
 */
public class RecipeFragment extends BaseFragmentView<RecipeController> {
    private static final Integer ACCORDION_OPENED = Utils.makeID();
    private static final Integer ACCORDION_CLOSED = Utils.makeID();

//    public static final String TAB_RECIPE = UUID.randomUUID().toString();
//    public static final String TAB_DESCRIPTION = UUID.randomUUID().toString();
//    public static final String TAB_MATERIALS = UUID.randomUUID().toString();
//    public static final String TAB_NOTES = UUID.randomUUID().toString();

    public RecipeFragment() {
        super(R.layout.layout_recipe_fragment_recipe);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void createAccordion(Item result, List<Recipe> recipes) {
        LinearLayout accordionLayout = view(R.id.accordion_layout);
        for (Recipe recipe : recipes) {
            Button accordionButton = new Button(controller.getActivity());
            accordionButton.setId(recipe.getId());
            accordionButton.setText("Рецепт " + (recipes.indexOf(recipe) + 1));
            accordionButton.setTypeface(null, 1);
            accordionButton.setTextSize(15);
            accordionButton.setGravity(Gravity.LEFT);
            accordionButton.setTextColor(Color.BLACK);
            accordionButton.setTag(ACCORDION_CLOSED);
            accordionButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher, 0);

            LinearLayout recipeLayout = new LinearLayout(controller.getActivity());
            recipeLayout.setOrientation(LinearLayout.VERTICAL);
            recipeLayout.addView(createCraftingArea(recipe));
            recipeLayout.setGravity(Gravity.CENTER);
            recipeLayout.setVisibility(TextView.GONE);

            accordionLayout.addView(accordionButton, new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
            accordionLayout.addView(recipeLayout, new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
            accordionButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (v instanceof Button) {
                        Button button = (Button) v;
                        Utils.deb("Clicked button " + v.getId() + " in accordion");
                        LinearLayout accordionLayout = view(R.id.accordion_layout);

                        if (ACCORDION_CLOSED.equals(button.getTag())) {
                            for (int n = 0; n < accordionLayout.getChildCount(); n++) {
                                if (button.getId() == accordionLayout.getChildAt(n).getId()) {
                                    button.setTextColor(Color.RED);
                                    button.setTag(ACCORDION_OPENED);
                                    View nextChild = accordionLayout.getChildAt(n + 1);
                                    if (nextChild != null) {
                                        nextChild.setVisibility(View.VISIBLE);
                                    }
                                    button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher, 0);
                                }
                            }
                        } else {
                            for (int n = 0; n < accordionLayout.getChildCount(); n++) {
                                View nextChild = accordionLayout.getChildAt(n + 1);
                                if (button.getId() == accordionLayout.getChildAt(n).getId() && nextChild != null && nextChild.getVisibility() == View.VISIBLE) {
                                    button.setTextColor(Color.BLACK);
                                    button.setTag(ACCORDION_CLOSED);
                                    nextChild.setVisibility(View.GONE);
                                    button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher, 0);
                                }
                            }
                        }
                    }
                }
            });
            if (recipes.size() == 1) {
                accordionButton.callOnClick();
            }
        }
        if (result != null) {
            this.<TextView>view(R.id.result_item_name).setText(result.getName());
            this.<ImageView>view(R.id.result_item_icon).setImageDrawable(
                    Utils.loadImageFromAssets(
                            controller.getActivity(),
                            result.getIcon()
                    )
            );
        }
    }

    public TableLayout createCraftingArea(Recipe recipe) {
        TableLayout tableLayout = new TableLayout(controller.getActivity());
        tableLayout.setStretchAllColumns(true);
        tableLayout.setBackgroundColor(Color.LTGRAY);
        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(300, 300, Gravity.CENTER));

        TableRow row;
        LinearLayout linearLayout;

        row = new TableRow(controller.getActivity());
        linearLayout = new LinearLayout(controller.getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(createCraftingPoint(recipe.getP1x1()));
        linearLayout.addView(createCraftingPoint(recipe.getP1x2()));
        linearLayout.addView(createCraftingPoint(recipe.getP1x3()));
        row.addView(linearLayout);
        tableLayout.addView(row);

        row = new TableRow(controller.getActivity());
        linearLayout = new LinearLayout(controller.getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(createCraftingPoint(recipe.getP2x1()));
        linearLayout.addView(createCraftingPoint(recipe.getP2x2()));
        linearLayout.addView(createCraftingPoint(recipe.getP2x3()));
        row.addView(linearLayout);
        tableLayout.addView(row);

        row = new TableRow(controller.getActivity());
        linearLayout = new LinearLayout(controller.getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(createCraftingPoint(recipe.getP3x1()));
        linearLayout.addView(createCraftingPoint(recipe.getP3x2()));
        linearLayout.addView(createCraftingPoint(recipe.getP3x3()));
        row.addView(linearLayout);
        tableLayout.addView(row);

        return tableLayout;
    }

    protected LinearLayout createCraftingPoint(Item item) {
        if (item == null) {
            Utils.deb("item is null in recipes");
        }
        LinearLayout itemLayout = new LinearLayout(controller.getActivity());
        itemLayout.setOrientation(LinearLayout.VERTICAL);

        ImageView imageView = new ImageView(controller.getActivity());
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(100, 80, 0.1f);
        imageViewParams.gravity = Gravity.CENTER;
        imageView.setLayoutParams(imageViewParams);
        imageView.setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? null : item.getIcon()));

        TextView textView = new TextView(controller.getActivity());
        ViewGroup.LayoutParams textViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20);
        textView.setLayoutParams(textViewParams);
        textView.setGravity(Gravity.CENTER);
        textView.setText(item == null ? null : item.getName());
        textView.setTextSize(12);

        itemLayout.addView(imageView);
        itemLayout.addView(textView);
        return itemLayout;
    }

    public void showRecipeNotFound() {
        TextView notFound = new TextView(controller.getActivity());
        notFound.setTextColor(Color.RED);
        notFound.setGravity(Gravity.CENTER);
        notFound.setTextSize(28);
        notFound.setText("Рецепт для этого предмета не найден!");

        this.<LinearLayout>view(R.id.accordion_layout)
                .addView(notFound, new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
    }
}
