package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;

public class Board {
    private ShapeRenderer shapeRenderer;
    private float tileSize;
    private float startX;
    private float startY;
    private int screenWidth;
    private int screenHeight;
    private OrthographicCamera camera;

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

    public Board() {
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        updateDimensions();
    }

    private int lastScreenWidth = -1;
    private int lastScreenHeight = -1;

    public void updateDimensions() {
        int newWidth = Gdx.graphics.getWidth();
        int newHeight = Gdx.graphics.getHeight();

        if (newWidth == lastScreenWidth && newHeight == lastScreenHeight) {
            return;
        }

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

        System.out.println("✅ Board Resized: TileSize = " + tileSize + ", StartX = " + startX + ", StartY = " + startY);
    }

    protected char[][] getMazeLayout() {
        char[][] charMazeCopy = new char[mazeLayout.length][mazeLayout[0].length];

        for (int row = 0; row < mazeLayout.length; row++) {
            for (int col = 0; col < mazeLayout[row].length; col++) {
                if (mazeLayout[row][col] == '.' || mazeLayout[row][col] == 'p') {
                    charMazeCopy[row][col] = ' ';
                } else {
                    charMazeCopy[row][col] = mazeLayout[row][col];
                }
            }
        }
        return charMazeCopy;
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

            if (!batch.isDrawing()) {
                batch.begin();
            }

            for (int row = 0; row < mazeLayout.length; row++) {
                for (int col = 0; col < mazeLayout[row].length; col++) {
                    float tileX = startX + col * tileSize;
                    float tileY = startY + (mazeLayout.length - row - 1) * tileSize;

                    char symbol = mazeLayout[row][col];
                    Texture texture = getTextureForSymbol(symbol);

                    if (texture != null) {
                        batch.draw(texture, tileX, tileY, tileSize, tileSize);
                    }
                }
            }

            batch.end();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error occurred while rendering the maze!");
        }
    }

    private Texture getTextureForSymbol(char symbol) {
        switch (symbol) {
            case '-': return Asset.pipeHorizontal;
            case '|': return Asset.pipeVertical;
            case '1': return Asset.pipeCorner1;
            case '2': return Asset.pipeCorner2;
            case '3': return Asset.pipeCorner3;
            case '4': return Asset.pipeCorner4;
            case 'b': return Asset.block;
            case '[': return Asset.capLeft;
            case ']': return Asset.capRight;
            case '_': return Asset.capBottom;
            case '^': return Asset.capTop;
            case '+': return Asset.pipeCross;
            case '5': return Asset.pipeConnectorTop;
            case '6': return Asset.pipeConnectorRight;
            case '7': return Asset.pipeConnectorBottom;
            case '8': return Asset.pipeConnectorLeft;
            default: return null;
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
