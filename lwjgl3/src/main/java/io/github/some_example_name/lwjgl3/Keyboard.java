package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Input;

public class Keyboard {
    private InputOutputManager ioManager;

    public Keyboard(InputOutputManager ioManager) {
        this.ioManager = ioManager;
    }

    public void checkKeys() {
        int[] keys = {Input.Keys.W, Input.Keys.A, Input.Keys.S, Input.Keys.D,
                      Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT};

        for (int key : keys) {
            if (ioManager.isKeyPressed(key)) {
                System.out.println("Key Pressed: " + ioManager.getMappedAction(key));
            }
        }
    }
}
