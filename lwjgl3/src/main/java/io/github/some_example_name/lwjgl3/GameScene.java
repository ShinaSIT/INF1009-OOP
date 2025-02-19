package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScene extends Scene {
    private GameMaster gameMaster;

    public GameScene(SceneManager sceneManager, GameMaster gameMaster) {
        super(sceneManager);
        this.gameMaster = gameMaster;
    }

    @Override
    public void create() {
        System.out.println("✅ Game Scene Created");
    }

    @Override
    public void render() {
        // ✅ Empty implementation to satisfy abstract class
    }

    @Override
    public void render(SpriteBatch batch) {
        System.out.println("✅ GameScene Render Called");
        
        if (gameMaster.getHealthManager().isGameOver()) {  
            gameMaster.getHealthManager().drawGameOverScreen(batch);
        } else {
            gameMaster.getBoard().render(batch);
            gameMaster.getEntityManager().render(batch);
            gameMaster.getHealthManager().drawLivesDisplay(batch);
        }
    }


    @Override
    public void resize(int width, int height) {
        System.out.println("✅ GameScene.resize() called! New size: " + width + "x" + height);

        // ✅ Update Board Dimensions
        gameMaster.getBoard().updateDimensions();
        
        // ✅ Update All Entities (Ensures static objects & enemies scale correctly)
        gameMaster.getEntityManager().updateAllEntities(gameMaster.getBoard());

        // ✅ Ensure Player Stays Inside Grid & Sync Position
        MoveableObjects player = gameMaster.getEntityManager().getPlayer();
        if (player != null) {
            int clampedX = Math.max(0, Math.min(player.getGridX(), gameMaster.getBoard().getMazeWidth() - 1));
            int clampedY = Math.max(0, Math.min(player.getGridY(), gameMaster.getBoard().getMazeHeight() - 1));

            player.setGridX(clampedX);
            player.setGridY(clampedY);
            player.updatePixelPosition(gameMaster.getBoard());

            System.out.println("📌 Player Clamped to Grid (" + clampedX + ", " + clampedY + ")");
        }

        // ✅ Ensure Game Over UI Resizes Correctly
        if (gameMaster.getHealthManager().isGameOver()) {
            gameMaster.getHealthManager().resize(width, height);
        }
    }

    @Override
    public void dispose() {
        System.out.println("✅ Disposing Game Scene...");
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void update(float deltaTime) {}

	@Override
	public void update() {}
}
