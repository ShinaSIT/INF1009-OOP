package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityManager {
    private List<Entity> entities;
    private Board board;

    public EntityManager() {
        entities = new ArrayList<>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void updatePositions(Board board) {
        for (Entity entity : entities) {
            if (entity instanceof GameObjects || entity instanceof StaticObjects) {
                int gridX = Math.round((entity.x - board.getStartX()) / board.getTileSize());
                int gridY = Math.round((entity.y - board.getStartY()) / board.getTileSize());

                // ✅ Ensure within bounds
                gridX = Math.max(0, Math.min(gridX, board.getMazeWidth() - 1));
                gridY = Math.max(0, Math.min(gridY, board.getMazeHeight() - 1));

                entity.x = gridX * board.getTileSize() + board.getStartX();
                entity.y = gridY * board.getTileSize() + board.getStartY();

                System.out.println("📌 Entity Clamped to Grid (" + gridX + ", " + gridY + ")");
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Entity entity : entities) {
//            System.out.println("Rendering entity at: (" + entity.getX() + ", " + entity.getY() + ")");
            entity.render(batch);
        }
    }
    
    public void updateAllEntities(Board board) {
        for (Entity entity : entities) {
            if (entity instanceof GameObjects || entity instanceof StaticObjects) {
                int newGridX = Math.round((entity.x - board.getStartX()) / board.getTileSize());
                int newGridY = Math.round((entity.y - board.getStartY()) / board.getTileSize());

                entity.x = newGridX * board.getTileSize() + board.getStartX();
                entity.y = newGridY * board.getTileSize() + board.getStartY();

                if (entity instanceof MoveableObjects) {
                    System.out.println("📌 MoveableObjects updated to Grid (" + newGridX + ", " + newGridY + ")");
                } else if (entity instanceof StaticObjects) {
                    System.out.println("📌 StaticObjects updated to Grid (" + newGridX + ", " + newGridY + ")");
                }
            }
        }
    }
    
    public void updateEntitiesOnResize(Board board) {
        for (Entity entity : entities) {
            // Retrieve grid position
            int gridX = entity.getGridX();
            int gridY = entity.getGridY();

            // Convert to pixel positions after resizing
            entity.updatePixelPosition(board);

            System.out.println("📌 " + entity.getClass().getSimpleName() +
                " updated to Grid (" + gridX + ", " + gridY + ") at (" + entity.x + ", " + entity.y + ")");
        }
    }

    public void clearStaticObjects() {
        entities.removeIf(entity -> entity instanceof StaticObjects);
    }

    public MoveableObjects getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof MoveableObjects) {
                return (MoveableObjects) entity;
            }
        }
        return null; // No player found
    }

    public void removeEntity(Entity entity) {
        if (entities.contains(entity)) {
            entities.remove(entity);
            System.out.println("❌ Entity removed: " + entity.getClass().getSimpleName());
        } else {
            System.out.println("⚠ Entity not found: " + entity.getClass().getSimpleName());
        }
    }

}
