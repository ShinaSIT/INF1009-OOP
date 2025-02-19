package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameObjects extends Entity {
    protected ShapeRenderer shapeRenderer;
    protected Board board;
    protected EntityManager entityManager;

    public GameObjects(Board board, EntityManager entityManager, int gridX, int gridY) {
        super(board, gridX, gridY);
        this.board = board;
        this.entityManager = entityManager;
        this.shapeRenderer = new ShapeRenderer();
    }

    public void setGridX(int gridX) {
        this.gridX = Math.max(0, Math.min(gridX, board.getMazeWidth() - 1));
    }

    public void setGridY(int gridY) {
        this.gridY = Math.max(0, Math.min(gridY, board.getMazeHeight() - 1));
    }

    @Override
    public void render(SpriteBatch batch) {
        float tileSize = board.getTileSize();
        float adjustedX = gridX * tileSize + board.getStartX();
        float adjustedY = (board.getMazeHeight() - 1 - gridY) * tileSize + board.getStartY();

        // ✅ Ensure the player is correctly scaled inside the tile
        float playerSize = Math.max(10, tileSize * 0.7f);  

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(adjustedX + tileSize / 2, adjustedY + tileSize / 2, playerSize / 2);
        shapeRenderer.end();
    }


    public void dispose() {
        shapeRenderer.dispose();
    }
    
    public void updatePosition(Board board) {
        this.board = board;
        updatePixelPosition(board);
    }

    public void updatePixelPosition(Board board) {
        float tileSize = board.getTileSize();
        float adjustedX = gridX * tileSize + board.getStartX();
        float adjustedY = (board.getMazeHeight() - 1 - gridY) * tileSize + board.getStartY();

        System.out.println("📌 Player Updated to (" + adjustedX + ", " + adjustedY + ")");
    }

}
