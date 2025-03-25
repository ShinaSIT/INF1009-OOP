package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenuScene extends MenuScene {
    private GameMaster gameMaster;
    private BitmapFont titleFont;
    private BitmapFont buttonFont;
    private Texture background;

    public MainMenuScene(SceneManager sceneManager, GameMaster gameMaster) {
        super(sceneManager, gameMaster);
        this.gameMaster = gameMaster;
    }

    @Override
    public void create() {
        super.create();
        System.out.println("✅ Main Menu Scene Created");

        // Load pixel-style font from assets/fonts/
        titleFont = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt"));
        titleFont.setColor(1, 1, 1, 1);  // white with full alpha
        titleFont.getData().setScale(3f);  // Bigger for title

        // Load smaller font for button
        buttonFont = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt"));
        buttonFont.getData().setScale(1.5f);  // Smaller for "Start Game"

        // Apply smaller font to button style
        TextButtonStyle style = new TextButtonStyle();
        style.font = buttonFont;
        applyButtonStyle(style);

        // Optional: Load background image
        background = new Texture(Gdx.files.internal("menu_background.png"));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = getBatch();

        if (batch == null) {
            System.out.println("❌ SpriteBatch is NULL!");
            return;
        }

        batch.begin();

        if (background != null) {
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            System.out.println("🖼️ Drawing background!");
        } else {
            System.out.println("❌ Background texture is NULL!");
        }

        titleFont.draw(batch, "Munch Quest", 100, 400);
        batch.end();

        // draw UI last
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void onStartGameButtonPressed() {
        System.out.println("🎮 Start Game Button Clicked in MainMenuScene!");
        if (gameMaster != null) {
            gameMaster.startGame();
        } else {
            System.out.println("❌ ERROR: gameMaster is NULL!");
        }
    }

    @Override public void update() {}
    @Override public void resume() {}
    @Override public void update(float deltaTime) {}
    @Override public void pause() {}
}