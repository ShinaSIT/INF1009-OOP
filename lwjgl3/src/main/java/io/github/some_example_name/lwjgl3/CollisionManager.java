package io.github.some_example_name.lwjgl3;

import java.util.*;

public class CollisionManager {
    private List<Collidable> collidableObjects;
    private Board board;
    private EntityManager entityManager;

    public CollisionManager(Board board, EntityManager entityManager) {
        this.collidableObjects = new ArrayList<>();
        this.board = board;
        this.entityManager = entityManager;
    }

    /**
     * Checks if a move to the specified grid position is valid.
     */
    public boolean isMoveValid(int newCol, int newRow) {
        // Check if the new position is blocked by a wall
        if (board.getMazeLayout()[newRow][newCol] == 1) {
        	System.out.println("Blocked by wall");
            return false; // Blocked by wall
        }

        // Check if the new position is blocked by a static object
        for (Entity entity : entityManager.getEntities()) {
            if (entity instanceof StaticObjects) {
                StaticObjects obj = (StaticObjects) entity;
                if (obj.getGridX() == newCol && obj.getGridY() == newRow) {
                	System.out.println("Blocked by Static Object");
                    return false; // Blocked by static object
                }
            }
        }
        System.out.println("Free to move");
        return true; // Move is allowed
    }

    /**
     * Adds a collidable object to the manager.
     */
    public void addCollidable(Collidable obj) {
        if (!collidableObjects.contains(obj)) {
            collidableObjects.add(obj);
        }
    }

    /**
     * Removes a collidable object from the manager.
     */
    public void removeCollidable(Collidable obj) {
        collidableObjects.remove(obj);
    }

    /**
     * Checks for collisions between all collidable objects.
     */
    public void checkCollisions() {
        for (int i = 0; i < collidableObjects.size(); i++) {
            Collidable obj1 = collidableObjects.get(i);

            for (int j = i + 1; j < collidableObjects.size(); j++) {
                Collidable obj2 = collidableObjects.get(j);

                if (obj1.detectCollision(obj2)) {
                    // Resolve the collision
                    obj1.resolveCollision(obj2);
                    obj2.resolveCollision(obj1);
                }
            }
        }
    }
}