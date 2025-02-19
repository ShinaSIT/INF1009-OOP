package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class StaticObjects extends Entity {
    private ShapeRenderer shapeRenderer;

    public StaticObjects(Board board, int gridX, int gridY) {
        super(board, gridX, gridY);
        this.shapeRenderer = new ShapeRenderer();
        updatePixelPosition(); 

        System.out.println("✅ Static Object Created at Grid (" + gridX + ", " + gridY + ")");
    }

    @Override
    public void render(SpriteBatch batch) {
        float objectSize = tileSize * 0.8f; // ✅ Make sure it's slightly smaller than the tile
        float centerX = x + (tileSize - objectSize) / 2; // ✅ Center horizontally
        float centerY = y + (tileSize - objectSize) / 2; // ✅ Center vertically

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(centerX, centerY, objectSize, objectSize); // ✅ Properly centered box
        shapeRenderer.end();
    }

    @Override
    public void updatePixelPosition() {
        super.updatePixelPosition(); 
        System.out.println("📌 Static Object Updated to Grid (" + gridX + ", " + gridY + ")");
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}