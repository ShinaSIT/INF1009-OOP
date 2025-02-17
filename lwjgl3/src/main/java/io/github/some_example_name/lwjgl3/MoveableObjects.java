package io.github.some_example_name.lwjgl3;

public class MoveableObjects extends GameObjects {
    private MovementManager movementManager;

    public MoveableObjects(Board board, EntityManager entityManager, int gridX, int gridY, float tileSize, MovementManager movementManager) {
        super(board, entityManager, gridX, gridY, tileSize);
        this.movementManager = movementManager;
    }

    public void move(float dx, float dy) {
        movementManager.applyMovement(this, dx, dy); 
    }
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
