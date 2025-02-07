package io.github.some_example_name.lwjgl3;

public class GameObjects extends Entity{

    protected float speed;
    protected String direction;

    public GameObjects(int x, int y, float speed, String direction) {
        super(x, y);
        this.speed = speed;
        this.direction = direction;
    }

    public void updatePosition(Entity entity) {}
    public void applyPhysics(Entity entity) {}
    public void move() {}
}
