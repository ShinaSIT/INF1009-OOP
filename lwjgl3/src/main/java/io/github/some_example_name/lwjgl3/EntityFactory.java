package io.github.some_example_name.lwjgl3;

public class EntityFactory {

    private Board board;
    private EntityManager entityManager;
    private MovementManager movementManager;
    private CollisionManager collisionManager;
    private BoardManager boardManager;
    private Speaker speaker;
    private InputManager inputManager;
    private Mouse mouse;

    public EntityFactory(Board board, EntityManager entityManager,
                         MovementManager movementManager, CollisionManager collisionManager,
                         BoardManager boardManager, Speaker speaker,
                         InputManager inputManager, Mouse mouse) {
        this.board = board;
        this.entityManager = entityManager;
        this.movementManager = movementManager;
        this.collisionManager = collisionManager;
        this.boardManager = boardManager;
        this.speaker = speaker;
        this.inputManager = inputManager;
        this.mouse = mouse;
    }

    public Entity getEntity(String type) {
        if (type.equalsIgnoreCase("player")) {
            Player player = new Player(board, entityManager, 1, 1, movementManager, 100, 3, collisionManager, boardManager, speaker);
            entityManager.addEntity(player);
            collisionManager.addCollidable(player);
            inputManager.setPlayer(player);
            mouse.setIoManager(inputManager);
            return player;
        }

        if (type.equalsIgnoreCase("germ")) {
            Germ germ = new Germ(board, entityManager, movementManager, collisionManager);
            entityManager.addEntity(germ);
            movementManager.addEntity(germ);
            collisionManager.addCollidable(germ);
            return germ;
        }

        System.out.println("❌ Unknown entity type: " + type);
        return null;
    }
}
