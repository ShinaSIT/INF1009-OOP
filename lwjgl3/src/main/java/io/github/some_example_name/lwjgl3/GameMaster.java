package io.github.some_example_name.lwjgl3;

public class GameMaster {
    private Timer timer;
    private Board board;
    private int score;
    private int lives;

    public GameMaster(int boardWidth, int boardHeight) {
        this.timer = new Timer();
        this.board = new Board(boardWidth, boardHeight);
        this.score = 0;
        this.lives = 3;
    }

    public void startTimer() {
        timer.start();
    }

    public void startGame() {
        startTimer();
        System.out.println("Game Started!");
    }
    
    public void updateGame() {
    	
    }
    
    public void endGame() {
    	
    }
    
    public void createCharacter() {
    	
    }
    
    public void resetScore() {
    	
    }

    public void render() {
    	
    }
    
    public void dispose() {
    	
    }
}