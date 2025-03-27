package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

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
            font = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt")); // Use your font path
        } catch (Exception e) {
            font = new BitmapFont();
            System.out.println("Using default font due to error: " + e.getMessage());
        }
        font.setColor(Color.WHITE);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton gameOverButton = new TextButton("GAME OVER", buttonStyle);
        gameOverButton.setPosition(Gdx.graphics.getWidth() / 2f - gameOverButton.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - gameOverButton.getHeight() / 2f);

        gameOverButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the application
            }
        });

        stage.addActor(gameOverButton);
    }

    @Override
    public void render(SpriteBatch batch) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        // Optionally, render "GAME OVER" in the center with a bigger font.
        font.getData().setScale(20);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, "GAME OVER");
        batch.begin();
        font.draw(batch, "GAME OVER", Gdx.graphics.getWidth() / 2f - layout.width / 2f, Gdx.graphics.getHeight() / 2f + layout.height / 2f);
        batch.end();

        font.getData().setScale(20); // Reset the scale for the button
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

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void update(float deltaTime) {}

    @Override
    public void update() {}

    @Override
    public void render() {}
}