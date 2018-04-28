package com.example.mvp.whatsfordinner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

public class RecipeScreenActivity extends AppCompatActivity implements RecipeListFragment.Callbacks{
    final static String RECIPE_BUNDLE = "Recipe Bundle";
    final static int REQUEST_CODE = 100;
    private boolean isLandscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);

        //If phone is in landscape mode, the recipe detail fragment will be loaded and not be null
        if (findViewById(R.id.recipeDetailContainer) != null) {
            isLandscape = true;
        }
    }

    @Override
    public void onItemSelected(Recipe recipe) {
        Bundle b = recipe.toBundle();
        recipe.incrementCount();
        MainScreenActivity.dbHelper.updateRecipe(recipe);

        if(isLandscape) {
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.recipeDetailContainer, fragment).commit();
        } else {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RECIPE_BUNDLE, b);
            startActivityForResult(intent, REQUEST_CODE);
        }


    }
}
