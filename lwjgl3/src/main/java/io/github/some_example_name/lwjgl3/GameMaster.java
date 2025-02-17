package io.github.some_example_name.lwjgl3;

import java.util.List;
import java.util.ArrayList;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter {
    private Board board;
    private SpriteBatch batch;
    private EntityManager entityManager;
    private MovementManager movementManager;
    private GameObjects player;
    private Speaker speaker; 
    private SceneManager sceneManager;
    private InputOutputManager ioManager;
    private Keyboard keyboard;  
    private Mouse mouse;        
    private List<StaticObjects> staticObjects = new ArrayList<StaticObjects>();




    
    @Override
    public void create() {
        batch = new SpriteBatch();
        board = new Board();
        entityManager = new EntityManager();
        movementManager = new MovementManager();
        
        entityManager.addEntity(new StaticObjects(board, 5, 5, board.getTileSize()));
        entityManager.addEntity(new StaticObjects(board, 10, 13, board.getTileSize()));

        player = new MoveableObjects(board, entityManager, 1, 1, board.getTileSize(), movementManager);
        entityManager.addEntity(player);
        Gdx.input.setInputProcessor(null);
        
        System.out.println("Player starts at: " + player.getX() + ", " + player.getY());

        // ✅ Initialize Speaker
        speaker = new Speaker();
        
        // ✅ Initialize Mouse with NULL ioManager temporarily
        mouse = new Mouse(null, speaker, sceneManager);  

        // ✅ Initialize InputOutputManager
        ioManager = new InputOutputManager(movementManager, player, speaker, board, mouse);

        // ✅ Now update Mouse with the correct ioManager
        mouse.setIoManager(ioManager);  // New method in Mouse.java

        // ✅ Initialize Keyboard
        keyboard = new Keyboard(ioManager);
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Call handleInput from InputOutputManager
        ioManager.handleInput();

        batch.begin();
        board.render(batch);
        entityManager.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        board.updateDimensions(); // Update board size
        updatePlayerPosition();   // Ensure player resizes properly
        updateStaticObjects();    // Ensure static objects adjust
    }
    private void updatePlayerPosition() {
        if (player != null) {
            player.setX(board.getStartX() + player.getGridX() * board.getTileSize());
            player.setY(board.getStartY() + (board.getMazeHeight() - player.getGridY() - 1) * board.getTileSize());
        }
    }
    private void updateStaticObjects() {
        for (StaticObjects obj : staticObjects) {
            obj.updateObjectPosition(board);
        }
    }



    @Override
    public void dispose() {
    	ioManager.stopTimer();
        board.dispose();
        batch.dispose();
        speaker.stopSound("click");
    }

    public static void main(String[] args) {
        Lwjgl3Launcher.main(args);
    }
}
