package io.github.some_example_name.lwjgl3;

public class Board {
    private int width;
    private int height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() { 
    	return width; 
    }
    
    public int getHeight() { 
    	return height; 
    }
}
