package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.HashSet;
import java.util.Set;

public abstract class Entity {
    protected float x, y;
    protected int gridX, gridY;
    protected Board board;
    protected Set<String> entityTags;

    public Entity(Board board, int gridX, int gridY, String... tags) {
        this.board = board;
        this.gridX = gridX;
        this.gridY = gridY;
        this.entityTags = new HashSet<>();
        for (String tag : tags) {
            this.entityTags.add(tag);
        }
        updatePixelPosition();
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public int getGridX() { return gridX; }
    public int getGridY() { return gridY; }
    
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

    /**
     * Updates `x` and `y` positions based on `gridX` and `gridY`
     * This ensures the **entity always aligns correctly** with the board!
     */
    public void updatePixelPosition() {
        float tileSize = board.getTileSize();
        
        // ✅ Fix: Adjust Y-Position to Ensure Correct Alignment
        this.x = gridX * tileSize + board.getStartX();
        this.y = (board.getMazeHeight() - gridY - 1) * tileSize + board.getStartY();

        System.out.println("📌 Updated pixel position: (" + x + ", " + y + "), Screen Height: " + Gdx.graphics.getHeight());
    }
    
    public abstract void render(SpriteBatch batch);
}