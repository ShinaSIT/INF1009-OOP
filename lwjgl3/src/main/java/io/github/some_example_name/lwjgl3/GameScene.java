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
        // ✅ Return early if `gameMaster` is null
        if (gameMaster == null) return;

        // ✅ Render Board & Entities safely
        if (getBoard() != null) getBoard().render(batch);
        if (getEntityManager() != null) getEntityManager().render(batch);

        // gameMaster.getHealthManager().drawLivesDisplay(batch); // Part 2: Commented out
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("✅ Resizing GameScene: " + width + "x" + height);

        if (gameMaster == null) return;

        if (getBoard() != null) {
            getBoard().updateDimensions();

            if (getEntityManager() != null) {
                getEntityManager().updateAllEntities(getBoard());
                getEntityManager().ensurePlayerExists(); // ✅ Ensure the player exists after resize
            }
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

    // ✅ Helper Methods to Get `gameMaster` Dependencies
    private Board getBoard() {
        return (gameMaster != null) ? gameMaster.getBoard() : null;
    }

    private EntityManager getEntityManager() {
        return (gameMaster != null) ? gameMaster.getEntityManager() : null;
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}