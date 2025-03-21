package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private ShapeRenderer shapeRenderer;
    private float tileSize;
    private float startX;
    private float startY;
    private int screenWidth;
    private int screenHeight;
    private OrthographicCamera camera;
    private List<Texture> textures;
    
    private final char[][] mazeLayout = {
            {'1', '-', '-', '-', '-', '-', '-', '-', '-', '-', '2'},
            {'|', '@', '.', '.', '.', '.', '.', '.', '.', '.', '|'},
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
        this.textures = new ArrayList<>();
        loadTextures();
        updateDimensions();
    }

    private void loadTextures() {
        textures.add(new Texture(Gdx.files.internal("board/pipeHorizontal.png"))); // index 0
        textures.add(new Texture(Gdx.files.internal("board/pipeVertical.png")));   // index 1
        textures.add(new Texture(Gdx.files.internal("board/pipeCorner1.png")));    // index 2
        textures.add(new Texture(Gdx.files.internal("board/pipeCorner2.png")));    // index 3
        textures.add(new Texture(Gdx.files.internal("board/pipeCorner3.png")));    // index 4
        textures.add(new Texture(Gdx.files.internal("board/pipeCorner4.png")));    // index 5
        textures.add(new Texture(Gdx.files.internal("board/block.png")));          // index 6
        textures.add(new Texture(Gdx.files.internal("board/capLeft.png")));        // index 7
        textures.add(new Texture(Gdx.files.internal("board/capRight.png")));       // index 8
        textures.add(new Texture(Gdx.files.internal("board/capBottom.png")));      // index 9
        textures.add(new Texture(Gdx.files.internal("board/capTop.png")));         // index 10
        textures.add(new Texture(Gdx.files.internal("board/pipeCross.png")));      // index 11
        textures.add(new Texture(Gdx.files.internal("board/pipeConnectorTop.png")));// index 12
        textures.add(new Texture(Gdx.files.internal("board/pipeConnectorRight.png")));// index 13
        textures.add(new Texture(Gdx.files.internal("board/pipeConnectorBottom.png")));// index 14
        textures.add(new Texture(Gdx.files.internal("board/pipeConnectorLeft.png")));// index 15
        textures.add(new Texture(Gdx.files.internal("board/End.png")));// index 16
    }

    private int lastScreenWidth = -1;
    private int lastScreenHeight = -1;

    public void updateDimensions() {
        int newWidth = Gdx.graphics.getWidth();
        int newHeight = Gdx.graphics.getHeight();

        // ✅ Prevent excessive resize calls
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
                    charMazeCopy[row][col] = ' '; // ✅ Replace pellets & power-ups with empty paths
                } else {
                    charMazeCopy[row][col] = mazeLayout[row][col]; // ✅ Copy everything else
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

            if (!batch.isDrawing()) { // ✅ Ensure batch is active before drawing
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

            batch.end(); // ✅ Ensure batch ends correctly
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error occurred while rendering the maze!");
        }
    }

    private Texture getTextureForSymbol(char symbol) {
        if (symbol == '.') { 
            return null; // ✅ Prevents texture lookup for paths
        }
    	
        switch (symbol) {
            case '-': return textures.get(0);
            case '|': return textures.get(1);
            case '1': return textures.get(2);
            case '2': return textures.get(3);
            case '3': return textures.get(4);
            case '4': return textures.get(5);
            case 'b': return textures.get(6);
            case '[': return textures.get(7);
            case ']': return textures.get(8);
            case '_': return textures.get(9);
            case '^': return textures.get(10);
            case '+': return textures.get(11);
            case '5': return textures.get(12);
            case '6': return textures.get(13);
            case '7': return textures.get(14);
            case '8': return textures.get(15);
            case '$': return textures.get(16);
            default: return null;
        }
    }

    public void dispose() {
        shapeRenderer.dispose();
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}
