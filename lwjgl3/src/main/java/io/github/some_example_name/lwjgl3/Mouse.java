package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
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
            if (Gdx.input.isButtonJustPressed(button)) { 
                System.out.println("Mouse Clicked: " + ioManager.getMappedAction(button));

                if (button == Input.Buttons.LEFT) {
                    // Stop click sound
                    speaker.stopSound("click");

                    // Toggle Background Music for Stop/Restart
                    if (speaker.isMusicPlaying()) {
                        System.out.println("Stopping music...");
                        speaker.stopMusic();  // Stop completely instead of pausing
                    } else {
                        System.out.println("Starting new music instance...");
                        speaker.playMusic("sounds/sample.mp3");  // Restart from beginning
                    }
                }
            }
        }
    }

}
