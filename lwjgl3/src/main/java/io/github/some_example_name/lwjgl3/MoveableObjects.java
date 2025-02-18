package io.github.some_example_name.lwjgl3;

public class MoveableObjects extends GameObjects {
    private MovementManager movementManager;

    public MoveableObjects(Board board, EntityManager entityManager, int gridX, int gridY, float tileSize, MovementManager movementManager) {
        super(board, entityManager, gridX, gridY, tileSize);
        this.movementManager = movementManager;
    }

    /**
     * Moves the object by the specified delta in grid coordinates.
     */
    public void move(float dx, float dy) {
        movementManager.applyMovement(this, dx, dy); 
    }

    public void setPosition(int gridX, int gridY) {
        this.setGridX(gridX);
        this.setGridY(gridY);
    }
}