package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
    protected float x, y, tileSize;
    protected int gridX, gridY;
    protected Board board;

    public Entity(Board board, int gridX, int gridY) {
        this.board = board;
        this.gridX = gridX;
        this.gridY = gridY;
        this.tileSize = board.getTileSize();
        updatePixelPosition();
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public int getGridX() { return gridX; }
    public int getGridY() { return gridY; }
    
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
        this.tileSize = board.getTileSize(); // ✅ Keep tile size updated
        this.x = gridX * tileSize + board.getStartX();
        this.y = (board.getMazeHeight() - 1 - gridY) * tileSize + board.getStartY();
    }

    public abstract void render(SpriteBatch batch);
}