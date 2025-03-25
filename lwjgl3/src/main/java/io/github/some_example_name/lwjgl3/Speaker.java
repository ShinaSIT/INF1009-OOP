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
    	 masterVolume = Math.max(0f, Math.min(volume, 1f)); // Ensure volume stays between 0 and 1

    	    if (backgroundMusic != null) {
    	        backgroundMusic.setVolume(masterVolume * 0.5f); // Adjust the background music volume (50% lower)
    	    }
    }

    // Play background music (already fixed in previous responses)
    public void playMusic(String file) {
        if (backgroundMusic == null) {  // Ensure we only create a new instance when needed
            System.out.println("Creating new music instance: " + file);
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.mp3"));
            backgroundMusic.setVolume(masterVolume  * 0.1f);
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
            System.out.println("Music started: " + file);
            return;
        }

        if (!backgroundMusic.isPlaying()) { // If music exists but is paused, resume instead of restarting
            System.out.println("Resuming music...");
            backgroundMusic.play();
        } else {
            System.out.println("Music already playing, no need to restart.");
        }
    }


    // Stop the background music
    public void stopMusic() {
        if (backgroundMusic != null) {
        	System.out.println("Stopping music...");
            backgroundMusic.stop();
            backgroundMusic.dispose();
            backgroundMusic = null;
            System.out.println("Music instance disposed.");
        } else {
            System.out.println("No music instance to stop.");
        }
    }
    public boolean isMusicPlaying() {
        return backgroundMusic != null && backgroundMusic.isPlaying();
    }

    // Pause the background music
    public void pauseMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.pause(); // Pause music so it can resume later
        }
    }

   
}