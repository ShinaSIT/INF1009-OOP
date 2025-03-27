package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Player extends MoveableObjects implements Collidable {
	private CollisionManager collisionManager;
    private BoardManager boardManager;
    private Speaker speaker;
    private SceneManager sceneManager;
    private int health;
    private int lives;

    // Character facing
    private String facingDirection = "RIGHT";
    private String facingSide = "RIGHT";
    private boolean isRightStep = true;

    public Player(Board board, EntityManager entityManager, int x, int y,
                MovementManager movementManager, int initialHealth, int initialLives,
                CollisionManager collisionManager, BoardManager boardManager, Speaker speaker) {
        super(board, entityManager, x, y, movementManager);
        this.health = initialHealth;
        this.lives = initialLives;
        this.collisionManager = collisionManager;
        this.boardManager = boardManager;
        this.speaker = speaker;
        this.sceneManager = collisionManager.getSceneManager();
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

        if (!collisionManager.isMoveValid(targetGridX, targetGridY, isGerm)) {
<<<<<<< HEAD
=======
        	for (Entity e : entityManager.getEntities()) {
                if (e instanceof Germ && e.getGridX() == targetGridX && e.getGridY() == targetGridY) {
                    speaker.playSound("germHit"); // 🔊 Only plays when *player* moves
                    return;
                }
            }
            System.out.println("🚧 Collision detected! Staying at (" + gridX + ", " + gridY + ")");
>>>>>>> branch 'main' of git@github.com:ShinaSIT/INF1009-OOP.git
            speaker.playSound("block");
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

        speaker.playSound(isRightStep ? "step1" : "step2");

        Food food = board.getFoodGrid()[gridY][gridX];
        if (food != null) {
            eatFood(food);
            board.getFoodGrid()[gridY][gridX] = null;
            board.getMazeLayout()[gridY][gridX] = ' ';
            System.out.println("Ate food at (" + gridX + ", " + gridY + ")");
        }
        
        boardManager.removeStaticObjectAt(gridX, gridY);
        board.getMazeLayout()[gridY][gridX] = ' ';
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
                System.out.println("+100 points for healthy food!");
            }
        } else {
            health -= 10;
            if (gameScene != null) {
                gameScene.setScore(Math.max(0, gameScene.getScore() - 100));
                System.out.println("-100 points for unhealthy food!");
            }
            
            if (health <= 0) {
                loseLife();
            }
        }
        speaker.playSound("eat");
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
            speaker.playSound("germHit"); // 🔊 Play sound only when Player touches Germ
            System.out.println("💥 Player collided with Germ!");
        }

        return collided;
    }

}
