package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.math.Rectangle;

public abstract class Collidable {
    public boolean isSolid = true;
    public String collisionType;   
    private Rectangle hitbox;      

    public Collidable(String collisionType, Rectangle hitbox) {
        this.collisionType = collisionType;
        this.hitbox = hitbox;
    }

    public boolean detectCollision(Collidable other) {
        return this.hitbox.overlaps(other.hitbox);
    }

    private void resolveCollision(Collidable other) {
    }

    private void onCollisionEffect() {
    }
}
