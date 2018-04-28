package com.example.mvp.whatsfordinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MealScreenActivity extends AppCompatActivity {

    ArrayList<Spinner> monday = new ArrayList<>();
    ArrayList<Spinner> tuesday = new ArrayList<>();
    ArrayList<Spinner> wednesday = new ArrayList<>();
    ArrayList<Spinner> thursday = new ArrayList<>();
    ArrayList<Spinner> friday = new ArrayList<>();
    ArrayList<Spinner> saturday = new ArrayList<>();
    ArrayList<Spinner> sunday = new ArrayList<>();
    ArrayAdapter<String> mealAdapter;   //adapt mealList to spinners
    ArrayList<Recipe> availableMeals = new ArrayList<>();
    ArrayList<String> mealList = new ArrayList<>(); //contain a list of available meals
    static ArrayList<Meal> assignedMeals = new ArrayList<>();
    ArrayList<TextView> mealTextViews = new ArrayList<>();
    Button nutritionBtn;
    Button saveNutritionBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_screen);

        //Locate all the spinners for breakfast, lunch, and dinners for all days of the week
        monday.add((Spinner) findViewById(R.id.mondayBreakfast));
        monday.add((Spinner) findViewById(R.id.mondayLunch));
        monday.add((Spinner) findViewById(R.id.mondayDinner));
        tuesday.add((Spinner) findViewById(R.id.tuesdayBreakfast));
        tuesday.add((Spinner) findViewById(R.id.tuesdayLunch));
        tuesday.add((Spinner) findViewById(R.id.tuesdayDinner));
        wednesday.add((Spinner) findViewById(R.id.wednesdayBreakfast));
        wednesday.add((Spinner) findViewById(R.id.wednesdayLunch));
        wednesday.add((Spinner) findViewById(R.id.wednesdayDinner));
        thursday.add((Spinner) findViewById(R.id.thursdayBreakfast));
        thursday.add((Spinner) findViewById(R.id.thursdayLunch));
        thursday.add((Spinner) findViewById(R.id.thursdayDinner));
        friday.add((Spinner) findViewById(R.id.fridayBreakfast));
        friday.add((Spinner) findViewById(R.id.fridayLunch));
        friday.add((Spinner) findViewById(R.id.fridayDinner));
        saturday.add((Spinner) findViewById(R.id.saturdayBreakfast));
        saturday.add((Spinner) findViewById(R.id.saturdayLunch));
        saturday.add((Spinner) findViewById(R.id.saturdayDinner));
        sunday.add((Spinner) findViewById(R.id.sundayBreakfast));
        sunday.add((Spinner) findViewById(R.id.sundayLunch));
        sunday.add((Spinner) findViewById(R.id.sundayDinner));

        mealTextViews.add((TextView) findViewById(R.id.mondayBreakfastMeal));
        mealTextViews.add((TextView) findViewById(R.id.mondayLunchMeal));
        mealTextViews.add((TextView) findViewById(R.id.mondayDinnerMeal));
        mealTextViews.add((TextView) findViewById(R.id.tuesdayBreakfastMeal));
        mealTextViews.add((TextView) findViewById(R.id.tuesdayLunchMeal));
        mealTextViews.add((TextView) findViewById(R.id.tuesdayDinnerMeal));
        mealTextViews.add((TextView) findViewById(R.id.wednesdayBreakfastMeal));
        mealTextViews.add((TextView) findViewById(R.id.wednesdayLunchMeal));
        mealTextViews.add((TextView) findViewById(R.id.wednesdayDinnerMeal));
        mealTextViews.add((TextView) findViewById(R.id.thursdayBreakfastMeal));
        mealTextViews.add((TextView) findViewById(R.id.thursdayLunchMeal));
        mealTextViews.add((TextView) findViewById(R.id.thursdayDinnerMeal));
        mealTextViews.add((TextView) findViewById(R.id.fridayBreakfastMeal));
        mealTextViews.add((TextView) findViewById(R.id.fridayLunchMeal));
        mealTextViews.add((TextView) findViewById(R.id.fridayDinnerMeal));
        mealTextViews.add((TextView) findViewById(R.id.saturdayBreakfastMeal));
        mealTextViews.add((TextView) findViewById(R.id.saturdayLunchMeal));
        mealTextViews.add((TextView) findViewById(R.id.saturdayDinnerMeal));
        mealTextViews.add((TextView) findViewById(R.id.sundayBreakfastMeal));
        mealTextViews.add((TextView) findViewById(R.id.sundayLunchMeal));
        mealTextViews.add((TextView) findViewById(R.id.sundayDinnerMeal));

        for (int i=0; i<mealTextViews.size(); i++) {
            mealTextViews.get(i).setText("Eating out");
        }

        availableMeals = MainScreenActivity.dbHelper.getAllRecipes();

        nutritionBtn = (Button) findViewById(R.id.nutritionBtn);
        nutritionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MealScreenActivity.this, NutritionScreenActivity.class);
                startActivity(intent);
            }
        });

        saveNutritionBtn = (Button) findViewById(R.id.saveNutritionBtn);
        saveNutritionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Add default eating out option
        mealList.add("Select Meal");
        mealList.add("Eating out");
        //Only add the recipes with count > 0 (clicked on previously at least once)
        for (int i=0; i<availableMeals.size(); i++) {
            if (availableMeals.get(i).getCount() > 0)
                mealList.add(availableMeals.get(i).getMealString());
        }

        mealAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, mealList);

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String chosenRecipe = adapterView.getItemAtPosition(i).toString();
                if (!chosenRecipe.equalsIgnoreCase("Eating out") && !chosenRecipe.equalsIgnoreCase("Select Meal")) {
                    int index = chosenRecipe.indexOf("(");
                    if (index != -1)
                        chosenRecipe = chosenRecipe.substring(0, index - 1);

                    Recipe recipe = MainScreenActivity.dbHelper.getRecipeByName(chosenRecipe);
                    if (recipe != null) {
                        recipe.decrementCount();
                        MainScreenActivity.dbHelper.updateRecipe(recipe);

                        //Constructing a meal object to insert into database
                        int spinnerID = adapterView.getId();
                        String timeOfDay = getTimeOfDay(spinnerID);
                        String dayOfWeek = getDayOfWeek(spinnerID);
                        MainScreenActivity.dbHelper.addMeal(new Meal(recipe.getName(), dayOfWeek, timeOfDay, recipe.getCalories(),
                                recipe.getCarbohydrates(), recipe.getFat(), recipe.getProtein(), recipe.getCalcium(), recipe.getCholesterol(), recipe.getSodium()));

                        int mealTextViewId = getCorrespondingMealTextView(dayOfWeek, timeOfDay);
                        TextView tv = (TextView) findViewById(mealTextViewId);
                        if (tv != null) {
                            tv.setText(recipe.getName());
                        }

                        String newString = recipe.getName() + " (" + recipe.getCount() + ")";
                        mealList.set(i, newString);
                        mealAdapter.notifyDataSetChanged();
                    }
                } else if (chosenRecipe.equalsIgnoreCase("Eating out")){
                    int spinnerID = adapterView.getId();
                    String timeOfDay = getTimeOfDay(spinnerID);
                    String dayOfWeek = getDayOfWeek(spinnerID);
                    int mealTextViewId = getCorrespondingMealTextView(dayOfWeek, timeOfDay);
                    TextView tv = (TextView) findViewById(mealTextViewId);
                    String oldMeal = tv.getText().toString();
                    MainScreenActivity.dbHelper.removeMeal(oldMeal, dayOfWeek, timeOfDay);

                    tv.setText("Eating out");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };

        for (int i=0; i<3; i++) {
            monday.get(i).setAdapter(mealAdapter);
            tuesday.get(i).setAdapter(mealAdapter);
            wednesday.get(i).setAdapter(mealAdapter);
            thursday.get(i).setAdapter(mealAdapter);
            friday.get(i).setAdapter(mealAdapter);
            saturday.get(i).setAdapter(mealAdapter);
            sunday.get(i).setAdapter(mealAdapter);

            monday.get(i).setOnItemSelectedListener(listener);
            tuesday.get(i).setOnItemSelectedListener(listener);
            wednesday.get(i).setOnItemSelectedListener(listener);
            thursday.get(i).setOnItemSelectedListener(listener);
            friday.get(i).setOnItemSelectedListener(listener);
            saturday.get(i).setOnItemSelectedListener(listener);
            sunday.get(i).setOnItemSelectedListener(listener);
        }

        assignedMeals = MainScreenActivity.dbHelper.getAllMeals();



        for (int i=0; i<assignedMeals.size(); i++) {
            Meal m = assignedMeals.get(i);
            int mealTvId = getCorrespondingMealTextView(m.getDayOfWeek(), m.getTimeOfDay());
            if (mealTvId != -1) {
                TextView textView = (TextView) findViewById(mealTvId);
                int index = m.getMealName().indexOf("(");
                textView.setText(m.getMealName());
            }
        }

    }

    public int getCorrespondingMealTextView(String day, String time) {
        if (day.equalsIgnoreCase("monday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.mondayBreakfastMeal;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.mondayLunchMeal;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.mondayDinnerMeal;
            }
        } else if (day.equalsIgnoreCase("tuesday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.tuesdayBreakfastMeal;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.tuesdayLunchMeal;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.tuesdayDinnerMeal;
            }
        } else if (day.equalsIgnoreCase("wednesday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.wednesdayBreakfastMeal;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.wednesdayLunchMeal;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.wednesdayDinnerMeal;
            }
        } else if (day.equalsIgnoreCase("thursday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.thursdayBreakfastMeal;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.thursdayLunchMeal;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.thursdayDinnerMeal;
            }
        } else if (day.equalsIgnoreCase("friday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.fridayBreakfastMeal;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.fridayLunchMeal;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.fridayDinnerMeal;
            }
        } else if (day.equalsIgnoreCase("saturday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.saturdayBreakfastMeal;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.saturdayLunchMeal;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.saturdayDinnerMeal;
            }
        } else if (day.equalsIgnoreCase("sunday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.sundayBreakfastMeal;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.sundayLunchMeal;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.sundayDinnerMeal;
            }
        }
        return -1;
    }

    public int getSpinnerID(String day, String time) {
        if (day.equalsIgnoreCase("monday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.mondayBreakfast;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.mondayLunch;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.mondayDinner;
            }
        } else if (day.equalsIgnoreCase("tuesday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.tuesdayBreakfast;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.tuesdayLunch;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.tuesdayDinner;
            }
        } else if (day.equalsIgnoreCase("wednesday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.wednesdayBreakfast;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.wednesdayLunch;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.wednesdayDinner;
            }
        } else if (day.equalsIgnoreCase("thursday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.thursdayBreakfast;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.thursdayLunch;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.thursdayDinner;
            }
        } else if (day.equalsIgnoreCase("friday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.fridayBreakfast;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.fridayLunch;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.fridayDinner;
            }
        } else if (day.equalsIgnoreCase("saturday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.saturdayBreakfast;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.saturdayLunch;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.saturdayDinner;
            }
        } else if (day.equalsIgnoreCase("sunday")) {
            if (time.equalsIgnoreCase("breakfast")) {
                return R.id.sundayBreakfast;
            } else if (time.equalsIgnoreCase("lunch")) {
                return R.id.sundayLunch;
            } else if (time.equalsIgnoreCase("dinner")) {
                return R.id.sundayDinner;
            }
        }
        return -1;
    }

    public String getTimeOfDay(int id) {
        if (id == R.id.mondayBreakfast) {
            return "breakfast";
        } else if (id == R.id.mondayLunch) {
            return "lunch";
        } else if (id == R.id.mondayDinner) {
            return "dinner";
        } else if (id == R.id.tuesdayBreakfast) {
            return "breakfast";
        } else if (id == R.id.tuesdayLunch) {
            return "lunch";
        } else if (id == R.id.tuesdayDinner) {
            return "dinner";
        } else if (id == R.id.wednesdayBreakfast) {
            return "breakfast";
        } else if (id == R.id.wednesdayLunch) {
            return "lunch";
        } else if (id == R.id.wednesdayDinner) {
            return "dinner";
        } else if (id == R.id.thursdayBreakfast) {
            return "breakfast";
        } else if (id == R.id.thursdayLunch) {
            return "lunch";
        } else if (id == R.id.thursdayDinner) {
            return "dinner";
        } else if (id == R.id.fridayBreakfast) {
            return "breakfast";
        } else if (id == R.id.fridayLunch) {
            return "lunch";
        } else if (id == R.id.fridayDinner) {
            return "dinner";
        } else if (id == R.id.saturdayBreakfast) {
            return "breakfast";
        } else if (id == R.id.saturdayLunch) {
            return "lunch";
        } else if (id == R.id.saturdayDinner) {
            return "dinner";
        } else if (id == R.id.sundayBreakfast) {
            return "breakfast";
        } else if (id == R.id.sundayLunch) {
            return "lunch";
        } else if (id == R.id.sundayDinner) {
            return "dinner";
        }
        return "";
    }

    public String getDayOfWeek(int id) {
        if (id == R.id.mondayBreakfast) {
            return "monday";
        } else if (id == R.id.mondayLunch) {
            return "monday";
        } else if (id == R.id.mondayDinner) {
            return "monday";
        } else if (id == R.id.tuesdayBreakfast) {
            return "tuesday";
        } else if (id == R.id.tuesdayLunch) {
            return "tuesday";
        } else if (id == R.id.tuesdayDinner) {
            return "tuesday";
        } else if (id == R.id.wednesdayBreakfast) {
            return "wednesday";
        } else if (id == R.id.wednesdayLunch) {
            return "wednesday";
        } else if (id == R.id.wednesdayDinner) {
            return "wednesday";
        } else if (id == R.id.thursdayBreakfast) {
            return "thursday";
        } else if (id == R.id.thursdayLunch) {
            return "thursday";
        } else if (id == R.id.thursdayDinner) {
            return "thursday";
        } else if (id == R.id.fridayBreakfast) {
            return "friday";
        } else if (id == R.id.fridayLunch) {
            return "friday";
        } else if (id == R.id.fridayDinner) {
            return "friday";
        } else if (id == R.id.saturdayBreakfast) {
            return "saturday";
        } else if (id == R.id.saturdayLunch) {
            return "saturday";
        } else if (id == R.id.saturdayDinner) {
            return "saturday";
        } else if (id == R.id.sundayBreakfast) {
            return "sunday";
        } else if (id == R.id.sundayLunch) {
            return "sunday";
        } else if (id == R.id.sundayDinner) {
            return "sunday";
        }
        return "";
    }
}
