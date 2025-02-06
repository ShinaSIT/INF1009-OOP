package com.game;

import java.util.ArrayList;
import java.util.List;

public class StaticObjects extends Entity{

    public StaticObjects(int x, int y) {
        super(x, y);
    }

    public List<Item> items() { 
    	return new ArrayList<>(); 
    }
    
    public Item createItem(String itemType, float param1, float param2) { 
    	return new Item(); 
    }
    
    public Item createRandomItem(float param1, float param2) { 
    	return new Item(); 
    }
    
    public void disposeTextures() {}
}
