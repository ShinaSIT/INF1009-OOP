package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Input;

public class Mouse {
    private InputOutputManager ioManager;
    private Speaker speaker;  // Will be initialized via constructor

    public Mouse(InputOutputManager ioManager, Speaker speaker) {
        this.ioManager = ioManager;
        this.speaker = speaker;
    }

    public void checkMouse() {
        int[] buttons = {Input.Buttons.LEFT, Input.Buttons.RIGHT};

        for (int button : buttons) {
            if (ioManager.isButtonPressed(button)) {
                System.out.println("Mouse Clicked: " + ioManager.getMappedAction(button));
                if (button == Input.Buttons.LEFT) {
                    speaker.stopSound("click");  // Stop sound when left-click is pressed
                }
            }
        }
    }
}
