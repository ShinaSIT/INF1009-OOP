package io.github.some_example_name.lwjgl3;

import java.util.*;

public class CollisionManager {
	private static CollisionManager instance; // Singleton instance
	
    private List<Collidable> collidableObjects;
    private Board board;
    private SceneManager sceneManager;
    private EntityManager entityManager;
    private int collisionCount = 0;
    private int healthyFoodCount = 0;
    private int unhealthyFoodCount = 0;
    
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

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void addCollidable(Collidable collidable) {
        if (!collidableObjects.contains(collidable)) {
            collidableObjects.add(collidable);
        }
    }

    public boolean isMoveValid(int newCol, int newRow, boolean isGerm) {
        char tile = board.getMazeLayout()[newRow][newCol];
        
        // Handle food collision first
        if (tile == 'f' && !isGerm) {
            handleFoodCollision(newCol, newRow);
            return true;
        }
        
        if (tile == '.' && !isGerm) {
            handlePelletCollision(newCol, newRow);
            return true;
        }

        // Wall collision check
        if (tile != ' ' && tile != '.' && tile != 'p') {
            if (!isGerm) {
                collisionCount++;
                System.out.println("🚧 Wall collision at (" + newCol + "," + newRow + ")");
            }
            return false;
        }

        // Entity collision check
        for (Collidable collidable : collidableObjects) {
            if (collidable.getGridX() == newCol && collidable.getGridY() == newRow && collidable.isSolid()) {
                return handleEntityCollision(collidable, isGerm);
            }
        }

        return true;
    }

    private boolean handleEntityCollision(Collidable collidable, boolean isGerm) {
        if (collidable instanceof Germ) {
            if (!isGerm) {
                handlePlayerGermCollision();
                return false;
            }
            return true; // Germs can move through each other
        } else if (collidable instanceof Player && isGerm) {
            return true; // Germs can move onto player
        } else {
            collisionCount++;
            System.out.println("❌ Collision with " + collidable.getClass().getSimpleName());
            return false;
        }
    }

    private void handlePlayerGermCollision() {
        System.out.println("💥 Player hit by Germ!");
        
        // Update game state
        GameScene gameScene = (GameScene) sceneManager.getCurrentScene();
        if (gameScene != null) {
            gameScene.setHealth(gameScene.getHealth() - 1);
        }

        // Reset player position
        for (Collidable playerCheck : collidableObjects) {
            if (playerCheck instanceof Player) {
                Player player = (Player) playerCheck;
                player.setGridX(1);
                player.setGridY(1);
                player.updatePixelPosition();
                break;
            }
        }
    }

    private void handleFoodCollision(int col, int row) {
        Food food = board.getFoodGrid()[row][col];
        if (food != null) {
            GameScene gameScene = (GameScene) sceneManager.getCurrentScene();
            if (gameScene != null) {
                int scoreChange = food.isHealthy() ? 100 : -100;
                gameScene.setScore(gameScene.getScore() + scoreChange);
                int healthyFoodCount = food.isHealthy() ? 1 : 0;
                gameScene.setHealthyFoodCount(gameScene.getHealthyFoodCount() + healthyFoodCount);
                int unhealthyFoodCount = food.isUnhealthy() ? 1 : 0;
                gameScene.setUnhealthyFoodCount(gameScene.getUnhealthyFoodCount() + unhealthyFoodCount);
                System.out.println("healthy food: " + healthyFoodCount);
                System.out.println("unhealthy food: " + unhealthyFoodCount);
//                if (food.isHealthy() == true) {
//                	int count = 1;
//                	healthyFoodCount = healthyFoodCount + count;
//                	gameScene.setHealthyFoodCount(gameScene.getHealthyFoodCount() + healthyFoodCount);
//                	System.out.println("healthy food: " + healthyFoodCount);
//                }
//                if (food.isUnhealthy() == true) {
//                	int count = 1;
//                	unhealthyFoodCount = unhealthyFoodCount + count;
//                		gameScene.setUnhealthyFoodCount(gameScene.getUnhealthyFoodCount() + unhealthyFoodCount);
//                		System.out.println("unhealthy food: " + unhealthyFoodCount);
//                }
                }
                System.out.println(food.isHealthy() ? "🍎 +100 points" : "🍔 -100 points");
            }
            
            board.getFoodGrid()[row][col] = null;
            board.getMazeLayout()[row][col] = ' ';
        }

    
    private void handlePelletCollision(int col, int row) {
        GameScene gameScene = (GameScene) sceneManager.getCurrentScene();
        if (gameScene != null) {
            gameScene.setScore(gameScene.getScore() + 10);
            System.out.println("🍪 +10 points");
        }
        board.getMazeLayout()[row][col] = ' ';
    }
    
    public void checkCollisions() {
        for (int i = 0; i < collidableObjects.size(); i++) {
            for (int j = i + 1; j < collidableObjects.size(); j++) {
                Collidable a = collidableObjects.get(i);
                Collidable b = collidableObjects.get(j);

                if (a.detectCollision(b)) {
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
            handleGermPlayerCollision((Player) b);
        } else if (a instanceof Player && b instanceof Germ) {
            handleGermPlayerCollision((Player) a);
        }
    }

    private void handleGermPlayerCollision(Player player) {
        System.out.println("💥 Germ hit Player!");
        GameScene gameScene = (GameScene) sceneManager.getCurrentScene();
        if (gameScene != null) {
            gameScene.setHealth(gameScene.getHealth() - 1);
        }
        
        player.setGridX(1);
        player.setGridY(1);
        player.updatePixelPosition();
    }

    public void removeCollidable(Collidable collidable) {
        collidableObjects.remove(collidable);
    }

    public void clearCollidables() {
        collidableObjects.clear();
    }
}