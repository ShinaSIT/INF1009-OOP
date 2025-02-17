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
    private Timer timer;
    private boolean hasMoved = false;
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
        this.timer = new Timer(); 
        this.inputMap = new HashMap<>();
        
        mapDefaultInputs(); // ✅ Initialize the input map
        initializeSounds(); // ✅ Load sounds when initializing
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
    
    private void initializeSounds() {
        speaker.loadSound("click", "sounds/sample.mp3");
        speaker.loadSound("sound", "sounds/sample2.mp3");
        speaker.playMusic("sounds/sample.mp3"); // ✅ Start background music
    }
    
    public void startTimer() {
        if (timer != null) {
           timer.start();
            System.out.println("✅ Timer started successfully!");
        } else {
            System.err.println("❌ Timer is null, could not start!");
        }
    }


    public void stopTimer() {
        timer.stop();
    }
    public void handleInput() {
        if (board == null) {
            System.err.println("Error: Board is not initialized!");
            return;
        }

        float step = board.getTileSize(); // ✅ Safe to call because we checked board is not null
        boolean moved = false;
        
        // Move Up
        if ((Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) && !wPressed) {
            System.out.println("Moving Up");
            movementManager.applyMovement((MoveableObjects) player, 0, step);
            speaker.playSound("sound");
            wPressed = true;
            moved=true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.UP)) {
            wPressed = false;
        }

        // Move Down
        if ((Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) && !sPressed) {
            System.out.println("Moving Down");
            movementManager.applyMovement((MoveableObjects) player, 0, -step);
            speaker.playSound("sound");
            sPressed = true;
            moved=true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            sPressed = false;
        }

        // Move Left
        if ((Gdx.input.isKeyJustPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) && !aPressed) {
            System.out.println("Moving Left");
            movementManager.applyMovement((MoveableObjects) player, -step, 0);
            speaker.playSound("sound");
            aPressed = true;
            moved=true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            aPressed = false;
        }

        // Move Right
        if ((Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) && !dPressed) {
            System.out.println("Moving Right");
            movementManager.applyMovement((MoveableObjects) player, step, 0);
            speaker.playSound("sound");
            dPressed = true;
            moved=true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dPressed = false;
        }

        // Spacebar: Pause/Resume Music
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (speaker.isMusicPlaying()) {
                speaker.pauseMusic();
            } else {
                speaker.playMusic("sound/sample.mp3");
            }
        }
        if (!hasMoved && moved) {
            startTimer();
            hasMoved = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            stopTimer();
            Gdx.app.exit(); // Exit the application
        }
        
     // ✅ Call mouse click detection
        if (mouse != null) {
            mouse.checkMouse();  
        }
    }
}
