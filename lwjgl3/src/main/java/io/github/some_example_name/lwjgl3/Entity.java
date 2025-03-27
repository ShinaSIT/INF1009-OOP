package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.HashSet;
import java.util.Set;

public abstract class Entity implements IMoveable {
    protected float x, y;
    protected int gridX, gridY;
    protected Board board;
    protected String tag;
    protected Set<String> entityTags;

    public Entity(Board board, int gridX, int gridY, String tag) {
        if (board == null) {
            throw new IllegalArgumentException("❌ Board cannot be null!");
        }
        this.board = board;
        this.gridX = gridX;
        this.gridY = gridY;
        this.tag = tag;
        this.entityTags = new HashSet<>();
        updatePixelPosition();
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public int getGridX() { return gridX; }
    public int getGridY() { return gridY; }
    
    public void addTag(String tag) {
        entityTags.add(tag);
    }
    
    public boolean hasTag(String tag) {
        return entityTags.contains(tag);
    }
    
    public Set<String> getTags() {
        return entityTags;
    }
    
    public void setGridX(int gridX) { 
        this.gridX = Math.max(0, Math.min(gridX, board.getMazeWidth() - 1)); 
        updatePixelPosition();
    }

    public void setGridY(int gridY) { 
        this.gridY = Math.max(0, Math.min(gridY, board.getMazeHeight() - 1)); 
        updatePixelPosition();
    }

    public void updatePixelPosition() {
        float tileSize = board.getTileSize();
        
        this.x = gridX * tileSize + board.getStartX();
        this.y = (board.getMazeHeight() - gridY - 1) * tileSize + board.getStartY();
    }

    public abstract void render(SpriteBatch batch);
    
    @Override
    public void move(float dx, float dy, boolean isGerm) {
        // Default empty implementation, subclasses like MoveableObjects will override it
    }
}
