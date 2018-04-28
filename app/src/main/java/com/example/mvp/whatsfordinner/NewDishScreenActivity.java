package com.example.mvp.whatsfordinner;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;


public class NewDishScreenActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    ImageView recipeImage;
    ImageButton selectImageBtn;
    Uri imageUri;
    EditText recipeNameInput;
    Button saveRecipeBtn;
    EditText ingredientNameInput;
    Button addNewIngredientBtn;
    EditText imageUrlLink;
    EditText cookingDirection;
    ArrayList<Spinner> spinners;
    EditText caloriesInput;
    EditText carbInput;
    EditText fatInput;
    EditText proteinInput;
    EditText calciumInput;
    EditText cholesterolInput;
    EditText sodiumInput;

    private ArrayAdapter<String> ingredientsAdapter;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> ingredientList;

    private ArrayList<Recipe> recipes;
    private ArrayList<String> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dish_screen);

        //INITIALIZE DATABASE AND ALL UI ELEMENTS
        MainScreenActivity.dbHelper = new DBHelper(getApplicationContext());
        recipeNameInput = (EditText) findViewById(R.id.recipeNameInput);
        ingredientNameInput = (EditText) findViewById(R.id.ingredientInput) ;
        cookingDirection = (EditText) findViewById(R.id.recipeDescription);

        //SETTING UP SPINNERS FOR INGREDIENT PICKERS
        spinners = new ArrayList<>();
        spinners.add((Spinner) findViewById(R.id.ingredient1));
        spinners.add((Spinner) findViewById(R.id.ingredient2));
        spinners.add((Spinner) findViewById(R.id.ingredient3));
        spinners.add((Spinner) findViewById(R.id.ingredient4));
        spinners.add((Spinner) findViewById(R.id.ingredient5));
        spinners.add((Spinner) findViewById(R.id.ingredient6));
        spinners.add((Spinner) findViewById(R.id.ingredient7));
        spinners.add((Spinner) findViewById(R.id.ingredient8));
        spinners.add((Spinner) findViewById(R.id.ingredient9));
        spinners.add((Spinner) findViewById(R.id.ingredient10));

        caloriesInput = (EditText) findViewById(R.id.caloriesInput);
        carbInput = (EditText) findViewById(R.id.carbInput);
        fatInput = (EditText) findViewById(R.id.fatInput);
        proteinInput = (EditText) findViewById(R.id.proteinInput);
        calciumInput = (EditText) findViewById(R.id.calciumInput);
        cholesterolInput = (EditText) findViewById(R.id.cholesterolInput);
        sodiumInput = (EditText) findViewById(R.id.sodiumInput);

        imageUrlLink = (EditText) findViewById(R.id.imageUrlInput);

        recipeImage = (ImageView) findViewById(R.id.recipeImage);
        imageUri = Uri.parse("android.resource://com.example.mvp.whatsfordinner/mipmap/defaultrecipeimage");
        selectImageBtn = (ImageButton) findViewById(R.id.addRecipeImageBtn);

        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assignRecipeImage();
            }
        });

        recipeNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    String name = recipeNameInput.getText().toString();
                    if (recipeExist(name)) {
                        CharSequence text = "Recipe has already existed!";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //LOCATE ADD INGREDIENT BUTTON AND ATTACH AN ONCLICK LISTENER TO ADD TO THE DATABASE
        addNewIngredientBtn = (Button) findViewById(R.id.addIngredientBtn);
        addNewIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewIngredient();
            }
        });

        //LOCATE SAVE RECIPE BUTTON AND ATTACH AN ONCLICK LISTENER TO ADD TO THE DATABASE
        saveRecipeBtn = (Button) findViewById(R.id.saveRecipeBtn);
        saveRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRecipe();
            }
        });

        ingredients = MainScreenActivity.dbHelper.getAllIngredients();
        ingredientList = new ArrayList<>();
        for (int i=0; i<ingredients.size(); i++) {
            String s;
            if (ingredients.get(i).getName().equalsIgnoreCase("ingredient"))
                s = ingredients.get(i).getName();
            else
                s = ingredients.get(i).getName() + " (" + ingredients.get(i).getQuantity() + " " + ingredients.get(i).getUnit() + ")";
            ingredientList.add(s);
        }

        ingredientsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ingredientList);
        for (int i=0; i<spinners.size(); i++) {
            spinners.get(i).setAdapter(ingredientsAdapter);
        }

        recipes = MainScreenActivity.dbHelper.getAllRecipes();
        recipeList = new ArrayList<>();
        for (int i=0; i<recipes.size(); i++) {
            recipeList.add(recipes.get(i).getName());
        }


        //SETTING UP DROPDOWN MENU FOR UNIT SPINNER
        Spinner unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
        ArrayAdapter<String> unitsAdapter;
        ArrayList<String> unitsList = new ArrayList<String>();

        //GENERATE A LIST OF COMMON UNITS FOR INGREDIENTS USED IN THE US
        unitsList.add("unit");
        unitsList.add("lb");
        unitsList.add("oz");
        unitsList.add("pieces");
        unitsList.add("cup");
        unitsList.add("kg");
        unitsList.add("g");
        unitsList.add("ml");
        unitsList.add("gal");
        unitsList.add("tablespoon");
        unitsList.add("tsp");

        //CONNECTING UNIT ADAPTER TO THE UNIT SPINNER
        unitsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, unitsList);
        unitSpinner.setAdapter(unitsAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ingredients = MainScreenActivity.dbHelper.getAllIngredients();
        ingredientsAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ingredientList);
    }

    private void assignRecipeImage() {
        if (imageUrlLink.getText().toString().isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        } else {
            String url = imageUrlLink.getText().toString();
            Picasso.with(getApplicationContext()).load(url).into(recipeImage);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            recipeImage.setImageURI(imageUri);
        }
    }

    public Ingredient makeIngredient(String dropdownName) {
        int index1 = dropdownName.indexOf("(");
        String name = dropdownName.substring(0, index1);
        int index2 = dropdownName.indexOf(" ", index1);
        int quantity = Integer.parseInt(dropdownName.substring(index1+1, index2));
        int index3 = dropdownName.indexOf(")");
        String unit = dropdownName.substring(index2+1, index3);
        return new Ingredient(name, quantity, unit);
    }

    public void addNewRecipe() {
        CharSequence text;
        String name = recipeNameInput.getText().toString();
        String image = "";
        if (imageUrlLink.getText().toString().isEmpty())
            image = imageUri.toString();
        else
            image = imageUrlLink.getText().toString();

        ArrayList<String> ingredients = new ArrayList<>();
        for (int i=0; i<spinners.size(); i++) {
            String item = spinners.get(i).getSelectedItem().toString();
            if (!item.equalsIgnoreCase("Ingredient")) {
                ingredients.add(item);
            }
        }
        String direction = cookingDirection.getText().toString();

        if (name.isEmpty()) {
            text = "Recipe's name cannot be blank!";
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        } else if (recipeExist(name)) {
            text = "Recipe has already existed!";
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        } else {
            if (direction.length() == 0) {
                text = "Cooking direction must not be blank!";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            } else {
                if (ingredients.size() == 0) {
                    text = "At least one ingredient must be selected (1-10)";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                } else {
                    int count, calories, carb, fat, protein, calcium, cholesterol, sodium;
                    count = 0;
                    calories = (caloriesInput.getText().toString().isEmpty()) ? 0 : Integer.parseInt(caloriesInput.getText().toString());
                    carb = (carbInput.getText().toString().isEmpty()) ? 0 : Integer.parseInt(carbInput.getText().toString());
                    fat = (fatInput.getText().toString().isEmpty()) ? 0 : Integer.parseInt(fatInput.getText().toString());
                    protein = (proteinInput.getText().toString().isEmpty()) ? 0 : Integer.parseInt(proteinInput.getText().toString());
                    calcium = (calciumInput.getText().toString().isEmpty()) ? 0 : Integer.parseInt(calciumInput.getText().toString());
                    cholesterol = (cholesterolInput.getText().toString().isEmpty()) ? 0 : Integer.parseInt(cholesterolInput.getText().toString());
                    sodium = (sodiumInput.getText().toString().isEmpty()) ? 0 : Integer.parseInt(sodiumInput.getText().toString());

                    MainScreenActivity.dbHelper.addRecipe(new Recipe(name, ingredients, image, direction, count, calories, carb, fat, protein, calcium, cholesterol, sodium));
                    recipeList.add(name);
                    for (int i=0; i<ingredients.size(); i++) {
                        MainScreenActivity.dbHelper.addGrocery(makeIngredient(ingredients.get(i)));
                    }

                    text = name + " added...";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        }

    }


    public void addNewIngredient() {

        CharSequence text;
        EditText ingredientInput = (EditText)findViewById(R.id.ingredientInput);
        Spinner unit = (Spinner) findViewById(R.id.unitSpinner);
        String ingredientName = ingredientInput.getText().toString();
        String unitName = unit.getSelectedItem().toString();

        if (!ingredientName.isEmpty()) {
            if (ingredientExist(ingredientName)) {
                text = "Ingredient has already exist!";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

            } else {
                if (unitName.equalsIgnoreCase("unit")) {
                    text = "PLease select a unit for the ingredient.";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                } else {
                    MainScreenActivity.dbHelper.addIngredient(new Ingredient(ingredientName, 1, unitName));
                    ingredientsAdapter.add(ingredientName + " (" + "1 " + unitName + ")");
                    text = ingredientName + " added...";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            text = "Ingredient name must not be blank!";
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean recipeExist(String recipe) {
        recipes = MainScreenActivity.dbHelper.getAllRecipes();
        recipeList = new ArrayList<>();

        for (int i=0; i<recipes.size(); i++) {
            recipeList.add(recipes.get(i).getName());
        }

        for (int i=0; i<recipeList.size(); i++) {
            if (recipeList.get(i).equalsIgnoreCase(recipe)) {
                return true;
            }
        }
        return false;
    }


    public boolean ingredientExist(String ingredient) {
        ingredients = MainScreenActivity.dbHelper.getAllIngredients();
        ingredientList = new ArrayList<String>();
        for (int i=0; i<ingredients.size(); i++) {
            ingredientList.add(ingredients.get(i).getName());
        }

        for (int i=0; i<ingredientList.size(); i++) {
            if (ingredientList.get(i).equalsIgnoreCase(ingredient)) {
                return true;
            }
        }
        return false;
    }

}
