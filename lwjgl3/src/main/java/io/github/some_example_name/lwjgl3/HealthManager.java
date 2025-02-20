/*
package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HealthManager {
    private int lives;
    private boolean gameOver;
    private final GameMaster gameMaster;
    private final SceneManager sceneManager;
    private final Stage stage;
    private final Skin skin;
    
    public HealthManager(GameMaster gameMaster, SceneManager sceneManager) {
        this.lives = 3;
        this.gameOver = false;
        this.gameMaster = gameMaster;
        this.sceneManager = sceneManager;
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
    }

    public void reduceLife() {  
        if (gameOver) return;  

        lives--;
        System.out.println("❌ Player lost a life! Remaining: " + lives);

        if (lives <= 0) {
            System.out.println("💀 Game Over!");
            gameOver = true;
            setupGameOverUI();
            Gdx.input.setInputProcessor(stage);
        }
    }

    private void setupGameOverUI() {
        stage.clear();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label gameOverLabel = new Label("GAME OVER!", skin);
        gameOverLabel.setFontScale(4);
        gameOverLabel.setColor(Color.RED);

        TextButton returnButton = new TextButton("Return to Menu", skin);
        returnButton.pad(15);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("✅ BUTTON PRESSED: Return to Menu");
                gameMaster.returnToMenu();
            }
        });

        table.add(gameOverLabel).padBottom(50).row();
        table.add(returnButton).width(Gdx.graphics.getWidth() * 0.3f)
                              .height(Gdx.graphics.getHeight() * 0.1f)
                              .padTop(20).row();
    }

    public void drawLivesDisplay(SpriteBatch batch) {
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
        font.draw(batch, "❤️ Lives: " + lives, 20, Gdx.graphics.getHeight() - 20);
    }

    public void drawGameOverScreen(SpriteBatch batch) {
        if (!gameOver) return;

        if (Gdx.input.getInputProcessor() != stage) {
            Gdx.input.setInputProcessor(stage);
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void reset() {
        lives = 3;
        gameOver = false;
        stage.clear();
        Gdx.input.setInputProcessor(null);
    }

    public void resize(int width, int height) {
        System.out.println("✅ Resizing HealthManager: " + width + "x" + height);
        stage.getViewport().update(width, height, true);
        setupGameOverUI();
    }

    public Stage getStage() {
        return stage;
    }
}
*/
