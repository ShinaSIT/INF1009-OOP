package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
        System.out.println("✅ Instructions Scene Created");{
        	font = new BitmapFont(Gdx.files.internal("fonts/start.fnt"));
        }
        font.setColor(Color.WHITE);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        TextButton continueButton = new TextButton("> Proceed to Game <", buttonStyle);
        continueButton.setPosition(Gdx.graphics.getWidth() / 2f - continueButton.getWidth() / 2f, 150);

        continueButton.addListener(new ClickListener() {
        	 @Override
        	    public void clicked(InputEvent event, float x, float y) {
        	        if (gameMaster != null) {
        	            gameMaster.gameStarted = true;

        	            EntityFactory factory = new EntityFactory(
        	                gameMaster.getBoardManager().getBoard(),
        	                gameMaster.getEntityManager(),
        	                gameMaster.movementManager,
        	                gameMaster.collisionManager,
        	                gameMaster.getBoardManager(),
        	                gameMaster.speaker,
        	                gameMaster.inputManager,
        	                gameMaster.mouse
        	            );

        	            factory.getEntity("player");
        	            factory.getEntity("germ");
        	            factory.getEntity("germ");

        	            gameMaster.sceneManager.transitionTo("GameScene");

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
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, "Instructions");
        font.draw(batch, "Instructions", 
                 Gdx.graphics.getWidth()/2f - layout.width/2, 
                 Gdx.graphics.getHeight() - 50);
        font.getData().setScale(2);
    }

    private void renderInstructions(SpriteBatch batch) {
        float startY = Gdx.graphics.getHeight() - 200; // Start lower to avoid overlap
        float sectionSpacing = 65; // Space between sections
        float lineSpacing = 50; // Space between lines
        float leftMargin = 50; // Left margin for better readability
        float wrapWidth = Gdx.graphics.getWidth() - (leftMargin * 2); // Width limit for text wrapping

        GlyphLayout layout = new GlyphLayout();

        String[][] instructions = {
            {"How to Play", 
             "Use arrow keys or 'W', 'A', 'S', 'D' to move", 
             "Gather healthy foods like fruits and vegetables"},
            {"Rules", 
             "Avoid junk food & ghosts - they reduce health", 
             "Collect ALL healthy foods to exit the maze"},
            {"Game Purpose", 
             "Learn about nutrition from food facts"}
        };

        for (String[] section : instructions) {
            // Section title in bold and slightly larger
            font.getData().setScale(1.2f); // Reduced size
            layout.setText(font, section[0]);
            font.draw(batch, section[0], leftMargin, startY);
            startY -= sectionSpacing; // Extra space before details

            // Content with word wrapping
            font.getData().setScale(1f); // Smaller size for content
            for (int i = 1; i < section.length; i++) {
                layout.setText(font, section[i], Color.WHITE, wrapWidth, 1, true);
                font.draw(batch, layout, leftMargin, startY);
                startY -= layout.height + lineSpacing;
            }

            startY -= sectionSpacing; // Extra space before next section
        }
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