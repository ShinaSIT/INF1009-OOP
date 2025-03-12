package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BoardManager {
    private Board board;

    protected BoardManager() {
        this.board = new Board();
    }

    protected void generateBoard() {
        board.generateRandomMaze();
        board.updateDimensions();
    }

    protected void updateBoard() {
        board.updateDimensions();
    }

    public void render(SpriteBatch batch) {
        board.render(batch);
    }

    protected Board getBoard() {  // ✅ Ensure GameMaster can access Board instance
        return board;
    }

    protected int[][] getMazeLayout() {
        return board.getMazeLayout();
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

    public void dispose() {
        board.dispose();
    }
}