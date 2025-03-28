package io.github.some_example_name.lwjgl3;

public class EntityFactory {

    private Board board;
    private EntityManager entityManager;
    private MovementManager movementManager;
    private CollisionChecker collisionChecker; // ✅ use interface
    private BoardManager boardManager;
    private SoundPlayer soundPlayer; // ✅ use interface
    private InputManager inputManager;
    private Mouse mouse;
    private SceneController sceneController; // ✅ interface

    public EntityFactory(Board board, EntityManager entityManager,
                         MovementManager movementManager, CollisionChecker collisionChecker,
                         BoardManager boardManager, SoundPlayer soundPlayer,
                         InputManager inputManager, Mouse mouse,
                         SceneController sceneController) {
        this.board = board;
        this.entityManager = entityManager;
        this.movementManager = movementManager;
        this.collisionChecker = collisionChecker;
        this.boardManager = boardManager;
        this.soundPlayer = soundPlayer;
        this.inputManager = inputManager;
        this.mouse = mouse;
        this.sceneController = sceneController;
    }

    public Entity getEntity(String type) {
        if (type.equalsIgnoreCase("player")) {
            Player player = new Player(
                board, entityManager, 1, 1,
                movementManager, 100, 3,
                collisionChecker, boardManager,
                soundPlayer, sceneController // ✅ corrected params
            );

            entityManager.addEntity(player);
            collisionChecker.addCollidable(player); // must update your interface or cast
            inputManager.setPlayer(player);
            mouse.setIoManager(inputManager);
            return player;
        }

        if (type.equalsIgnoreCase("germ")) {
            Germ germ = new Germ(board, entityManager, movementManager, (CollisionManager) collisionChecker);
            entityManager.addEntity(germ);
            movementManager.addEntity(germ);
            collisionChecker.addCollidable(germ); // again, check your interface
            return germ;
        }

        System.out.println("❌ Unknown entity type: " + type);
        return null;
    }

    public StaticObject createStatic(char symbol, int gridX, int gridY) {
        StaticObject obj = new StaticObject(board, symbol, gridX, gridY);
        entityManager.addEntity(obj);
        return obj;
    }
}
