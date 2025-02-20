package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class StaticObjects extends Entity {
    private ShapeRenderer shapeRenderer;

    public StaticObjects(Board board, int gridX, int gridY) {
        super(board, gridX, gridY, EntityType.STATIC);
        this.shapeRenderer = new ShapeRenderer();
        System.out.println("✅ Static Object Created at Grid (" + gridX + ", " + gridY + ")");
    }

    @Override
    public void render(SpriteBatch batch) {
        float tileSize = board.getTileSize();
        float objectSize = tileSize * 0.8f;
        float centerX = x + (tileSize - objectSize) / 2;
        float centerY = y + (tileSize - objectSize) / 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(centerX, centerY, objectSize, objectSize);
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
