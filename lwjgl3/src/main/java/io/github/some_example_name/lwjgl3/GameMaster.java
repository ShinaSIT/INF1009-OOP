package io.github.some_example_name.lwjgl3;

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
    private InputOutputManager ioManager;
    private Keyboard keyboard;  
    private Mouse mouse;        

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

        // Initialize Speaker
        speaker = new Speaker();  
        speaker.loadSound("click", "sounds/sample.mp3"); 
        speaker.playMusic("sounds/sample.mp3");
        speaker.loadSound("move", "sounds/move.mp3"); 

        // Initialize InputOutputManager first
        ioManager = new InputOutputManager(movementManager, player, speaker, board, null);

        // Initialize Mouse after InputOutputManager
        mouse = new Mouse(ioManager, speaker);  

        // Now update InputOutputManager with Mouse
        ioManager = new InputOutputManager(movementManager, player, speaker, board, mouse);

        // Initialize Keyboard
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
        board.updateDimensions();
        entityManager.updatePositions(board);
    }

    @Override
    public void dispose() {
        board.dispose();
        batch.dispose();
        speaker.stopSound("click");
    }

    public static void main(String[] args) {
        Lwjgl3Launcher.main(args);
    }
}
