package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter {
    private Board board;
    private SpriteBatch batch;
    private EntityManager entityManager;
    private MovementManager movementManager;
    private GameObjects player;
    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;
//    private Speaker speaker; 
//    private InputOutputManager ioManager;
//    private Keyboard keyboard;  // Declare Keyboard instance
//    private Mouse mouse;        // Declare Mouse instance
    	
    @Override
    public void create() {
        batch = new SpriteBatch();
        board = new Board();
    	entityManager = new EntityManager();
    	movementManager = new MovementManager();
    	entityManager.addEntity(new StaticObjects(board, 5, 5, board.getTileSize()));
    	entityManager.addEntity(new StaticObjects(board,10,13,board.getTileSize()));
    	player = new MoveableObjects(board, entityManager, 1, 1, board.getTileSize(),movementManager);
    	entityManager.addEntity(player);
    	Gdx.input.setInputProcessor(null);
    	System.out.println("Player starts at: " + player.getX() + ", " + player.getY());

//        ioManager = new InputOutputManager();
        
        // Initialize the speaker object first, so it's fully set up before passed to Mouse
//        speaker = new Speaker();  
//        speaker.loadSound("click", "sounds/sample.mp3"); // Ensure the sound is loaded before use

        // Initialize Keyboard and Mouse, passing the speaker to Mouse constructor
//        keyboard = new Keyboard(ioManager);
//        mouse = new Mouse(ioManager, speaker);  // Pass speaker to Mouse constructor
        
        // Play the sound
//        speaker.playSound("click");
    }

    public void update() {
        float step = board.getTileSize(); 

        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && !wPressed) {
            System.out.println("Moving Up");
            movementManager.applyMovement((MoveableObjects) player, 0, step);  // ✅ Use movementManager
            wPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.W)) {
            wPressed = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S) && !sPressed) {
            System.out.println("Moving Down");
            movementManager.applyMovement((MoveableObjects) player, 0, -step);
            sPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.S)) {
            sPressed = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A) && !aPressed) {
            System.out.println("Moving Left");
            movementManager.applyMovement((MoveableObjects) player, -step, 0);
            aPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.A)) {
            aPressed = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D) && !dPressed) {
            System.out.println("Moving Right");
            movementManager.applyMovement((MoveableObjects) player, step, 0);
            dPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.D)) {
            dPressed = false;
        }
    }
    
//    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update();

        batch.begin();
        board.render(batch);
        entityManager.render(batch);
        batch.end();
        
        // Handle keyboard and mouse inputs
//        keyboard.checkKeys();  // Check keyboard inputs
//        mouse.checkMouse();    // Check mouse inputs
    }

    @Override
    public void resize(int width, int height) {
        board.updateDimensions();  // Update board dimensions when the window resizes
        entityManager.updatePositions(board);
    }
    
    @Override
    public void dispose() {
        board.dispose();
        batch.dispose();
//        speaker.stopSound("click");  // Stop the sound when disposing
    }
    
    public static void main(String[] args) {
        Lwjgl3Launcher.main(args); 
    }
}
