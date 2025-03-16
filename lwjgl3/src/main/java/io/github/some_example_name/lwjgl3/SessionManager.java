package io.github.some_example_name.lwjgl3;

public class SessionManager {

    private Timer timer;
    private boolean isTimerRunning;
    private long elapsedTime; // To store the final elapsed time

    public SessionManager() {
        this.timer = new Timer();
        this.isTimerRunning = false;
        this.elapsedTime = 0;
    }

    public void startTimer() {
        if (timer != null) {
            timer.start();
            System.out.println("Timer started successfully!");
        } else {
            System.err.println("Timer is null, could not start!");
        }
    }

    public void stopTimer() {
        timer.stop();
        isTimerRunning = false;
        //elapsedTime = timer.getElapsedTime(); // Store the final time
    }

    public boolean isTimerRunning() {
        return isTimerRunning;
    }
}