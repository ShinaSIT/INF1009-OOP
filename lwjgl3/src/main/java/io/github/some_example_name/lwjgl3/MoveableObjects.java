package io.github.some_example_name.lwjgl3;

public class MoveableObjects extends GameObjects {
    protected MovementManager movementManager;

    public MoveableObjects(Board board, EntityManager entityManager, int gridX, int gridY, MovementManager movementManager) {
        super(board, entityManager, gridX, gridY, "moveable");
        this.movementManager = movementManager;
    }

    @Override
    public void move(float dx, float dy) {
        movementManager.applyMovement(this, dx, dy);
    }

    public void setPosition(int gridX, int gridY) {
        this.setGridX(gridX);
        this.setGridY(gridY);
        updatePixelPosition();
    }
}
