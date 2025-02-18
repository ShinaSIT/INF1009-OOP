package io.github.some_example_name.lwjgl3;

import java.util.List;
import java.util.ArrayList;

public class MovementManager {
    private List<MoveableObjects> movingEntities;
    private boolean physicsEnabled;
    private Speaker speaker;
    private CollisionManager collisionManager; // ✅ Added CollisionManager

    public MovementManager(Speaker speaker, CollisionManager collisionManager) {
        this.movingEntities = new ArrayList<>();
        this.physicsEnabled = true;
        this.speaker = speaker;
        this.collisionManager = collisionManager; // ✅ Initialize CollisionManager
    }

    /**
     * Adds a moveable entity to the manager.
     */
    public void addEntity(MoveableObjects entity) {
        movingEntities.add(entity);
    }

    /**
     * Applies movement to an entity based on the specified delta (dx, dy).
     */
    public void applyMovement(MoveableObjects entity, float dx, float dy) {
        int currentCol = entity.getGridX();
        int currentRow = entity.getGridY();

        int newCol = currentCol + Math.round(dx / entity.board.getTileSize());
        int newRow = currentRow - Math.round(dy / entity.board.getTileSize());

        System.out.println("Trying to move from tile (" + currentCol + ", " + currentRow + ") to (" + newCol + ", " + newRow + ")");

        // ✅ Use CollisionManager to check if the move is valid
        if (collisionManager.isMoveValid(newCol, newRow)) {
            // Update the entity's grid position
            entity.setGridX(newCol);
            entity.setGridY(newRow);

            // Update the entity's pixel position
            entity.x = newCol * entity.board.getTileSize() + entity.board.getStartX();
            entity.y = (entity.board.getMazeHeight() - 1 - newRow) * entity.board.getTileSize() + entity.board.getStartY();

            System.out.println("Moved to: " + entity.x + ", " + entity.y);
            speaker.playSound("sound");
        } else {
            System.out.println("Blocked! Cannot move.");
            speaker.playSound("block");
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