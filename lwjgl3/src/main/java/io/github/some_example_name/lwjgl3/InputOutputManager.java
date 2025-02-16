package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

public class InputOutputManager {
    private Map<Integer, String> inputMap;

    public InputOutputManager() {
        inputMap = new HashMap<>();
        mapDefaultInputs();
    }

    private void mapDefaultInputs() {
        // WASD + Arrow Keys
        inputMap.put(Input.Keys.W, "Move Up");
        inputMap.put(Input.Keys.S, "Move Down");
        inputMap.put(Input.Keys.A, "Move Left");
        inputMap.put(Input.Keys.D, "Move Right");
        
        // up down left right
        inputMap.put(Input.Keys.UP, "Move Up");
        inputMap.put(Input.Keys.DOWN, "Move Down");
        inputMap.put(Input.Keys.LEFT, "Move Left");
        inputMap.put(Input.Keys.RIGHT, "Move Right");

        // Mouse Buttons
        inputMap.put(Input.Buttons.LEFT, "Left Click");
        inputMap.put(Input.Buttons.RIGHT, "Right Click");
    }

    public String getMappedAction(int key) {
        return inputMap.getOrDefault(key, "Unknown Action");
    }

    // for keyboard
    public boolean isKeyPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }

    // for mouse
    public boolean isButtonPressed(int button) {
        return Gdx.input.isButtonPressed(button);
    }
}
