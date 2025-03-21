package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class Player extends MoveableObjects {
    private int health;
    private int lives;

    // Character facing
    private String facingDirection = "RIGHT";  // Default facing right
    private String facingSide = "RIGHT";      // Default using right-facing sprites

    public Player(Board board, EntityManager entityManager, int x, int y, MovementManager movementManager, int initialHealth, int initialLives) {
        super(board, entityManager, x, y, movementManager);
        this.health = initialHealth;
        this.lives = initialLives;
    }

    @Override
    public void move(float dx, float dy) {
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

        movementManager.applyMovement(this, dx, dy);
    }

    @Override
    public void render(SpriteBatch batch) {
        Texture currentSprite = Asset.psyduckRRight; // Default sprite

        if (facingSide.equals("LEFT")) {
            if (facingDirection.equals("UP")) {
                currentSprite = Asset.psyduckLUp;
            } else if (facingDirection.equals("DOWN")) {
                currentSprite = Asset.psyduckLDown;
            } else if (facingDirection.equals("LEFT")) {
                currentSprite = Asset.psyduckLLeft;
            } else if (facingDirection.equals("RIGHT")) {
                currentSprite = Asset.psyduckLRight;
            }
        } else { // facingSide == "RIGHT"
            if (facingDirection.equals("UP")) {
                currentSprite = Asset.psyduckRUp;
            } else if (facingDirection.equals("DOWN")) {
                currentSprite = Asset.psyduckRDown;
            } else if (facingDirection.equals("LEFT")) {
                currentSprite = Asset.psyduckRLeft;
            } else if (facingDirection.equals("RIGHT")) {
                currentSprite = Asset.psyduckRRight;
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
