package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;

import java.util.Random;

public class Board {
    private ShapeRenderer shapeRenderer;
    private float tileSize;
    private float startX;
    private float startY;
    private int screenWidth;
    private int screenHeight;
    private OrthographicCamera camera;
    
    private int[][] mazeLayout;
    private int mazeWidth = 15;  // Must be odd
    private int mazeHeight = 7;  // Must be odd
    private Random random = new Random();

    public Board() {
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        generateRandomMaze(); 
        updateDimensions();
    }

    private void generateRandomMaze() {
        mazeLayout = new int[mazeHeight][mazeWidth];

        boolean isSolvable = false;
        while (!isSolvable) {
            // Fill maze with walls
            for (int r = 0; r < mazeHeight; r++) {
                for (int c = 0; c < mazeWidth; c++) {
                    mazeLayout[r][c] = 1;
                }
            }

            // Generate paths
            carvePath(1, 1);
            
            // Entrance and single exit
            mazeLayout[0][1] = 0;  // Entrance
            mazeLayout[mazeHeight - 2][mazeWidth - 2] = 0; // Exit (Only one exit)
            
            // Ensure maze is solvable
            isSolvable = isMazeSolvable();
        }
        System.out.println("✅ Maze Board Created (" + mazeHeight + ", " + mazeWidth + ")");
    }

    private void carvePath(int row, int col) {
        int[] directions = {0, 1, 2, 3}; 
        shuffleArray(directions); 

        for (int direction : directions) {
            int newRow = row;
            int newCol = col;

            switch (direction) {
                case 0: newRow -= 2; break; 
                case 1: newCol += 2; break; 
                case 2: newRow += 2; break; 
                case 3: newCol -= 2; break; 
            }

            if (isValidCell(newRow, newCol)) {
                mazeLayout[newRow][newCol] = 0;
                mazeLayout[(row + newRow) / 2][(col + newCol) / 2] = 0; 
                carvePath(newRow, newCol);
            }
        }
    }

    private boolean isValidCell(int row, int col) {
        return row > 0 && col > 0 && row < mazeHeight - 1 && col < mazeWidth - 1 && mazeLayout[row][col] == 1;
    }

    private boolean isMazeSolvable() {
        boolean[][] visited = new boolean[mazeHeight][mazeWidth];
        return depthFirstSearch(0, 1, visited); // Start from entrance
    }

    private boolean depthFirstSearch(int row, int col, boolean[][] visited) {
        if (row == mazeHeight - 2 && col == mazeWidth - 2) {
            return true; // Exit found
        }

        if (row < 0 || col < 0 || row >= mazeHeight || col >= mazeWidth || 
            mazeLayout[row][col] == 1 || visited[row][col]) {
            return false; // Out of bounds, wall, or already visited
        }

        visited[row][col] = true;

        // Explore in all four directions
        return depthFirstSearch(row - 1, col, visited) || 
               depthFirstSearch(row + 1, col, visited) || 
               depthFirstSearch(row, col - 1, visited) || 
               depthFirstSearch(row, col + 1, visited);
    }

    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    public void updateDimensions() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        
        int mazeColumns = mazeLayout[0].length;
        int mazeRows = mazeLayout.length;
        
        tileSize = Math.min((float) screenWidth / mazeColumns, (float) screenHeight / mazeRows);
        float mazePixelWidth = mazeColumns * tileSize;
        float mazePixelHeight  = mazeRows * tileSize;
        
        startX = (screenWidth - mazePixelWidth) / 2.0f;
        startY = (screenHeight - mazePixelHeight) / 2.0f;
        
        camera.setToOrtho(false, screenWidth, screenHeight);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void render(SpriteBatch batch) {
        updateDimensions(); // Ensure resizing works
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int row = 0; row < mazeHeight; row++) {
            for (int col = 0; col < mazeWidth; col++) {
                if (mazeLayout[row][col] == 1) {
                    drawNeonWall(startX + col * tileSize, startY + (mazeHeight - row - 1) * tileSize);
                }
            }
        }

        shapeRenderer.end();
    }

    private void drawNeonWall(float x, float y) {
        float glowSize = tileSize * 0.1f; 
        Color neonGlow = new Color(0.0f, 0.8f, 1.0f, 0.3f);
        Color neonCore = new Color(0.0f, 0.8f, 1.0f, 1.0f);

        shapeRenderer.setColor(neonGlow);
        shapeRenderer.rect(x - glowSize, y - glowSize, tileSize + 2 * glowSize, tileSize + 2 * glowSize);

        shapeRenderer.setColor(neonCore);
        shapeRenderer.rect(x, y, tileSize, tileSize);

        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.rect(x, y, tileSize, tileSize);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    public int[][] getMazeLayout() {
        return mazeLayout;
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

    public int getMazeHeight() {
        return mazeHeight;
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
