package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class Asset {
    public static Texture psyduckLUp;
    public static Texture psyduckLDown;
    public static Texture psyduckLLeft;
    public static Texture psyduckLRight;

    public static Texture psyduckRUp;
    public static Texture psyduckRDown;
    public static Texture psyduckRLeft;
    public static Texture psyduckRRight;

    public static void load() {
        psyduckLUp = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLUp.png"));
        psyduckLDown = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLDown.png"));
        psyduckLLeft = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLLeft.png"));
        psyduckLRight = new Texture(Gdx.files.internal("player/Left Facing/PsyduckLRight.png"));

        psyduckRUp = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRUp.png"));
        psyduckRDown = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRDown.png"));
        psyduckRLeft = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRLeft.png"));
        psyduckRRight = new Texture(Gdx.files.internal("player/Right Facing/PsyduckRRight.png"));
    }

    public static void dispose() {
        // Dispose all textures
        psyduckLUp.dispose();
        psyduckLDown.dispose();
        psyduckLLeft.dispose();
        psyduckLRight.dispose();
        psyduckRUp.dispose();
        psyduckRDown.dispose();
        psyduckRLeft.dispose();
        psyduckRRight.dispose();
    }
}
