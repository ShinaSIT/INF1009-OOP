package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class MenuScene extends Scene {
    private GameMaster gameMaster;
    protected Stage stage;
    private Texture backgroundTexture;
    private TextButton startButton;
    private Table table;
    private Label welcomeLabel; 
    private BitmapFont buttonFont;
    private BitmapFont titleFont;

    public MenuScene(SceneManager sceneManager, GameMaster gameMaster) {
        super(sceneManager);
        this.gameMaster = gameMaster;
    }

    @Override
    public void create() {
        System.out.println("✅ Creating Menu Scene...");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        // Load the background texture
        backgroundTexture = new Texture(Gdx.files.internal("menu_background.png"));

        // Create Welcome Label using the pixel-style font
        titleFont = new BitmapFont(Gdx.files.internal("fonts/title.fnt"));
        titleFont.getData().setScale(2.5f);
        Label.LabelStyle welcomeStyle = new Label.LabelStyle();
        welcomeStyle.font = titleFont;
        welcomeLabel = new Label("Munch Quest", welcomeStyle);      
        

        // Create Start Button with matching font style
        buttonFont = new BitmapFont(Gdx.files.internal("fonts/start.fnt"));
        buttonFont.getData().setScale(1.5f);
        
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = buttonFont;

        startButton = new TextButton(">Start Game<", buttonStyle);

        // ✅ Create a Table
        table = new Table();
        table.setFillParent(true); // ✅ Make the table fill the screen
        table.center();
        stage.addActor(table);

        // ✅ Add the Welcome Label to the table
        table.add(welcomeLabel).padBottom(40).row(); // Add padding below the welcome label

        // ✅ Add the Start button to the table (automatically aligns to center)
        table.add(startButton).width(300).height(100).padBottom(20).row(); // Make the button taller

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
        System.out.println("Stage Elements: " + stage.getActors().size);

    }

    @Override
    public void render(SpriteBatch batch) {
        if (Gdx.input.getInputProcessor() != stage) {
            Gdx.input.setInputProcessor(stage);
        }

        if (!batch.isDrawing()) { // ✅ Prevent duplicate batch.begin()
            batch.begin();
        }

        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (batch.isDrawing()) { // ✅ Ensure batch is ended before rendering UI
            batch.end();
        }

        // ✅ Render UI elements after ending batch
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }



    @Override
    public void resize(int width, int height) {
        System.out.println("✅ Resizing Menu Scene: " + width + "x" + height);
        stage.getViewport().update(width, height, true);
        stage.getViewport().apply(true);
    }

    public void applyButtonStyle(TextButton.TextButtonStyle style) {
        if (startButton != null) {
            startButton.setStyle(style);
        }
    }

    public SpriteBatch getBatch() {
        return gameMaster != null ? gameMaster.getBatch() : null;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        System.out.println("✅ Disposing Menu Scene...");
        stage.dispose();
        backgroundTexture.dispose();  // ✅ Dispose of the background texture
        buttonFont.dispose();
    }
}