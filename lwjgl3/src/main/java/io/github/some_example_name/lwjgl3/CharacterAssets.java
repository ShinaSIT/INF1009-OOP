package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class CharacterAssets {
    public static Texture playerLUp, playerLUp2;
    public static Texture playerLDown, playerLDown2;
    public static Texture playerLLeft, playerLLeft2;
    public static Texture playerLRight, playerLRight2;

    public static Texture playerRUp, playerRUp2;
    public static Texture playerRDown, playerRDown2;
    public static Texture playerRLeft, playerRLeft2;
    public static Texture playerRRight, playerRRight2;

    public static void load() {
    	playerLUp = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLUp.png"));
    	playerLUp2 =  new Texture(Gdx.files.internal("player/Right Facing/PsyduckRDown.png"));
    	playerLDown = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLDown.png"));
    	playerLDown2 = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLUp.png"));
    	playerLLeft = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLLeft.png"));
    	playerLLeft2 = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLRight.png"));
    	playerLRight = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLRight.png"));
    	playerLRight2 = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLLeft.png"));

    	playerRUp = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRUp.png"));
    	playerRUp2 = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRDown.png"));
    	playerRDown = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRDown.png"));
    	playerRDown2 = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRUp.png"));
    	playerRLeft = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRLeft.png"));
    	playerRLeft2 = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRRight.png"));
    	playerRRight = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRRight.png"));
    	playerRRight2 = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRLeft.png"));
    }

    public static void dispose() {
    	playerLUp.dispose(); playerLUp2.dispose();
    	playerLDown.dispose(); playerLDown2.dispose();
        playerLLeft.dispose(); playerLLeft2.dispose();
        playerLRight.dispose(); playerLRight2.dispose();

        playerRUp.dispose(); playerRUp2.dispose();
        playerRDown.dispose(); playerRDown2.dispose();
        playerRLeft.dispose(); playerRLeft2.dispose();
        playerRRight.dispose(); playerRRight2.dispose();
    }
}
