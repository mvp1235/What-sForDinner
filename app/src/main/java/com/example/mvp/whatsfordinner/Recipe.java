package com.example.mvp.whatsfordinner;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Recipe {
    static String defaultURI = "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Ffile%2F67/ORIGINAL/NONE/1880254826";
    private String name;
    private ArrayList<String> ingredients;
    private String imageURI;
    private String direction;
    private int count;
    private int calories;
    private int carbohydrates;
    private int fat;
    private int protein;
    private int calcium;
    private int cholesterol;
    private int sodium;

    public Recipe(String name) {
        this.name = name;
        ingredients = new ArrayList<String>();
        this.imageURI = defaultURI;
        direction = "";
    }

    public Recipe(Bundle b) {
        this.name = b.getString(DBHelper.RECIPE_NAME);
        String[] ings = b.getString(DBHelper.RECIPE_INGREDIENTS).split(",");
        this.ingredients = new ArrayList<>(Arrays.asList(ings));
        this.imageURI = b.getString(DBHelper.RECIPE_IMAGE);
        this.direction = b.getString(DBHelper.RECIPE_DIRECTION);
        this.count = b.getInt(DBHelper.RECIPE_COUNT);
        this.calories = b.getInt(DBHelper.RECIPE_CALORIES);
        this.carbohydrates = b.getInt(DBHelper.RECIPE_CARB);
        this.fat = b.getInt(DBHelper.RECIPE_FAT);
        this.protein = b.getInt(DBHelper.RECIPE_PROTEIN);
        this.calcium = b.getInt(DBHelper.RECIPE_CALCIUM);
        this.cholesterol = b.getInt(DBHelper.RECIPE_CHOLESTEROL);
        this.sodium = b.getInt(DBHelper.RECIPE_SODIUM);
    }

    public Recipe(String name, ArrayList<String> ingredients, String imageURI, String direction, int count, int calories, int carbohydrates, int fat, int protein, int calcium, int cholesterol, int sodium) {
        this.name = name;
        this.ingredients = ingredients;
        this.imageURI = imageURI;
        this.direction = direction;
        this.count = count;
        this.calories = calories;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.protein = protein;
        this.calcium = calcium;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
    }

    public String buildIngredientList() {
        String result = "";
        ArrayList<String> distinctList = new ArrayList<>();
        for (int i=0; i<ingredients.size(); i++) {
            if (!distinctList.contains(ingredients.get(i))) {
                distinctList.add(ingredients.get(i));
            }
        }

        for (int i=0; i<distinctList.size(); i++) {
            String temp = distinctList.get(i);
            int count = Collections.frequency(ingredients, temp);
            if (i > 0) result += "\n";
            if (count > 0) {
                temp = temp.replace("1", Integer.toString(count));
            }
            result += temp;
        }
        return result;
    }

    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putString(DBHelper.RECIPE_NAME, this.name);
        String ingredientString = "";
        for (int i=0; i<ingredients.size(); i++) {
            if (i > 0) ingredientString += ",";
            ingredientString += ingredients.get(i);
        }
        b.putString(DBHelper.RECIPE_INGREDIENTS, ingredientString);
        b.putString(DBHelper.RECIPE_IMAGE, this.imageURI);
        b.putString(DBHelper.RECIPE_DIRECTION, this.direction);
        b.putInt(DBHelper.RECIPE_COUNT, this.count);
        b.putInt(DBHelper.RECIPE_CALORIES, this.calories);
        b.putInt(DBHelper.RECIPE_CARB, this.carbohydrates);
        b.putInt(DBHelper.RECIPE_FAT, this.fat);
        b.putInt(DBHelper.RECIPE_PROTEIN, this.protein);
        b.putInt(DBHelper.RECIPE_CALCIUM, this.calcium);
        b.putInt(DBHelper.RECIPE_CHOLESTEROL, this.cholesterol);
        b.putInt(DBHelper.RECIPE_SODIUM, this.sodium);

        return b;
    }

    public void incrementCount() { count++; }
    public void decrementCount() { if(count > 0) count--;}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCalcium() {
        return calcium;
    }

    public void setCalcium(int calcium) {
        this.calcium = calcium;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getIngredientsString() {
        String result = "";
        for (int i=0; i<ingredients.size(); i++) {
            if (i > 0) result += ",";
            result += ingredients.get(i);
        }
        return result;
    }

    public String getImageURI() {
        return imageURI;
    }

    public String getDirection() {
        return direction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getMealString() {
        return name + " (" + count + ")";
    }
}
