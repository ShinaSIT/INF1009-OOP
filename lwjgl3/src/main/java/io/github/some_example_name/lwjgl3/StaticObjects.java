package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class StaticObjects extends Entity {
    private ShapeRenderer shapeRenderer;
    private Board board;

    public StaticObjects(Board board, int gridX, int gridY) {
        super(board, gridX, gridY);
        this.board = board;
        this.shapeRenderer = new ShapeRenderer();
        updatePixelPosition(board); 

        System.out.println("✅ Static Object Created at Grid (" + gridX + ", " + gridY + ")");
    }

    @Override
    public void render(SpriteBatch batch) {
        float tileSize = board.getTileSize();
        float adjustedX = board.getStartX() + gridX * tileSize;
        float adjustedY = board.getStartY() + (board.getMazeHeight() - 1 - gridY) * tileSize;

        float objectSize = tileSize * 0.8f; // ✅ Make sure it's slightly smaller than the tile
        float centerX = adjustedX + (tileSize - objectSize) / 2; // ✅ Center horizontally
        float centerY = adjustedY + (tileSize - objectSize) / 2; // ✅ Center vertically

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(centerX, centerY, objectSize, objectSize); // ✅ Properly centered box
        shapeRenderer.end();
    }

    @Override
    public void updatePixelPosition(Board board) {
        super.updatePixelPosition(board); 

        System.out.println("📌 Static Object Updated to Grid (" + gridX + ", " + gridY + ")");
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
