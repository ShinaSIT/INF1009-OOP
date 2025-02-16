package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

public class InputOutputManager {
    private Map<Integer, String> inputMap;
    private MovementManager movementManager;
    private GameObjects player;
    private Speaker speaker;
    private Board board;
    private Mouse mouse;

    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;

    public InputOutputManager(MovementManager movementManager, GameObjects player, Speaker speaker, Board board, Mouse mouse) {
        this.movementManager = movementManager;
        this.player = player;
        this.speaker = speaker;
        this.board = board;
        this.mouse = mouse;
        this.inputMap = new HashMap<>();
        
        mapDefaultInputs(); // ✅ Initialize the input map
    }

    private void mapDefaultInputs() {
        inputMap.put(Input.Keys.W, "Move Up");
        inputMap.put(Input.Keys.S, "Move Down");
        inputMap.put(Input.Keys.A, "Move Left");
        inputMap.put(Input.Keys.D, "Move Right");

        inputMap.put(Input.Keys.UP, "Move Up");
        inputMap.put(Input.Keys.DOWN, "Move Down");
        inputMap.put(Input.Keys.LEFT, "Move Left");
        inputMap.put(Input.Keys.RIGHT, "Move Right");

        inputMap.put(Input.Buttons.LEFT, "Left Click");
        inputMap.put(Input.Buttons.RIGHT, "Right Click");
    }

    public String getMappedAction(int key) {
        return inputMap.getOrDefault(key, "Unknown Action");
    }

    public boolean isKeyPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }

    public boolean isButtonPressed(int button) {
        return Gdx.input.isButtonPressed(button);
    }

    public void handleInput() {
        if (board == null) {
            System.err.println("Error: Board is not initialized!");
            return;
        }

        float step = board.getTileSize(); // ✅ Safe to call because we checked board is not null

        // Move Up
        if ((Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) && !wPressed) {
            System.out.println("Moving Up");
            movementManager.applyMovement((MoveableObjects) player, 0, step);
            speaker.playSound("move");
            wPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.UP)) {
            wPressed = false;
        }

        // Move Down
        if ((Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) && !sPressed) {
            System.out.println("Moving Down");
            movementManager.applyMovement((MoveableObjects) player, 0, -step);
            speaker.playSound("move");
            sPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            sPressed = false;
        }

        // Move Left
        if ((Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) && !aPressed) {
            System.out.println("Moving Left");
            movementManager.applyMovement((MoveableObjects) player, -step, 0);
            speaker.playSound("move");
            aPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            aPressed = false;
        }

        // Move Right
        if ((Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) && !dPressed) {
            System.out.println("Moving Right");
            movementManager.applyMovement((MoveableObjects) player, step, 0);
            speaker.playSound("move");
            dPressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dPressed = false;
        }

        // Spacebar: Pause/Resume Music
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (speaker.isMusicPlaying()) {
                speaker.pauseMusic();
            } else {
                speaker.playMusic("sounds/sample.mp3");
            }
        }
        
     // ✅ Call mouse click detection
        if (mouse != null) {
            mouse.checkMouse();  
        }
    }
}
