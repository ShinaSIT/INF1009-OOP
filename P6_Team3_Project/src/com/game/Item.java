package com.game;

public class Item {
    private String type;
    private float weight;
    private float value;
    
    public Item() {}
    public Item(String type, float weight, float value) {
        this.type = type;
        this.weight = weight;
        this.value = value;
    }
    
    public String getType() { return type; }
    public float getWeight() { return weight; }
    public float getValue() { return value; }
}
