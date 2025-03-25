package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class BoardManager {
    private Board board;
    private ArrayList<StaticObjects> staticObjects = new ArrayList<>(); // ✅ Store pellets & power-ups

    protected BoardManager() {
        this.board = new Board();
        board.generateFoods();
        generateStaticObjects(); // ✅ Generate pellets & power-ups
    }

    protected void generateBoard() {
        if (board == null) {
            System.err.println("❌ Board is NULL in BoardManager! Creating a new instance...");
            this.board = new Board();
        }
        board.updateDimensions();
    }

    protected void generateStaticObjects() {
        char[][] charMaze = board.getMazeLayout();

        for (int row = 0; row < charMaze.length; row++) {
            for (int col = 0; col < charMaze[row].length; col++) {
                float tileX = board.getStartX() + col * board.getTileSize();
                float tileY = board.getStartY() + (charMaze.length - row - 1) * board.getTileSize();

                if (charMaze[row][col] == '.') {
                    staticObjects.add(new StaticObjects(board, charMaze[row][col], col, row));
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        board.render(batch); // ✅ Render the maze first

        for (StaticObjects obj : staticObjects) { // ✅ Render all pellets & power-ups
            obj.render(batch);
        }
    }

    protected Board getBoard() {
        return board;
    }

    protected int[][] getMazeLayout() {
        char[][] charMaze = board.getMazeLayout();
        int[][] intMaze = new int[charMaze.length][charMaze[0].length];

        for (int row = 0; row < charMaze.length; row++) {
            for (int col = 0; col < charMaze[row].length; col++) {
                char tile = charMaze[row][col];

                // ✅ Only these are walkable
                if (tile == '.' || tile == 'p' || tile == 'f') {
                    intMaze[row][col] = 0;
                } else {
                    intMaze[row][col] = 1; // ✅ Treat all other characters as wall/obstacle
                }
            }
        }

        return intMaze;
    }

    protected float getTileSize() {
        return board.getTileSize();
    }

    protected float getStartX() {
        return board.getStartX();
    }

    protected float getStartY() {
        return board.getStartY();
    }

    protected int getMazeHeight() {
        return board.getMazeHeight();
    }

    protected int getMazeWidth() {
        return board.getMazeWidth();
    }
    
    public ArrayList<StaticObjects> getStaticObjects() {
        return staticObjects;
    }
    
    public void removeStaticObjectAt(int col, int row) {
        staticObjects.removeIf(obj -> obj.getGridX() == col && obj.getGridY() == row);
    }

    public void dispose() {
        board.dispose();
        for (StaticObjects obj : staticObjects) {
            obj.dispose();
        }
    }
}
