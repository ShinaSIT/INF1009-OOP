package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.HashMap;

public class InputManager extends InputClass {
	private Player player;
    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;


    public InputManager(MovementManager movementManager, Player player, Board board, Mouse mouse) {
        super(movementManager, player, board, mouse);
        this.player = player;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    protected void initializeInputMap() {
        inputMap = new HashMap<>();
        inputMap.put(Input.Keys.W, "Move Up");
        inputMap.put(Input.Keys.S, "Move Down");
        inputMap.put(Input.Keys.A, "Move Left");
        inputMap.put(Input.Keys.D, "Move Right");
        inputMap.put(Input.Keys.UP, "Move Up");
        inputMap.put(Input.Keys.DOWN, "Move Down");
        inputMap.put(Input.Keys.LEFT, "Move Left");
        inputMap.put(Input.Keys.RIGHT, "Move Right");
        inputMap.put(Input.Buttons.LEFT, "Left Click");
        inputMap.put(Input.Buttons.RIGHT, "Right Click");
    }

    @Override
    public boolean isKeyPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }

    @Override
    public boolean isButtonPressed(int button) {
        return Gdx.input.isButtonPressed(button);
    }

    public boolean handleInput() {
        boolean moved = false;
        if (board == null) {
            System.err.println("Error: Board is not initialized!");
            return false;
        }

        float step = board.getTileSize();
        int oldX = player.getGridX();  // ✅ Store old position
        int oldY = player.getGridY();

        // ✅ Use key press/release tracking but functionally act like `isKeyJustPressed()`
        if ((Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) && !wPressed) {
            player.move(0, -1);
            wPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.UP)) {
            wPressed = false;
        }

        if ((Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) && !sPressed) {
            player.move(0, 1);
            sPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            sPressed = false;
        }

        if ((Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) && !aPressed) {
            player.move(-1, 0);
            aPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            aPressed = false;
        }

        if ((Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) && !dPressed) {
            player.move(1, 0);
            dPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dPressed = false;
        }

        Gdx.graphics.requestRendering();

        // ✅ Only return true if position actually changed
        if (player.getGridX() != oldX || player.getGridY() != oldY) {
            moved = true;
        }

        return moved;
    }
    
    public void setDependencies(MovementManager movementManager, Board board) {
        this.movementManager = movementManager;
        this.board = board;
    }
}
