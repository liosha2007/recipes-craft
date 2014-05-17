package com.github.liosha2007.android.recipes.craft.common;

/**
 * Created by liosha on 23.04.2014.
 */
public enum RecipeType {
    // UPDATE IN README!!!
    CRAFTING_TABLE(1), // Craft in crafting table // Крафт в верстаке или кармане
    DROP(2), // Drops out at murder mobs // Выпадает при убийстве мобов
    CULTIVATION(3) // It is necessary to grow up // Нужно выращивать
    ;

    private final int id;

    /**
     * Constructor
     *
     * @param id annotation type id
     */
    private RecipeType(int id) {
        this.id = id;
    }

    /**
     * Check if annotation type is equal
     *
     * @param id annotation type id to compare
     * @return
     */
    public boolean equalsName(int id) {
        return this.id == id;
    }

    /**
     * Convert id to string
     *
     * @return id
     */
    @Override
    public String toString() {
        return Integer.toString(this.id);
    }

    /**
     * Get annotation type by id
     *
     * @param id annotation type id
     * @return annotation type
     */
    public static RecipeType fromValue(int id) {
        switch (id){
            case 1:
                return CRAFTING_TABLE;
            case 2:
                return DROP;
            case 3:
                return CULTIVATION;
            default:
                return CRAFTING_TABLE;
        }
    }

    /**
     * Get id
     *
     * @return id
     */
    public int value() {
        return id;
    }

}
