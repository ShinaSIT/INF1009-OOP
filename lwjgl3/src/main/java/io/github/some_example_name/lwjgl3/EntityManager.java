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

                entity.x = gridX * board.getTileSize() + board.getStartX();
                entity.y = gridY * board.getTileSize() + board.getStartY();
            }
        }
    }
    
    public void render(SpriteBatch batch) {
        for (Entity entity : entities) {
//            System.out.println("Rendering entity at: (" + entity.getX() + ", " + entity.getY() + ")");
            entity.render(batch);
        }
    }

}
