package io.github.some_example_name.lwjgl3;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager { 
    private Scene currentScene;
    private Scene nextScene;
    private Map<String, Scene> scenes; 
    private boolean isTransitioning;

    private GameMaster gameMaster;
    private InputManager inputManager;
    private Speaker speaker;

    public SceneManager(GameMaster gameMaster, InputManager inputManager, Speaker speaker) {
        this.scenes = new HashMap<>();
        this.isTransitioning = false;
        this.gameMaster = gameMaster;
        this.inputManager = inputManager;
        this.speaker = speaker;
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
        for (String key : scenes.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();
        return scenes;
    }
    
    public void switchToGameScene() {
        currentScene = new GameScene(this, gameMaster, inputManager, speaker);
        currentScene.create();
    }

    public void addScene(String sceneName, Scene scene) {
        scenes.put(sceneName, scene);

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