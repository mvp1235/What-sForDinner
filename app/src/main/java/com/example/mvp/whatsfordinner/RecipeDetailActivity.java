package com.example.mvp.whatsfordinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MVP on 9/24/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle b = getIntent().getBundleExtra(RecipeScreenActivity.RECIPE_BUNDLE);
        fragment.setArguments(b);

        getFragmentManager().beginTransaction()
                .add(R.id.recipe_detail_container, fragment)
                .commit();

    }
}
