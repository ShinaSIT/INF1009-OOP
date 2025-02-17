package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.math.Rectangle;

public class NonCollidable extends Collidable {

    public NonCollidable(String collisionType, Rectangle hitbox) {
        super(collisionType, hitbox);
        this.isSolid = false;  // Non-collidables are not solid
    }

    @Override
    public boolean detectCollision(Collidable other) {
        return false; // Non-collidables should not trigger collisions
    }
}

