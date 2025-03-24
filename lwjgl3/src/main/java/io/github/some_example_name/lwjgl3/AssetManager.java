package io.github.some_example_name.lwjgl3;

public class AssetManager {
    public static void loadAll() {
        CharacterAssets.load();
        StaticObjectAssets.load();
//        EnemyAssets.load();
//        UIAssets.load();
    }

    public static void disposeAll() {
        CharacterAssets.dispose();
        StaticObjectAssets.dispose();
//        EnemyAssets.dispose();
//        UIAssets.dispose();
    }
}
