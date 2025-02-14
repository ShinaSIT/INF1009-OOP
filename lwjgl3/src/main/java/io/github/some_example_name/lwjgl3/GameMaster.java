package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter {
    private Board board;
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        board = new Board(800, 600); // Set the game board size
    }

    @Override
    public void render() {
        // Clear the screen before drawing
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        board.render(batch);  // Draws the game board
        batch.end();
    }

    @Override
    public void dispose() {
        board.dispose();
        batch.dispose();
    }
}
