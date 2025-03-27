package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public abstract class OutputClass {

    protected Speaker speaker;
    
    public OutputClass(Speaker speaker) {
        this.speaker = speaker;
        initializeOutput();
    }

    protected abstract void initializeOutput();

    public abstract void handleOutput();
}