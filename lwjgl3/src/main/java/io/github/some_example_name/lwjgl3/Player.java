package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Player extends MoveableObjects implements Collidable {
	private CollisionChecker collisionChecker;
    private BoardManager boardManager;
    private SoundPlayer soundPlayer;
    private SceneManager sceneManager;
    private SceneController sceneController;
    private int health;
    private int lives;

    // Character facing
    private String facingDirection = "RIGHT";
    private String facingSide = "RIGHT";
    private boolean isRightStep = true;

    public Player(Board board, EntityManager entityManager, int x, int y,
                MovementManager movementManager, int initialHealth, int initialLives,
                CollisionChecker collisionChecker, BoardManager boardManager, SoundPlayer soundPlayer, SceneController sceneController) {
        super(board, entityManager, x, y, movementManager);
        this.health = initialHealth;
        this.lives = initialLives;
        this.collisionChecker  = collisionChecker ;
        this.boardManager = boardManager;
        this.soundPlayer = soundPlayer;
        this.sceneController = sceneController;
        addTag("moveable"); 
	}

    @Override
    public void move(float dx, float dy, boolean isGerm) {
        int targetGridX = gridX;
        int targetGridY = gridY;

        if (dx > 0) targetGridX++;
        else if (dx < 0) targetGridX--;
        else if (dy > 0) targetGridY++;
        else if (dy < 0) targetGridY--;

        if (!collisionChecker.isMoveValid(targetGridX, targetGridY, isGerm)) {
        	for (Entity e : entityManager.getEntities()) {
                if (e instanceof Germ && e.getGridX() == targetGridX && e.getGridY() == targetGridY) {
                    soundPlayer.playSound("germHit"); // 🔊 Only plays when *player* moves
                    return;
                }
            }
            System.out.println("🚧 Collision detected! Staying at (" + gridX + ", " + gridY + ")");
            soundPlayer.playSound("block");
            return;
        }

        setGridX(targetGridX);
        setGridY(targetGridY);
        updatePixelPosition();
        isRightStep = !isRightStep;

        if (dx > 0) {
            facingDirection = "RIGHT";
            facingSide = "RIGHT";
        } else if (dx < 0) {
            facingDirection = "LEFT";
            facingSide = "LEFT";
        } else if (dy > 0) {
            facingDirection = "UP";
        } else if (dy < 0) {
            facingDirection = "DOWN";
        }

        soundPlayer.playSound(isRightStep ? "step1" : "step2");

        Food food = board.getFoodGrid()[gridY][gridX];
        if (food != null) {
            eatFood(food);
            board.getFoodGrid()[gridY][gridX] = null;
            board.getMazeLayout()[gridY][gridX] = ' ';
            System.out.println("Ate food at (" + gridX + ", " + gridY + ")");
        }
        board.getMazeLayout()[gridY][gridX] = ' ';
        board.checkAndRemoveBirdGate();
        boardManager.removeStaticObjectAt(gridX, gridY);
    }

    @Override
    public void render(SpriteBatch batch) {
        Texture currentSprite = CharacterAssets.playerRDown;

        if (facingSide.equals("LEFT")) {
            switch (facingDirection) {
                case "UP": currentSprite = isRightStep ? CharacterAssets.playerLUp : CharacterAssets.playerLUp2; break;
                case "DOWN": currentSprite = isRightStep ? CharacterAssets.playerLDown : CharacterAssets.playerLDown2; break;
                case "LEFT": currentSprite = isRightStep ? CharacterAssets.playerLLeft : CharacterAssets.playerLLeft2; break;
                case "RIGHT": currentSprite = isRightStep ? CharacterAssets.playerLRight : CharacterAssets.playerLRight2; break;
            }
        } else {
            switch (facingDirection) {
                case "UP": currentSprite = isRightStep ? CharacterAssets.playerRUp : CharacterAssets.playerRUp2; break;
                case "DOWN": currentSprite = isRightStep ? CharacterAssets.playerRDown : CharacterAssets.playerRDown2; break;
                case "LEFT": currentSprite = isRightStep ? CharacterAssets.playerRLeft : CharacterAssets.playerRLeft2; break;
                case "RIGHT": currentSprite = isRightStep ? CharacterAssets.playerRRight : CharacterAssets.playerRRight2; break;
            }
        }

        batch.draw(currentSprite, x, y, board.getTileSize(), board.getTileSize());
    }

    public void eatFood(Food food) {
        GameScene gameScene = (GameScene) sceneManager.getCurrentScene();
        
        if (food.isHealthy()) {
            health += 10;
            if (gameScene != null) {
                gameScene.setScore(gameScene.getScore() + 100);
                gameScene.setHealthyFoodCount(gameScene.getHealthyFoodCount() + 1);
                System.out.println("+100 points for healthy food!");
            }
        } else {
            health -= 10;
            if (gameScene != null) {
                gameScene.setScore(Math.max(0, gameScene.getScore() - 100));
                gameScene.setUnhealthyFoodCount(gameScene.getUnhealthyFoodCount() + 1);
                System.out.println("-100 points for unhealthy food!");
            }
            
            if (health <= 0) {
                loseLife();
            }
        }
        soundPlayer.playSound("eat");
    }


    private void loseLife() {
        lives--;
        health = 100; // Reset health on losing a life
        if (lives <= 0) {
            System.out.println("Game Over!");
        }
    }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
    }
    
    @Override
    public boolean isSolid() {
        return true; 
    }

    @Override
    public int getGridX() {
        return super.getGridX();
    }

    @Override
    public int getGridY() {
        return super.getGridY();
    }

    @Override
    public void setGridX(int gridX) {
        super.setGridX(gridX);
        updatePixelPosition();
    }

    @Override
    public void setGridY(int gridY) {
        super.setGridY(gridY);
        updatePixelPosition();
    }

    @Override
    public boolean detectCollision(Collidable other) {
        if (other == this) return false; // No self-collision
        boolean collided = getGridX() == other.getGridX() && getGridY() == other.getGridY();

        if (collided && other instanceof Germ) {
        	soundPlayer.playSound("germHit"); // 🔊 Play sound only when Player touches Germ
            System.out.println("💥 Player collided with Germ!");
        }

        return collided;
    }

}
