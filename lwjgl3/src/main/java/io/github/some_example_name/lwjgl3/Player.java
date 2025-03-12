package io.github.some_example_name.lwjgl3;

public class Player extends MoveableObjects {
    private int health;
    private int lives;

    public Player(Board board, EntityManager entityManager, int x, int y, MovementManager movementManager, int initialHealth, int initialLives) {
        super(board, entityManager, x, y, movementManager);
        this.health = initialHealth;
        this.lives = initialLives;
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
