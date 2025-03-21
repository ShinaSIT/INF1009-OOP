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

    public void addCollidable(Collidable collidable) {
        collidableObjects.add(collidable);
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

        // Check against collidable objects
        for (Collidable collidable : collidableObjects) {
            if (collidable.getGridX() == newCol && collidable.getGridY() == newRow && collidable.isSolid()) {
                System.out.println("❌ Blocked by Collidable Object");
                collisionCount++;
                System.out.println("Collision count: " + collisionCount);
                collisionCache.add(key); // ✅ Store invalid positions
                return false;  // Move is not valid
            }
        }

        System.out.println("Free to move");
        return true;
    }
    
    public void checkCollisions() {
        for (int i = 0; i < collidableObjects.size(); i++) {
            for (int j = i + 1; j < collidableObjects.size(); j++) {
                Collidable a = collidableObjects.get(i);
                Collidable b = collidableObjects.get(j);

                if (a.detectCollision(b)) {
                    System.out.println("Collision Detected between: " + a.getClass().getSimpleName() + " and " + b.getClass().getSimpleName());
                    resolveCollision(a, b);
                }
            }
        }
    }

    private void resolveCollision(Collidable a, Collidable b) {
        if (a instanceof NonCollidable || b instanceof NonCollidable) {
            return;
        }

        int tempX = a.getGridX();
        int tempY = a.getGridY();
        a.setGridX(b.getGridX());
        a.setGridY(b.getGridY());
        b.setGridX(tempX);
        b.setGridY(tempY);
    }
}