package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Mouse extends InputAdapter {
    private InputOutputManager ioManager;
    private Speaker speaker;
    private SceneManager sceneManager;

    // ✅ Unified constructor for both sound and scene management
    public Mouse(InputOutputManager ioManager, Speaker speaker, SceneManager sceneManager) {
        this.ioManager = ioManager;
        this.speaker = speaker;
        this.sceneManager = sceneManager;
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
    
    public void setIoManager(InputOutputManager ioManager) {
        this.ioManager = ioManager;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
            Vector2 worldCoords = new Vector2(screenX, Gdx.graphics.getHeight() - screenY); 

            if (isStartButtonClicked(worldCoords)) {
                if (sceneManager != null) {
                    sceneManager.transitionTo("gameScene"); // ✅ Transition scene properly
                } else {
                    System.err.println("Error: SceneManager is NULL, cannot transition scene!");
                }
            }
        }
        return false;
    }

    private boolean isStartButtonClicked(Vector2 clickPos) {
        float buttonX = 300, buttonY = 200, buttonWidth = 200, buttonHeight = 80;

        return (clickPos.x >= buttonX && clickPos.x <= buttonX + buttonWidth &&
                clickPos.y >= buttonY && clickPos.y <= buttonY + buttonHeight);
    }
}