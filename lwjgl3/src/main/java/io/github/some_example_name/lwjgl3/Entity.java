package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
    protected float x, y, tileSize;
    
    public Entity(float x, float y, float tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
    }
    
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    
    public abstract void render(SpriteBatch batch);
}

