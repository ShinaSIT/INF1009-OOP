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
        board = new Board();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        board.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        board.updateDimensions();  // Update board dimensions when the window resizes
    }
    
    @Override
    public void dispose() {
        board.dispose();
        batch.dispose();
    }
}
