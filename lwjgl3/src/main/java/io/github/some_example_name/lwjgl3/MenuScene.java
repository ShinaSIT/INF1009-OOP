package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class MenuScene extends Scene {
    
    public MenuScene(SceneManager sceneManager) {
        super(sceneManager);
    }
    
    @Override
    public void resize(int width, int height) {
        // Implement resizing logic here
    }

    @Override
    public void render() {
        // Implement rendering logic here
    }
    
    @Override
    public void render(SpriteBatch batch) {
        // Implement rendering logic using SpriteBatch here
    }
    
    @Override
    public void create() {
        // Implement scene creation logic here
    }
    
    @Override
    public void dispose() {
        // Implement cleanup logic here
    }
    
    @Override
    public void pause() {
        // Implement pause logic here
    }
    
    @Override
    public void resume() {
        // Implement resume logic here
    }
    
    @Override
    public void update(float deltaTime) {
        // Implement update logic here
    }
}
