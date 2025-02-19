package io.github.some_example_name.lwjgl3;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager { //scenemanager class
    private Scene currentScene;
    private Scene nextScene;
    private Map<String, Scene> scenes; //Dictionary for sceneData
    private boolean isTransitioning;

    public SceneManager() {
        this.scenes = new HashMap<>();
        this.isTransitioning = false;
    }

    public void loadScene(String sceneName) {
        if (scenes.containsKey(sceneName)) {
            nextScene = scenes.get(sceneName);
            isTransitioning = true;
        }
    }

    public void unloadScene(String sceneName) {
        scenes.remove(sceneName);
    }

    public void addScene(String sceneName, Scene scene) {
        scenes.put(sceneName, scene);
        if (currentScene == null) { // ✅ Ensure the first scene is MenuScene
            currentScene = scenes.get("MenuScene");
            if (currentScene != null) {
                currentScene.create(); // ✅ Initialize menu scene first
            }
        }
    }

    public void removeScene(String sceneName) {
        scenes.remove(sceneName);
    }

    public void render() {
        if (currentScene != null) {
            currentScene.render();
        }
    }

    public void update() {
        if (isTransitioning && nextScene != null) {
            currentScene.dispose(); // ✅ Dispose current scene before transitioning
            currentScene = nextScene;
            nextScene = null;
            isTransitioning = false;
            currentScene.create(); // ✅ Ensure scene setup when transitioning
        }
        if (currentScene != null) {
            currentScene.update();
        }
    }

    public void transitionTo(String sceneName) {
        if (scenes.containsKey(sceneName)) {
            if (currentScene != null) {
                currentScene.dispose();
            }
            currentScene = scenes.get(sceneName);
            currentScene.create();

            if (sceneName.equals("GameScene")) {
                ((GameMaster) Gdx.app.getApplicationListener()).initializeGame(); // ✅ Lazy load game objects
            }
        }
    }


    public Scene getScene(String sceneName) {
        return scenes.get(sceneName);
    }

    public Scene getCurrentScene() { // ✅ Ensure scene is always initialized
        return currentScene;
    }

    public void render(SpriteBatch batch) {
        if (currentScene != null) {
            currentScene.render(batch);
        }
    }
}