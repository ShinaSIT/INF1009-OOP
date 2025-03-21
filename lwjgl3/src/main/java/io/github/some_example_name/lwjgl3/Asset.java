package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class Asset {
    public static Texture psyduckLUp, psyduckLUp2;
    public static Texture psyduckLDown, psyduckLDown2;
    public static Texture psyduckLLeft, psyduckLLeft2;
    public static Texture psyduckLRight, psyduckLRight2;

    public static Texture psyduckRUp, psyduckRUp2;
    public static Texture psyduckRDown, psyduckRDown2;
    public static Texture psyduckRLeft, psyduckRLeft2;
    public static Texture psyduckRRight, psyduckRRight2;

    public static void load() {
        psyduckLUp = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLUp.png"));
        psyduckLUp2 =  new Texture(Gdx.files.internal("player/Right Facing/PsyduckRDown.png"));
        psyduckLDown = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLDown.png"));
        psyduckLDown2 = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLUp.png"));
        psyduckLLeft = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLLeft.png"));
        psyduckLLeft2 = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLRight.png"));
        psyduckLRight = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLRight.png"));
        psyduckLRight2 = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLLeft.png"));

        psyduckRUp = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRUp.png"));
        psyduckRUp2 = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRDown.png"));
        psyduckRDown = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRDown.png"));
        psyduckRDown2 = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRUp.png"));
        psyduckRLeft = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRLeft.png"));
        psyduckRLeft2 = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRRight.png"));
        psyduckRRight = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRRight.png"));
        psyduckRRight2 = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRLeft.png"));
    }

    public static void dispose() {
        // Left facing
        psyduckLUp.dispose(); psyduckLUp2.dispose();
        psyduckLDown.dispose(); psyduckLDown2.dispose();
        psyduckLLeft.dispose(); psyduckLLeft2.dispose();
        psyduckLRight.dispose(); psyduckLRight2.dispose();

        // Right facing
        psyduckRUp.dispose(); psyduckRUp2.dispose();
        psyduckRDown.dispose(); psyduckRDown2.dispose();
        psyduckRLeft.dispose(); psyduckRLeft2.dispose();
        psyduckRRight.dispose(); psyduckRRight2.dispose();
    }

}
