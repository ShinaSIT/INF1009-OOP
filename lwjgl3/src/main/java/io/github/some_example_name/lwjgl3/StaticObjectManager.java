package io.github.some_example_name.lwjgl3;

import java.util.*;

public class StaticObjectManager {
    private Board board;
    private Set<String> staticObjectPositions = new HashSet<>(); // ✅ Faster lookup

    public StaticObjectManager(Board board) {
        this.board = board;
    }

    public void generateStaticObjects(int count, EntityManager entityManager) {
        System.out.println("🛠 Generating " + count + " static objects...");
        
        // Get a list of all moveable entities (e.g., the player, enemies, etc.)
        List<Entity> moveableEntities = entityManager.getEntities();
        
        // ✅ Clear previous positions before generating new ones
        staticObjectPositions.clear();

        int generated = 0;
        Random random = new Random();
        while (generated < count) { // ✅ Loop until we successfully generate 'count' objects
            int gridX = random.nextInt(board.getMazeWidth());
            int gridY = random.nextInt(board.getMazeHeight());

            String positionKey = gridX + "," + gridY;

            // ✅ Ensure objects are placed **only on paths** and **not on moveable objects**
            if (board.getMazeLayout()[gridY][gridX] == 0 
                && !staticObjectPositions.contains(positionKey) 
                && !isMoveableEntityAt(gridX, gridY, moveableEntities)) { 
                
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
}
