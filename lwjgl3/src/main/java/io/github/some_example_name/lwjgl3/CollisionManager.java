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
//        String key = newCol + "," + newRow;
//        if (collisionCache.contains(key)) {
//            return false; // Instantly return cached result
//        }

        // ✅ Updated: Check wall collision using tile characters
        char tile = board.getMazeLayout()[newRow][newCol];
        if (tile != ' ' && tile != '.' && tile != 'p') {
            System.out.println("🚧 Blocked by wall");
            collisionCount++;
            System.out.println("🔢 Collision count: " + collisionCount);
//            collisionCache.add(key); // Cache invalid position
            return false;
        }

        // ✅ Check against collidable objects
        for (Collidable collidable : collidableObjects) {
            if (collidable.getGridX() == newCol && collidable.getGridY() == newRow && collidable.isSolid()) {
                System.out.println("❌ Blocked by Collidable Object");
                collisionCount++;
                System.out.println("🔢 Collision count: " + collisionCount);
//                collisionCache.add(key); // Cache invalid position
                return false;
            }
        }

        System.out.println("✅ Free to move");
        return true;
    }

    public void checkCollisions() {
    	collisionCache.clear();
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
            int dx = a.getGridX() - b.getGridX();
            int dy = a.getGridY() - b.getGridY();

            if (dx != 0) dx = dx > 0 ? 1 : -1;
            if (dy != 0) dy = dy > 0 ? 1 : -1;

            int newGridX = a.getGridX() + dx;
            int newGridY = a.getGridY() + dy;

            // Check if the new position is valid
            if (board.getMazeLayout()[newGridY][newGridX] == ' ' && isMoveValid(newGridX, newGridY)) {
                a.setGridX(newGridX);
                a.setGridY(newGridY);
            }
        } else if (a instanceof Player && b instanceof Germ) {
            resolveCollision(b,a);
        }
    }
}
