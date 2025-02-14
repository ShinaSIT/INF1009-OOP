package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private ShapeRenderer shapeRenderer;
    private float tileSize;
    private float startX;
    private float startY;
    private int screenWidth;
    private int screenHeight;
    private OrthographicCamera camera;
    private List<StaticObjects> staticObjects;

    private final int[][] mazeLayout = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
    


    public Board() {
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        this.staticObjects = new ArrayList<>();
        updateDimensions();
        System.out.println("Maze Layout:");
        for (int r = 0; r < mazeLayout.length; r++) {
            System.out.print("Row " + r + ": ");
            System.out.println(Arrays.toString(mazeLayout[r]));
//            System.out.println("Row 18: " + Arrays.toString(mazeLayout[18]));
        }
    }

    public void updateDimensions() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        int mazeColumns = mazeLayout[0].length;
        int mazeRows = mazeLayout.length;

        tileSize = Math.min((float) screenWidth / mazeColumns, (float) screenHeight / mazeRows);

        float mazeWidth = mazeColumns * tileSize;
        float mazeHeight = mazeRows * tileSize;

        startX = (screenWidth - mazeWidth) / 2.0f;
        startY = (screenHeight - mazeHeight) / 2.0f;

        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();
    }


    public void render(SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int row = 0; row < mazeLayout.length; row++) {
            for (int col = 0; col < mazeLayout[row].length; col++) {
                if (mazeLayout[row][col] == 1) {
                    drawNeonWall(startX + col * tileSize, startY + (mazeLayout.length - row - 1) * tileSize);
                }
            }
        }

        shapeRenderer.end();
    }

    private void drawNeonWall(float x, float y) {
        float glowSize = tileSize * 0.1f; // Outer glow size
        Color neonGlow = new Color(0.0f, 0.8f, 1.0f, 0.3f); // Cyan glow effect
        Color neonCore = new Color(0.0f, 0.8f, 1.0f, 1.0f); // Bright neon center

        // Outer glow
        shapeRenderer.setColor(neonGlow);
        shapeRenderer.rect(x - glowSize, y - glowSize, tileSize + 2 * glowSize, tileSize + 2 * glowSize);

        // Inner core
        shapeRenderer.setColor(neonCore);
        shapeRenderer.rect(x, y, tileSize, tileSize);

        // Add outline effect
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.rect(x, y, tileSize, tileSize);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }
    
    public int getScreenHeight() {
        return screenHeight;
    }

    public int[][] getMazeLayout() {
        return mazeLayout;
    }

    public int getMazeWidth() {
        return mazeLayout[0].length;
    }

    public int getMazeHeight() {
        return mazeLayout.length;
    }

    public float getTileSize() {
        return tileSize;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }
    
    public List<StaticObjects> getStaticObjects() {
        return staticObjects;
    }
    
    public void setStaticObjects(List<StaticObjects> staticObjects) {
        this.staticObjects = staticObjects;
    }

    
    public void dispose() {
        shapeRenderer.dispose();
    }
}
