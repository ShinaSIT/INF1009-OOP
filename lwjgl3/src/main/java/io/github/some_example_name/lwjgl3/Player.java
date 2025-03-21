package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Player extends MoveableObjects {
	private CollisionManager collisionManager;
    private int health;
    private int lives;

    // Character facing
    private String facingDirection = "RIGHT";  // Default facing right
    private String facingSide = "RIGHT";       // Default using right-facing sprites

    // Animation toggle (used to simulate stepping)
    private boolean isRightStep = true;
    private float stepDistance = 0f;
    private final float STEP_TRIGGER = 20f; // toggle every 20 pixels
    private float lastX = -1;
    private float lastY = -1;



    public Player(Board board, EntityManager entityManager, int x, int y,
	            MovementManager movementManager, int initialHealth, int initialLives,
	            CollisionManager collisionManager) {
	  super(board, entityManager, x, y, movementManager);
	  this.health = initialHealth;
	  this.lives = initialLives;
	  this.collisionManager = collisionManager;
	  addTag("moveable");  
	}

    @Override
    public void move(float dx, float dy) {
        int targetGridX = gridX;
        int targetGridY = gridY;

        // Convert float movement to grid-based movement
        if (dx > 0) targetGridX++;
        else if (dx < 0) targetGridX--;
        else if (dy > 0) targetGridY++;
        else if (dy < 0) targetGridY--;

        System.out.println("🔄 Attempting Move: (" + gridX + ", " + gridY + ") → (" + targetGridX + ", " + targetGridY + ")");

        // ✅ Check if destination is valid
        if (!collisionManager.isMoveValid(targetGridX, targetGridY)) {
            System.out.println("🚧 Collision detected! Staying at (" + gridX + ", " + gridY + ")");
            return;
        }

        // ✅ Update grid position
        setGridX(targetGridX);
        setGridY(targetGridY);
        updatePixelPosition();

        // ✅ Toggle walking animation step
        isRightStep = !isRightStep;

        // ✅ Update facing direction
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

        System.out.println("✅ Move Successful!");
        System.out.println("✅ Step toggled to: " + (isRightStep ? "Right" : "Left"));
    }

    @Override
    public void render(SpriteBatch batch) {
        System.out.println("🎨 Rendering Player at (x=" + x + ", y=" + y + "), Grid (" + gridX + ", " + gridY + ")");
        System.out.println("🦶 Current Step: " + (isRightStep ? "Right Leg" : "Left Leg"));

        Texture currentSprite = Asset.psyduckRDown; // fallback

        if (facingSide.equals("LEFT")) {
            switch (facingDirection) {
                case "UP":
                    currentSprite = isRightStep ? Asset.psyduckLUp : Asset.psyduckLUp2;
                    break;
                case "DOWN":
                    currentSprite = isRightStep ? Asset.psyduckLDown : Asset.psyduckLDown2;
                    break;
                case "LEFT":
                    currentSprite = isRightStep ? Asset.psyduckLLeft : Asset.psyduckLLeft2;
                    break;
                case "RIGHT":
                    currentSprite = isRightStep ? Asset.psyduckLRight : Asset.psyduckLRight2;
                    break;
            }
        } else {
            switch (facingDirection) {
                case "UP":
                    currentSprite = isRightStep ? Asset.psyduckRUp : Asset.psyduckRUp2;
                    break;
                case "DOWN":
                    currentSprite = isRightStep ? Asset.psyduckRDown : Asset.psyduckRDown2;
                    break;
                case "LEFT":
                    currentSprite = isRightStep ? Asset.psyduckRLeft : Asset.psyduckRLeft2;
                    break;
                case "RIGHT":
                    currentSprite = isRightStep ? Asset.psyduckRRight : Asset.psyduckRRight2;
                    break;
            }
        }

        batch.draw(currentSprite, x, y, board.getTileSize(), board.getTileSize());
    }



    public void eatFood(Food food) {
        if (food.isHealthy()) {
            health += 10;
        } else {
            health -= 10;
            if (health <= 0) {
                loseLife();
            }
        }
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
}
