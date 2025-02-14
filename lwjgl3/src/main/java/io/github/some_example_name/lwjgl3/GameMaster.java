package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter {
    private Board board;
    private SpriteBatch batch;
    private Speaker speaker; 
    private InputOutputManager ioManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        board = new Board(800, 600); // Set the game board size
        ioManager = new InputOutputManager();
        Keyboard keyboard = new Keyboard(ioManager);
        Mouse mouse = new Mouse(ioManager);
        
     // Ensure speaker is initialized here
        speaker = new Speaker();  // Initialize the speaker object

        // Load a sample sound
        speaker.loadSound("click", "sounds/sample.mp3");

        // Play the sound
        speaker.playSound("click");
    }

    @Override
    public void render() {
        // Clear the screen before drawing
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        board.render(batch);  // Draws the game board
        batch.end();
        
        Keyboard keyboard = new Keyboard(ioManager);  // This will handle the input checks
        keyboard.checkKeys();
        Mouse mouse = new Mouse(ioManager);  // This will handle the input checks
        mouse.checkMouse();
    }

    @Override
    public void dispose() {
        board.dispose();
        batch.dispose();
        speaker.stopMusic();
    }
}
