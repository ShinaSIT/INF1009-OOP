package io.github.some_example_name.lwjgl3;

public interface SceneController {
    Scene getCurrentScene();
    void transitionTo(String sceneName);
}
