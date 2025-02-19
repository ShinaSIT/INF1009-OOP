package io.github.some_example_name.lwjgl3;

import java.util.List;
import java.util.ArrayList;

public class StaticObjectManager {
    private Board board;
    private List<int[]> staticObjectPositions = new ArrayList<>(); // ✅ Store positions

    public StaticObjectManager(Board board) {
        this.board = board;
    }

    public void generateStaticObjects(int count, EntityManager entityManager) {
        // ✅ If positions exist, use them instead of generating new ones
        if (!staticObjectPositions.isEmpty()) {
            for (int[] pos : staticObjectPositions) {
                entityManager.addEntity(new StaticObjects(board, pos[0], pos[1]));
            }
            return;
        }

        // ✅ Clear positions before generating new ones
        staticObjectPositions.clear();

        int generated = 0;
        while (generated < count) { // ✅ Loop until we successfully generate 'count' objects
            int gridX = (int) (Math.random() * board.getMazeWidth());
            int gridY = (int) (Math.random() * board.getMazeHeight());

            // ✅ Ensure objects are placed **only on paths** and not on existing static objects
            if (board.getMazeLayout()[gridY][gridX] == 0 && !isPositionTaken(gridX, gridY)) { 
                staticObjectPositions.add(new int[]{gridX, gridY});
                entityManager.addEntity(new StaticObjects(board, gridX, gridY));
                generated++; // ✅ Successfully placed, increment count
            }
        }
    }

    // ✅ Ensure we don't place two objects in the same spot
    private boolean isPositionTaken(int x, int y) {
        for (int[] pos : staticObjectPositions) {
            if (pos[0] == x && pos[1] == y) return true;
        }
        return false;
    }

}
