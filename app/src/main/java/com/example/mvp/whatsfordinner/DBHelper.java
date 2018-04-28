package com.example.mvp.whatsfordinner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Database contains 5 tables in total
 * INGREDIENT_TABLE: stores the list of available ingredients to choose for making recipes, originated with a default list of common items first, then users can manually add more as they need
 * RECIPE_TABLE: stores the list of recipes which users added through the app
 * GROCERY_TABLE: store the list of ingredients needed to make the recipes added by the user
 * MEAL_TABLE: stores the list of meal planned for each meal of the day and each of week
 * NUTRITION_TABLE: stores the weekly nutritional value information which the user enter
 */
public class DBHelper extends SQLiteOpenHelper {
    //DEFINE DATABASE AND TABLE
    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "mealPlanner";
    private static final String INGREDIENT_TABLE = "ingredients";
    private static final String RECIPES_TABLE = "recipes";
    private static final String GROCERY_TABLE = "grocery";
    private static final String MEAL_TABLE = "meal";
    private static final String NUTRITION_TABLE = "nutrition";

    //DEFINE COLUMN NAMES FOR THE TABLE INGREDIENTS
    static final String INGREDIENT_ID = "id";
    static final String INGREDIENT_NAME = "name";
    static final String INGREDIENT_QUANTITY = "quantity";
    static final String INGREDIENT_UNIT = "unit";

    //DEFINE COLUMN NAMES FOR THE TABLE RECIPES
    static final String RECIPE_ID = "id";
    static final String RECIPE_NAME = "name";
    static final String RECIPE_INGREDIENTS = "ingredients";
    static final String RECIPE_IMAGE = "image";
    static final String RECIPE_DIRECTION = "direction";
    static final String RECIPE_COUNT = "count";
    static final String RECIPE_CALORIES = "calories";
    static final String RECIPE_CARB = "carbohydrate";
    static final String RECIPE_FAT = "fat";
    static final String RECIPE_PROTEIN = "protein";
    static final String RECIPE_CALCIUM = "calcium";
    static final String RECIPE_CHOLESTEROL = "cholesterol";
    static final String RECIPE_SODIUM = "sodium";

    //DEFINE COLUMN NAMES FOR THE TABLE GROCERY
    static final String GROCERY_ID = "id";
    static final String GROCERY_NAME = "name";
    static final String GROCERY_COUNT = "count";
    static final String GROCERY_UNIT = "unit";

    //DEFINE COLUMN NAMES FOR THE TABLE RECIPES
    static final String MEAL_ID = "id";
    static final String MEAL_NAME = "name";
    static final String MEAL_DAY = "dayOfWeek";
    static final String MEAL_TIME = "timeOfDay";
    static final String MEAL_CALORIES = "calories";
    static final String MEAL_CARB = "carb";
    static final String MEAL_FAT = "fat";
    static final String MEAL_PROTEIN = "protein";
    static final String MEAL_CALCIUM = "calcium";
    static final String MEAL_CHOLESTEROL = "cholesterol";
    static final String MEAL_SODIUM = "sodium";

    //DEFINE COLUMN NAMES FOR THE TABLE GROCERY
    static final String NUTRITION_ID = "id";
    static final String NUTRITION_NAME = "name";
    static final String NUTRITION_CALORIES = "calories";
    static final String NUTRITION_CARB = "carbohydrates";
    static final String NUTRITION_FAT = "fat";
    static final String NUTRITION_PROTEIN = "protein";
    static final String NUTRITION_CALCIUM = "calcium";
    static final String NUTRITION_CHOLESTEROL = "cholesterol";
    static final String NUTRITION_SODIUM = "sodium";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATING INGREDIENTS TABLE
        String table =
                "CREATE TABLE " + INGREDIENT_TABLE + "("
                + INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + INGREDIENT_NAME + " VARCHAR(255), "
                + INGREDIENT_QUANTITY + " INTEGER, "
                + INGREDIENT_UNIT + " VARCHAR(255)" + ")";
        db.execSQL(table);

        //CREATING RECIPES TABLE
        table =
                "CREATE TABLE " + RECIPES_TABLE + "("
                        + RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + RECIPE_NAME + " VARCHAR(255), "
                        + RECIPE_INGREDIENTS + " VARCHAR(255), "
                        + RECIPE_IMAGE + " VARCHAR(255), "
                        + RECIPE_DIRECTION + " VARCHAR(255), "
                        + RECIPE_COUNT + " INTEGER, "
                        + RECIPE_CALORIES + " INTEGER, "
                        + RECIPE_CARB + " INTEGER, "
                        + RECIPE_FAT + " INTEGER, "
                        + RECIPE_PROTEIN + " INTEGER, "
                        + RECIPE_CALCIUM + " INTEGER, "
                        + RECIPE_CHOLESTEROL + " INTEGER, "
                        + RECIPE_SODIUM + " INTEGER" + ")";
        db.execSQL(table);

        //CREATING GROCERY TABLE
        table =
                "CREATE TABLE " + GROCERY_TABLE + "("
                        + GROCERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + GROCERY_NAME + " VARCHAR(255), "
                        + GROCERY_COUNT + " INTEGER, "
                        + GROCERY_UNIT + " VARCHAR(255)" + ")";
        db.execSQL(table);

        //CREATING MEAL TABLE
        table =
                "CREATE TABLE " + MEAL_TABLE + "("
                        + MEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + MEAL_NAME + " VARCHAR(255), "
                        + MEAL_DAY + " VARCHAR(255), "
                        + MEAL_TIME + " VARCHAR(255), "
                        + MEAL_CALORIES + " INTEGER, "
                        + MEAL_CARB + " INTEGER, "
                        + MEAL_FAT + " INTEGER, "
                        + MEAL_PROTEIN + " INTEGER, "
                        + MEAL_CALCIUM + " INTEGER, "
                        + MEAL_CHOLESTEROL + " INTEGER, "
                        + MEAL_SODIUM + " INTEGER" + ")";
        db.execSQL(table);

        //CREATING NUTRITION TABLE
        table =
                "CREATE TABLE " + NUTRITION_TABLE + "("
                        + NUTRITION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + NUTRITION_NAME + " VARCHAR(255), "
                        + NUTRITION_CALORIES + " INTEGER, "
                        + NUTRITION_CARB + " INTEGER, "
                        + NUTRITION_FAT + " INTEGER, "
                        + NUTRITION_PROTEIN + " INTEGER, "
                        + NUTRITION_CALCIUM + " INTEGER, "
                        + NUTRITION_CHOLESTEROL + " INTEGER, "
                        + NUTRITION_SODIUM + " INTEGER" + ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //DROP OLDER TABLE IF EXIST
        database.execSQL("DROP TABLE IF EXISTS " + INGREDIENT_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + RECIPES_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + GROCERY_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + MEAL_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + NUTRITION_TABLE);
        //CREATE TABLE AGAIN
        onCreate(database);
    }

    //DATABASE OPERATIONS: ADD, EDIT, and DELETE


    /**
     * Get the nutritional values for the weekly goal the user entered through the app
     * @return a list of nutritional values for the weekly goal the user entered through the app
     * [0] = calories, [1] = carb, [2] = fat, [3] = protein, [4] = calcium, [5] = cholesterol, [6] = sodium
     */
    public ArrayList<Integer> getWeeklyNutrition() {
        ArrayList<Integer> result = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + NUTRITION_TABLE + " WHERE " + NUTRITION_NAME + " = '" + NutritionScreenActivity.NUTRITION_NAME + "' LIMIT 1;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            int calories = cursor.getInt(2);
            int carb = cursor.getInt(3);
            int fat = cursor.getInt(4);
            int protein = cursor.getInt(5);
            int calcium = cursor.getInt(6);
            int cholesterol = cursor.getInt(7);
            int sodium = cursor.getInt(8);

            result.add(calories);
            result.add(carb);
            result.add(fat);
            result.add(protein);
            result.add(calcium);
            result.add(cholesterol);
            result.add(sodium);
        }
        return result;
    }

    /**
     * Add a new nutritional list or update existing one in the INGREDIENT_TABLE. Mainly used for the weekly goal
     * @param name name of the nutritional list
     * @param calories calories value
     * @param carb carbohydrate value
     * @param fat fat value
     * @param protein protein value
     * @param calcium calcium value
     * @param cholesterol cholesterol value
     * @param sodium sodium value
     */
    public void updateNutrition(String name, int calories, int carb, int fat, int protein, int calcium, int cholesterol, int sodium) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NUTRITION_NAME, name);
        values.put(NUTRITION_CALORIES, calories);
        values.put(NUTRITION_CARB, carb);
        values.put(NUTRITION_FAT, fat);
        values.put(NUTRITION_PROTEIN, protein);
        values.put(NUTRITION_CALCIUM, calcium);
        values.put(NUTRITION_CHOLESTEROL, cholesterol);
        values.put(NUTRITION_SODIUM, sodium);

        if (nutritionAdded(name)) { //update existing one if already exist
            db.update(NUTRITION_TABLE, values, NUTRITION_NAME + " = ?", new String[]{name});
        } else {    //Insert new one
            db.insert(NUTRITION_TABLE, null, values);
        }
        db.close();
    }

    /**
     * Check if a certain nutrition list was already added
     * @param name name of the nutrition list
     * @return true if found, false otherwise
     */
    public boolean nutritionAdded(String name) {
        String selectQuery = "SELECT * FROM " + NUTRITION_TABLE + " WHERE " + NUTRITION_NAME + " = '" + name + "' LIMIT 1;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Add or update a meal in the MEAL_TABLE of the database
     * @param meal the meal to be added or updated
     */
    public void addMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEAL_NAME, meal.getMealName());
        values.put(MEAL_DAY, meal.getDayOfWeek());
        values.put(MEAL_TIME, meal.getTimeOfDay());
        values.put(MEAL_CALORIES, meal.getCalories());
        values.put(MEAL_CARB, meal.getCarb());
        values.put(MEAL_FAT, meal.getFat());
        values.put(MEAL_PROTEIN, meal.getProtein());
        values.put(MEAL_CALCIUM, meal.getCalcium());
        values.put(MEAL_CHOLESTEROL, meal.getCholesterol());
        values.put(MEAL_SODIUM, meal.getSodium());

        Meal m = mealExist(meal);
        if (m != null) {    //if meal already exist, update
            db.update(MEAL_TABLE, values, MEAL_NAME + " = ? AND "
                    + MEAL_DAY + " = ? AND "
                    + MEAL_TIME + " = ?"
                    , new String[]{m.getMealName(), m.getDayOfWeek(), m.getTimeOfDay()});
        } else {    //else , insert new meal to table
            db.insert(MEAL_TABLE, null, values);
        }
        db.close();
    }


    /**
     * Remove a meal from the MEAL_TABLE, helper function for removeMeal(Meal meal)
     * @param name name of meal
     * @param day day of meal, e.g. monday, tuesday, etc
     * @param time time of day, e.g. breakfast, lunch, or dinner
     * @return number of affected row in the MEAL_TABLE
     */
    public int removeMeal(String name, String day, String time) {
        int affected = 0;
        String selectQuery = "SELECT * FROM " + MEAL_TABLE + " WHERE " + MEAL_NAME + " = '" + name
                + "' AND " + MEAL_DAY + " = '" + day
                + "' AND " + MEAL_TIME + " = '" + time
                + "' LIMIT 1;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            String mealName = cursor.getString(1);
            String mealDay = cursor.getString(2);
            String mealTime = cursor.getString(3);
            int calories = cursor.getInt(4);
            int carb = cursor.getInt(5);
            int fat = cursor.getInt(6);
            int protein = cursor.getInt(7);
            int calcium = cursor.getInt(8);
            int cholesterol = cursor.getInt(9);
            int sodium = cursor.getInt(10);

            Meal m = new Meal(mealName, mealDay, mealTime, calories, carb, fat, protein, calcium, cholesterol, sodium);
            affected = removeMeal(m);   //call the removeMeal(Meal meal) method after constructing the meal object
        }
        return affected;
    }

    /**
     * Remove a meal from the MEAL_TABLE
     * @param meal the meal to be removed
     * @return number of affected rows
     */
    public int removeMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int affected = db.delete(MEAL_TABLE, MEAL_NAME + " = ? AND "
                        + MEAL_DAY + " = ? AND "
                        + MEAL_TIME + " = ?"
                        , new String[]{meal.getMealName(), meal.getDayOfWeek(), meal.getTimeOfDay()});

        db.close();
        return affected;
    }

    /**
     * Get a list of all meals in MEAL_TABLE
     * @return a list of all meals in MEAL_TABLE
     */
    public ArrayList<Meal> getAllMeals() {
        ArrayList<Meal> meals = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + MEAL_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String mealName = cursor.getString(1);
                String mealDay = cursor.getString(2);
                String mealTime = cursor.getString(3);
                int calories = cursor.getInt(4);
                int carb = cursor.getInt(5);
                int fat = cursor.getInt(6);
                int protein = cursor.getInt(7);
                int calcium = cursor.getInt(8);
                int cholesterol = cursor.getInt(9);
                int sodium = cursor.getInt(10);
                meals.add(new Meal(mealName, mealDay, mealTime, calories, carb, fat, protein, calcium, cholesterol, sodium));
            } while (cursor.moveToNext());
        }

        return meals;
    }


    /**
     * Check if a meal already exists in the database. A combination of meal name, day of week, and time of day is to be used to determine
     * @param meal the meal to be checked
     * @return true if meal exists, false otherwise
     */
    public Meal mealExist(Meal meal) {
        String selectQuery = "SELECT * FROM " + MEAL_TABLE + " WHERE " + MEAL_NAME + " = '" + meal.getMealName()
                + " AND " + MEAL_DAY + " = " + meal.getDayOfWeek()
                + " AND " + MEAL_TIME + " = " + meal.getTimeOfDay()
                + "' LIMIT 1;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            String mealName = cursor.getString(1);
            String mealDay = cursor.getString(2);
            String mealTime = cursor.getString(3);
            int calories = cursor.getInt(4);
            int carb = cursor.getInt(5);
            int fat = cursor.getInt(6);
            int protein = cursor.getInt(7);
            int calcium = cursor.getInt(8);
            int cholesterol = cursor.getInt(9);
            int sodium = cursor.getInt(10);

            return new Meal(mealName, mealDay, mealTime, calories, carb, fat, protein, calcium, cholesterol, sodium);
        }
        return null;
    }


    /**
     * Add an ingredient to the INGREDIENT_TABLE in the database
     * @param ingredient the ingredient to be added
     */
    public void addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(INGREDIENT_NAME, ingredient.getName());
        values.put(INGREDIENT_QUANTITY, ingredient.getQuantity());
        values.put(INGREDIENT_UNIT, ingredient.getUnit());
        db.insert(INGREDIENT_TABLE, null, values);

        //CLOSE DATABASE CONNECTION
        db.close();
    }

    /**
     * Check if a grocery item exists in the GROCERY_TABLE
     * @param ingredient the item to be checked
     * @return true if exists, false otherwise
     */
    public Ingredient groceryExist(Ingredient ingredient) {
        String selectQuery = "SELECT * FROM " + GROCERY_TABLE + " WHERE " + GROCERY_NAME + " = '" + ingredient.getName() + "' LIMIT 1;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            String groceryName = cursor.getString(1);
            int count = cursor.getInt(2);
            String unit = cursor.getString(3);
            Ingredient i = new Ingredient(groceryName, count, unit);
            return i;
        }

        return null;
    }

    /**
     * Get all groceries in GROCERY_TABLE
     * @return a list of all groceries in GROCERY_TABLE
     */
    public ArrayList<Ingredient> getAllGroceries() {
        ArrayList<Ingredient> groceries = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + GROCERY_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                int quantity = cursor.getInt(2);
                String unit = cursor.getString(3);
                Ingredient i = new Ingredient(name, quantity, unit);
                groceries.add(i);
            } while (cursor.moveToNext());
        }
        return groceries;
    }

    /**
     * Add or update a grocery item to the GROCERY_TABLE of the database by incrementing its count (happens when user swipe right on a certain grocery item)
     * @param ingredient the ingredient to be added or updated
     */
    public void addGrocery(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String name = ingredient.getName();
        String unit = ingredient.getUnit();

        values.put(GROCERY_NAME, name);
        values.put(GROCERY_UNIT, unit);

        Ingredient i = groceryExist(ingredient);
        if (i != null) {    //update if exist
            values.put(GROCERY_COUNT, 1 + i.getQuantity());
            db.update(GROCERY_TABLE, values, GROCERY_NAME + " = ?", new String[]{i.getName()});
        } else {    //insert new if not exist
            values.put(GROCERY_COUNT, 1);
            db.insert(GROCERY_TABLE, null, values);
        }
        db.close();
    }

    /**
     * Remove a grocery item by decrementing its count (happens when user swipe left on a certain grocery item)
     * @param ingredient the grocery item to be removed
     */
    public void removeGrocery(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String name = ingredient.getName();
        String unit = ingredient.getUnit();

        values.put(GROCERY_NAME, name);
        values.put(GROCERY_UNIT, unit);

        Ingredient i = groceryExist(ingredient);
        if (i != null && i.getQuantity() > 0) {
            values.put(GROCERY_COUNT, i.getQuantity() - 1);
            db.update(GROCERY_TABLE, values, GROCERY_NAME + " = ?", new String[]{i.getName()});
        }

        db.close();
    }

    /**
     * Get all ingredients from the INGREDIENT_TABLE
     * @return a list of ingredients obtained from INGREDIENT_TABLE
     */
    public ArrayList<Ingredient> getAllIngredients() {
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        //SELECT ALL QUERY FROM TABLE
        String selectQuery = "SELECT * FROM " + INGREDIENT_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //LOOP THROUGH THE RESULTS
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                int quantity = cursor.getInt(2);
                String unit = cursor.getString(3);
                Ingredient i = new Ingredient(name, quantity, unit);
                ingredients.add(i);
            } while (cursor.moveToNext());
        }
        return ingredients;
    }

    /**
     * Search for a certain recipe in the RECIPE_TABLE by name
     * @param name name of the recipe
     * @return the recipe item found in the RECIPE_TABLE, null if not exists
     */
    public Recipe getRecipeByName(String name) {
        String selectQuery = "SELECT * FROM " + RECIPES_TABLE + " WHERE " + RECIPE_NAME + " = '" + name + "' LIMIT 1;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            String recipeName = cursor.getString(1);
            String imageURI = cursor.getString(3);
            String direction = cursor.getString(4);
            String[] ingredientArray = cursor.getString(2).split(",");
            ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(ingredientArray));
            int count = cursor.getInt(5);
            int calories = cursor.getInt(6);
            int carb = cursor.getInt(7);
            int fat = cursor.getInt(8);
            int protein = cursor.getInt(9);
            int calcium = cursor.getInt(10);
            int cholesterol = cursor.getInt(11);
            int sodium = cursor.getInt(12);

            Recipe r = new Recipe(name, ingredients, imageURI, direction, count, calories, carb, fat, protein, calcium, cholesterol, sodium);
            return r;
        }
        return null;
    }

    /**
     * Get all recipes from RECIPE_TABLE
     * @return A list of all recipes from RECIPE_TABLE
     */
    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        //SELECT ALL QUERY FROM TABLE
        String selectQuery = "SELECT * FROM " + RECIPES_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //LOOP THROUGH THE RESULTS
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(1);
                String imageURI = cursor.getString(3);
                String direction = cursor.getString(4);
                String[] ingredientArray = cursor.getString(2).split(",");
                ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(ingredientArray));

                int count = cursor.getInt(5);
                int calories = cursor.getInt(6);
                int carb = cursor.getInt(7);
                int fat = cursor.getInt(8);
                int protein = cursor.getInt(9);
                int calcium = cursor.getInt(10);
                int cholesterol = cursor.getInt(11);
                int sodium = cursor.getInt(12);

                Recipe r = new Recipe(name, ingredients, imageURI, direction, count, calories, carb, fat, protein, calcium, cholesterol, sodium);

                recipes.add(r);
            } while (cursor.moveToNext());
        }
        return recipes;
    }

    /**
     * Add a new recipe to the RECIPE_TABLE
     * @param recipe the item to be added
     */
    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RECIPE_NAME, recipe.getName());
        values.put(RECIPE_IMAGE, recipe.getImageURI());
        values.put(RECIPE_DIRECTION, recipe.getDirection());

        String ingredientNames = "";

        ArrayList<String> ingredients = recipe.getIngredients();
        for (int i=0; i<ingredients.size(); i++) {
            if (i > 0)
                ingredientNames += ",";
            ingredientNames += ingredients.get(i);
        }
        values.put(RECIPE_INGREDIENTS, ingredientNames);
        values.put(RECIPE_COUNT, recipe.getCount());
        values.put(RECIPE_CALORIES, recipe.getCalories());
        values.put(RECIPE_CARB, recipe.getCarbohydrates());
        values.put(RECIPE_FAT, recipe.getFat());
        values.put(RECIPE_PROTEIN, recipe.getProtein());
        values.put(RECIPE_CALCIUM, recipe.getCalcium());
        values.put(RECIPE_CHOLESTEROL, recipe.getCholesterol());
        values.put(RECIPE_SODIUM, recipe.getSodium());

        db.insert(RECIPES_TABLE, null, values);
        db.close();
    }

    /**
     * Update a recipe item in the RECIPE_TABLE
     * @param recipe the recipe to be updated
     */
    public void updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RECIPE_NAME, recipe.getName());
        values.put(RECIPE_IMAGE, recipe.getImageURI());
        values.put(RECIPE_DIRECTION, recipe.getDirection());

        String ingredientNames = "";

        ArrayList<String> ingredients = recipe.getIngredients();
        for (int i=0; i<ingredients.size(); i++) {
            if (i > 0)
                ingredientNames += ",";
            ingredientNames += ingredients.get(i);
        }
        values.put(RECIPE_INGREDIENTS, ingredientNames);
        values.put(RECIPE_COUNT, recipe.getCount());
        values.put(RECIPE_CALORIES, recipe.getCalories());
        values.put(RECIPE_CARB, recipe.getCarbohydrates());
        values.put(RECIPE_FAT, recipe.getFat());
        values.put(RECIPE_PROTEIN, recipe.getProtein());
        values.put(RECIPE_CALCIUM, recipe.getCalcium());
        values.put(RECIPE_CHOLESTEROL, recipe.getCholesterol());
        values.put(RECIPE_SODIUM, recipe.getSodium());

        db.update(RECIPES_TABLE, values, RECIPE_NAME + " = ?", new String[]{recipe.getName()});
        db.close();
    }

    /**
     * Delete a recipe in RECIPE_TABLE using name
     * @param name name of the recipe to be deleted
     * @return true if item is deleted, false if item didn't exist in database
     */
    public boolean deleteRecipe(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        int affected = db.delete(RECIPES_TABLE, RECIPE_NAME + " = ?", new String[]{name});
        db.close();
        return affected > 0;
    }

    /**
     * Clear all ingredients in INGREDIENT_TABLE
     * @param list list of all ingredients currently available
     */
    public void clearAllIngredient(ArrayList<Ingredient> list) {
        list.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(INGREDIENT_TABLE, null, new String[]{});
        db.close();
    }

    /**
     * Clear all recipes in RECIPE_TABLE
     * @param list list of all recipes currently available
     */
    public void clearAllRecipes(ArrayList<Recipe> list) {
        list.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RECIPES_TABLE, null, new String[]{});
        db.close();
    }

    /**
     * Clear all groceries in GROCERIES_TABLE
     * @param list list of all groceries currently available
     */
    public void clearAllGroceries(ArrayList<Ingredient> list) {
        list.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GROCERY_TABLE, null, new String[]{});
        db.close();
    }
}
