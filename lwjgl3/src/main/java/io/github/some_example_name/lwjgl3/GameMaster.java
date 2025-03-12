package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter {
    private BoardManager boardManager;
    private SpriteBatch batch;
    private EntityManager entityManager;
    private MovementManager movementManager;
    private CollisionManager collisionManager;
    private GameObjects player;
    private Speaker speaker;
    private SceneManager sceneManager;
    private InputOutputManager ioManager;
    private Mouse mouse;
    private StaticObjectManager staticObjectManager;
    private boolean gameStarted = false;
    private boolean isResizing = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        sceneManager = new SceneManager();
        boardManager = new BoardManager();
        boardManager.generateBoard(); // ✅ Only call this once at the start
        
        sceneManager.addScene("MenuScene", new MainMenuScene(sceneManager, this));
        sceneManager.transitionTo("MenuScene");
    }

    public void startGame() {
        System.out.println("✅ startGame() called!");
        gameStarted = true;
        speaker = new Speaker();
        entityManager = new EntityManager();
        collisionManager = new CollisionManager(boardManager.getBoard(), entityManager); // ✅ Use getBoard()
        movementManager = new MovementManager(speaker, collisionManager);
        player = new MoveableObjects(boardManager.getBoard(), entityManager, 1, 1, movementManager);
        entityManager.addEntity(player);
        mouse = new Mouse(null, speaker, sceneManager);
        ioManager = new InputOutputManager(movementManager, player, speaker, boardManager.getBoard(), mouse);
        mouse.setIoManager(ioManager);
        staticObjectManager = new StaticObjectManager(boardManager.getBoard());
        staticObjectManager.generateStaticObjects(2, entityManager);
        
        sceneManager.addScene("GameScene", new GameScene(sceneManager, this));
        sceneManager.transitionTo("GameScene");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        
        if (!gameStarted) {
            sceneManager.render(batch);
        } else {
            ioManager.handleInput();
            sceneManager.render(batch);
            boardManager.render(batch);
            entityManager.render(batch);
        }
        
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("✅ Resizing GameMaster: " + width + "x" + height);
        isResizing = true;
        
        if (sceneManager.getCurrentScene() != null) {
            sceneManager.getCurrentScene().resize(width, height);
        }

        if (gameStarted) {
            boardManager.updateBoard();
            entityManager.clearStaticObjects();
            staticObjectManager.generateStaticObjects(2, entityManager);
            entityManager.updateAllEntities(boardManager.getBoard()); // ✅ Use getBoard() to avoid unintended resets
            entityManager.ensurePlayerExists();
        }
        
        isResizing = false;
    }

    public void resetGame() {
        System.out.println("🔄 Resetting game...");
        gameStarted = false;
        sceneManager.transitionTo("MenuScene");
    }

    public void returnToMenu() {
        System.out.println("🔄 Returning to Menu...");
        gameStarted = false;
        sceneManager.transitionTo("MenuScene");
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        ioManager.stopTimer();
        boardManager.dispose();
        batch.dispose();
        sceneManager.getCurrentScene().dispose();
        speaker.stopSound("click");
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public static void main(String[] args) {
        Lwjgl3Launcher.main(args);
    }
}