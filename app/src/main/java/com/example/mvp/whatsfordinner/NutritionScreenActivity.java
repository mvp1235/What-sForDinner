package com.example.mvp.whatsfordinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class NutritionScreenActivity extends AppCompatActivity {

    static final String NUTRITION_NAME = "Weekly Goal";
    EditText caloriesGoal, carbGoal, fatGoal, proteinGoal, calciumGoal, cholesterolGoal, sodiumGoal;
    TextView caloriesPlanned, carbPlanned, fatPlanned, proteinPlanned, calciumPlanned, cholesterolPlanned, sodiumPlanned;
    TextView caloriesMet, carbMet, fatMet, proteinMet, calciumMet, cholesterolMet, sodiumMet;
    Button updateGoalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_screen);

        updateGoalBtn = (Button) findViewById(R.id.setGoalBtn);

        caloriesGoal = (EditText) findViewById(R.id.weeklyCalories);
        carbGoal = (EditText) findViewById(R.id.weeklyCarb);
        fatGoal = (EditText) findViewById(R.id.weeklyFat);
        proteinGoal = (EditText) findViewById(R.id.weeklyProtein);
        calciumGoal = (EditText) findViewById(R.id.weeklyCalcium);
        cholesterolGoal = (EditText) findViewById(R.id.weeklyCholesterol);
        sodiumGoal = (EditText) findViewById(R.id.weeklySodium);

        caloriesPlanned = (TextView) findViewById(R.id.plannedCalories);
        carbPlanned = (TextView) findViewById(R.id.plannedCarb);
        fatPlanned = (TextView) findViewById(R.id.plannedFat);
        proteinPlanned = (TextView) findViewById(R.id.plannedProtein);
        calciumPlanned = (TextView) findViewById(R.id.plannedCalcium);
        cholesterolPlanned = (TextView) findViewById(R.id.plannedCholesterol);
        sodiumPlanned = (TextView) findViewById(R.id.plannedSodium);

        caloriesMet = (TextView) findViewById(R.id.caloriesMet);
        carbMet = (TextView) findViewById(R.id.carbMet);
        fatMet = (TextView) findViewById(R.id.fatMet);
        proteinMet = (TextView) findViewById(R.id.proteinMet);
        calciumMet = (TextView) findViewById(R.id.calciumMet);
        cholesterolMet = (TextView) findViewById(R.id.cholesterolMet);
        sodiumMet = (TextView) findViewById(R.id.sodiumMet);

        updateGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "Weekly Goal";
                int calories = Integer.parseInt(caloriesGoal.getText().toString());
                int carb = Integer.parseInt(carbGoal.getText().toString());
                int fat = Integer.parseInt(fatGoal.getText().toString());
                int protein = Integer.parseInt(proteinGoal.getText().toString());
                int calcium = Integer.parseInt(calciumGoal.getText().toString());
                int cholesterol = Integer.parseInt(cholesterolGoal.getText().toString());
                int sodium = Integer.parseInt(sodiumGoal.getText().toString());

                MainScreenActivity.dbHelper.updateNutrition(name, calories, carb, fat, protein, calcium, cholesterol, sodium);
                finish();
            }
        });

        ArrayList<Integer> weeklyNutrition = MainScreenActivity.dbHelper.getWeeklyNutrition();
        int goalCalories = 0;
        int goalCarb = 0;
        int goalFat = 0;
        int goalProtein = 0;
        int goalCalcium = 0;
        int goalCholesterol = 0;
        int goalSodium = 0;
        if (weeklyNutrition.size() > 0) {
            goalCalories = weeklyNutrition.get(0);
            goalCarb = weeklyNutrition.get(1);
            goalFat = weeklyNutrition.get(2);
            goalProtein = weeklyNutrition.get(3);
            goalCalcium = weeklyNutrition.get(4);
            goalCholesterol = weeklyNutrition.get(5);
            goalSodium = weeklyNutrition.get(6);

            caloriesGoal.setText(String.valueOf(goalCalories));
            carbGoal.setText(String.valueOf(goalCarb));
            fatGoal.setText(String.valueOf(goalFat));
            proteinGoal.setText(String.valueOf(goalProtein));
            calciumGoal.setText(String.valueOf(goalCalcium));
            cholesterolGoal.setText(String.valueOf(goalCholesterol));
            sodiumGoal.setText(String.valueOf(goalSodium));
        }

        int totalCalories = 0;
        int totalCarb = 0;
        int totalFat = 0;
        int totalProtein = 0;
        int totalCalcium = 0;
        int totalCholesterol = 0;
        int totalSodium = 0;
        ArrayList<Meal> meals = MealScreenActivity.assignedMeals;
        for (int i=0; i<meals.size(); i++) {
            totalCalories += meals.get(i).getCalories();
            totalCarb += meals.get(i).getCarb();
            totalFat += meals.get(i).getFat();
            totalProtein += meals.get(i).getProtein();
            totalCalcium += meals.get(i).getCalcium();
            totalCholesterol += meals.get(i).getCholesterol();
            totalSodium += meals.get(i).getSodium();
        }

        caloriesPlanned.setText(String.valueOf(totalCalories));
        carbPlanned.setText(String.valueOf(totalCarb));
        fatPlanned.setText(String.valueOf(totalFat));
        proteinPlanned.setText(String.valueOf(totalProtein));
        calciumPlanned.setText(String.valueOf(totalCalcium));
        cholesterolPlanned.setText(String.valueOf(totalCholesterol));
        sodiumPlanned.setText(String.valueOf(totalSodium));

        if (goalCalories <= totalCalories) {
            caloriesMet.setText("Met");
        }
        if (goalCarb <= totalCarb) {
            carbMet.setText("Met");
        }
        if (goalFat <= totalFat) {
            fatMet.setText("Met");
        }
        if (goalProtein <= totalProtein) {
            proteinMet.setText("Met");
        }
        if (goalCalcium <= totalCalcium) {
            caloriesMet.setText("Met");
        }
        if (goalCholesterol <= totalCholesterol) {
            cholesterolMet.setText("Met");
        }
        if (goalSodium <= totalSodium) {
            sodiumMet.setText("Met");
        }

    }
}
