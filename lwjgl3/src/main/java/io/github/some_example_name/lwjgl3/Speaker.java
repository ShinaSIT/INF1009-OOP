package io.github.some_example_name.lwjgl3;

import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class Speaker {
    private Map<String, Sound> soundEffect;  // Holds all loaded sound effects
    private Music backgroundMusic;  // Background music
    private float masterVolume;  // Volume control

    public Speaker() {
        soundEffect = new HashMap<>();
        masterVolume = 1.0f; // Default volume
    }

    // Load a sound into the speaker
    public void loadSound(String name, String file) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(file));
        soundEffect.put(name, sound);
    }

    // Play a sound by name
    public void playSound(String name) {
        if (soundEffect.containsKey(name)) {
            soundEffect.get(name).play(masterVolume);
        }
    }

    // Stop a sound by name
    public void stopSound(String name) {
        if (soundEffect.containsKey(name)) {
            soundEffect.get(name).stop();
        }
    }

    // Set master volume
    public void setVolume(float volume) {
        masterVolume = volume;
    }

    // Play background music (already fixed in previous responses)
    public void playMusic(String file) {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(file));
        backgroundMusic.setVolume(masterVolume);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }

    // Stop the background music
    public void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    // Pause the background music
    public void pauseMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.pause();
        }
    }
}
