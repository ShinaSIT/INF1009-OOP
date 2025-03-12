package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

public class FoodManager {
    private List<Food> foodItems;

    public FoodManager() {
        foodItems = new ArrayList<>();
    }

    public void addFood(Food food) {
        foodItems.add(food);
    }

    public void removeFood(Food food) {
        foodItems.remove(food);
    }

    public List<Food> getFoodItems() {
        return foodItems;
    }
}