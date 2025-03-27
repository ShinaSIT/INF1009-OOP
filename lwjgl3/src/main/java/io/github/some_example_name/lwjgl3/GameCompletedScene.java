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

public class GameCompletedScene extends Scene {
    private GameMaster gameMaster;
    private BitmapFont font;
    private Stage stage;
    private int healthyFoodCount;
    private int unhealthyFoodCount;
    private int totalScore;
    
    private String currentFunFact;

    public GameCompletedScene(SceneManager sceneManager, 
            GameMaster gameMaster,
            int healthyFoodCount, 
            int unhealthyFoodCount,
            int totalScore) {
		super(sceneManager);
		this.gameMaster = gameMaster;
		this.healthyFoodCount = healthyFoodCount;
		this.unhealthyFoodCount = unhealthyFoodCount;
		this.totalScore = totalScore;
		generateFunFact();
		
		System.out.println("Created GameCompletedScene with values: " + 
		healthyFoodCount + ", " + unhealthyFoodCount + ", " + totalScore);
	}

    private void generateFunFact() {
        String[] funFacts = {
            "Eating healthy foods boosts your energy and improves concentration!",
            "Sugary snacks may give quick energy but lead to crashes later.",
            "Fruits and vegetables contain essential vitamins for growth.",
            "Processed foods often contain hidden sugars and unhealthy fats.",
            "A balanced diet helps maintain a strong immune system."
        };
        
        String fact = funFacts[(int)(Math.random() * funFacts.length)];
        
        if (healthyFoodCount > unhealthyFoodCount) {
            fact += "\n\nGreat job choosing healthy options!";
        } else if (unhealthyFoodCount > healthyFoodCount) {
            fact += "\n\nTry choosing more healthy foods next time!";
        } else {
            fact += "\n\nYou had a balance of healthy and unhealthy foods.";
        }
        
        this.currentFunFact = fact;
    }

    @Override
    public void create() {
        System.out.println("✅ Game Completed Scene Created");
        System.out.println("Creating GameCompletedScene with values: " + 
                healthyFoodCount + ", " + unhealthyFoodCount + ", " + totalScore); {
        font = new BitmapFont(Gdx.files.internal("fonts/start.fnt"));
        
        }
        font.setColor(Color.WHITE);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;

        // Exit Button
        TextButton exitButton = new TextButton("> Exit <", buttonStyle);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2f - exitButton.getWidth() / 2f, 120);


        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	if (gameMaster != null) {
                  gameMaster.resetGame();
                  gameMaster.sceneManager.transitionTo("MenuScene");
              } else {
                  System.out.println("❌ ERROR: gameMaster is NULL!");
              }
            }
        });

        stage.addActor(exitButton);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (gameMaster == null) return;

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        renderTitle(batch);
        renderResults(batch);
        renderFunFact(batch);
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();
    }

    private void renderTitle(SpriteBatch batch) {
        font.getData().setScale(2f);
        GlyphLayout layout = new GlyphLayout(font, "Game Completed!");
        font.draw(batch, "Game Completed!", 
                 Gdx.graphics.getWidth()/2f - layout.width/2, 
                 Gdx.graphics.getHeight() - 50);
        font.getData().setScale(2);
    }

    private void renderResults(SpriteBatch batch) {
    	float startY = Gdx.graphics.getHeight() - 180;
    	float lineSpacing = 90; 
    	float paddingLeft = 100; 
    	font.getData().setScale(1.5f);
        
        String[] results = {
            "Your Results",
            "Healthy Foods Collected: " + healthyFoodCount,
            "Unhealthy Foods Collected: " + unhealthyFoodCount,
            "Total Score: " + totalScore
        };
        for (String line : results) {
            font.draw(batch, line, 
                      paddingLeft, // Align text properly
                      startY);
            startY -= lineSpacing;
        }
    }

    private void renderFunFact(SpriteBatch batch) {
        float startY = Gdx.graphics.getHeight() - 700; // Adjusted for proper placement
        float paddingLeft = 100; // Left padding to align text properly
        float maxWidth = Gdx.graphics.getWidth() - 200; // Maximum width for text wrapping

        GlyphLayout layout = new GlyphLayout();
        String wrappedText = wrapText(currentFunFact, maxWidth);
        String[] lines = wrappedText.split("\n");

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                layout.setText(font, line);
                font.draw(batch, line, paddingLeft, startY);
                startY -= layout.height + 20; // Ensure correct line spacing
            }
        }
    }
    
    private String wrapText(String text, float maxWidth) {
        StringBuilder wrappedText = new StringBuilder();
        String[] words = text.split(" ");
        String line = "";

        for (String word : words) {
            GlyphLayout testLayout = new GlyphLayout(font, line + word);
            if (testLayout.width > maxWidth) {
                wrappedText.append(line).append("\n"); // Add new line
                line = word + " ";
            } else {
                line += word + " ";
            }
        }
        wrappedText.append(line.trim()); // Append the final line
        return wrappedText.toString();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        // Re-center buttons on resize
        create();
    }

    @Override
    public void dispose() {
        font.dispose();
        stage.dispose();
    }

    // Other required methods
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void update(float deltaTime) {}
    @Override public void update() {}
    @Override public void render() {}
}