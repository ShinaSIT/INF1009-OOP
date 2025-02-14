package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Input;

public class Mouse {
    private InputOutputManager ioManager;

    public Mouse(InputOutputManager ioManager) {
        this.ioManager = ioManager;
    }

    public void checkMouse() {
        int[] buttons = {Input.Buttons.LEFT, Input.Buttons.RIGHT};

        for (int button : buttons) {
            if (ioManager.isButtonPressed(button)) {
                System.out.println("Mouse Clicked: " + ioManager.getMappedAction(button));
            }
        }
    }
}