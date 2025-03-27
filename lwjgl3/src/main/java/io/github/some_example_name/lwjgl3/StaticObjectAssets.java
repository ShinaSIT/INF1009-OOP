package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.*;

public class StaticObjectAssets {
    private static final List<Texture> healthyFoods = new ArrayList<>();
    private static final List<Texture> unhealthyFoods = new ArrayList<>();
    private static final Map<Character, Texture> textureMap = new HashMap<>();
    private static final Random random = new Random();

    public static void load() {
        // 🍎 Load healthy food textures
        healthyFoods.add(new Texture(Gdx.files.internal("food/healthy/Apple.png")));
        healthyFoods.add(new Texture(Gdx.files.internal("food/healthy/Carrot.png")));
        healthyFoods.add(new Texture(Gdx.files.internal("food/healthy/Turnip.png")));
        healthyFoods.add(new Texture(Gdx.files.internal("food/healthy/Watermelon.png")));

        // 🍔 Load unhealthy food textures
        unhealthyFoods.add(new Texture(Gdx.files.internal("food/unhealthy/Burger.png")));
        unhealthyFoods.add(new Texture(Gdx.files.internal("food/unhealthy/Cake.png")));
        unhealthyFoods.add(new Texture(Gdx.files.internal("food/unhealthy/Kebab.png")));

        // 🧱 Load static object textures
        loadStaticTextures();
    }

    static void loadStaticTextures() {
        textureMap.put('.', new Texture(Gdx.files.internal("board/pellet.png")));
        textureMap.put('$', new Texture(Gdx.files.internal("board/End.png")));
        textureMap.put('-', new Texture(Gdx.files.internal("board/pipeHorizontal.png")));
        textureMap.put('|', new Texture(Gdx.files.internal("board/pipeVertical.png")));
        textureMap.put('1', new Texture(Gdx.files.internal("board/pipeCorner1.png")));
        textureMap.put('2', new Texture(Gdx.files.internal("board/pipeCorner2.png")));
        textureMap.put('3', new Texture(Gdx.files.internal("board/pipeCorner3.png")));
        textureMap.put('4', new Texture(Gdx.files.internal("board/pipeCorner4.png")));
        textureMap.put('[', new Texture(Gdx.files.internal("board/capLeft.png")));
        textureMap.put(']', new Texture(Gdx.files.internal("board/capRight.png")));
        textureMap.put('_', new Texture(Gdx.files.internal("board/capBottom.png")));
        textureMap.put('^', new Texture(Gdx.files.internal("board/capTop.png")));
        textureMap.put('+', new Texture(Gdx.files.internal("board/pipeCross.png")));
        textureMap.put('5', new Texture(Gdx.files.internal("board/pipeConnectorTop.png")));
        textureMap.put('7', new Texture(Gdx.files.internal("board/pipeConnectorBottom.png")));
        textureMap.put('b', new Texture(Gdx.files.internal("board/block.png")));
        // Add more symbols as needed
    }

    public static Texture getTexture(char symbol) {
        return textureMap.get(symbol);
    }

    public static Texture getRandomHealthyFood() {
        return healthyFoods.get(random.nextInt(healthyFoods.size()));
    }

    public static Texture getRandomUnhealthyFood() {
        return unhealthyFoods.get(random.nextInt(unhealthyFoods.size()));
    }

    public static void dispose() {
        healthyFoods.forEach(Texture::dispose);
        unhealthyFoods.forEach(Texture::dispose);
        textureMap.values().forEach(Texture::dispose);
        textureMap.clear();
    }
}
