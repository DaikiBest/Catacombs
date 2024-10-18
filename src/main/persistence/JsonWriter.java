package persistence;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Inventory;
import model.Player;
import model.RoomHandler;

// Represents a writer that writes the game's state to a JSON file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: creates the writer that will write the game's state into a json file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the write. Will throw an exception if the file to be writen can't be opened.
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes the game state into a JSON file
    public void write(Player player, RoomHandler roomHandler) {
        Inventory inventory = player.getInventory();
        JSONArray jsonInventory = inventory.itemsToJson();
        int coins = inventory.getCoins();
        int health = player.getHealth();
        int roomNumber = roomHandler.getRoomNum();

        JSONObject gameJson = new JSONObject();
        gameJson.put("items", jsonInventory);
        gameJson.put("coins", coins); 
        gameJson.put("health", health);
        gameJson.put("roomNumber", roomNumber);

        saveToFile(gameJson.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: close the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
