package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class Mouse extends InputAdapter {
    private InputOutputManager ioManager;
    private Speaker speaker;  // Will be initialized via constructor
    private SceneManager sceneManager;
    
  //For start button
    public Mouse(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) { // Left click
            Vector2 worldCoords = new Vector2(screenX, Gdx.graphics.getHeight() - screenY); // Convert to world coordinates
            
            if (isStartButtonClicked(worldCoords)) {
               // sceneManager.setScene("gameScene"); // Change to game scene for the start button
            }
        }
        return false;
    }

    private boolean isStartButtonClicked(Vector2 clickPos) {
        // Example Start Button Bounds (Adjust as per your UI)
        float buttonX = 300, buttonY = 200, buttonWidth = 200, buttonHeight = 80;

        return (clickPos.x >= buttonX && clickPos.x <= buttonX + buttonWidth &&
                clickPos.y >= buttonY && clickPos.y <= buttonY + buttonHeight);
    }
  //For music
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
