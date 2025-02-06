package com.game;

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
    }}
