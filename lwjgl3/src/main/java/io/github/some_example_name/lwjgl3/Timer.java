package io.github.some_example_name.lwjgl3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer {
    private long startTime;
    private long endTime;
    private boolean hasStarted = false;

    public void start() {
        if (!hasStarted) {
            startTime = System.currentTimeMillis();
            hasStarted = true;
            System.out.println("Start Time: " + formatTime(startTime));
        }
    }

    public void stop() {
        if (hasStarted) {
            endTime = System.currentTimeMillis();
            long elapsed = endTime - startTime;
            System.out.println("End Time: " + formatTime(endTime));
            System.out.println("Total Time: " + formatElapsedTime(elapsed));
        }
    }

    private String formatTime(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date(timeInMillis));
    }

    private String formatElapsedTime(long elapsedMillis) {
        long seconds = (elapsedMillis / 1000) % 60;
        long minutes = (elapsedMillis / (1000 * 60)) % 60;
        long hours = (elapsedMillis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d (%d ms)", hours, minutes, seconds, elapsedMillis);
    }
}
