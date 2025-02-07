package io.github.some_example_name.lwjgl3;
import java.util.*;

public class InputOutputManager {
	 private Map<String, String> inputMap;
	 private Queue<String> outputQueue;

	 public InputOutputManager() {
	        inputMap = new HashMap<>();
	        outputQueue = new LinkedList<>();
	      }

	    public void captureInput() {

	    }

	    public void mapInputToAction(String input) {
	        if (inputMap.containsKey(input)) {
	            System.out.println("Mapped Action: " + inputMap.get(input));
	        }
	    }

	    public void displayOutput(String message) {
	        outputQueue.add(message);
	        System.out.println(message);
	    }

		/*
		 * public void saveGame(GameState state) {
		 * 
		 * }
		 * 
		 * public GameState loadGame() {
		 * 
		 * 
		 * }
		 * 
		 * public Keyboard getKeyboard() { return new Keyboard(); }
		 * 
		 * public Mouse getMouse() { return new Mouse(); }
		 */

}
