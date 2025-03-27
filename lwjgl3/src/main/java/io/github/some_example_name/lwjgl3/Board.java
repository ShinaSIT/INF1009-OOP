package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;

import java.awt.Point;
import java.util.*;

public class Board {
    private ShapeRenderer shapeRenderer;
    private float tileSize;
    private float startX;
    private float startY;
    private int screenWidth;
    private int screenHeight;
    private OrthographicCamera camera;
    private EntityManager entityManager;


    private final char[][] mazeLayout = {
            {'1', '-', '-', '-', '-', '-', '-', '-', '-', '-', '2'},
            {'|', '.', '.', '.', '.', '.', '.', '.', '.', '.', '|'},
            {'|', '.', 'b', '.', '[', '7', ']', '.', 'b', '.', '|'},
            {'|', '.', '.', '.', '.', '_', '.', '.', '.', '.', '|'},
            {'|', '.', '[', ']', '.', '.', '.', '[', ']', '.', '|'},
            {'|', '.', '.', '.', '.', '^', '.', '.', '.', '.', '|'},
            {'|', '.', 'b', '.', '[', '+', ']', '.', 'b', '.', '|'},
            {'|', '.', '.', '.', '.', '_', '.', '.', '.', '.', '|'},
            {'|', '.', '[', ']', '.', '.', '.', '[', ']', '.', '|'},
            {'|', '.', '.', '.', '.', '^', '.', '.', '.', '.', '|'},
            {'|', '.', 'b', '.', '[', '5', ']', '.', 'b', '.', '|'},
            {'|', '.', '.', '.', '.', '.', '.', '.', '.', '.', '$'},
            {'4', '-', '-', '-', '-', '-', '-', '-', '-', '-', '3'}
    };

    private Food[][] foodGrid = new Food[mazeLayout.length][mazeLayout[0].length];
    private long lastRegenerateTime = System.currentTimeMillis();
    private final long regenDelay = 15000; // 15 seconds

    public Board() {
        this.camera = new OrthographicCamera();
        mazeLayout[1][1] = ' ';
    }
    
    public void initGL() {
        this.shapeRenderer = new ShapeRenderer();
        updateDimensions();
    }

    private int lastScreenWidth = -1;
    private int lastScreenHeight = -1;

    public void updateDimensions() {
        int newWidth = Gdx.graphics.getWidth();
        int newHeight = Gdx.graphics.getHeight();

        if (newWidth == lastScreenWidth && newHeight == lastScreenHeight) return;

        lastScreenWidth = newWidth;
        lastScreenHeight = newHeight;

        screenWidth = newWidth;
        screenHeight = newHeight;
        tileSize = Math.min((float) screenWidth / mazeLayout[0].length, (float) screenHeight / mazeLayout.length);
        float mazePixelWidth = mazeLayout[0].length * tileSize;
        float mazePixelHeight = mazeLayout.length * tileSize;
        startX = (screenWidth - mazePixelWidth) / 2.0f;
        startY = (screenHeight - mazePixelHeight) / 2.0f;
        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.update();

        System.out.println("✅ Board Created");
    }

    public void generateFoods() {
        System.out.println("🍽️ Generating food...");

        // 🧹 Full reset of layout and foodGrid before regenerating
        for (int row = 0; row < foodGrid.length; row++) {
            for (int col = 0; col < foodGrid[row].length; col++) {
                if (foodGrid[row][col] != null || mazeLayout[row][col] == 'f') {
                    foodGrid[row][col] = null;
                    mazeLayout[row][col] = '.';  // Set back to pellet
                }
            }
        }

        // ✅ Generate new food
        Random random = new Random();
        int unhealthyCount = 0;
        int unhealthyLimit = 5;
        List<Point> foodSpots = new ArrayList<>();
        Set<Point> skipPoints = Set.of(new Point(1, 1), new Point(1, 10));

        for (int row = 0; row < mazeLayout.length; row++) {
            for (int col = 0; col < mazeLayout[row].length; col++) {
                Point point = new Point(col, row);
                if (mazeLayout[row][col] == '.' && !skipPoints.contains(point)) {
                    foodSpots.add(point);
                }
            }
        }

        Collections.shuffle(foodSpots);
        int foodToSpawn = foodSpots.size() / 3;

        for (int i = 0; i < foodToSpawn; i++) {
            Point p = foodSpots.get(i);
            String type;
            Texture tex;

            if (unhealthyCount < unhealthyLimit && random.nextBoolean()) {
                type = "unhealthy";
                tex = StaticObjectAssets.getRandomUnhealthyFood();
                unhealthyCount++;
            } else {
                type = "healthy";
                tex = StaticObjectAssets.getRandomHealthyFood();
            }

            foodGrid[p.y][p.x] = new Food(type, tex);
            mazeLayout[p.y][p.x] = 'f'; // update maze layout to mark food
        }
        
        int count = 0;
        for (int r = 0; r < foodGrid.length; r++) {
            for (int c = 0; c < foodGrid[r].length; c++) {
                if (foodGrid[r][c] != null && mazeLayout[r][c] == '.') {
                    System.out.println("⚠️ WARNING: Food at (" + c + "," + r + ") on a pellet tile!");
                    count++;
                }
            }
        }
        System.out.println("⚠️ Total ghost overlaps: " + count);


        System.out.println("🍏 Finished generating food.");
    }

    public void updateFoodRegeneration() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastRegenerateTime >= regenDelay) {
            regenerateOneFood();
            lastRegenerateTime = currentTime;
        }
    }

    private void regenerateOneFood() {
        List<Point> emptyTiles = new ArrayList<>();

        for (int row = 0; row < mazeLayout.length; row++) {
            for (int col = 0; col < mazeLayout[row].length; col++) {
                if (mazeLayout[row][col] == ' ') {
                    emptyTiles.add(new Point(col, row));
                }
            }
        }

        if (!emptyTiles.isEmpty()) {
            Collections.shuffle(emptyTiles);
            Point p = emptyTiles.get(0);

            boolean isHealthy = Math.random() < 0.5;
            Texture tex = isHealthy
                    ? StaticObjectAssets.getRandomHealthyFood()
                    : StaticObjectAssets.getRandomUnhealthyFood();
            String type = isHealthy ? "healthy" : "unhealthy";

            foodGrid[p.y][p.x] = new Food(type, tex);
            mazeLayout[p.y][p.x] = 'f';
        }

        checkAndRemoveBirdGate();
    }

    public void checkAndRemoveBirdGate() {
        if (countHealthyFoodRemaining() == 0) {
            for (int row = 0; row < mazeLayout.length; row++) {
                for (int col = 0; col < mazeLayout[row].length; col++) {
                    if (mazeLayout[row][col] == '$') {
                        mazeLayout[row][col] = ' ';
                        System.out.println("🕊️ Bird gate removed at (" + col + ", " + row + ")");

                        // ✅ Remove static visual from entity system
                        if (entityManager != null) {
                            entityManager.removeStaticAtPosition(col, row);
                        } else {
                            System.err.println("⚠️ entityManager is NULL in Board!");
                        }
                    }
                }
            }
        }
    }
    
    private int countHealthyFoodRemaining() {
        int count = 0;
        for (int row = 0; row < foodGrid.length; row++) {
            for (int col = 0; col < foodGrid[row].length; col++) {
                if (foodGrid[row][col] != null && foodGrid[row][col].isHealthy()) {
                    count++;
                }
            }
        }
        return count;
    }

    public char[][] getMazeLayout() {
        return mazeLayout;
    }

    public char[][] getMazeLayoutCopy() {
        char[][] copy = new char[mazeLayout.length][mazeLayout[0].length];

        for (int row = 0; row < mazeLayout.length; row++) {
            for (int col = 0; col < mazeLayout[row].length; col++) {
                char c = mazeLayout[row][col];
                if (c == '.' || c == 'p' || c == 'f' || c == ' ') {
                    copy[row][col] = ' '; // path
                } else {
                    copy[row][col] = c; // walls
                }
            }
        }
        return copy;
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        System.out.println("✅ Board linked to EntityManager!");
    }

    public Food[][] getFoodGrid() {
        return foodGrid;
    }

    public int getMazeHeight() {
        return mazeLayout.length;
    }

    public int getMazeWidth() {
        return mazeLayout[0].length;
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

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public void render(SpriteBatch batch) {
        try {
            updateDimensions();
            updateFoodRegeneration();

            if (!batch.isDrawing()) {
                batch.begin();
            }

            for (int row = 0; row < mazeLayout.length; row++) {
                for (int col = 0; col < mazeLayout[row].length; col++) {
                    float tileX = startX + col * tileSize;
                    float tileY = startY + (mazeLayout.length - row - 1) * tileSize;

                    char symbol = mazeLayout[row][col];

                    // 🧠 Skip rendering pellets if a fruit is also present
                    if (symbol == '.' && foodGrid[row][col] != null) continue;

                    if (symbol == '.' && foodGrid[row][col] == null) {
                        Texture pelletTexture = StaticObjectAssets.getTexture('.');
                        if (pelletTexture != null) {
                            batch.draw(pelletTexture, tileX, tileY, tileSize, tileSize);
                        }
                    }

                    // 2. Draw food if present
                    if (symbol == 'f' && foodGrid[row][col] != null) {
                        batch.draw(foodGrid[row][col].getTexture(), tileX, tileY, tileSize, tileSize);
                    }

                    // 3. Draw all other symbols (walls, gates, etc.)
                    if (symbol != '.' && symbol != 'f') {
                        Texture texture = StaticObjectAssets.getTexture(symbol);
                        if (texture != null) {
                            batch.draw(texture, tileX, tileY, tileSize, tileSize);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error occurred while rendering the maze!");
        }
    }

    private Texture getTextureForSymbol(char symbol, int row, int col) {
        if (symbol == 'f') {
            return foodGrid[row][col] != null ? foodGrid[row][col].getTexture() : null;
        }
        return StaticObjectAssets.getTexture(symbol);
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
    
}
