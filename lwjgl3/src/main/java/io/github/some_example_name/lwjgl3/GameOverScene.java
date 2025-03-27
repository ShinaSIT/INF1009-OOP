package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GameOverScene extends Scene {

    private BitmapFont font;
    private Stage stage;

    public GameOverScene(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    public void create() {
        System.out.println("✅ Game Over Scene Created");

        try {
            font = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt"));
        } catch (Exception e) {
            font = new BitmapFont();
            System.out.println("Using default font due to error: " + e.getMessage());
        }
        font.setColor(Color.WHITE);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton menuButton = new TextButton("Return to Menu", buttonStyle);
        menuButton.setPosition(Gdx.graphics.getWidth() / 2f - menuButton.getWidth() / 2f, 
                               Gdx.graphics.getHeight() / 2f - menuButton.getHeight() / 2f - 50);

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.transitionTo("MenuScene");  // Ensure your main menu is registered with this name
            }
        });

        TextButton exitButton = new TextButton("Exit", buttonStyle);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2f - exitButton.getWidth() / 2f, 
                               Gdx.graphics.getHeight() / 2f - exitButton.getHeight() / 2f - 120);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(menuButton);
        stage.addActor(exitButton);
    }

    @Override
    public void render(SpriteBatch batch) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        // SceneManager likely calls batch.begin(), so you should not call it here again
        font.getData().setScale(3);
        GlyphLayout layout = new GlyphLayout(font, "GAME OVER");
        
        // Draw using the batch assuming batch.begin() was already called by SceneManager
        font.draw(batch, "GAME OVER", 
                  Gdx.graphics.getWidth() / 2f - layout.width / 2f,
                  Gdx.graphics.getHeight() / 2f + 100);
        
        font.getData().setScale(1);
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("✅ Resizing Game Over Scene: " + width + "x" + height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        System.out.println("✅ Disposing Game Over Scene...");
        font.dispose();
        stage.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void update(float deltaTime) {}
    @Override public void update() {}
    @Override public void render() {}
}