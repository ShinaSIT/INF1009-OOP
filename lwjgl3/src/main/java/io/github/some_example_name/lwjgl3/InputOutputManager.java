package io.github.some_example_name.lwjgl3;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InputOutputManager {
    private Map<String, Object> gameState;  // Stores game state
    private Map<Integer, String> inputMap;  // Stores input mappings (key -> action)

    public InputOutputManager() {
        gameState = new HashMap<>();
        inputMap = new HashMap<>();
    }

    // Capture Input (Placeholder, can be expanded for real input handling)
    public void captureInput() {
        System.out.println("Capturing input...");
    }

    // Map Input Key to an Action
    public void mapInputToAction(int key, String action) {
        inputMap.put(key, action);
        System.out.println("Mapped key " + key + " to action: " + action);
    }

    // Retrieve the Action for a Key (Used by Mouse & Keyboard)
    public String getMappedAction(int key) {
        return inputMap.getOrDefault(key, "Unknown Action");
    }

    // Display Output
    public void displayOutput(String message) {
        System.out.println("Output: " + message);
    }

    // Save game state in memory
    public void saveGame(Map<String, Object> state) {
        this.gameState.putAll(state);
        System.out.println("Game state saved: " + state);
    }

    // Load game state from memory
    public Map<String, Object> loadGame() {
        System.out.println("Game state loaded: " + gameState);
        return new HashMap<>(gameState); // Return a copy to prevent modification
    }

    // Save game state to file
    public void saveGameToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(gameState);
            System.out.println("Game state saved to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load game state from file with type safety check
    @SuppressWarnings("unchecked")
    public void loadGameFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Object loadedObject = in.readObject();
            
            // Ensure the loaded object is of type Map<String, Object>
            if (loadedObject instanceof Map) {
                gameState = (Map<String, Object>) loadedObject;
                System.out.println("Game state loaded from file: " + filename);
            } else {
                System.out.println("Error: Loaded data is not a valid game state.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
