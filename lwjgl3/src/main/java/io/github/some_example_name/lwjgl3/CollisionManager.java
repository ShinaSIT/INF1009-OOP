package io.github.some_example_name.lwjgl3;

import java.util.*;

public class CollisionManager {
    private List<Collidable> collidableObjects;
    private Board board;
    private EntityManager entityManager;
    private int collisionCount = 0;
    private Set<String> collisionCache = new HashSet<>();

    public CollisionManager(Board board, EntityManager entityManager) {
        this.collidableObjects = new ArrayList<>();
        this.board = board;
        this.entityManager = entityManager;
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
            collisionCache.add(key); // ✅ Store invalid positions
            return false;  // Move is not valid
        }

        for (Entity entity : entityManager.getEntities()) {
            if (entity.hasTag("static")) { 
                StaticObjects obj = (StaticObjects) entity;
                if (obj.getGridX() == newCol && obj.getGridY() == newRow) {
                    System.out.println("❌ Blocked by Static Object");
                    collisionCount++;
                    System.out.println("Collision count: " + collisionCount);
                    collisionCache.add(key); // ✅ Store invalid positions
                    return false;  // Move is not valid
                }
            }
        }

        System.out.println("Free to move");
        return true; 
    }
}
