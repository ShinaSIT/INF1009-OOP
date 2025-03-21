package io.github.some_example_name.lwjgl3;

public class AssetManager {
    public static void loadAll() {
        CharacterAssets.load();
        BoardAssets.load();
//        StaticObjectAssets.load();
//        EnemyAssets.load();
//        UIAssets.load();
    }

    public static void disposeAll() {
        CharacterAssets.dispose();
        BoardAssets.dispose();
//        StaticObjectAssets.dispose();
//        EnemyAssets.dispose();
//        UIAssets.dispose();
    }
}
