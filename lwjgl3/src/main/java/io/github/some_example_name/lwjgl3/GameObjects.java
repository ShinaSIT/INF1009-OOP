package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameObjects extends Entity {
    protected ShapeRenderer shapeRenderer;
    protected Board board;
    protected EntityManager entityManager;  // To check static objects

    public GameObjects(Board board, EntityManager entityManager, int gridX, int gridY, float tileSize) {
        super(gridX * tileSize + board.getStartX(), gridY * tileSize + board.getStartY(), tileSize);
        this.board = board;
        this.entityManager = entityManager;
        this.shapeRenderer = new ShapeRenderer();
    }

    public boolean canMove(int newCol, int newRow) {
        int mazeRow = newRow;
        System.out.println("Checking move to tile: (" + newCol + ", " + newRow + ")");

        for (Entity entity : entityManager.getEntities()) {
            if (entity instanceof StaticObjects) {
                StaticObjects obj = (StaticObjects) entity;
                System.out.println("Checking static object at (" + obj.getGridX() + ", " + obj.getGridY() + ")");
                if (obj.getGridX() == newCol && obj.getGridY() == newRow) {
                    System.out.println("❌ Blocked: Static object at (" + newCol + ", " + newRow + ")");
                    return false;
                }
            }
        }

        System.out.println("Checking wall at [" + mazeRow + "][" + newCol + "]");
        if (board.getMazeLayout()[mazeRow][newCol] == 1) {
            System.out.println("❌ Blocked: Hit a wall at (" + newCol + ", " + newRow + ")");
            return false;
        }

        return true;
    }

    public int getGridX() {
        return (int) ((x - board.getStartX()) / board.getTileSize());
    }

    public int getGridY() {
        return (int) ((board.getMazeHeight() - 1) - (y - board.getStartY()) / board.getTileSize());
    }
    
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
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
}
