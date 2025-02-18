package io.github.some_example_name.lwjgl3;

import java.util.List;
import java.util.ArrayList;

public class MovementManager {
    private List<MoveableObjects> movingEntities;
    private boolean physicsEnabled;
    private Speaker speaker;

    public MovementManager(Speaker speaker) {
        this.movingEntities = new ArrayList<>();
        this.physicsEnabled = true;
        this.speaker = speaker;
    }

    public void addEntity(MoveableObjects entity) {
        movingEntities.add(entity);
    }

    public void applyMovement(MoveableObjects entity, float dx, float dy) {
        int currentCol = Math.round((entity.x - entity.board.getStartX()) / entity.board.getTileSize());
        int currentRow = entity.board.getMazeHeight() - 1 - Math.round((entity.y - entity.board.getStartY()) / entity.board.getTileSize()); 

        int newCol = currentCol + Math.round(dx / entity.board.getTileSize());
        int newRow = currentRow - Math.round(dy / entity.board.getTileSize());

        System.out.println("Trying to move from tile (" + currentCol + ", " + currentRow + ") to (" + newCol + ", " + newRow + ")");

        if (entity.canMove(newCol, newRow)) {
            entity.x = newCol * entity.board.getTileSize() + entity.board.getStartX();
            entity.y = (entity.board.getMazeHeight() - 1 - newRow) * entity.board.getTileSize() + entity.board.getStartY();
            System.out.println("Moved to: " + entity.x + ", " + entity.y);
            speaker.playSound("sound");
        } else {
            System.out.println("Blocked! Cannot move.");
            speaker.playSound("block");
        }
    }

    public void updatePositions() {
        for (MoveableObjects entity : movingEntities) {
            applyMovement(entity, 0, 0); // This ensures entities are always aligned to the grid
        }
    }

    public void applyPhysics(MoveableObjects entity) {
        if (physicsEnabled) {
            // Implement gravity, collisions, etc.
        }
    }
}