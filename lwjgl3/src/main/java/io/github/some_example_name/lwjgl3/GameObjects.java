package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameObjects extends Entity {
    protected ShapeRenderer shapeRenderer;
    protected Board board;
    protected EntityManager entityManager;
    protected int gridX, gridY; // Grid coordinates

    public GameObjects(Board board, EntityManager entityManager, int gridX, int gridY, float tileSize) {
        super(gridX * tileSize + board.getStartX(), gridY * tileSize + board.getStartY(), tileSize);
        this.board = board;
        this.entityManager = entityManager;
        this.shapeRenderer = new ShapeRenderer();
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    @Override
    public void render(SpriteBatch batch) {
        float centerX = x + board.getTileSize() / 2; 
        float centerY = y + board.getTileSize() / 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(centerX, centerY, board.getTileSize() / 3);
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }

	public void setX(float f) {
		this.x = f;
	    System.out.println("Player X updated: " + this.x);
	}
		

	public void setY(float f) {
		this.y = f;
	    System.out.println("Player Y updated: " + this.y);
		
	}
}