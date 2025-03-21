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
        if (entity.hasTag("moveable")) { // ✅ Replaced EntityType with tags
            movingEntities.add((MoveableObjects) entity);
        }
    }

    /**
     * Applies movement to an entity based on the specified delta (dx, dy).
     */
    public void applyMovement(Entity entity, float dx, float dy) {
        if (!entity.hasTag("moveable")) return; // ✅ Replaced EntityType check
        
        int currentCol = entity.getGridX();
        int currentRow = entity.getGridY();
        float tileSize = entity.board.getTileSize();
        boolean isExpanded = entity.board.getScreenWidth() > 640;

        System.out.println("applyMovement called with dx: " + dx + ", dy: " + dy);
        System.out.println("Tile size: " + tileSize);
        
        if (isExpanded) {
            float scale = baseTile / tileSize;
            dx = dx * scale;
            dy = dy * scale;
            System.out.println("Normalized dx: " + dx + ", dy: " + dy);
        }
        
        int deltaCol = (int) dx;
        int deltaRow = (int) dy;

        
        int newCol = currentCol + deltaCol;
        int newRow = currentRow + deltaRow;
        
        System.out.println("Calculated grid position: (" + newCol + ", " + newRow + ")");
        
        newCol = Math.max(0, Math.min(entity.board.getMazeWidth() - 1, newCol));
        newRow = Math.max(0, Math.min(entity.board.getMazeHeight() - 1, newRow));
        
        System.out.println("Clamped grid position: (" + newCol + ", " + newRow + ")");
        
        if (collisionManager.isMoveValid(newCol, newRow)) {
            entity.setGridX(newCol);
            entity.setGridY(newRow);
            
            entity.x = newCol * tileSize + entity.board.getStartX();
            entity.y = (entity.board.getMazeHeight() - 1 - newRow) * tileSize + entity.board.getStartY();
            
            System.out.println("📌 Player moved to Grid (" + newCol + ", " + newRow + ")");
            System.out.println("📌 Updated pixel position: (" + entity.x + ", " + entity.y + ")");
            
            speaker.playSound("sound");
        } else {
            System.out.println("❌ Move blocked: Collision at (" + newCol + ", " + newRow + ")");
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
