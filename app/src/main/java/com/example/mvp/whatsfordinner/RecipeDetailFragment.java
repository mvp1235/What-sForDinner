package com.example.mvp.whatsfordinner;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MVP on 9/24/2017.
 */

public class RecipeDetailFragment extends Fragment {
    Recipe recipe;

    public RecipeDetailFragment(){};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null && b.containsKey(DBHelper.RECIPE_NAME)) {
            recipe = new Recipe(b);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_detail, container, false);

        if (recipe != null) {
            TextView name = (TextView) view.findViewById(R.id.detail_recipe_name);
            ImageView image = (ImageView) view.findViewById(R.id.detail_recipe_image);
            TextView ingredientList = (TextView) view.findViewById(R.id.detail_recipe_ingredient_list);
            TextView direction = (TextView) view.findViewById(R.id.detail_recipe_direction);

            name.setText(recipe.getName());

//          Load uri into image using Picasso
            Picasso.with(getActivity()).load(recipe.getImageURI()).into(image);

            direction.setText(recipe.getDirection());

            String ingredientString = recipe.buildIngredientList();
            ingredientList.setText(ingredientString);
        }
        return view;
    }
}
