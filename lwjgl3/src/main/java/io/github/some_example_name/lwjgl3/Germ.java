package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Germ extends MoveableObjects {
    private Texture germTexture;
    private Random random;
    private int moveCooldown = 60;
    private int lastDirection = -1; // -1: No direction, 0: Up, 1: Right, 2: Down, 3: Left

    public Germ(Board board, EntityManager entityManager, int gridX, int gridY, MovementManager movementManager) {
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

        int currentX = getGridX();
        int currentY = getGridY();

        // Check Up (0)
		/*
		 * if (board.isPath(currentX, currentY - 1) && lastDirection != 2) {
		 * directions.add(0); } // Check Right (1) if (board.isPath(currentX + 1,
		 * currentY) && lastDirection != 3) { directions.add(1); } // Check Down (2) if
		 * (board.isPath(currentX, currentY + 1) && lastDirection != 0) {
		 * directions.add(2); } // Check Left (3) if (board.isPath(currentX - 1,
		 * currentY) && lastDirection != 1) { directions.add(3); }
		 */
        Collections.shuffle(directions); // ✅ Shuffle to add randomness while keeping valid movement
        return directions;
    }

    private int chooseBestDirection(List<Integer> validDirections) {
        return validDirections.get(random.nextInt(validDirections.size())); // ✅ Pick a random valid move
    }

    private void moveInDirection(int direction) {
        float step = board.getTileSize();

        switch (direction) {
            case 0: movementManager.applyMovement(this, 0, -step); break; // Move Up
            case 1: movementManager.applyMovement(this, step, 0); break;  // Move Right
            case 2: movementManager.applyMovement(this, 0, step); break;  // Move Down
            case 3: movementManager.applyMovement(this, -step, 0); break; // Move Left
        }
    }

    public void dispose() {
        germTexture.dispose();
    }
}
