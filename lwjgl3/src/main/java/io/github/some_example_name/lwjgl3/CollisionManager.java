package io.github.some_example_name.lwjgl3;

import java.util.*;

public class CollisionManager {
    private List<Collidable> collidableObjects;
    private Board board;
    private EntityManager entityManager;
    // private HealthManager healthManager;  // Part 2: Commented out
    private int collisionCount = 0;
    private Set<String> collisionCache = new HashSet<>();

    public CollisionManager(Board board, EntityManager entityManager /*, HealthManager healthManager */) { // Part 2: Commented out
        this.collidableObjects = new ArrayList<>();
        this.board = board;
        this.entityManager = entityManager;
        // this.healthManager = healthManager;  // Part 2: Commented out
    }

    /**
     * Checks if a move to the specified grid position is valid.
     */
    public boolean isMoveValid(int newCol, int newRow) {
        String key = newCol + "," + newRow;
        if (collisionCache.contains(key)) {
            return false; //Instantly return cached result
        }

        // ✅ Check if the new position is a wall
        if (board.getMazeLayout()[newRow][newCol] == 1) {
            System.out.println("Blocked by wall");
            collisionCount++;
            System.out.println("Collision count: " + collisionCount);

            // if (collisionCount % 3 == 0) {  // ✅ Reduce life every 3rd wall collision (Part 2: Commented out)
            //     healthManager.reduceLife();
            // }
            collisionCache.add(key); // ✅ Store invalid positions
            return false;  // Move is not valid
        }

        for (Entity entity : entityManager.getEntities()) {
            if (entity.getType() == EntityType.STATIC) {
                StaticObjects obj = (StaticObjects) entity;
                if (obj.getGridX() == newCol && obj.getGridY() == newRow) {
                    System.out.println("❌ Blocked by Static Object");
                    collisionCount++;
                    System.out.println("Collision count: " + collisionCount);
                    // ✅ Immediately reduce a life when hitting a static object (Part 2: Commented out)
                    // healthManager.reduceLife();
                    collisionCache.add(key); // ✅ Store invalid positions
                    return false;  // Move is not valid
                }
            }
        }

        System.out.println("Free to move");
        return true; 
    }
    /**
     * Checks for collisions between all collidable objects.
     */
//    public void checkCollisions() {
//        for (int i = 0; i < collidableObjects.size(); i++) {
//            Collidable obj1 = collidableObjects.get(i);
//
//            for (int j = i + 1; j < collidableObjects.size(); j++) {
//                Collidable obj2 = collidableObjects.get(j);
//
//                if (obj1.detectCollision(obj2)) {
//                    obj1.resolveCollision(obj2);
//                    obj2.resolveCollision(obj1);
//                }
//            }
//        }
//    }
}
