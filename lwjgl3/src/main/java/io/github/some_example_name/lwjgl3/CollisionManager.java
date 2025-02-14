package io.github.some_example_name.lwjgl3;
import java.util.*;

public class CollisionManager {
    private List<Collidable> collidableObjects;
    private Map<Collidable, List<Collidable>> collisionData; //Dictionary for collisionData

    public CollisionManager() {
        collidableObjects = new ArrayList<>();
        collisionData = new HashMap<>();
    }

    
    public void checkCollisions() {
        collisionData.clear();

        for (int i = 0; i < collidableObjects.size(); i++) {
            Collidable obj1 = collidableObjects.get(i);

            for (int j = i + 1; j < collidableObjects.size(); j++) {
                Collidable obj2 = collidableObjects.get(j);

                if (obj1.detectCollision(obj2)) {
                    // Ensure obj1 has an entry in the map
                    if (!collisionData.containsKey(obj1)) {
                        collisionData.put(obj1, new ArrayList<>());
                    }
                    collisionData.get(obj1).add(obj2);

                    // Ensure obj2 has an entry in the map
                    if (!collisionData.containsKey(obj2)) {
                        collisionData.put(obj2, new ArrayList<>());
                    }
                    collisionData.get(obj2).add(obj1);

                    // Resolve the collision
                    //resolveCollision(obj1, obj2);
                }
            }
        }
    }

    //public void resolveCollision(Collidable obj1, Collidable obj2) {
      //  obj1.resolveCollision(obj2);
        //obj2.resolveCollision(obj1);
    //}

    public void addCollidable(Collidable obj) {
        if (!collidableObjects.contains(obj)) {
            collidableObjects.add(obj);
        }
    }

    public void removeCollidable(Collidable obj) {
        collidableObjects.remove(obj);
        collisionData.remove(obj);
    }

    public List<Collidable> getCollisionsFor(Collidable obj) {
        return collisionData.getOrDefault(obj, new ArrayList<>());
    }
}

