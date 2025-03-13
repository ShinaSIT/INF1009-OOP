package io.github.some_example_name.lwjgl3;

import java.util.Map;
import com.badlogic.gdx.Input;

public abstract class InputClass {

    protected Map<Integer, String> inputMap;
    protected MovementManager movementManager;
    protected GameObjects player;
    protected Board board;
    protected Mouse mouse;

    public InputClass(MovementManager movementManager, GameObjects player, Board board, Mouse mouse) {
        this.movementManager = movementManager;
        this.player = player;
        this.board = board;
        this.mouse = mouse;
        initializeInputMap();
    }

    protected abstract void initializeInputMap();

    public String getMappedAction(int key) {
        return inputMap.getOrDefault(key, "Unknown Action");
    }

    public abstract boolean isKeyPressed(int key);

    public abstract boolean isButtonPressed(int button);

    public abstract void handleInput();
}