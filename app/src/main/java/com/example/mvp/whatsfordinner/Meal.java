package com.example.mvp.whatsfordinner;

/**
 * Meal object, including name, day of week (monday, tuesday,...), time of day (breakfast, lucnh, dinner), calories, carbohydrates, fat, protein, calcium, cholesterol, and sodium values
 */
public class Meal {
    private String mealName;
    private String dayOfWeek;
    private String timeOfDay;
    private int calories;
    private int carb;
    private int fat;
    private int protein;
    private int calcium;
    private int cholesterol;
    private int sodium;

    public Meal(String mealName, String dayOfWeek, String timeOfDay, int calories, int carb, int fat, int protein, int calcium, int cholesterol, int sodium) {
        this.mealName = mealName;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
        this.calories = calories;
        this.carb = carb;
        this.fat = fat;
        this.protein = protein;
        this.calcium = calcium;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
    }


    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarb() {
        return carb;
    }

    public void setCarb(int carb) {
        this.carb = carb;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCalcium() {
        return calcium;
    }

    public void setCalcium(int calcium) {
        this.calcium = calcium;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }
}
