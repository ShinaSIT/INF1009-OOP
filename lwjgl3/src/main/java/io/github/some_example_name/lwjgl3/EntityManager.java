package io.github.some_example_name.lwjgl3;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private List<Entity> entityList = new ArrayList<>();
    private int entityCount;

    public Entity createEntity(String name) {
        Entity entity = new Entity(0, 0); // Default position
        entityList.add(entity);
        entityCount++;
        return entity;
    }
    
    public void removeEntity(int id) {}
    public void updateEntities() {}
    public Entity getEntityById(int id) { 
    	return null; 
    }
}
