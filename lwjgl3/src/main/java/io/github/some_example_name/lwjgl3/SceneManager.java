package io.github.some_example_name.lwjgl3;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Scene> getScenes() {
        System.out.print("🧐 getScenes() called. Currently stored scenes: ");
        for (String key : scenes.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();
        return scenes;
    }


    public void addScene(String sceneName, Scene scene) {
        System.out.println("✅ SceneManager.addScene() called with: " + sceneName);
        System.out.println("🧐 SceneManager instance inside addScene: " + this);

        scenes.put(sceneName, scene);

        // ✅ Print all stored scenes after adding
        System.out.print("📌 Stored Scenes after adding: ");
        for (String key : scenes.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();

        if (scenes.containsKey(sceneName)) {
            System.out.println("✅ Scene '" + sceneName + "' successfully added!");
        } else {
            System.out.println("❌ ERROR: Scene '" + sceneName + "' was NOT stored!");
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
            currentScene = scenes.get(sceneName);
            System.out.println("Transitioned to scene: " + sceneName); // Added line
            if (currentScene != null){
                currentScene.create();
            }
        } else {
            System.err.println("Scene not found: " + sceneName);
        }
    }
    
    public Scene getScene(String sceneName) {
        return scenes.get(sceneName);
    }

    public Scene getCurrentScene() { // ✅ Ensure scene is always initialized
        return currentScene;
    }

    public void render(SpriteBatch batch) {
        //System.out.println("SceneManager render() called");
        if (currentScene != null) {
            //System.out.println("Current scene is: " + currentScene.getClass().getSimpleName()); // Added line
            currentScene.render(batch);
        }
    }
}