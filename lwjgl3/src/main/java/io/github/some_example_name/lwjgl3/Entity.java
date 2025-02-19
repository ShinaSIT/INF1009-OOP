package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
    protected float x, y, tileSize;
    protected int gridX, gridY;

    public Entity(Board board, int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.tileSize = board.getTileSize();
        updatePixelPosition(board);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public int getGridX() { return gridX; }
    public int getGridY() { return gridY; }
    
    public void setGridX(int gridX, Board board) { 
        this.gridX = Math.max(0, Math.min(gridX, board.getMazeWidth() - 1)); 
        updatePixelPosition(board);
    }
    
    public void setGridY(int gridY, Board board) { 
        this.gridY = Math.max(0, Math.min(gridY, board.getMazeHeight() - 1)); 
        updatePixelPosition(board);
    }

    /**
     * Updates `x` and `y` positions based on `gridX` and `gridY`
     * This ensures the **entity always aligns correctly** with the board!
     */
    public void updatePixelPosition(Board board) {
        this.tileSize = board.getTileSize(); // ✅ Keep tile size updated
        this.x = gridX * tileSize + board.getStartX();
        this.y = (board.getMazeHeight() - 1 - gridY) * tileSize + board.getStartY();
    }

    public abstract void render(SpriteBatch batch);
}
