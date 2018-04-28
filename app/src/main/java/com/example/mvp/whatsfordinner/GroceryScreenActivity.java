package com.example.mvp.whatsfordinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Obtained and modified from https://stackoverflow.com/questions/17520750/list-view-item-swipe-left-and-swipe-right
 */
public class GroceryScreenActivity extends AppCompatActivity {

    ArrayList<Ingredient> ingredientList = new ArrayList<>();
    ListView groceriesLV;
    Grocery grocery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_screen);

        grocery = new Grocery();
        ingredientList = grocery.getDistinctList();
        groceriesLV = (ListView) findViewById(R.id.groceryList);
        final GroceryArrayAdapter groceryAdapter = new GroceryArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, ingredientList);
        groceriesLV.setAdapter(groceryAdapter);


        final SwipeDetector swipeDetector = new SwipeDetector();
        groceriesLV.setOnTouchListener(swipeDetector);
        groceriesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (swipeDetector.swipeDetected()) {
                    Ingredient ingredient = (Ingredient) adapterView.getItemAtPosition(i);
                    if (swipeDetector.getAction() == SwipeDetector.Action.LR) {
                        MainScreenActivity.dbHelper.addGrocery(ingredient);
                        grocery.addIngredient(ingredient);

                    } else if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
                        MainScreenActivity.dbHelper.removeGrocery(ingredient);
                        grocery.removeIngredient(ingredient);
                    } else {
                        //If it is not swiping right or left gesture, request intercept event. Without this, listview cannot scroll up and down
                        adapterView.requestDisallowInterceptTouchEvent(false);
                    }
                    groceryAdapter.notifyDataSetChanged();
                }
            }
        });
    }



}
