package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class GameScene extends Scene {
    private GameMaster gameMaster;
    private Texture crownTexture;
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

    // UI components
    private BitmapFont font;
    private int highScore = 0; // Added high score variable
    private Preferences prefs; // Added Preferences
	private InputManager inputManager;
	private Speaker speaker;

    public GameScene(SceneManager sceneManager, GameMaster gameMaster, InputManager inputManager, Speaker speaker) {
        super(sceneManager);
        this.gameMaster = gameMaster;
        this.score = 0;
        this.health = 3;
        this.startTime = System.currentTimeMillis();
        this.healthyFoodCount = 0;
        this.unhealthyFoodCount = 0;
        this.totalHealthyFoodCount = 0;
        this.totalUnhealthyFoodCount = 0;
        this.totalScore = 0;
        this.inputManager = inputManager;
        this.speaker = speaker;
        
        this.prefs = Gdx.app.getPreferences("MyGamePreferences"); // Initialize Preferences
        this.highScore = prefs.getInteger("highScore", 0); // Load high score from Preferences
    }

    @Override
    public void create() {
        System.out.println("✅ Game Scene Created");
        if (gameMaster == null) {
            System.out.println("GameMaster is Null");
        }
        
        this.mouse = new Mouse(inputManager, speaker);

        try {
            font = new BitmapFont(Gdx.files.internal("assets/fonts/menu_font.fnt")); //dont change this please
        } catch (Exception e) {
            font = new BitmapFont();
            font = new BitmapFont(); // Default font if the custom one fails
            System.out.println("Using default font due to error: " + e.getMessage());
        }
        font.setColor(Color.WHITE);
        
        // Remove this line - we'll create the completed scene when needed
       // sceneManager.addScene("GameCompletedScene", new GameCompletedScene(sceneManager, gameMaster, this));
        font.setColor(Color.WHITE);// Set the font color to white
    }

    @Override
    public void render(SpriteBatch batch) {
        //System.out.println("GameScene render() called");
        if (gameMaster == null) return;

        // Update High Score
        update(Gdx.graphics.getDeltaTime()); // Ensure update is called.

        // Render UI elements (score and timer) at the top of the screen
        renderUI(batch);

        // Render the game board (positioned below the UI)
        renderBoard(batch);
        
        //mouse.checkMouse(); 
        totalHealthyFoodCount = healthyFoodCount;
        totalUnhealthyFoodCount = unhealthyFoodCount;
        totalScore = score;

        if (mouse != null) {
            mouse.checkMouse();
        }
    }

    private void renderUI(SpriteBatch batch) {
        // Update the score and timer text
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

        int minutes = (int) (elapsedTime / 60);
        int seconds = (int) (elapsedTime % 60);

        String formattedTime = String.format("%02d:%02d", minutes, seconds); // Format as MM:SS

        String scoreText = "Score: " + score;
        String timeText = "Time: " + formattedTime; // Use formatted time string
        String healthText = "Health: " + health;

//        String healthyFoodCountText = "good food: " + healthyFoodCount;
//        String unhealthyFoodCountText = "bad food: " + unhealthyFoodCount;
        String highScoreText = "High Score "; // Added high score text
        String highScoreValue = ": " + highScore;


        // Debug to ensure UI elements are being rendered
        //System.out.println("Rendering Score: " + scoreText);
        //System.out.println("Time: " + timeText);
        //System.out.println("Health: " + healthText);

        // Draw the score at the top-left corner
        font.draw(batch, scoreText, 10, Gdx.graphics.getHeight() - 20);

        // Draw the time at the top-left corner, just below the score
        font.draw(batch, timeText, 10, Gdx.graphics.getHeight() - 40);

        // Draw the health at the top-left corner, just below the time
        font.draw(batch, healthText, 10, Gdx.graphics.getHeight() - 60);
        
//        font.draw(batch, healthyFoodCountText, 10, Gdx.graphics.getHeight() - 80);
//        font.draw(batch, unhealthyFoodCountText, 10, Gdx.graphics.getHeight() - 100);

        //Draw the highscore below the health
        font.draw(batch, highScoreText, 10, Gdx.graphics.getHeight() - 80);

        font.draw(batch, highScoreValue, 10, Gdx.graphics.getHeight() - 100);
    }

    private void renderBoard(SpriteBatch batch) {
        // After the score and timer, render the game board below the UI
        if (getBoard() != null) {
            // Adjust the board's Y position based on the height of the score and timer
            float boardYOffset = Gdx.graphics.getHeight() - 80; // Adjusted for high score

            // Move the board's Y position down so it's not overlapping with the UI
            batch.getProjectionMatrix().translate(0, -boardYOffset, 0); // Shift board downwards
            getBoard().render(batch); // Render the board
            batch.getProjectionMatrix().translate(0, boardYOffset, 0); // Reset projection matrix after rendering board
        }
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("✅ Resizing GameScene: " + width + "x" + height);

        if (gameMaster == null) return;

        // Resize board and other game components as necessary
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
        System.out.println("Total time: " + elapsedTime / 60 + "min" + elapsedTime % 60 + "s"); // Use the class member
        font.dispose(); // Dispose of the font when done
        if (mouse != null) {
            // If Mouse has cleanup methods, call them here
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void update(float deltaTime) {
        if (score > highScore) {
            highScore = score;
            prefs.putInteger("highScore", highScore); // Save high score to Preferences
            prefs.flush(); // Save changes immediately
        }
    }

    private Board getBoard() {
        return (gameMaster != null) ? gameMaster.getBoardManager().getBoard() : null;
    }

    private EntityManager getEntityManager() {
        return (gameMaster != null) ? gameMaster.getEntityManager() : null;
    }

    @Override
    public void update() {
        // You may have specific gameplay update logic here
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub

    }
	
	public int getHealthyFoodCount() {
	    return healthyFoodCount;
	}

	public int getUnhealthyFoodCount() {
	    return unhealthyFoodCount;
	}
	
	public void setHealthyFoodCount(int healthyFoodCount) {
	    this.healthyFoodCount = healthyFoodCount;
	}

	public void setUnhealthyFoodCount(int unhealthyFoodCount) {
	    this.unhealthyFoodCount = unhealthyFoodCount;
	}
	
	public int getTotalHealthyFoodCount() {
		return  totalHealthyFoodCount;
	}
	
	public int getTotalUnhealthyFoodCount() {
		return  totalUnhealthyFoodCount;
	}
	
	public int getTotalScore() {
		return  totalScore;
	}
	
    public int getScore() {
        return score;
    }

    public int getHealth() {
        return health;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}