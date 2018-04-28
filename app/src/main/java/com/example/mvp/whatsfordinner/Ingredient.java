package com.example.mvp.whatsfordinner;

/**
 * Ingredient object, containing a name, quantity, and unit
 */
public class Ingredient {
    private String name;
    private int quantity;
    private String unit;

    public Ingredient(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void incrementCount() {
        this.quantity++;
    }

    public void decrementCount() {
        if (this.quantity > 0)
            this.quantity--;
    }

    @Override
    public boolean equals(Object o) {
        Ingredient ingredient = (Ingredient) o;
        return this.name.equalsIgnoreCase(ingredient.getName());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(quantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + " (" + quantity + " " + unit + ")";
    }
}
