package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter {
    private BoardManager boardManager;
    private SpriteBatch batch;
    private EntityManager entityManager;
    private Player player;
    private OutputManager outputManager;
    protected boolean gameStarted = false;
    protected Mouse mouse;
    protected MovementManager movementManager;
    protected CollisionManager collisionManager;
    protected Speaker speaker;
    protected SceneManager sceneManager;
    protected InputManager inputManager;
    
     

    public GameMaster() {
        sceneManager = new SceneManager();
        speaker = new Speaker();
        mouse = new Mouse(null, speaker, sceneManager);
        boardManager = new BoardManager();
        entityManager = new EntityManager(boardManager.getBoard(), speaker);
        collisionManager = CollisionManager.getInstance(boardManager.getBoard(), entityManager, sceneManager);
        movementManager = new MovementManager(speaker, collisionManager);
        inputManager = new InputManager(null, null, null, mouse);
    }

    @Override
    public void create() {
        AssetManager.loadAll();
        batch = new SpriteBatch();
        outputManager = new OutputManager(speaker);

        boardManager.getBoard().initGL();
        boardManager.getBoard().generateFoods();
        boardManager.generateStaticObjects();
        boardManager.generateBoard();
        inputManager.setDependencies(movementManager, boardManager.getBoard());

        sceneManager.addScene("MenuScene", new MainMenuScene(sceneManager, this));
        sceneManager.addScene("InstructionsScene", new InstructionsScene(sceneManager, this));
        sceneManager.addScene("GameScene", new GameScene(sceneManager, this));
        sceneManager.addScene("GameOverScene", new GameOverScene(sceneManager));
        sceneManager.transitionTo("MenuScene");
    }

    @Override
    public void render() {
        try {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            if (!batch.isDrawing()) batch.begin();
            sceneManager.render(batch);
            //mouse.checkMouse(); 

            if (gameStarted) {
            	boolean playerAlreadyExists = false;
            	for (Entity e : entityManager.getEntities()) {
            	    if (e instanceof Player) {
            	        player = (Player) e;
            	        playerAlreadyExists = true;
            	        break;
            	    }
            	}

            	if (!playerAlreadyExists) {
            	    EntityFactory factory = new EntityFactory(boardManager.getBoard(),entityManager,movementManager,collisionManager,boardManager,speaker,inputManager,mouse);
            	    factory.getEntity("player");
            	}

            	boolean moved = inputManager.handleInput();

                for (Entity entity : entityManager.getEntities()) {
                    if (entity instanceof Germ) ((Germ) entity).moveSmartly();
                }

                if (!outputManager.isHasMoved()) {
                    outputManager.setHasMoved(true);
                }

                boardManager.getBoard().updateFoodRegeneration();
                boardManager.render(batch);
                entityManager.render(batch);
                outputManager.handleOutput();
                collisionManager.checkCollisions();
                
                

                if (player.getGridX() == 10 && player.getGridY() == 11) {
                    gameStarted = false;
                    sceneManager.transitionTo("MenuScene");
                }
                
                GameScene gameScene = (GameScene) sceneManager.getCurrentScene();
                if (gameScene != null && gameScene.getHealth() <= 0) {
                    gameStarted = false;
                    sceneManager.transitionTo("GameOverScene");
                }
            }

            if (batch.isDrawing()) batch.end();
        } catch (Exception e) {
            e.printStackTrace();
            if (batch.isDrawing()) batch.end();
        }
    }

    @Override
    public void dispose() {
        AssetManager.disposeAll();
        boardManager.dispose();
        batch.dispose();
        if (sceneManager.getCurrentScene() != null) sceneManager.getCurrentScene().dispose();
        speaker.stopSound("click");
    }

    @Override
    public void resize(int width, int height) {
        if (sceneManager.getCurrentScene() != null) sceneManager.getCurrentScene().resize(width, height);

        if (gameStarted) {
            Board board = boardManager.getBoard();
            if (board != null) {
                entityManager.updateBoardReference(board);
                entityManager.updateEntitiesOnResize();
                board.render(batch);
            }
            entityManager.ensurePlayerExists();
        }
    }

    public SpriteBatch getBatch() { 
    	return batch; 
    }
    
    public EntityManager getEntityManager() { 
    	return entityManager; 
    }
    
    public BoardManager getBoardManager() { 
    	return boardManager; 
    }

    public static void main(String[] args) {
        Lwjgl3Launcher.main(args);
    }
    
    public Mouse getMouse() {
        return mouse;
    }
}