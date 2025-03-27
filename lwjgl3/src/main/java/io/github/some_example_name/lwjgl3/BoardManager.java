package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class BoardManager {
    private Board board;

    private ArrayList<StaticObject> staticObjects = new ArrayList<>(); 

    protected BoardManager() {
        this.board = new Board();
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
                if (charMaze[row][col] == '.' || charMaze[row][col] == 'f') {
                    staticObjects.add(new StaticObject(board, charMaze[row][col], col, row));
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        board.render(batch);

        for (StaticObject obj : staticObjects) { 
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

                // Only these are walkable
                if (tile == '.' || tile == 'p' || tile == 'f') {
                    intMaze[row][col] = 0;
                } else {
                    intMaze[row][col] = 1; // All other characters as wall
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
    
    public ArrayList<StaticObject> getStaticObjects() {
        return staticObjects;
    }
    
    public void removeStaticObjectAt(int col, int row) {
      staticObjects.removeIf(obj -> obj.getGridX() == col && obj.getGridY() == row);
    }
    
    public void init() {
        board.generateFoods(); 
        generateStaticObjects();
    }

    public void initGL() {
        board.initGL();
    }

    public void dispose() {
        board.dispose();
        for (StaticObject obj : staticObjects) {
            obj.dispose();
        }
    }
    
    public void setBoard(Board newBoard) {
        this.board = newBoard;
    }
}
