//package io.github.some_example_name.lwjgl3;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.Gdx;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class StaticObjects extends Entity {  // ✅ Ensure StaticObjects extends Entity
//    private float x, y;
//    private Texture texture;
//    private char type;
//    private int gridX, gridY;
//    
// // ✅ Store textures in a static HashMap to avoid reloading
//    private static final HashMap<Character, Texture> textureMap = new HashMap<>();
//
//    // ✅ Call super() because Entity has no default constructor
//    public StaticObjects(Board board, char type, float x, float y, int gridX, int gridY) {
//        super(board, gridX, gridY, "static"); // ✅ Call Entity constructor
//        this.x = x;
//        this.y = y;
//        this.gridX = gridX;
//        this.gridY = gridY;
//        this.type = type;
//
//        // ✅ Load textures once and store in a HashMap
//        if (textureMap.isEmpty()) {
//            textureMap.put('.', new Texture(Gdx.files.internal("board/pellet.png")));
////            textureMap.put('p', new Texture(Gdx.files.internal("board/powerup.png")));
//        }
//
//        // ✅ Set texture
//        texture = textureMap.get(type);
//
//        if (texture == null) {
//            System.err.println("⚠️ No texture found for symbol: '" + type + "' at (" + gridX + "," + gridY + ")");
//        }    }
//
//    public void render(SpriteBatch batch) {
//        if (type == '.' || type == 'p') {
//            return; // ❌ Skip drawing pellets & power-ups
//        }
//
//        if (texture != null) {
//            batch.draw(texture, x, y, 16, 16);
//        }
//    }
//
//
//    public void dispose() {
//        if (texture != null) {
//            texture.dispose();
//        }
//    }
//
//    public int getGridX() { // ✅ Ensure gridX can be accessed
//        return gridX;
//    }
//
//    public int getGridY() { // ✅ Ensure gridY can be accessed
//        return gridY;
//    }
//
//    /**
//     * ✅ Generates all Static Objects like pellets and power-ups.
//     */
//    public static void generateStaticObjects(Board board, EntityManager entityManager) {
//        char[][] maze = board.getMazeLayout();
//
//        // ✅ Skip pellet ('.') and power-up ('p') generation
//        for (int row = 0; row < maze.length; row++) {
//            for (int col = 0; col < maze[row].length; col++) {
//                if (maze[row][col] == '.' || maze[row][col] == 'p') {
//                    continue; // ✅ Ignore pellets & power-ups
//                }
//            }
//        }
//    }
//}

package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class StaticObjects extends Entity {  
    private Texture texture;
    private char type;

    public StaticObjects(Board board, char type, int gridX, int gridY) {
        super(board, gridX, gridY, "static");
        this.type = type;
        updateTexture();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y, board.getTileSize(), board.getTileSize());
            }
    }

    public static void generateStaticObjects(Board board, EntityManager entityManager) {
        System.out.println("🔄 generateStaticObjects() called!"); // Debugging Step

        char[][] maze = board.getMazeLayout();

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                char type = maze[row][col];

                if (type != '.' && type != '$') {
                    continue; 
                }

                StaticObjects staticObject = new StaticObjects(board, type, col, row);
                entityManager.addEntity(staticObject);

                if (type == '$') {
                    System.out.println("✅ Game Over object placed at: (" + col + ", " + row + ")");
                }
            }
        }
    }
    
    private void updateTexture() {
        this.texture = StaticObjectAssets.getTexture(type);
    }

    
  public void dispose() {
  if (texture != null) {
      texture.dispose();
  }
}
}
