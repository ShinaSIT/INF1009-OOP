	package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public abstract class MenuScene extends Scene {
    private Stage stage;
    private Skin skin;
    private TextButton startButton;
    private TextButton settingsButton;
    private TextButton exitButton;
    
    public MenuScene(SceneManager sceneManager) {
        super(sceneManager);
    }
    
    @Override
    public void resize(int width, int height) {
        System.out.println("Resizing menu scene to width: " + width + ", height: " + height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        System.out.println("Rendering menu scene...");
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    
    @Override
    public void render(SpriteBatch batch) {
//        System.out.println("Rendering menu scene with SpriteBatch...");
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
    
    @Override
    public void create() { 
        System.out.println("Creating menu scene...");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Make sure to include a valid skin file

        startButton = new TextButton("Start Game", skin);
        settingsButton = new TextButton("Settings", skin);
        exitButton = new TextButton("Exit", skin);
        
        startButton.setPosition(100, 300);
        settingsButton.setPosition(100, 200);
        exitButton.setPosition(100, 100);
        
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Start Game button clicked");
                sceneManager.transitionTo("GameScene");
            }
        });
        
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Start Game button clicked");
                
                if (sceneManager.getScene("GameScene") == null) {
                    sceneManager.addScene("GameScene", new DefaultScene(sceneManager));  // Create the game scene if it doesn't exist
                }
                
                sceneManager.transitionTo("GameScene");
            }
        });

        
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Exit button clicked");
                Gdx.app.exit();
            }
        });
        
        stage.addActor(startButton);
        stage.addActor(settingsButton);
        stage.addActor(exitButton);
    }
    
    @Override
    public void dispose() {
        System.out.println("Disposing menu scene resources...");
        stage.dispose();
        skin.dispose();
    }
    
    @Override
    public void pause() {
        System.out.println("Pausing menu scene...");
    }
    
    @Override
    public void resume() {
        System.out.println("Resuming menu scene...");
    }
    
    @Override
    public void update(float deltaTime) {
        System.out.println("Updating menu scene with delta time: " + deltaTime);
    }
}
