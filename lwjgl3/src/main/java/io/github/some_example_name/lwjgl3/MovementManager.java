package io.github.some_example_name.lwjgl3;

import java.util.List;
import java.util.ArrayList;

public class MovementManager {
    private List<MoveableObjects> movingEntities;
    private boolean physicsEnabled;
    private Speaker speaker;
    private CollisionManager collisionManager; 

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
        if (entity.getType() == EntityType.MOVEABLE) { 
            movingEntities.add((MoveableObjects) entity);
        }
    }

    /**
     * Applies movement to an entity based on the specified delta (dx, dy).
     */
    public void applyMovement(Entity entity, float dx, float dy) {
        if (entity.getType() != EntityType.MOVEABLE) return;

        int currentCol = entity.getGridX();
        int currentRow = entity.getGridY();
        float tileSize = entity.board.getTileSize();
        boolean isExpanded = entity.board.getScreenWidth() > 640;

        // Debugging statements
        System.out.println("dx: " + dx + ", dy: " + dy);
        System.out.println("Tile size: " + tileSize);

        // Adjust dx and dy if expanded
        if (isExpanded) {
            dx /= 2.0f; // Adjust step size (experiment with divisor)
            dy /= 2.0f;
        }

        // Determine new grid position
        int newCol = currentCol + (dx > tileSize / 2 ? 1 : dx < -tileSize / 2 ? -1 : 0);
        int newRow = currentRow - (dy > tileSize / 2 ? 1 : dy < -tileSize / 2 ? -1 : 0);

        // Debugging new position
        System.out.println("Calculated newCol: " + newCol + ", newRow: " + newRow);

        // Ensure new position is within bounds
        newCol = Math.max(0, Math.min(entity.board.getMazeWidth() - 1, newCol));
        newRow = Math.max(0, Math.min(entity.board.getMazeHeight() - 1, newRow));

        // Ensure movement always moves at least one tile
        if (newCol == currentCol && newRow == currentRow) {
            newCol += (dx > 0) ? 1 : (dx < 0) ? -1 : 0;
            newRow += (dy > 0) ? -1 : (dy < 0) ? 1 : 0;
        }

        // Collision check
        System.out.println("Collision check for (" + newCol + ", " + newRow + "): " + collisionManager.isMoveValid(newCol, newRow));

        // Validate move
        if (collisionManager.isMoveValid(newCol, newRow)) {
            entity.setGridX(newCol);
            entity.setGridY(newRow);

            // Update pixel position
            entity.x = newCol * tileSize + entity.board.getStartX();
            entity.y = (entity.board.getMazeHeight() - 1 - newRow) * tileSize + entity.board.getStartY();

            System.out.println("📌 Player moved to Grid (" + newCol + ", " + newRow + ")");
            speaker.playSound("sound");
        } else {
            System.out.println("❌ Blocked! Cannot move.");
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