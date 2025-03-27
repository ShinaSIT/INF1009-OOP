package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StaticObject extends Entity {
    private char symbol;

    public StaticObject(Board board, char symbol, int gridX, int gridY) {
        super(board, gridX, gridY, "static");
        this.symbol = symbol;
        this.addTag("static");
    }

    public char getSymbol() {
        return symbol;
    }
    @Override
    public void render(SpriteBatch batch) {
        Texture texture = StaticObjectAssets.getTexture(symbol);
        if (texture != null) {
            float tileSize = board.getTileSize();
            float drawX = gridX * tileSize + board.getStartX();
            float drawY = (board.getMazeHeight() - gridY - 1) * tileSize + board.getStartY();
            batch.draw(texture, drawX, drawY, tileSize, tileSize);
        }
    }
    public void dispose() {
        // Optionally log disposal
        System.out.println("🧹 Disposing static object: " + symbol);
        // If you're using textures dynamically, no need to dispose them here — handled by StaticObjectAssets
    }

}
