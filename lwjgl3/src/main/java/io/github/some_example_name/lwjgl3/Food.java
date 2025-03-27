package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;

public class Food {
    private String type;
    private Texture texture;
    private int scoreValue;

    public Food(String type, Texture texture) {
        this.type = type;
        this.texture = texture;
        this.scoreValue = "healthy".equals(type) ? 100 : -100;
    }

    public String getType() {
        return type;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isHealthy() {
        return "healthy".equals(type);
    }

    public boolean isUnhealthy() {
        return "unhealthy".equals(type);
    }

    public int getScoreValue() {
        return scoreValue;
    }
    
}
