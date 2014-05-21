package com.github.liosha2007.android.recipes.craft.fragment.recipe.view;

import android.app.ActionBar;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.liosha2007.android.R;
import com.github.liosha2007.android.library.common.Utils;
import com.github.liosha2007.android.library.fragment.view.BaseFragmentView;
import com.github.liosha2007.android.recipes.craft.common.RecipeType;
import com.github.liosha2007.android.recipes.craft.database.domain.Item;
import com.github.liosha2007.android.recipes.craft.database.domain.Recipe;
import com.github.liosha2007.android.recipes.craft.fragment.recipe.controller.RecipeController;

import java.util.List;

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

    public void createAccordion(Item result, List<Recipe> recipes, boolean addDeleteMode) {
        LinearLayout parent = view(R.id.accordion_layout);
        for (final Recipe recipe : recipes) {
            RecipeType recipeType = RecipeType.fromValue(recipe.getType());
            LayoutInflater inflater = controller.getActivity().getLayoutInflater();
            LinearLayout accordionLayout = (LinearLayout) inflater.inflate(R.layout.layout_recipe_fragment_recipe_accordion, parent, false);
            int recipeNumber = recipes.indexOf(recipe) + 1;
            Item item;

            Button recipeButton = Utils.view(accordionLayout, R.id.recipe_craft_recipe_button);
            recipeButton.setText(String.format("Рецепт %d", recipeNumber));
            recipeButton.setTag(R.string.LAYOUT_STATE, ACCORDION_CLOSED);
            recipeButton.setTag(R.string.RECIPE_ID, recipe.getId() + "_" + recipeNumber);
            recipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view instanceof Button) {
                        controller.onAccordionClicked((Button) view);
                    }
                }
            });
            Utils.<LinearLayout>view(accordionLayout, R.id.recipe_craft_recipe_layout).setTag(R.string.RECIPE_ID, recipe.getId() + "_" + recipeNumber);

            if (addDeleteMode) {
                Button deleteButton = Utils.view(accordionLayout, R.id.recipe_craft_delete_button);
                deleteButton.setVisibility(View.VISIBLE);
                deleteButton.setTag(recipe.getId());
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view instanceof Button) {
                            controller.onDeleteClicked(Integer.parseInt(view.getTag().toString()));
                        }
                    }
                });
            }

            if (recipeType == RecipeType.CRAFTING_TABLE) {
                item = recipe.getP1x1();
                Utils.<ImageView>view(accordionLayout, R.id.p_1_x_1).setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? null : item.getIcon()));
                item = recipe.getP1x2();
                Utils.<ImageView>view(accordionLayout, R.id.p_1_x_2).setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? null : item.getIcon()));
                item = recipe.getP1x3();
                Utils.<ImageView>view(accordionLayout, R.id.p_1_x_3).setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? null : item.getIcon()));

                item = recipe.getP2x1();
                Utils.<ImageView>view(accordionLayout, R.id.p_2_x_1).setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? null : item.getIcon()));
                item = recipe.getP2x2();
                Utils.<ImageView>view(accordionLayout, R.id.p_2_x_2).setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? null : item.getIcon()));
                item = recipe.getP2x3();
                Utils.<ImageView>view(accordionLayout, R.id.p_2_x_3).setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? null : item.getIcon()));

                item = recipe.getP3x1();
                Utils.<ImageView>view(accordionLayout, R.id.p_3_x_1).setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? null : item.getIcon()));
                item = recipe.getP3x2();
                Utils.<ImageView>view(accordionLayout, R.id.p_3_x_2).setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? null : item.getIcon()));
                item = recipe.getP3x3();
                Utils.<ImageView>view(accordionLayout, R.id.p_3_x_3).setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? null : item.getIcon()));
            } else {
                // TODO: Implement others
                Utils.deb("Implement others layouts for make items");
            }

            parent.addView(accordionLayout);
            // Open first recipe
            if (recipes.indexOf(recipe) == 0) {
                recipeButton.callOnClick();
            }
        }
    }

    public void switchAccordion(Button button) {
        LinearLayout parent = ((LinearLayout) button.getParent().getParent());
        int childCount = parent.getChildCount();
        String recipeId = (String) button.getTag(R.string.RECIPE_ID);
        for (int n = 0; n < childCount; n++) {
            if (parent.getChildAt(n) instanceof LinearLayout && parent.getChildAt(n).getTag(R.string.RECIPE_ID).equals(recipeId)) {
                if (ACCORDION_CLOSED.equals(button.getTag(R.string.LAYOUT_STATE))) {
                    button.setTextColor(Color.RED);
                    button.setTag(R.string.LAYOUT_STATE, ACCORDION_OPENED);
                    parent.getChildAt(n).setVisibility(View.VISIBLE);
//                    button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher, 0);
                } else if (parent.getChildAt(n) instanceof LinearLayout) {
                    button.setTextColor(Color.BLACK);
                    button.setTag(R.string.LAYOUT_STATE, ACCORDION_CLOSED);
                    parent.getChildAt(n).setVisibility(View.GONE);
//                    button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher, 0);
                }
            }
        }
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

    public void showCreateRecipePopup() {
        view(R.id.create_recipe_popup).setVisibility(View.VISIBLE);
    }

    public void showNewRecipeItem(Item item, int imageViewId) {
        ImageView imageView = view(imageViewId);
        if (imageView != null){
            imageView.setImageDrawable(Utils.loadImageFromAssets(controller.getActivity(), item == null ? "empty.png" : item.getIcon()));
            imageView.setTag(item == null ? null : item.getId());
        }
    }

    public void createRecipeControls() {
        AbsoluteLayout createButton = view(R.id.create_recipe);
        createButton.setVisibility(View.VISIBLE);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onCreateClicked();
            }
        });

        this.<ImageView>view(R.id.p_1_x_1_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    controller.onCraftingPointSelected(R.id.p_1_x_1_create);
                }
            }
        });
        this.<ImageView>view(R.id.p_1_x_2_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    controller.onCraftingPointSelected(R.id.p_1_x_2_create);
                }
            }
        });
        this.<ImageView>view(R.id.p_1_x_3_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    controller.onCraftingPointSelected(R.id.p_1_x_3_create);
                }
            }
        });

        this.<ImageView>view(R.id.p_2_x_1_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    controller.onCraftingPointSelected(R.id.p_2_x_1_create);
                }
            }
        });
        this.<ImageView>view(R.id.p_2_x_2_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    controller.onCraftingPointSelected(R.id.p_2_x_2_create);
                }
            }
        });
        this.<ImageView>view(R.id.p_2_x_3_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    controller.onCraftingPointSelected(R.id.p_2_x_3_create);
                }
            }
        });

        this.<ImageView>view(R.id.p_3_x_1_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    controller.onCraftingPointSelected(R.id.p_3_x_1_create);
                }
            }
        });
        this.<ImageView>view(R.id.p_3_x_2_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    controller.onCraftingPointSelected(R.id.p_3_x_2_create);
                }
            }
        });
        this.<ImageView>view(R.id.p_3_x_3_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof ImageView) {
                    controller.onCraftingPointSelected(R.id.p_3_x_3_create);
                }
            }
        });
        this.<Button>view(R.id.add_recipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.onAddRecipeClicked(
                        (Integer)view(R.id.p_1_x_1_create).getTag(),
                        (Integer)view(R.id.p_1_x_2_create).getTag(),
                        (Integer)view(R.id.p_1_x_3_create).getTag(),

                        (Integer)view(R.id.p_2_x_1_create).getTag(),
                        (Integer)view(R.id.p_2_x_2_create).getTag(),
                        (Integer)view(R.id.p_2_x_3_create).getTag(),

                        (Integer)view(R.id.p_3_x_1_create).getTag(),
                        (Integer)view(R.id.p_3_x_2_create).getTag(),
                        (Integer)view(R.id.p_3_x_3_create).getTag()
                );
            }
        });
    }
}
