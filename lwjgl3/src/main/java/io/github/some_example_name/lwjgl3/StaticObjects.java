package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class StaticObjects extends Entity {
    private ShapeRenderer shapeRenderer;
    private Board board;
    private int gridX, gridY;
    
    public StaticObjects(Board board, int gridX, int gridY, float tileSize) {
    	super(gridX * tileSize + board.getStartX(), (board.getMazeHeight() - 1 - gridY) * tileSize + board.getStartY(), tileSize);
    	this.board = board;
        this.shapeRenderer = new ShapeRenderer();
        this.gridX = gridX;
        this.gridY = gridY;
        System.out.println("✅ Static Object Created at Grid (" + gridX + ", " + gridY + ")");
    }
    
    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    @Override
    public void render(SpriteBatch batch) {
        float objectSize = board.getTileSize();
        float adjustedX = gridX * board.getTileSize() + board.getStartX();
        float adjustedY = (board.getMazeHeight() - 1 - gridY) * board.getTileSize() + board.getStartY();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(adjustedX, adjustedY, objectSize, objectSize);
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
