package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class OutputManager extends OutputClass {

    private boolean hasMoved = false;
    private boolean hasTimerStarted = false;

    public OutputManager(Speaker speaker) {
        super(speaker);
    }

    @Override
    protected void initializeOutput() {
        speaker.loadSound("click", "sounds/sample.mp3");
        speaker.loadSound("sound", "sounds/sample2.mp3");
        speaker.loadSound("block", "sounds/sample3.mp3");
        speaker.playMusic("sounds/sample.mp3");
    }

    @Override
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

    public boolean isHasTimerStarted() {
        return hasTimerStarted;
    }

    public void setHasTimerStarted(boolean hasTimerStarted) {
        this.hasTimerStarted = hasTimerStarted;
    }
}