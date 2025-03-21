package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class BoardAssets {
    public static Texture pipeHorizontal, pipeVertical;
    public static Texture pipeCorner1, pipeCorner2, pipeCorner3, pipeCorner4;
    public static Texture block;
    public static Texture capLeft, capRight, capBottom, capTop;
    public static Texture pipeCross;
    public static Texture pipeConnectorTop, pipeConnectorRight, pipeConnectorBottom, pipeConnectorLeft;

    public static void load() {
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
        pipeHorizontal.dispose(); pipeVertical.dispose();
        pipeCorner1.dispose(); pipeCorner2.dispose(); pipeCorner3.dispose(); pipeCorner4.dispose();
        block.dispose();
        capLeft.dispose(); capRight.dispose(); capBottom.dispose(); capTop.dispose();
        pipeCross.dispose();
        pipeConnectorTop.dispose(); pipeConnectorRight.dispose(); pipeConnectorBottom.dispose(); pipeConnectorLeft.dispose();
    }
}
