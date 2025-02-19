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
    private CollisionManager collisionManager;
    private GameObjects player;
    private Speaker speaker; 
    private SceneManager sceneManager;
    private InputOutputManager ioManager;
    private Keyboard keyboard;  
    private Mouse mouse;        
    private StaticObjectManager staticObjectManager;
    private HealthManager healthManager;
    
    private boolean gameStarted = false; // Track Game Status
    private boolean isResizing = false; 

    @Override
    public void create() {
        batch = new SpriteBatch();
        sceneManager = new SceneManager();
        healthManager = new HealthManager(this, sceneManager);

        // ✅ Load MenuScene
        sceneManager.addScene("MenuScene", new MainMenuScene(sceneManager, this)); 
        sceneManager.transitionTo("MenuScene");
    }

    public void startGame() {  
        System.out.println("✅ startGame() called!");

        gameStarted = true;

        board = new Board();
        speaker = new Speaker();
        entityManager = new EntityManager();
        collisionManager = new CollisionManager(board, entityManager, healthManager);
        movementManager = new MovementManager(speaker, collisionManager);
        player = new MoveableObjects(board, entityManager, 1, 1, movementManager);
        entityManager.addEntity(player);

        mouse = new Mouse(null, speaker, sceneManager);
        ioManager = new InputOutputManager(movementManager, player, speaker, board, mouse);
        mouse.setIoManager(ioManager);
        keyboard = new Keyboard(ioManager);

        staticObjectManager = new StaticObjectManager(board);
        staticObjectManager.generateStaticObjects(2, entityManager);

        // ✅ Load GameScene
        sceneManager.addScene("GameScene", new GameScene(sceneManager, this));  
        sceneManager.transitionTo("GameScene");  
    }

    // ✅ Getters for external access
    public Board getBoard() {
        return board;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public HealthManager getHealthManager() {
        return healthManager;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (!gameStarted) {
            sceneManager.render(batch);  
        } else {
            if (!healthManager.isGameOver()) {  
                ioManager.handleInput();
                collisionManager.checkCollisions();
                sceneManager.render(batch);
                board.render(batch);
                entityManager.render(batch);
                healthManager.drawLivesDisplay(batch); 
            } else {
                healthManager.drawGameOverScreen(batch);
            }
        }
        
        batch.end(); 
        
        if (healthManager.isGameOver()) {
            healthManager.getStage().act(Gdx.graphics.getDeltaTime());
            healthManager.getStage().draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("✅ Resizing GameMaster: " + width + "x" + height);
        isResizing = true; // ✅ Prevents rendering while resizing

        if (sceneManager.getCurrentScene() != null) {
            sceneManager.getCurrentScene().resize(width, height);
        }

        if (gameStarted && board != null) {
            board.updateDimensions();
            entityManager.clearStaticObjects();
            staticObjectManager.generateStaticObjects(2, entityManager);

            // ✅ Remove and regenerate the Player safely
            if (player != null) {
                int lastX = player.getGridX();
                int lastY = player.getGridY();
                entityManager.removeEntity(player);
                player = new MoveableObjects(board, entityManager, lastX, lastY, movementManager);
                entityManager.addEntity(player);
            }
        }

        if (healthManager.isGameOver()) {
            healthManager.resize(width, height);
        }

        isResizing = false; // ✅ Allow rendering again after resizing
    }
    
    public void resetGame() {
        System.out.println("🔄 Resetting game...");
        gameStarted = false; 
        healthManager.reset();
        sceneManager.transitionTo("MenuScene");
    }
    
    public void updatePlayerPosition() {
        if (player != null) {
            int clampedX = Math.max(0, Math.min(player.getGridX(), board.getMazeWidth() - 1));
            int clampedY = Math.max(0, Math.min(player.getGridY(), board.getMazeHeight() - 1));

            player.setGridX(clampedX);
            player.setGridY(clampedY);

            player.updatePixelPosition(board);

            System.out.println("📌 Player Clamped to Grid (" + clampedX + ", " + clampedY + ")");
        }
    }

    
    public void returnToMenu() {
        System.out.println("🔄 Returning to Menu...");
        gameStarted = false;  
        healthManager.reset();
        sceneManager.transitionTo("MenuScene");
        Gdx.input.setInputProcessor(null);
    }
    
    @Override
    public void dispose() {
        ioManager.stopTimer();
        board.dispose();
        batch.dispose();
        sceneManager.getCurrentScene().dispose();
        speaker.stopSound("click");
    }

    public static void main(String[] args) {
        Lwjgl3Launcher.main(args);
    }
}
