package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Germ extends MoveableObjects implements Collidable { // Implement Collidable
    private Texture germTexture;
    private Random random;
    private int moveCooldown = 60;
    private int lastDirection = -1; // -1: No direction, 0: Up, 1: Right, 2: Down, 3: Left

    public Germ(Board board, EntityManager entityManager, int gridX, int gridY, MovementManager movementManager, CollisionManager collisionManager) {
        super(board, entityManager, gridX, gridY, movementManager);
        this.entityTags.add("moveable");
        this.random = new Random();

        FileHandle file = Gdx.files.internal("Germs.png");
        this.germTexture = new Texture(file);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (germTexture == null) {
            System.out.println("❌ ERROR: Germ texture is NULL!");
            return;
        }

        float size = board.getTileSize();
        
        batch.end(); // ✅ Force batch reset (this fixes "invisible germ" issue)
        batch.begin();
        
        batch.draw(germTexture, getX(), getY(), size, size);
        
        batch.end(); // ✅ Ensure batch properly closes
        batch.begin(); // ✅ Restart batch after drawing germ
    }

    public void moveSmartly() {
        if (moveCooldown > 0) {
            moveCooldown--; // ✅ Decrease cooldown every frame
            return;
        }

        List<Integer> validDirections = getValidDirections();
        
        if (validDirections.isEmpty()) {
            return; // ✅ If no valid move, do nothing
        }

        // ✅ Choose a new direction, avoiding reversing the last movement
        int newDirection = chooseBestDirection(validDirections);

        // ✅ Move in the chosen direction
        moveInDirection(newDirection);
        lastDirection = newDirection; // ✅ Store last direction to prevent immediate backtracking

        moveCooldown = 60; // ✅ Set cooldown to delay next move
    }

    private List<Integer> getValidDirections() {
        List<Integer> directions = new ArrayList<>();
        char[][] maze = board.getMazeLayout();  // OR use board.getMazeLayoutCopy() for more flexibility
        int currentX = getGridX();
        int currentY = getGridY();

        // Check bounds and allow walking on ' ', 'f' (food), and optionally 'p' (player)
        if (currentY > 0 && isWalkable(maze[currentY - 1][currentX])) directions.add(0); // Up
        if (currentX < maze[0].length - 1 && isWalkable(maze[currentY][currentX + 1])) directions.add(1); // Right
        if (currentY < maze.length - 1 && isWalkable(maze[currentY + 1][currentX])) directions.add(2); // Down
        if (currentX > 0 && isWalkable(maze[currentY][currentX - 1])) directions.add(3); // Left

        Collections.shuffle(directions);
        return directions;
    }

    private int chooseBestDirection(List<Integer> validDirections) {
        return validDirections.get(random.nextInt(validDirections.size())); // ✅ Pick a random valid move
    }

    private void moveInDirection(int direction) {
        float step = board.getTileSize();

        switch (direction) {
            case 0: movementManager.applyMovement(this, 0, -1); break; // Up
            case 1: movementManager.applyMovement(this, 1, 0); break;  // Right
            case 2: movementManager.applyMovement(this, 0, 1); break;  // Down
            case 3: movementManager.applyMovement(this, -1, 0); break; // Left
        }
    }
    
    private boolean isWalkable(char tile) {
        return tile == ' ' || tile == 'f'; // Add other walkable characters if needed
    }

    public void dispose() {
        germTexture.dispose();
    }

    // Implement Collidable interface methods
    @Override
    public boolean isSolid() {
        return true; // Germ is solid
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
    }

    @Override
    public void setGridY(int gridY) {
        super.setGridY(gridY);
    }

    @Override
    public boolean detectCollision(Collidable other) {
        System.out.println("Germ.detectCollision: Germ(" + this.getGridX() + ", " + this.getGridY() + ") vs. " + other.getClass().getSimpleName() + "(" + other.getGridX() + ", " + other.getGridY() + ")");
        // ... your collision detection logic ...
        // Example simple collision check
        if (this.getGridX() == other.getGridX() && this.getGridY() == other.getGridY()) {
            return true;
        } else {
            return false;
        }
    }

}