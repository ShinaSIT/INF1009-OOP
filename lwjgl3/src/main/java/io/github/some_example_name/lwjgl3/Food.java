package io.github.some_example_name.lwjgl3;

public class Food {
    private String name;
    private boolean isHealthy;

    public Food(String name, boolean isHealthy) {
        this.name = name;
        this.isHealthy = isHealthy;
    }

    public String getName() {
        return name;
    }

    public boolean isHealthy() {
        return isHealthy;
    }
}
