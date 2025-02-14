package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;

public class Board {
    private int width;
    private int height;
    private ShapeRenderer shapeRenderer;
    private Texture dotTexture;

    
    private final int tileSize = 24;
    private final int[][] mazeLayout = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
    
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.shapeRenderer = new ShapeRenderer();
        this.dotTexture = new Texture(Gdx.files.internal("dot.png")); // Ensure dot.png is inside core/assets/
    }

    public void render(SpriteBatch batch) {
        int mazeWidth = mazeLayout[0].length * tileSize;
        int mazeHeight = mazeLayout.length * tileSize;

        // 🔹 Centering offset for drawing the maze
        int startX = (width - mazeWidth) / 2;
        int startY = (height - mazeHeight) / 2;

        // Draw maze walls
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);

        for (int row = 0; row < mazeLayout.length; row++) {
            for (int col = 0; col < mazeLayout[row].length; col++) {
                if (mazeLayout[row][col] == 1) {
                    shapeRenderer.rect(startX + col * tileSize, startY + (mazeLayout.length - row - 1) * tileSize, tileSize, tileSize);                }
            }
        }
        shapeRenderer.end();

        // Draw dots
        for (int row = 0; row < mazeLayout.length; row++) {
            for (int col = 0; col < mazeLayout[row].length; col++) {
                if (mazeLayout[row][col] == 0) { // Empty spaces should contain dots
                	batch.draw(dotTexture, startX + col * tileSize + 8, startY + (mazeLayout.length - row - 1) * tileSize + 8, 8, 8);                
                    }
            }
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
        dotTexture.dispose();
    }
}
