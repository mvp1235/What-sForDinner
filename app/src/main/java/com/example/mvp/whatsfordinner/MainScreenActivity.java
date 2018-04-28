package com.example.mvp.whatsfordinner;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity {
    static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //Initialize the database
        dbHelper = new DBHelper(getApplicationContext());

        //Add functionalities to each button. Add new dish button was handled by XML
        ImageButton recipesBtn = (ImageButton) findViewById(R.id.recipesBtn);
        recipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecipes();
            }
        });
        ImageButton groceryBtn = (ImageButton) findViewById(R.id.groceriesBtn);
        groceryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGroceries();
            }
        });
        ImageButton mealBtn = (ImageButton) findViewById(R.id.mealsBtn);
        mealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMeals();
            }
        });

        populateDefault();       //Only populate if app runs the first time
    }

    public void showAppInfo(View view) {
        startActivity(new Intent(MainScreenActivity.this, Pop.class));
    }

    public void addNewDish(View view) {
        Intent intent = new Intent(MainScreenActivity.this, NewDishScreenActivity.class);
        startActivity(intent);
    }

    public void showRecipes() {
        Intent intent = new Intent(MainScreenActivity.this, RecipeScreenActivity.class);
        startActivity(intent);
    }

    public void showGroceries() {
        Intent intent = new Intent(MainScreenActivity.this, GroceryScreenActivity.class);
        startActivity(intent);
    }

    public void showMeals() {
        Intent intent = new Intent(MainScreenActivity.this, MealScreenActivity.class);
        startActivity(intent);
    }

    public void clearDatabase() {
        dbHelper.clearAllGroceries(new ArrayList<Ingredient>());
        dbHelper.clearAllRecipes(new ArrayList<Recipe>());
        dbHelper.clearAllIngredient(new ArrayList<Ingredient>());
    }

    public void populateDefault() {
        if (dbHelper.getAllIngredients().size() == 0) {
            Ingredient i = new Ingredient("Ingredient", 0, "NA");
            Ingredient i1 = new Ingredient("Egg", 1, "pieces");
            Ingredient i2 = new Ingredient("Beef", 1, "lbs");
            Ingredient i3 = new Ingredient("Sugar", 1, "tsp");
            Ingredient i4 = new Ingredient("Salt", 1, "tsp");
            Ingredient i5 = new Ingredient("Milk", 1, "oz");
            Ingredient i6 = new Ingredient("Salmon", 1, "oz");
            Ingredient i7 = new Ingredient("Rice", 1, "oz");
            Ingredient i8 = new Ingredient("Black Pepper", 1, "tsp");
            Ingredient i9 = new Ingredient("Italian Seasoning", 1, "pieces");
            Ingredient i10 = new Ingredient("Whole Wheat Flour", 1, "cup");
            Ingredient i11 = new Ingredient("Olive Oil", 1, "tablespoons");
            Ingredient i12 = new Ingredient("Tomatoes", 1, "pieces");
            MainScreenActivity.dbHelper.addIngredient(i);
            MainScreenActivity.dbHelper.addIngredient(i1);
            MainScreenActivity.dbHelper.addIngredient(i2);
            MainScreenActivity.dbHelper.addIngredient(i3);
            MainScreenActivity.dbHelper.addIngredient(i4);
            MainScreenActivity.dbHelper.addIngredient(i5);
            MainScreenActivity.dbHelper.addIngredient(i6);
            MainScreenActivity.dbHelper.addIngredient(i7);
            MainScreenActivity.dbHelper.addIngredient(i8);
            MainScreenActivity.dbHelper.addIngredient(i9);
            MainScreenActivity.dbHelper.addIngredient(i10);
            MainScreenActivity.dbHelper.addIngredient(i11);
            MainScreenActivity.dbHelper.addIngredient(i12);
        }

        ArrayList<Integer> nutritions = dbHelper.getWeeklyNutrition();
        if (nutritions.size() == 0) {
            dbHelper.updateNutrition(NutritionScreenActivity.NUTRITION_NAME, 0, 0, 0, 0, 0, 0, 0);
        }

    }
}
