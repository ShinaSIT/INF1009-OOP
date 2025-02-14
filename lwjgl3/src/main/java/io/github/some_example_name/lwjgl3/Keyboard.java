package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import java.util.HashSet;
import java.util.Set;

public class Keyboard {
    private Set<Integer> pressedKeys = new HashSet<>();
    private InputOutputManager ioManager;

    public Keyboard(InputOutputManager ioManager) {
        this.ioManager = ioManager;
        Gdx.input.setInputProcessor(new KeyboardInputProcessor());
    }

    // Process the action for a single key
    private void captureInput(int keycode) {
        String action = ioManager.getMappedAction(keycode);
        System.out.println("Key Pressed: " + keycode + " -> Action: " + action);
    }

    private class KeyboardInputProcessor implements com.badlogic.gdx.InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            pressedKeys.add(keycode);
            captureInput(keycode); // Immediately trigger action
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            pressedKeys.remove(keycode);
            return false;
        }

        // Other methods not needed for keyboard, so just returning false
        @Override public boolean keyTyped(char character) { return false; }
        @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
        @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
        @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
        @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
        @Override public boolean scrolled(float amountX, float amountY) { return false; }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            return false;
        }
    }
}
