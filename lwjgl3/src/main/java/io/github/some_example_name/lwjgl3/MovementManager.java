package io.github.some_example_name.lwjgl3;

import java.util.List;
import java.util.ArrayList;

public class MovementManager {
    private List<MoveableObjects> movingEntities;
    private boolean physicsEnabled;
    private Speaker speaker;
    private CollisionManager collisionManager;
    
    private final float baseTile = 42.666668f;

    public MovementManager(Speaker speaker, CollisionManager collisionManager) {
        this.movingEntities = new ArrayList<>();
        this.physicsEnabled = true;
        this.speaker = speaker;
        this.collisionManager = collisionManager;
    }

    public void addEntity(Entity entity) {
        if (entity.hasTag("moveable")) {  
            movingEntities.add((MoveableObjects) entity);
            System.out.println("✅ Added Moveable Object - AI (Germ)");
        }
    }

    public void applyMovement(MoveableObjects entity, float dx, float dy, boolean isGerm) {
        int oldCol = entity.getGridX();
        int oldRow = entity.getGridY();

        int newCol = oldCol + (int) dx;
        int newRow = oldRow + (int) dy;

        if (collisionManager != null && collisionManager.isMoveValid(newCol, newRow, isGerm)) {
            entity.setGridX(newCol);
            entity.setGridY(newRow);
            entity.updatePixelPosition();
        }
    }


    public void applyPhysics(MoveableObjects entity) {
        if (physicsEnabled) {
        }
    }
}
