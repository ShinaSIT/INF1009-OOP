package com.game;

public class Timer {
	    private long startTime;
	    
	    public void start() {
	        startTime = System.currentTimeMillis();
	    }
	    
	    public long getElapsedTime() {
	        return System.currentTimeMillis() - startTime;
	    }
}
