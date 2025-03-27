package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Mouse extends InputAdapter {
    private InputManager ioManager;
    private Speaker speaker;

    // ✅ Unified constructor for both sound and scene management
    public Mouse(InputManager ioManager, Speaker speaker, SceneManager sceneManager) {
        this.ioManager = ioManager;
        this.speaker = speaker;
    }

    public void checkMouse() {
        int[] buttons = {Input.Buttons.LEFT, Input.Buttons.RIGHT};

        for (int button : buttons) {
            if (Gdx.input.isButtonJustPressed(button)) { 
                System.out.println("Mouse Clicked: " + ioManager.getMappedAction(button));

                if (button == Input.Buttons.LEFT) {
                    speaker.stopSound("click");

                    if (speaker.isMusicPlaying()) {
                        System.out.println("Stopping music...");
                        speaker.stopMusic();
                    } else {
                        System.out.println("Starting new music instance...");
                        speaker.playMusic("sounds/sample.mp3");
                    }
                }
            }
        }
    }
    
    public void setIoManager(InputManager ioManager) {
        this.ioManager = ioManager;
    }

}