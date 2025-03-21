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
    private boolean isResizing = false;

    @Override
    public void create() {
    	AssetManager.loadAll(); // ✅ Ensure assets are loaded before the game starts
        batch = new SpriteBatch();
        sceneManager = new SceneManager();
        boardManager = new BoardManager();
        boardManager.generateBoard(); // ✅ Only call this once at the start

        sceneManager.addScene("MenuScene", new MainMenuScene(sceneManager, this));
        sceneManager.transitionTo("MenuScene");
    }

    public void startGame() {
        System.out.println("✅ startGame() called!");
        System.out.println("🟢 GameMaster.startGame() - Board reference: " + boardManager);

        gameStarted = true;
        speaker = new Speaker();
        entityManager = new EntityManager(boardManager.getBoard());
        collisionManager = new CollisionManager(boardManager.getBoard(), entityManager);
        movementManager = new MovementManager(speaker, collisionManager);

        // ✅ Instantiate player at a valid position on the board
        Player player = new Player(boardManager.getBoard(), entityManager, 1, 1, movementManager, 100, 3, collisionManager);
        entityManager.addEntity(player); // ✅ Ensure player is added

        mouse = new Mouse(null, speaker, sceneManager);
        inputManager = new InputManager(movementManager, player, boardManager.getBoard(), mouse);
        outputManager = new OutputManager(speaker);
        sessionManager = new SessionManager();
        mouse.setIoManager(inputManager);

        // ✅ Add Germ entity (if needed)
        Germ germ = new Germ(boardManager.getBoard(), entityManager, 1, 2, movementManager);
        entityManager.addEntity(germ);
        movementManager.addEntity(germ);
//        collisionManager.addCollidable(germ);

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

                // ✅ Print logs only if movement occurs
                if (moved) {  
                    System.out.println("🎮 Game is running...");
                    System.out.println("📌 Player position (render): " + player.getX() + ", " + player.getY());
                }

                boolean germMoved = false;
                for (Entity entity : entityManager.getEntities()) {
                    if (entity instanceof Germ) {
                        if (!germMoved) { // ✅ Print "Germ Moving..." only once per cycle
//                            System.out.println("🦠 Germ Moving...");
                            germMoved = true;
                        }
                        ((Germ) entity).moveSmartly();
                    }
                }

                if (!outputManager.isHasMoved()) {
                    System.out.println("⏳ Timer Started: " + sessionManager.isTimerRunning());
                    sessionManager.startTimer();
                    outputManager.setHasMoved(true);
                }
				entityManager.render(batch);
                outputManager.handleOutput();
                boardManager.render(batch);
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
            System.out.println("🔄 Resizing Board & Entities...");

            Board board = boardManager.getBoard();
            if (board != null) {
//                board.updateDimensions(); // ✅ Ensure board resizes correctly
                entityManager.updateBoardReference(board); // ✅ Ensure entities have the right reference
                entityManager.updateEntitiesOnResize(); // ✅ Ensure entities reposition

                System.out.println("🔄 Forcing a Board Re-render!");
                board.render(batch);  // ✅ Force re-render
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

    @Override
    public void dispose() {
        System.out.println("🛑 Disposing GameMaster...");
        AssetManager.disposeAll(); // ✅ Release textures before closing
        sessionManager.stopTimer();
        boardManager.dispose();
        batch.dispose();
        if (sceneManager.getCurrentScene() != null) {
            sceneManager.getCurrentScene().dispose();
        }
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
