package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Scene {
    protected SceneManager sceneManager;

    public Scene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    public abstract void update();
    public abstract void resume();
    public abstract void update(float deltaTime);
    public abstract void resize(int width, int height);
    public abstract void render();
    public abstract void render(SpriteBatch batch);
    public abstract void create();
    public abstract void dispose();
    public abstract void pause();
}
