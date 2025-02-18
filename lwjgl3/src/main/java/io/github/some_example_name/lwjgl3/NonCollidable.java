package io.github.some_example_name.lwjgl3;

public class NonCollidable extends Collidable {

    public NonCollidable(String collisionType, int gridX, int gridY) {
        super(collisionType, gridX, gridY);
        this.isSolid = false;  // Non-collidables are not solid
    }

    @Override
    public boolean detectCollision(Collidable other) {
        return false; // Non-collidables should not trigger collisions
    }
}