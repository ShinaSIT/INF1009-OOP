package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public abstract class OutputClass {

    protected Speaker speaker;
    protected Timer timer;

    public OutputClass(Speaker speaker) {
        this.speaker = speaker;
        this.timer = new Timer();
        initializeOutput();
    }

    protected abstract void initializeOutput();

    public abstract void startTimer();

    public abstract void stopTimer();

    public abstract void handleOutput();
}