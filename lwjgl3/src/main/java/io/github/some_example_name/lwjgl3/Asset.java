package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class Asset {
// Player psyduck Character
    public static Texture psyduckLUp, psyduckLUp2;
    public static Texture psyduckLDown, psyduckLDown2;
    public static Texture psyduckLLeft, psyduckLLeft2;
    public static Texture psyduckLRight, psyduckLRight2;

    public static Texture psyduckRUp, psyduckRUp2;
    public static Texture psyduckRDown, psyduckRDown2;
    public static Texture psyduckRLeft, psyduckRLeft2;
    public static Texture psyduckRRight, psyduckRRight2;
    
// Board 
    public static Texture pipeHorizontal, pipeVertical;
    public static Texture pipeCorner1, pipeCorner2, pipeCorner3, pipeCorner4;
    public static Texture block;
    public static Texture capLeft, capRight, capBottom, capTop;
    public static Texture pipeCross;
    public static Texture pipeConnectorTop, pipeConnectorRight, pipeConnectorBottom, pipeConnectorLeft;


    public static void load() {
	// Player psyduck Character
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
        
     // Board 
        pipeHorizontal = new Texture(Gdx.files.internal("board/pipeHorizontal.png"));
        pipeVertical = new Texture(Gdx.files.internal("board/pipeVertical.png"));
        pipeCorner1 = new Texture(Gdx.files.internal("board/pipeCorner1.png"));
        pipeCorner2 = new Texture(Gdx.files.internal("board/pipeCorner2.png"));
        pipeCorner3 = new Texture(Gdx.files.internal("board/pipeCorner3.png"));
        pipeCorner4 = new Texture(Gdx.files.internal("board/pipeCorner4.png"));
        block = new Texture(Gdx.files.internal("board/block.png"));
        capLeft = new Texture(Gdx.files.internal("board/capLeft.png"));
        capRight = new Texture(Gdx.files.internal("board/capRight.png"));
        capBottom = new Texture(Gdx.files.internal("board/capBottom.png"));
        capTop = new Texture(Gdx.files.internal("board/capTop.png"));
        pipeCross = new Texture(Gdx.files.internal("board/pipeCross.png"));
        pipeConnectorTop = new Texture(Gdx.files.internal("board/pipeConnectorTop.png"));
        pipeConnectorRight = new Texture(Gdx.files.internal("board/pipeConnectorRight.png"));
        pipeConnectorBottom = new Texture(Gdx.files.internal("board/pipeConnectorBottom.png"));
        pipeConnectorLeft = new Texture(Gdx.files.internal("board/pipeConnectorLeft.png"));
    }
    public static void dispose() {
    // Player psyduck Character
        psyduckLUp.dispose(); psyduckLUp2.dispose();
        psyduckLDown.dispose(); psyduckLDown2.dispose();
        psyduckLLeft.dispose(); psyduckLLeft2.dispose();
        psyduckLRight.dispose(); psyduckLRight2.dispose();

        psyduckRUp.dispose(); psyduckRUp2.dispose();
        psyduckRDown.dispose(); psyduckRDown2.dispose();
        psyduckRLeft.dispose(); psyduckRLeft2.dispose();
        psyduckRRight.dispose(); psyduckRRight2.dispose();
        
     // Dispose maze textures
        pipeHorizontal.dispose(); pipeVertical.dispose();
        pipeCorner1.dispose(); pipeCorner2.dispose(); pipeCorner3.dispose(); pipeCorner4.dispose();
        block.dispose();
        capLeft.dispose(); capRight.dispose(); capBottom.dispose(); capTop.dispose();
        pipeCross.dispose();
        pipeConnectorTop.dispose(); pipeConnectorRight.dispose(); pipeConnectorBottom.dispose(); pipeConnectorLeft.dispose();

    }

}
