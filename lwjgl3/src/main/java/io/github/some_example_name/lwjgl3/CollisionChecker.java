package io.github.some_example_name.lwjgl3;

public interface CollisionChecker {
    boolean isMoveValid(int gridX, int gridY, boolean isGerm);
    void addCollidable(Collidable c);      
    void removeCollidable(Collidable c);   
    void clearCollidables();               
}
