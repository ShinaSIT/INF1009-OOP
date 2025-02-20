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
        if (!staticObjectPositions.isEmpty()) {
            for (String pos : staticObjectPositions) {
                String[] coords = pos.split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);

                System.out.println("📌 Re-adding STATIC object at (" + x + ", " + y + ")");
                entityManager.addEntity(new StaticObjects(board, x, y));
            }
            return;
        }

        // ✅ Clear positions before generating new ones
        staticObjectPositions.clear();

        int generated = 0;
        while (generated < count) { // ✅ Loop until we successfully generate 'count' objects
            int gridX = (int) (Math.random() * board.getMazeWidth());
            int gridY = (int) (Math.random() * board.getMazeHeight());

            String positionKey = gridX + "," + gridY;

            // ✅ Ensure objects are placed **only on paths** and not on existing static objects
            if (board.getMazeLayout()[gridY][gridX] == 0 && !staticObjectPositions.contains(positionKey)) { 
                staticObjectPositions.add(positionKey);
                entityManager.addEntity(new StaticObjects(board, gridX, gridY));
                generated++; // ✅ Successfully placed, increment count
            }
        }
    }
}
