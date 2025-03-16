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
            if (entity.hasTag("moveable") || entity.hasTag("static")) { 
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
            entity.render(batch);
        }
    }
    
    public void updateAllEntities(Board board) {
        for (Entity entity : entities) {
            if (entity.hasTag("moveable")) {
                System.out.println("📌 MOVEABLE entity retained at (" + entity.getGridX() + ", " + entity.getGridY() + ")");
                continue;
            }
            
            if (entity.hasTag("static")) {
                int newGridX = Math.round((entity.x - board.getStartX()) / board.getTileSize());
                int newGridY = Math.round((entity.y - board.getStartY()) / board.getTileSize());

                entity.x = newGridX * board.getTileSize() + board.getStartX();
                entity.y = newGridY * board.getTileSize() + board.getStartY();
            }
        }
    }
    
    public void updateEntitiesOnResize() {
        for (Entity entity : entities) {
            int gridX = entity.getGridX();
            int gridY = entity.getGridY();
            entity.updatePixelPosition();
            System.out.println("📌 " + entity.getTags() + " updated to Grid (" + gridX + ", " + gridY + ") at (" + entity.x + ", " + entity.y + ")");
        }
    }

    public void clearStaticObjects() {
        System.out.println("⚠️ Clearing Static Objects...");
        entities.removeIf(entity -> {
            boolean isStatic = entity.hasTag("static");
            if (isStatic) {
                System.out.println("❌ Removing STATIC entity at (" + entity.getGridX() + ", " + entity.getGridY() + ")");
            }
            return isStatic;
        });
    }

    public void removeEntity(Entity entity) {
        System.out.println("⚠️ Attempting to remove entity: " + entity.getTags() + " at (" + entity.getGridX() + ", " + entity.getGridY() + ")");

        if (entity.hasTag("moveable")) {
            System.out.println("🚨 WARNING: Preventing removal of MOVEABLE entity!");
            return;
        }

        if (entities.contains(entity)) {
            entities.remove(entity);
            System.out.println("❌ Entity removed: " + entity.getTags() + " at (" + entity.getGridX() + ", " + entity.getGridY() + ")");
        } else {
            System.out.println("⚠ Entity not found: " + entity.getTags());
        }
    }

    public void ensurePlayerExists() {
        if (getPlayer() == null) {
            System.out.println("🔄 Player entity was removed. Re-adding...");
            MoveableObjects player = new MoveableObjects(board, this, 1, 1, new MovementManager(new Speaker(), new CollisionManager(board, this)));
            addEntity(player);
        }
    }
    
    public MoveableObjects getPlayer() {
        for (Entity entity : entities) {
            if (entity.hasTag("moveable")) {
                return (MoveableObjects) entity;
            }
        }
        return null;
    }
}
