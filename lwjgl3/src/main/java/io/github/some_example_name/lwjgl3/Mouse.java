package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Mouse {
    private Point position = new Point(0, 0);
    private Map<Integer, Boolean> buttonState = new HashMap<>();
    private InputOutputManager ioManager;  // Reference to I/O Manager

    public Mouse(InputOutputManager ioManager) {
        this.ioManager = ioManager;
        Gdx.input.setInputProcessor(new MouseInputProcessor());
    }

    public void captureInput() {
        for (Map.Entry<Integer, Boolean> entry : buttonState.entrySet()) {
            if (entry.getValue()) {
                // Fetch action from InputOutputManager
                String action = ioManager.getMappedAction(entry.getKey());
                System.out.println("Mouse Clicked: " + entry.getKey() + " -> Action: " + action);
            }
        }
    }

    private class MouseInputProcessor implements com.badlogic.gdx.InputProcessor {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            buttonState.put(button, true);
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            buttonState.put(button, false);
            return false;
        }

        // Other methods not needed for mouse, so just returning false
        @Override public boolean keyDown(int keycode) { return false; }
        @Override public boolean keyUp(int keycode) { return false; }
        @Override public boolean keyTyped(char character) { return false; }
        @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
        @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
        @Override public boolean scrolled(float amountX, float amountY) { return false; }

		@Override
		public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
			return false;
		}
    }
}
