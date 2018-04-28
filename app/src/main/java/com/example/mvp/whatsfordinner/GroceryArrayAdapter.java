package com.example.mvp.whatsfordinner;

import android.content.Context;
import android.graphics.Paint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Custom ArrayAdapter to list a grocery items
 */
public class GroceryArrayAdapter extends ArrayAdapter<Ingredient> {
    private Context context;
    private List<Ingredient> ingredients;

    public GroceryArrayAdapter(Context context, int resource, List<Ingredient> ingredients) {
        super(context, resource, ingredients);
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ingredient ingredient = ingredients.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(android.R.layout.simple_list_item_1, null);
        TextView groceryNameText = (TextView) view.findViewById(android.R.id.text1);
        groceryNameText.setText(ingredient.toString());

        //If grocery item's count is 0, change style to strike-through
        if (ingredient.getQuantity() == 0) {
            groceryNameText.setPaintFlags(groceryNameText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        return view;
    }

    @Override
    public void setNotifyOnChange(boolean notifyOnChange) {
        super.setNotifyOnChange(notifyOnChange);
    }
}