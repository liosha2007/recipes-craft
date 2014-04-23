package com.github.liosha2007.android.recipes.craft.common;

/**
 * Created by liosha on 23.04.2014.
 */
public enum  RecipeType {
    CRAFTING_TABLE(1);

    private final int id;

    /**
     * Constructor
     * @param id annotation type id
     */
    private RecipeType(int id) {
        this.id = id;
    }

    /**
     * Check if annotation type is equal
     * @param id annotation type id to compare
     * @return
     */
    public boolean equalsName(int id){
        return this.id == id;
    }

    /**
     * Convert id to string
     * @return id
     */
    @Override
    public String toString(){
        return Integer.toString(this.id);
    }

    /**
     * Get annotation type by id
     * @param id annotation type id
     * @return annotation type
     */
    public static RecipeType fromValue(int id) {
        return (id < 1 || id > 1) ? null : RecipeType.values()[id];
    }

    /**
     * Get id
     * @return id
     */
    public int value() {
        return id;
    }

}
