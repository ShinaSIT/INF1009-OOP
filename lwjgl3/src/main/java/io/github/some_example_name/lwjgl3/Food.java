package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;

public class Food {
    private String type; // "healthy" or "unhealthy"
    private Texture texture;

    public Food(String type, Texture texture) {
        this.type = type;
        this.texture = texture;
    }

    public String getType() {
        return type;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isUnhealthy() {
        return "unhealthy".equalsIgnoreCase(type);
    }
    
    public boolean isHealthy() {
    	return "healthy".equalsIgnoreCase(type);
    }
    
}
