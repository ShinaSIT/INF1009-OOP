package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

public class GameScene extends Scene {
    private GameMaster gameMaster;
    private int score;
    private int health;
    private long startTime;
    private long elapsedTime;
    protected Mouse mouse;
    private int healthyFoodCount = 0;
    private int unhealthyFoodCount = 0;
    private int totalHealthyFoodCount = 0;
    private int totalUnhealthyFoodCount = 0;
    private int totalScore = 0;
    private HealthFactPopup healthFactPopup;
    private BitmapFont font;
    private BitmapFont defaultFont;
    private int highScore = 0;
    private Preferences prefs;
    private InputManager inputManager;
    private Speaker speaker;

    public GameScene(SceneManager sceneManager, GameMaster gameMaster, InputManager inputManager, Speaker speaker) {
        super(sceneManager);
        this.gameMaster = gameMaster;
        this.score = 0;
        this.health = 3;
        this.startTime = System.currentTimeMillis();
        this.inputManager = inputManager;
        this.speaker = speaker;
        this.prefs = Gdx.app.getPreferences("MyGamePreferences");
        this.highScore = prefs.getInteger("highScore", 0);
    }

    @Override
    public void create() {
        System.out.println("✅ Game Scene Created");
        this.mouse = new Mouse(inputManager, speaker);
        healthFactPopup = new HealthFactPopup(defaultFont);
        defaultFont = new BitmapFont();

        try {
            font = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt"));
        } catch (Exception e) {
            font = new BitmapFont();
            System.out.println("Using default font due to error: " + e.getMessage());
        }
        font.setColor(Color.WHITE);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (gameMaster == null) return;

        update(Gdx.graphics.getDeltaTime());

        renderUI(batch);
        renderBoard(batch);

        totalHealthyFoodCount = healthyFoodCount;
        totalUnhealthyFoodCount = unhealthyFoodCount;
        totalScore = score;

        if (mouse != null) {
            mouse.checkMouse();
        }

        // Render the popup after everything
        if (healthFactPopup != null) {
            healthFactPopup.render(batch, defaultFont);
        }
    }
    
    public HealthFactPopup getHealthFactPopup() {
        return healthFactPopup;
    }

    private void renderUI(SpriteBatch batch) {
    	// Update the score and timer text
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

        int minutes = (int) (elapsedTime / 60);
        int seconds = (int) (elapsedTime % 60);

        String formattedTime = String.format("%02d:%02d", minutes, seconds); // Format as MM:SS
        String timeText = "Time " + formattedTime; // Use formatted time string
        String healthText = "Health " + health;
        String scoreText = "Score " + score;
        String highScoreText = "High Score"; 
        String highScoreValue = String.valueOf(highScore);

        float xPos = 20; // Left padding
        float yStart = Gdx.graphics.getHeight() - 30; // Start position (from top)
        float lineHeight = 40; // Space between lines
        
        // Padding while drawing text
        font.draw(batch, timeText, xPos, yStart);
        font.draw(batch, healthText, xPos, yStart - lineHeight);
        font.draw(batch, scoreText, xPos, yStart - (2 * lineHeight));
        
        // High Score Label & Value (Aligns High Score text and value)
        font.draw(batch, highScoreText, xPos, yStart - (4 * lineHeight));
        font.draw(batch, highScoreValue, xPos, yStart - (5 * lineHeight)); 
    }

    private void renderBoard(SpriteBatch batch) {
        if (getBoard() != null) {
            float boardYOffset = Gdx.graphics.getHeight() - 80;
            batch.getProjectionMatrix().translate(0, -boardYOffset, 0);
            getBoard().render(batch);
            batch.getProjectionMatrix().translate(0, boardYOffset, 0);
        }
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("✅ Resizing GameScene: " + width + "x" + height);
        if (gameMaster == null) return;

        if (getBoard() != null) {
            getBoard().updateDimensions();
            if (getEntityManager() != null) {
                getEntityManager().updateAllEntities(getBoard());
                getEntityManager().ensurePlayerExists();
            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("✅ Disposing Game Scene...");
        System.out.println("Total time: " + elapsedTime / 60 + "min" + elapsedTime % 60 + "s");
        font.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void update(float deltaTime) {
        if (score > highScore) {
            highScore = score;
            prefs.putInteger("highScore", highScore);
            prefs.flush();
        }
    }

    @Override public void update() {}
    @Override public void render() {}

    // ✅ INPUT HANDLING for closing the popup
    public void handleInput(int screenX, int screenY) {
        if (healthFactPopup != null && healthFactPopup.isVisible()) {
            healthFactPopup.handleInput(screenX, screenY);
        }
    }

    // ✅ Getters and setters
    public int getHealthyFoodCount() { return healthyFoodCount; }
    public int getUnhealthyFoodCount() { return unhealthyFoodCount; }

    public void setHealthyFoodCount(int healthyFoodCount) {
        this.healthyFoodCount = healthyFoodCount;
    }

    public void setUnhealthyFoodCount(int unhealthyFoodCount) {
        if (unhealthyFoodCount > this.unhealthyFoodCount && healthFactPopup != null) {
            healthFactPopup.showRandomFact(); // ✅ show popup on new unhealthy food
        }
        this.unhealthyFoodCount = unhealthyFoodCount;
    }

    public int getTotalHealthyFoodCount() { return totalHealthyFoodCount; }
    public int getTotalUnhealthyFoodCount() { return totalUnhealthyFoodCount; }
    public int getTotalScore() { return totalScore; }
    public int getScore() { return score; }
    public int getHealth() { return health; }
    public void setScore(int score) { this.score = score; }
    public void setHealth(int health) { this.health = health; }

    private Board getBoard() {
        return (gameMaster != null) ? gameMaster.getBoardManager().getBoard() : null;
    }

    private EntityManager getEntityManager() {
        return (gameMaster != null) ? gameMaster.getEntityManager() : null;
    }
}
