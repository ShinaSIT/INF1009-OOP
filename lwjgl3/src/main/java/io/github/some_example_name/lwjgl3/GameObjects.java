package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameObjects extends Entity {
    protected ShapeRenderer shapeRenderer;
    protected Board board;
    protected EntityManager entityManager;

    public GameObjects(Board board, EntityManager entityManager, int gridX, int gridY, String... tags) {
        super(board, gridX, gridY, tags); 
        this.board = board;
        this.entityManager = entityManager;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(SpriteBatch batch) {
        float tileSize = board.getTileSize();
        float playerSize = tileSize * 0.7f;  // Base player size
        float adjustedX = x + tileSize / 2.0f;
        float adjustedY = y + tileSize / 2.0f;

        if (board.getScreenWidth() > 640) { 
            float scaleFactor = Math.min(board.getScreenWidth() / 640.0f, board.getScreenHeight() / 480.0f);
            float aspectRatio = board.getScreenWidth() / (float) board.getScreenHeight();
            
            // Apply additional width correction
            float widthCorrection = Math.max(1.0f, aspectRatio * 1.2f); // reduces width
            playerSize *= scaleFactor / widthCorrection;  // Scale player width slightly down

            // Reduce player size to fit better in 1 tile
            playerSize /= 2.0f;  

            // Adjust position proportionally
            adjustedX -= tileSize * 1.0f;  // Slight left shift
            adjustedY -= tileSize * 3.1f;   // Keep vertical positioning
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(adjustedX, adjustedY, playerSize / 2.0f);
        shapeRenderer.end();
    }
    
    public void dispose() {
        shapeRenderer.dispose();
    }
}