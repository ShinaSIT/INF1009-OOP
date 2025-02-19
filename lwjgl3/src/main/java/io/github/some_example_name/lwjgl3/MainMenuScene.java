package io.github.some_example_name.lwjgl3;

public class MainMenuScene extends MenuScene {
    private GameMaster gameMaster;  

    public MainMenuScene(SceneManager sceneManager, GameMaster gameMaster) {
        super(sceneManager, gameMaster);  
        this.gameMaster = gameMaster;  
    }

    @Override
    public void create() {
        super.create();
        System.out.println("✅ Main Menu Scene Created");
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {}

    public void onStartGameButtonPressed() {
        System.out.println("🎮 Start Game Button Clicked in MainMenuScene!");
        if (gameMaster != null) {
            System.out.println("✅ Calling gameMaster.startGame()...");
            gameMaster.startGame();
        } else {
            System.out.println("❌ ERROR: gameMaster is NULL!");
        }
    }

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
}
