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
    private Keyboard keyboard;  // Declare Keyboard instance
    private Mouse mouse;        // Declare Mouse instance

    @Override
    public void create() {
        batch = new SpriteBatch();
        board = new Board();
        ioManager = new InputOutputManager();
        
        // Initialize the speaker object first, so it's fully set up before passed to Mouse
        speaker = new Speaker();  
        speaker.loadSound("click", "sounds/sample.mp3"); // Ensure the sound is loaded before use

        // Initialize Keyboard and Mouse, passing the speaker to Mouse constructor
        keyboard = new Keyboard(ioManager);
        mouse = new Mouse(ioManager, speaker);  // Pass speaker to Mouse constructor
        
        // Play the sound
        speaker.playSound("click");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        board.render(batch);
        batch.end();
        
        // Handle keyboard and mouse inputs
        keyboard.checkKeys();  // Check keyboard inputs
        mouse.checkMouse();    // Check mouse inputs
    }

    @Override
    public void resize(int width, int height) {
        board.updateDimensions();  // Update board dimensions when the window resizes
    }
    
    @Override
    public void dispose() {
        board.dispose();
        batch.dispose();
        speaker.stopSound("click");  // Stop the sound when disposing
    }
}
