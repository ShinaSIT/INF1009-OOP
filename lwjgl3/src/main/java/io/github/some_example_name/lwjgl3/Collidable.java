package io.github.some_example_name.lwjgl3;

public abstract class Collidable {
    public boolean isSolid = true;
    public String collisionType;   
    private int gridX, gridY;  

    public Collidable(String collisionType, int gridX, int gridY) {
        this.collisionType = collisionType;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public boolean detectCollision(Collidable other) {
        // Check if two objects occupy the same grid cell
        return this.gridX == other.gridX && this.gridY == other.gridY;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    //public void resolveCollision(Collidable other) {
        // Default collision resolution logic (can be overridden by subclasses)
    //}
}