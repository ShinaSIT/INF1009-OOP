package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InstructionsScene extends Scene {

    private GameMaster gameMaster;
    private BitmapFont font;
    private Stage stage;

    public InstructionsScene(SceneManager sceneManager, GameMaster gameMaster) {
        super(sceneManager);
        this.gameMaster = gameMaster;
    }

    @Override
    public void create() {
        System.out.println("✅ Instructions Scene Created");

        try {
            font = new BitmapFont(Gdx.files.internal("path/to/your/font.fnt")); // Use your font path
        } catch (Exception e) {
            font = new BitmapFont();
            System.out.println("Using default font due to error: " + e.getMessage());
        }
        font.setColor(Color.WHITE);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton continueButton = new TextButton("Continue", buttonStyle);
        continueButton.setPosition(Gdx.graphics.getWidth() / 2f - continueButton.getWidth() / 2f, 50);

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameMaster != null) {
                    gameMaster.startGame();
                } else {
                    System.out.println("❌ ERROR: gameMaster is NULL!");
                }
            }
        });

        stage.addActor(continueButton);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (gameMaster == null) return;   

        renderUI(batch);
        renderInstructions(batch);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void renderUI(SpriteBatch batch) {
        font.getData().setScale(2);
        font.draw(batch, "Instructions", 10, Gdx.graphics.getHeight() - 20);
        font.getData().setScale(1);
    }

    private void renderInstructions(SpriteBatch batch) {
        float startY = Gdx.graphics.getHeight() - 80;
        float lineSpacing = 30;

        font.draw(batch, "How to Play", 230, startY);
        startY -= lineSpacing;
        font.draw(batch, "• Use arrow keys to move your character", 120, startY);
        startY -= lineSpacing;
        font.draw(batch, "• Collect healthy foods like fruits and vegetables", 120, startY);
        startY -= lineSpacing;
        startY -= lineSpacing;
        font.draw(batch, "• Avoid junk food ghosts that will reduce your health", 120, startY);
        startY -= lineSpacing;
        font.draw(batch, "• Special power foods let you chase ghosts temporarily", 120, startY);
        startY -= lineSpacing;
        font.draw(batch, "• Learn about nutrition from food facts", 120, startY);
        startY -= lineSpacing;
        font.draw(batch, "• Complete each level by collecting all healthy foods", 120, startY);
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("✅ Resizing Instructions Scene: " + width + "x" + height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        System.out.println("✅ Disposing Instructions Scene...");
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