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

    /**
     * Adds a moveable entity to the manager.
     */
    public void addEntity(Entity entity) {
        if (entity.hasTag("moveable")) {  
            movingEntities.add((MoveableObjects) entity);
            System.out.println("✅ Added " + entity + " to movingEntities.");
        }
    }

    /**
     * Applies movement to an entity based on the specified delta (dx, dy).
     */
    public void applyMovement(MoveableObjects entity, float dx, float dy) {
        int oldCol = entity.getGridX();
        int oldRow = entity.getGridY();

        int newCol = oldCol + (int) dx;
        int newRow = oldRow + (int) dy;

        System.out.println("🔄 Attempting Move: (" + oldCol + ", " + oldRow + ") → (" + newCol + ", " + newRow + ")");

        if (collisionManager != null && collisionManager.isMoveValid(newCol, newRow)) {
            entity.setGridX(newCol);
            entity.setGridY(newRow);
            entity.updatePixelPosition();

            System.out.println("✅ Move Successful!");
        } else {
            System.out.println("❌ Collision! Cannot move to (" + newCol + ", " + newRow + ")");
        }
    }

    /**
     * Applies physics to an entity (e.g., gravity, collisions).
     */
    public void applyPhysics(MoveableObjects entity) {
        if (physicsEnabled) {
            // Implement gravity, collisions, etc.
        }
    }
}
