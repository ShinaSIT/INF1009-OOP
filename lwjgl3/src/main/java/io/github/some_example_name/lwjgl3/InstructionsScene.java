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
            font = new BitmapFont();
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
        font.getData().setScale(3);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, "Instructions");
        font.draw(batch, "Instructions", 
                 Gdx.graphics.getWidth()/2f - layout.width/2, 
                 Gdx.graphics.getHeight() - 50);
        font.getData().setScale(2);
    }

    private void renderInstructions(SpriteBatch batch) {
        float startY = Gdx.graphics.getHeight() - 150;
        float lineSpacing = 40;
        GlyphLayout layout = new GlyphLayout();
        
        String[] instructions = {
            "How to Play",
            "• Use arrow keys to move your character",
            "• Collect healthy foods like fruits and vegetables",
            "",
            "• Avoid junk food ghosts that will reduce your health",
            "• Special power foods let you chase ghosts temporarily",
            "• Learn about nutrition from food facts",
            "• Complete each level by collecting all healthy foods"
        };

        for (String line : instructions) {
            if (!line.isEmpty()) {
                layout.setText(font, line);
                font.draw(batch, line, 
                         Gdx.graphics.getWidth()/2f - layout.width/2, 
                         startY);
            }
            startY -= lineSpacing;
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