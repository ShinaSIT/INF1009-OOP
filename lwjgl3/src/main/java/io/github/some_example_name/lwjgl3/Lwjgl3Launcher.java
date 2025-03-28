package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        // Ensure macOS compatibility and handle re-launch if required
        if (StartupHelper.startNewJvmIfRequired()) return;
        
        // Start the application
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new GameMaster(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Munch Quest");

        // Enable VSync for smoother rendering
        configuration.useVsync(true);
        
        // Set refresh rate to prevent tearing
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        
        // Set the window size (width x height)
        configuration.setWindowedMode(2000, 1280);
        
        // Set the window icons (make sure the paths are correct)
        configuration.setWindowIcon("Watermelon.png");

        return configuration;
    }
}
