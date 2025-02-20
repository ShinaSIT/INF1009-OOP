package io.github.some_example_name.lwjgl3;

import java.util.*;

public class StaticObjectManager {
    private Board board;
    private Set<String> staticObjectPositions = new HashSet<>(); // ✅ Faster lookup

    public StaticObjectManager(Board board) {
        this.board = board;
    }

    public void generateStaticObjects(int count, EntityManager entityManager) {
        System.out.println("🛠 Generating " + count + " static objects **before** maze generation...");
        
        List<Entity> moveableEntities = entityManager.getEntities();
        staticObjectPositions.clear();

        int generated = 0;
        Random random = new Random();

        while (generated < count) {
            int gridX = random.nextInt(board.getMazeWidth());
            int gridY = random.nextInt(board.getMazeHeight());
            String positionKey = gridX + "," + gridY;

            // Ensure placement does not block paths, exit, walls, or player accessibility from start to exit
            if (board.getMazeLayout()[gridY][gridX] == 0 // Must be on a path, not a wall
                && !staticObjectPositions.contains(positionKey) // Avoid duplicate positions
                && !isMoveableEntityAt(gridX, gridY, moveableEntities) // Avoid moveable entities
                && isPathFromStartToExitClear(gridX, gridY)) { // Ensure solvability from start to exit
                
                staticObjectPositions.add(positionKey);
                entityManager.addEntity(new StaticObjects(board, gridX, gridY));
                generated++; // ✅ Successfully placed, increment count
            }
        }
    }

    /**
     * ✅ Checks if a moveable entity (like the player or enemies) is at the given position.
     */
    private boolean isMoveableEntityAt(int gridX, int gridY, List<Entity> moveableEntities) {
        for (Entity entity : moveableEntities) {
            if (entity.getGridX() == gridX && entity.getGridY() == gridY) {
                return true; // ✅ There is already a moveable object here
            }
        }
        return false;
    }

    /**
     * ✅ Ensures that placing a static object does not block a valid path from the start to the exit.
     */
    private boolean isPathFromStartToExitClear(int gridX, int gridY) {
        // Temporarily place the static object to test solvability
        board.getMazeLayout()[gridY][gridX] = 1; // Set as a wall
        boolean solvable = board.isMazeSolvable(); // Use existing DFS/BFS check
        board.getMazeLayout()[gridY][gridX] = 0; // Restore original state
        return solvable;
    }
}
