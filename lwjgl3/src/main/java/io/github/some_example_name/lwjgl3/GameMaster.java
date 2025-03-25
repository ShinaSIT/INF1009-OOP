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
    private Player player;
    private Speaker speaker;
    private SceneManager sceneManager;
    private InputManager inputManager;
    private OutputManager outputManager;
    private SessionManager sessionManager;
    private Mouse mouse;
    private boolean gameStarted = false;

    public GameMaster() {
        sceneManager = new SceneManager();
        sessionManager = new SessionManager();

        mouse = new Mouse(null, null, sceneManager);
        inputManager = new InputManager(null, null, null, mouse);  // Delay initialization
        outputManager = null;  // Delay initialization
    }

    @Override
    public void create() {
        AssetManager.loadAll(); // ✅ Ensure assets are loaded first
        batch = new SpriteBatch(); // ✅ Must be initialized after Gdx is ready
        speaker = new Speaker();

        boardManager = new BoardManager();
        boardManager.generateBoard();

        entityManager = new EntityManager(boardManager.getBoard());
        collisionManager = new CollisionManager(boardManager.getBoard(), entityManager);
        movementManager = new MovementManager(speaker, collisionManager);

         // ✅ Move here to avoid NullPointerException
        outputManager = new OutputManager(speaker); // ✅ Move here

        inputManager.setDependencies(movementManager, boardManager.getBoard()); // ✅ Update inputManager

        sceneManager.addScene("MenuScene", new MainMenuScene(sceneManager, this));
        sceneManager.transitionTo("MenuScene");
    }


    public void startGame() {
        System.out.println("✅ startGame() called!");
        gameStarted = true;

        player = new Player(boardManager.getBoard(), entityManager, 1, 1, movementManager, 100, 3, collisionManager);
        entityManager.addEntity(player);
        collisionManager.addCollidable(player);
        

        // ✅ Set the player reference in inputManager
        inputManager.setPlayer(player);
        mouse.setIoManager(inputManager);

        Germ germ = new Germ(boardManager.getBoard(), entityManager, 1, 10, movementManager, collisionManager);
        Germ germ2 = new Germ(boardManager.getBoard(), entityManager, 9, 1, movementManager, collisionManager);
        Germ germ3 = new Germ(boardManager.getBoard(), entityManager, 9, 10, movementManager, collisionManager);
        
        entityManager.addEntity(germ);
        entityManager.addEntity(germ2);
        entityManager.addEntity(germ3);
        
        movementManager.addEntity(germ);
        movementManager.addEntity(germ2);
        movementManager.addEntity(germ3);
        
        collisionManager.addCollidable(germ);
        collisionManager.addCollidable(germ2);
        collisionManager.addCollidable(germ3);

        

        sceneManager.addScene("GameScene", new GameScene(sceneManager, this));
        sceneManager.transitionTo("GameScene");
    }

    @Override
    public void render() {
        try {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            if (!batch.isDrawing()) {
                batch.begin(); // ✅ Ensures batch starts
            }

            if (!gameStarted) {
                sceneManager.render(batch);
            } else {
                boolean moved = inputManager.handleInput();  // ✅ Check if input is detected

                if (moved) {  
                    System.out.println("🎮 Game is running...");
                    System.out.println("📌 Player position (render): " + player.getX() + ", " + player.getY());
                }

                // ✅ Move AI enemies
                boolean germMoved = false;
                for (Entity entity : entityManager.getEntities()) {
                    if (entity instanceof Germ) {
                        if (!germMoved) germMoved = true;
                        ((Germ) entity).moveSmartly();
                    }
                }

                // ✅ Start session timer
                if (!outputManager.isHasMoved()) {
                    System.out.println("⏳ Timer Started: " + sessionManager.isTimerRunning());
                    sessionManager.startTimer();
                    outputManager.setHasMoved(true);
                }

                // ✅ Add this line BEFORE rendering board
                boardManager.getBoard().updateFoodRegeneration();

                // ✅ Render board and entities
                boardManager.render(batch);
                entityManager.render(batch);

                outputManager.handleOutput();
                collisionManager.checkCollisions();
            }

            if (batch.isDrawing()) {
                batch.end();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error occurred while rendering GameMaster!");
            if (batch.isDrawing()) {
                batch.end();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("✅ Resizing GameMaster: " + width + "x" + height);

        if (sceneManager.getCurrentScene() != null) {
            sceneManager.getCurrentScene().resize(width, height);
        }

        if (gameStarted) {
            Board board = boardManager.getBoard();
            if (board != null) {
                entityManager.updateBoardReference(board);
                entityManager.updateEntitiesOnResize();
                board.render(batch);
            } else {
                System.out.println("⚠️ Error: Board is NULL after resizing!");
            }

            entityManager.ensurePlayerExists();
        }
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
    
    // ✅ Returns the BoardManager instance
    public BoardManager getBoardManager() {
        return boardManager;
    }

    // ✅ Returns the EntityManager instance
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void dispose() {
//        System.out.println("🛑 Disposing GameMaster...");
        AssetManager.disposeAll();
        sessionManager.stopTimer();
        boardManager.dispose();
        batch.dispose();
        if (sceneManager != null && sceneManager.getCurrentScene() != null) {
            sceneManager.getCurrentScene().dispose();
        }
        speaker.stopSound("click");
    }
    
    public static void main(String[] args) {
    	Lwjgl3Launcher.main(args);
    }
}
