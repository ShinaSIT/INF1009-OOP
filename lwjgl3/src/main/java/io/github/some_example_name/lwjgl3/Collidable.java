package io.github.some_example_name.lwjgl3;

public interface Collidable {
    boolean isSolid();
    int getGridX();
    int getGridY();
    void setGridX(int gridX);
    void setGridY(int gridY);
    boolean detectCollision(Collidable other);
}
