package com.github.liosha2007.android.recipes.craft.fragment;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.activity.view.BaseActivityView;
import com.github.liosha2007.android.recipes.craft.controller.RecipeController;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;

import java.util.List;

/**
 * Created by liosha on 22.04.2014.
 */
public class RecipeView extends BaseActivityView<RecipeController> {
//    private static final Integer ACCORDION_OPENED = Utils.makeID();
//    private static final Integer ACCORDION_CLOSED = Utils.makeID();

    public RecipeView() {
        super(R.layout.layout_recipe);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Tabs functional
//        TabHost tabHost = view(R.id.recipe_tab_host);
//
//        tabHost.setup();
//
//        TabHost.TabSpec spec;
//
//        spec = tabHost.newTabSpec(RecipeController.TAB_RECIPE);
//        spec.setContent(R.id.recipe_tab_recipe);
//        spec.setIndicator("Рецепты");
//        tabHost.addTab(spec);
//
//        spec = tabHost.newTabSpec(RecipeController.TAB_DESCRIPTION);
//        spec.setContent(R.id.recipe_tab_description);
//        spec.setIndicator("Описание");
//        tabHost.addTab(spec);
//
//        spec = tabHost.newTabSpec(RecipeController.TAB_MATERIALS);
//        spec.setContent(R.id.recipe_tab_materials);
//        spec.setIndicator("Материалы");
//        tabHost.addTab(spec);
//
//        spec = tabHost.newTabSpec(RecipeController.TAB_NOTES);
//        spec.setContent(R.id.recipe_tab_notes);
//        spec.setIndicator("Примечание");
//        tabHost.addTab(spec);
//
//        tabHost.setCurrentTab(0);
//
//        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//                if (RecipeController.TAB_RECIPE.equalsIgnoreCase(tabId)) {
//                    controller.onRecipeTabSelected();
//                } else if (RecipeController.TAB_DESCRIPTION.equalsIgnoreCase(tabId)) {
//                    controller.onDescriptionTabSelected();
//                } else if (RecipeController.TAB_MATERIALS.equalsIgnoreCase(tabId)) {
//                    controller.onMaterialsTabSelected();
//                } else if (RecipeController.TAB_NOTES.equalsIgnoreCase(tabId)) {
//                    controller.onNotesTabSelected();
//                }
//            }
//        });
    }

    public void createAccordion(Item result, List<Recipe> recipes) {
//        LinearLayout accordionLayout = view(R.id.accordion_layout);
//        for (Recipe recipe : recipes) {
//            Button accordionButton = new Button(controller);
//            accordionButton.setId(recipe.getId());
//            accordionButton.setText("Рецепт " + (recipes.indexOf(recipe) + 1));
//            accordionButton.setTypeface(null, 1);
//            accordionButton.setTextSize(15);
//            accordionButton.setGravity(Gravity.LEFT);
//            accordionButton.setTextColor(Color.BLACK);
//            accordionButton.setTag(ACCORDION_CLOSED);
//            accordionButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher, 0);
//
//            LinearLayout recipeLayout = new LinearLayout(controller);
//            recipeLayout.setOrientation(LinearLayout.VERTICAL);
//            recipeLayout.addView(createCraftingArea(recipe));
//            recipeLayout.setGravity(Gravity.CENTER);
//            recipeLayout.setVisibility(TextView.GONE);
//
//            accordionLayout.addView(accordionButton, new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
//            accordionLayout.addView(recipeLayout, new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
//            accordionButton.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    if (v instanceof Button) {
//                        Button button = (Button) v;
//                        Utils.deb("Clicked button " + v.getId() + " in accordion");
//                        LinearLayout accordionLayout = view(R.id.accordion_layout);
//
//                        if (ACCORDION_CLOSED.equals(button.getTag())) {
//                            for (int n = 0; n < accordionLayout.getChildCount(); n++) {
//                                if (button.getId() == accordionLayout.getChildAt(n).getId()) {
//                                    button.setTextColor(Color.RED);
//                                    button.setTag(ACCORDION_OPENED);
//                                    View nextChild = accordionLayout.getChildAt(n + 1);
//                                    if (nextChild != null) {
//                                        nextChild.setVisibility(View.VISIBLE);
//                                    }
//                                    button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher, 0);
//                                }
//                            }
//                        } else {
//                            for (int n = 0; n < accordionLayout.getChildCount(); n++) {
//                                View nextChild = accordionLayout.getChildAt(n + 1);
//                                if (button.getId() == accordionLayout.getChildAt(n).getId() && nextChild != null && nextChild.getVisibility() == View.VISIBLE) {
//                                    button.setTextColor(Color.BLACK);
//                                    button.setTag(ACCORDION_CLOSED);
//                                    nextChild.setVisibility(View.GONE);
//                                    button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher, 0);
//                                }
//                            }
//                        }
//                    }
//                }
//            });
//            if (recipes.size() == 1) {
//                accordionButton.callOnClick();
//            }
//        }
//        if (result != null) {
//            this.<TextView>view(R.id.result_item_name).setText(result.getName());
//            this.<ImageView>view(R.id.result_item_icon).setImageDrawable(
//                    Utils.loadImageFromAssets(
//                            controller,
//                            result.getIcon()
//                    )
//            );
//        }
    }

//    public TableLayout createCraftingArea(Recipe recipe) {
//        TableLayout tableLayout = new TableLayout(controller);
//        tableLayout.setStretchAllColumns(true);
//        tableLayout.setBackgroundColor(Color.LTGRAY);
//        tableLayout.setLayoutParams(new LinearLayout.LayoutParams(300, TableLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
//
//        TableRow row;
//        LinearLayout linearLayout;
//
//        row = new TableRow(controller);
//        linearLayout = new LinearLayout(controller);
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayout.addView(createCraftingPoint(recipe.getP1x1()));
//        linearLayout.addView(createCraftingPoint(recipe.getP1x2()));
//        linearLayout.addView(createCraftingPoint(recipe.getP1x3()));
//        row.addView(linearLayout);
//        tableLayout.addView(row);
//
//        row = new TableRow(controller);
//        linearLayout = new LinearLayout(controller);
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayout.addView(createCraftingPoint(recipe.getP2x1()));
//        linearLayout.addView(createCraftingPoint(recipe.getP2x2()));
//        linearLayout.addView(createCraftingPoint(recipe.getP2x3()));
//        row.addView(linearLayout);
//        tableLayout.addView(row);
//
//        row = new TableRow(controller);
//        linearLayout = new LinearLayout(controller);
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayout.addView(createCraftingPoint(recipe.getP3x1()));
//        linearLayout.addView(createCraftingPoint(recipe.getP3x2()));
//        linearLayout.addView(createCraftingPoint(recipe.getP3x3()));
//        row.addView(linearLayout);
//        tableLayout.addView(row);
//
//        return tableLayout;
//    }

//    protected LinearLayout createCraftingPoint(Item item) {
//        if (item == null) {
//            Utils.deb("item is null in recipes");
//        }
//        LinearLayout itemLayout = new LinearLayout(controller);
//        itemLayout.setOrientation(LinearLayout.VERTICAL);
//
//        ImageView imageView = new ImageView(controller);
//        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(100, 80, 0.1f);
//        imageViewParams.gravity = Gravity.CENTER;
//        imageView.setLayoutParams(imageViewParams);
//        imageView.setImageDrawable(Utils.loadImageFromAssets(controller, item == null ? null : item.getIcon()));
//
//        TextView textView = new TextView(controller);
//        ViewGroup.LayoutParams textViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20);
//        textView.setLayoutParams(textViewParams);
//        textView.setGravity(Gravity.CENTER);
//        textView.setText(item == null ? null : item.getName());
//        textView.setTextSize(12);
//
//        itemLayout.addView(imageView);
//        itemLayout.addView(textView);
//        return itemLayout;
//    }

    public void showRecipeNotFound() {
//        TextView notFound = new TextView(controller);
//        notFound.setTextColor(Color.RED);
//        notFound.setGravity(Gravity.CENTER);
//        notFound.setTextSize(28);
//        notFound.setText("Рецепт для этого предмета не найден!");
//
//        this.<LinearLayout>view(R.id.accordion_layout)
//                .addView(notFound, new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
    }

    public void showAdditionalItemInformation(Item item) {

    }
}
