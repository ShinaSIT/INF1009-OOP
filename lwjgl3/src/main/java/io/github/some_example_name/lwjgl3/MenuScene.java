package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class MenuScene extends Scene {
    private GameMaster gameMaster;
    private Stage stage;
    private Skin skin;
    private TextButton startButton;
    private Table table; // ✅ Layout manager

    public MenuScene(SceneManager sceneManager, GameMaster gameMaster) {  
        super(sceneManager);
        this.gameMaster = gameMaster;
    }

    @Override
    public void create() { 
        System.out.println("✅ Creating Menu Scene...");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json")); // Ensure this file exists!

        // ✅ Create Start Button
        startButton = new TextButton("Start Game", skin);

        // ✅ Create a Table 
        table = new Table();
        table.setFillParent(true);  // ✅ Make the table fill the screen
        table.center();
        stage.addActor(table);

        // ✅ Add the Start button to the table (automatically aligns to center)
        table.add(startButton).width(300).height(80).padBottom(20).row();

        // ✅ Handle Start Game button
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("✅ Start Game Button Clicked!");
                if (MenuScene.this instanceof MainMenuScene) {
                    ((MainMenuScene) MenuScene.this).onStartGameButtonPressed();
                }
            }
        });
    }

    @Override
    public void render(SpriteBatch batch) {
        if (Gdx.input.getInputProcessor() != stage) { 
            Gdx.input.setInputProcessor(stage);
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("✅ Resizing Menu Scene: " + width + "x" + height);
        stage.getViewport().update(width, height, true);
        stage.getViewport().apply(true);
    }

    @Override
    public void dispose() {
        System.out.println("✅ Disposing Menu Scene...");
        stage.dispose();
        skin.dispose();
    }
}
