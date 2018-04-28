package com.example.mvp.whatsfordinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by MVP on 9/24/2017.
 */

public class RecipeArrayAdapter extends ArrayAdapter<Recipe> {
    private Context context;
    private List<Recipe> recipes;

    public RecipeArrayAdapter(Context context, int resource, List<Recipe> recipes) {
        super(context, resource, recipes);
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe recipe = recipes.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, null);

        TextView text = (TextView) view.findViewById(android.R.id.text1);
        text.setText(recipe.getName());
        return view;
    }

}
