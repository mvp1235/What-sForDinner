package com.example.mvp.whatsfordinner;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Grocery object, containing a list of distinct Ingredients
 */
public class Grocery {
    private ArrayList<Ingredient> distinctList;

    /**
     * Default constructor
     * Loading all existing groceries from GROCERY_TABLE
     */
    public Grocery() {
        distinctList = MainScreenActivity.dbHelper.getAllGroceries();
    }

    /**
     * Get the distinct list of ingredients
     * @return the distinct list of ingredients
     */
    public ArrayList<Ingredient> getDistinctList() {
        return distinctList;
    }

    /**
     * Add new ingredient to the distinct list.
     * If already exist, increment the ingredient count, else add a new one to the list
     * @param ingredient ingredient to be add
     */
    public void addIngredient(Ingredient ingredient) {
        int pos = ingredientPosition(ingredient);
        if (pos == -1) {
            distinctList.add(ingredient);
        } else {
            distinctList.get(pos).incrementCount();
        }
    }

    /**
     * Helper function for addIngredient(Ingredient ingredient) method.
     * Convert dropdownName to an actual Ingredient
     * @param dropdownName dropdown name of the ingredient, e.g. Salt (2 tsp)
     */
    public void addIngredient(String dropdownName) {
        int index1 = dropdownName.indexOf("(");
        String name = dropdownName.substring(0, index1);
        int index2 = dropdownName.indexOf(" ", index1);
        int quantity = Integer.parseInt(dropdownName.substring(index1+1, index2));
        int index3 = dropdownName.indexOf(")");
        String unit = dropdownName.substring(index2+1, index3);
        addIngredient(new Ingredient(name, quantity, unit));
    }

    /**
     * Remove an ingredient from the list by decrementing its count.
     * If not exist, then nothing will happen
     * @param ingredient the ingredient to be removed
     */
    public void removeIngredient(Ingredient ingredient) {
        int pos = ingredientPosition(ingredient);
        if (pos != -1) {
            distinctList.get(pos).decrementCount();
        }
    }

    /**
     * Helper function for removeIngredient(Ingredient ingredient)
     * Convert dropdownName to an actual Ingredient
     * @param dropdownName dropdown name of the ingredient, e.g. Salt (2 tsp)
     */
    public void removeIngredient(String dropdownName) {
        int index1 = dropdownName.indexOf("(");
        String name = dropdownName.substring(0, index1);
        int index2 = dropdownName.indexOf(" ", index1);
        int quantity = Integer.parseInt(dropdownName.substring(index1+1, index2));
        int index3 = dropdownName.indexOf(")");
        String unit = dropdownName.substring(index2+1, index3);
        removeIngredient(new Ingredient(name, quantity, unit));
    }

    /**
     * Find the position of an ingredient in the list
     * @param ingredient ingredient to be used
     * @return the position index of the ingredient in the list, -1 if not found
     */
    public int ingredientPosition(Ingredient ingredient) {
        for (int i=0; i<distinctList.size(); i++) {
            if (distinctList.get(i).getName().equalsIgnoreCase(ingredient.getName())) {
                return i;
            }
        }
        return -1;
    }
}
