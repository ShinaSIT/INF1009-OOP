package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class OutputManager {

    private Speaker speaker;
    private Timer timer;
    private boolean hasMoved = false;

    public OutputManager(Speaker speaker) {
        this.speaker = speaker;
        this.timer = new Timer();
        initializeSounds();
    }

    private void initializeSounds() {
        speaker.loadSound("click", "sounds/sample.mp3");
        speaker.loadSound("sound", "sounds/sample2.mp3");
        speaker.loadSound("block", "sounds/sample3.mp3");
        speaker.playMusic("sounds/sample.mp3"); // ✅ Start background music
    }

    public void startTimer() {
        if (timer != null) {
            timer.start();
            System.out.println("Timer started successfully!");
        } else {
            System.err.println("Timer is null, could not start!");
        }
    }

    public void stopTimer() {
        timer.stop();
    }

    public void handleOutput() {
        // Spacebar: Pause/Resume Music
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (speaker.isMusicPlaying()) {
                speaker.pauseMusic();
            } else {
                speaker.playMusic("sound/sample.mp3");
            }
        }
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}