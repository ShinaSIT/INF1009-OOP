package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.*;

public class StaticObjects extends Entity {
    private ShapeRenderer shapeRenderer;
    private static Set<String> staticObjectPositions = new HashSet<>(); // ✅ Track placed objects

    public StaticObjects(Board board, int gridX, int gridY) {
        super(board, gridX, gridY, EntityType.STATIC);
        this.shapeRenderer = new ShapeRenderer();
        staticObjectPositions.add(gridX + "," + gridY); // Track position globally
        System.out.println("✅ Static Object Created at Grid (" + gridX + ", " + gridY + ")");
    }

    public static void generateStaticObjects(Board board, int count, EntityManager entityManager) {
        System.out.println("🛠 Generating " + count + " static objects **before** maze generation...");

        List<Entity> moveableEntities = entityManager.getEntities();
        staticObjectPositions.clear();
        Random random = new Random();
        int generated = 0;

        while (generated < count) {
            int gridX = random.nextInt(board.getMazeWidth());
            int gridY = random.nextInt(board.getMazeHeight());
            String positionKey = gridX + "," + gridY;

            // ✅ Ensure placement doesn't block paths or key areas
            if (board.getMazeLayout()[gridY][gridX] == 0 // Must be on a path
                && !staticObjectPositions.contains(positionKey) // Avoid duplicate positions
                && !isMoveableEntityAt(gridX, gridY, moveableEntities) // Avoid overlap
                && isPathFromStartToExitClear(board, gridX, gridY)) { // Ensure solvability
                
                entityManager.addEntity(new StaticObjects(board, gridX, gridY));
                generated++; // ✅ Successfully placed
            }
        }
    }

    private static boolean isMoveableEntityAt(int gridX, int gridY, List<Entity> moveableEntities) {
        for (Entity entity : moveableEntities) {
            if (entity.getGridX() == gridX && entity.getGridY() == gridY) {
                return true; // ✅ A moveable object is already here
            }
        }
        return false;
    }

    private static boolean isPathFromStartToExitClear(Board board, int gridX, int gridY) {
        board.getMazeLayout()[gridY][gridX] = 1; // Temporarily block
        boolean solvable = board.isMazeSolvable(); // ✅ Check solvability using BFS/DFS
        board.getMazeLayout()[gridY][gridX] = 0; // Restore state
        return solvable;
    }

    @Override
    public void render(SpriteBatch batch) {
        float tileSize = board.getTileSize();
        float objectSize = tileSize * 0.8f;
        float centerX = x + (tileSize - objectSize) / 2;
        float centerY = y + (tileSize - objectSize) / 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(centerX, centerY, objectSize, objectSize);
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
