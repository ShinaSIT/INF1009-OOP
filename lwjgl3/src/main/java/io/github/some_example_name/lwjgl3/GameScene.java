package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

public class GameScene extends Scene {
    private GameMaster gameMaster;
    private int score;
    private long startTime;
    private long elapsedTime;

    // UI components
    private BitmapFont font;

    public GameScene(SceneManager sceneManager, GameMaster gameMaster) {
        super(sceneManager);
        this.gameMaster = gameMaster;
        this.score = 0;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void create() {
        System.out.println("✅ Game Scene Created");
        if (gameMaster == null){
            System.out.println("GameMaster is Null");
        }

        // Initialize the font (you can load a custom font if needed)
        try {
            font = new BitmapFont(Gdx.files.internal("path/to/your/font.fnt"));  // Custom font (optional)
        } catch (Exception e) {
            font = new BitmapFont();  // Default font if the custom one fails
            System.out.println("Using default font due to error: " + e.getMessage());
        }
        font.setColor(Color.WHITE);  // Set the font color to white
    }

    @Override
    public void render(SpriteBatch batch) {
        System.out.println("GameScene render() called");
        if (gameMaster == null) return;

        // Render UI elements (score and timer) at the top of the screen
        renderUI(batch);

        // Render the game board (positioned below the UI)
        renderBoard(batch);
    }

    private void renderUI(SpriteBatch batch) {
        // Update the score and timer text
        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        String scoreText = "Score: " + score;
        String timeText = "Time: " + elapsedTime + "s";

        // Debug to ensure UI elements are being rendered
        System.out.println("Rendering Score: " + scoreText + ", Time: " + timeText);

        // Draw the score at the top-left corner
        font.draw(batch, scoreText, 10, Gdx.graphics.getHeight() - 20);

        // Draw the time at the top-left corner, just below the score
        font.draw(batch, timeText, 10, Gdx.graphics.getHeight() - 40);
    }

    private void renderBoard(SpriteBatch batch) {
        // After the score and timer, render the game board below the UI
        if (getBoard() != null) {
            // Adjust the board's Y position based on the height of the score and timer
            float boardYOffset = Gdx.graphics.getHeight() - 60; // Adjust as needed (60 = 20 for score + 40 for timer)

            // Move the board's Y position down so it's not overlapping with the UI
            batch.getProjectionMatrix().translate(0, -boardYOffset, 0);  // Shift board downwards
            getBoard().render(batch);  // Render the board
            batch.getProjectionMatrix().translate(0, boardYOffset, 0);  // Reset projection matrix after rendering board
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
        System.out.println("Total time: " + elapsedTime + "s"); // Use the class member
        font.dispose();  // Dispose of the font when done
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void update(float deltaTime) {
        // Update score or other gameplay mechanics
        // Example: Increase score based on some game conditions
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
}