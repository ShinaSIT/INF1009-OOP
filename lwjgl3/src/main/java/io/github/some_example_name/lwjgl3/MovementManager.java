package io.github.some_example_name.lwjgl3;

import java.util.List;
import java.util.ArrayList;

public class MovementManager {
    private List<MoveableObjects> movingEntities;
    private boolean physicsEnabled;
    private Speaker speaker;
    private CollisionManager collisionManager;
    
    // (Optional) You can add a cooldown mechanism here if needed.
    
    // Base tile size used for input normalization (when minimized)
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
        if (entity.getType() == EntityType.MOVEABLE) {
            movingEntities.add((MoveableObjects) entity);
        }
    }

    /**
     * Applies movement to an entity based on the specified delta (dx, dy).
     */
    public void applyMovement(Entity entity, float dx, float dy) {
        // Only process MOVEABLE entities.
        if (entity.getType() != EntityType.MOVEABLE) return;
        
        int currentCol = entity.getGridX();
        int currentRow = entity.getGridY();
        float tileSize = entity.board.getTileSize();
        // Determine mode: expanded if the screen width is greater than 640.
        boolean isExpanded = entity.board.getScreenWidth() > 640;
        
        System.out.println("applyMovement called with dx: " + dx + ", dy: " + dy);
        System.out.println("Tile size: " + tileSize);
        
        // If expanded, normalize dx and dy to the base tile size.
        // For example, if tileSize is 128, then scale = baseTile / 128.
        if (isExpanded) {
            float scale = baseTile / tileSize;
            dx = dx * scale;
            dy = dy * scale;
            System.out.println("Normalized dx: " + dx + ", dy: " + dy);
        }
        
        // Use the baseTile as the threshold for moving one grid cell.
        float threshold = baseTile;
        
        // Determine horizontal grid movement.
        int deltaCol = (dx >= threshold) ? 1 : (dx <= -threshold ? -1 : 0);
        // For vertical movement:
        //   Positive dy (swipe up) should decrease the grid row.
        //   Negative dy (swipe down) should increase the grid row.
        int deltaRow = (dy >= threshold) ? -1 : (dy <= -threshold ? 1 : 0);
        
        int newCol = currentCol + deltaCol;
        int newRow = currentRow + deltaRow;
        
        System.out.println("Calculated grid position: (" + newCol + ", " + newRow + ")");
        
        // Clamp new grid coordinates to the board boundaries.
        newCol = Math.max(0, Math.min(entity.board.getMazeWidth() - 1, newCol));
        newRow = Math.max(0, Math.min(entity.board.getMazeHeight() - 1, newRow));
        
        System.out.println("Clamped grid position: (" + newCol + ", " + newRow + ")");
        
        // Check collision once.
        if (collisionManager.isMoveValid(newCol, newRow)) {
            entity.setGridX(newCol);
            entity.setGridY(newRow);
            
            // Update pixel position.
            // Using an inverted y formula so that row 0 is at the bottom:
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
