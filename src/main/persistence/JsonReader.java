package persistence;

import model.Inventory;
import model.Player;
import model.RoomHandler;

// Represents a reader that reads the game state JSON file and updates the game information accordingly
public class JsonReader {
    
    // EFFECTS: constructs a reader to read from the source file
    public JsonReader(String source) {
        //stub
    }

    // MODIFIES: player, roomHandler (roomNumber)
    // EFFECTS: reads the JSON file to retrieve the saved game state
    // throws an exception if file is not found.
    public void read(Player player, Inventory inventory, RoomHandler roomHandler) {
        //stub
    }

    // EFFECTS: parse game state from the JSON file
    private void parseJson() {
        //stub
    }

    // MODIFIES: inventory
    // EFFECTS: parses and adds the saved items to the player inventory from JSON
    private void addItems() {
        //stub
    }

    // MODIFIES: inventory
    // EFFECTS: parse and adds individual item to player inventory from JSON
    private void addItem() {

    }

}
