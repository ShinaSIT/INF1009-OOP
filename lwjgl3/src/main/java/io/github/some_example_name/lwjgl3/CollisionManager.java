package io.github.some_example_name.lwjgl3;

import java.util.*;

public class CollisionManager {
	private static CollisionManager instance; // Singleton instance
	
    private List<Collidable> collidableObjects;
    private Board board;
    private SceneManager sceneManager;
    private EntityManager entityManager;
    private int collisionCount = 0;
    private Set<String> collisionCache = new HashSet<>();

    private CollisionManager(Board board, EntityManager entityManager, SceneManager sceneManager) {
        this.collidableObjects = new ArrayList<>();
        this.board = board;
        this.entityManager = entityManager;
        this.sceneManager = sceneManager;
    }
    
    // Public method to get the Singleton instance
    public static CollisionManager getInstance(Board board, EntityManager entityManager, SceneManager sceneManager) {
        if (instance == null) {
            instance = new CollisionManager(board, entityManager, sceneManager);
        }
        return instance;
    }

    public void addCollidable(Collidable collidable) {
        collidableObjects.add(collidable);
    }

    /**
     * Checks if a move to the specified grid position is valid.
     */
    public boolean isMoveValid(int newCol, int newRow, boolean isGerm) {
        char tile = board.getMazeLayout()[newRow][newCol];
        if (tile != ' ' && tile != '.' && tile != 'p' && tile != 'f') {
            System.out.println("🚧 Blocked by wall");
            if (isGerm) {
                return false;
            }
            collisionCount++;
            System.out.println("🔢 Collision count: " + collisionCount);
            return false;
        }

        for (Collidable collidable : collidableObjects) {
            if (collidable.getGridX() == newCol && collidable.getGridY() == newRow && collidable.isSolid()) {
                if (collidable instanceof Germ) {
                    // Collision with a Germ
                    if (!isGerm) { // Only reset player if the player is moving into the germ
                        System.out.println("💥 Player hit by Germ! Resetting player.");
                        
                        // Reduce player health
                        GameScene gameScene = (GameScene) sceneManager.getCurrentScene();
                        gameScene.setHealth(gameScene.getHealth() - 1);

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
                        return true; // if the germ is moving into the player, allow the move
                    }
                } else if (collidable instanceof Player && isGerm) {
                    return true; // allow the germ to move onto the player's space.
                } else {
                    // Collision with a non-Germ Collidable
                    System.out.println("❌ Blocked by Collidable Object");
                    collisionCount++;
                    System.out.println("🔢 Collision count: " + collisionCount);
                    return false;
                }
            }
        }

        //System.out.println("✅ Free to move");
        return true;
    }

    public void checkCollisions() {
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
            System.out.println("💥 Germ hit Player! Reducing health.");
            Player player = (Player) b;
            GameScene gameScene = (GameScene) sceneManager.getCurrentScene();
            gameScene.setHealth(gameScene.getHealth() - 1); // Reduce health by 1
            
            player.setGridX(1);
            player.setGridY(1);
            player.updatePixelPosition();
        } else if (a instanceof Player && b instanceof Germ) {
            resolveCollision(b, a); // Just call the same method with reversed parameters
        }
    }
}