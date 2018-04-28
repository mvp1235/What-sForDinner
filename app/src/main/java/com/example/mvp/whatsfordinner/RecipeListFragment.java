package com.example.mvp.whatsfordinner;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;



public class RecipeListFragment extends ListFragment {
    static final String SELECT_RECIPE_NAME = "Selected Recipe Name";

    List<Recipe> recipes;
    private Callbacks activity;
    public RecipeListFragment() {};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipes = MainScreenActivity.dbHelper.getAllRecipes();
        RecipeArrayAdapter adapter = new RecipeArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, recipes);
        adapter.setNotifyOnChange(true);
        this.setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        recipes = MainScreenActivity.dbHelper.getAllRecipes();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), EditRecipeActivity.class);
                Recipe recipe = (Recipe) adapterView.getItemAtPosition(i);

                intent.putExtra(SELECT_RECIPE_NAME, recipe.getName());
                startActivity(intent);
                return true;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipe_list, container, false);
    }

    public interface Callbacks {
        public void onItemSelected(Recipe recipe);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Recipe recipe = recipes.get(position);
        activity.onItemSelected(recipe);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }
}
