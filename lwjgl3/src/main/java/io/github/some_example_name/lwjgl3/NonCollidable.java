package io.github.some_example_name.lwjgl3;

public class NonCollidable implements Collidable {

    private int gridX, gridY;

    public NonCollidable(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
    }

    @Override
    public boolean isSolid() {
        return false;
    }


    @Override
    public int getGridX() {
        return gridX;
    }

    @Override
    public int getGridY() {
        return gridY;
    }

    @Override
    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    @Override
    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    @Override
    public boolean detectCollision(Collidable other) {
        return false;
    }
}
