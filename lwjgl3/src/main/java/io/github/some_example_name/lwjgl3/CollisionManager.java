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
        char tile = board.getMazeLayout()[newRow][newCol];
        if (tile != ' ' && tile != '.' && tile != 'p') {
            System.out.println("🚧 Blocked by wall");
            collisionCount++;
            System.out.println("🔢 Collision count: " + collisionCount);
            return false;
        }

        for (Collidable collidable : collidableObjects) {
            if (collidable.getGridX() == newCol && collidable.getGridY() == newRow && collidable.isSolid()) {
                if (collidable instanceof Germ) {
                    // Collision with a Germ
                    System.out.println("💥 Player hit by Germ! Resetting player.");

                    // Find the Player object in collidableObjects and reset it.
                    for (Collidable playerCheck : collidableObjects) {
                        if (playerCheck instanceof Player) {
                            Player player = (Player) playerCheck;
                            player.setGridX(1);
                            player.setGridY(1);
                            player.updatePixelPosition();
                            System.out.println("💥 Player position after reset: (" + player.getGridX() + ", " + player.getGridY() + ")");
                            return false; // Prevent further movement after reset
                        }
                    }
                    // If no player is found, return false.
                    return false;
                } else {
                    // Collision with a non-Germ Collidable
                    System.out.println("❌ Blocked by Collidable Object");
                    collisionCount++;
                    System.out.println("🔢 Collision count: " + collisionCount);
                    return false;
                }
            }
        }

        System.out.println("✅ Free to move");
        return true;
    }

    public void checkCollisions() {
//    	System.out.println("Collidable Objects: " + collidableObjects);
        for (int i = 0; i < collidableObjects.size(); i++) {
            for (int j = i + 1; j < collidableObjects.size(); j++) {
                Collidable a = collidableObjects.get(i);
                Collidable b = collidableObjects.get(j);

                if (a.detectCollision(b)) {
                    System.out.println("⚠️ Collision Detected between: " +
                            a.getClass().getSimpleName() + " and " +
                            b.getClass().getSimpleName());
                    resolveCollision(a, b);
                }
            }
        }
    }

    private void resolveCollision(Collidable a, Collidable b) {
        if (a instanceof NonCollidable || b instanceof NonCollidable) {
            return;
        }

        if (a instanceof Germ && b instanceof Player) {
            System.out.println("💥 Germ hit Player! Resetting player.");
            Player player = (Player) b;
            player.setGridX(1);
            player.setGridY(1);
            player.updatePixelPosition();
            System.out.println("💥 Germ hit Player! Resetting player. Player position after reset: (" + player.getGridX() + ", " + player.getGridY() + ")");
        } else if (a instanceof Player && b instanceof Germ) {
            resolveCollision(b,a);
        }
    }
}
