package persistence;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Inventory;
import model.Item;
import model.Player;
import ui.Game;

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
    public void write(Inventory inventory, Player player, Game game) {
        JSONObject jsonInventory = inventory.toJson();
        JSONObject jsonCoins = inventory.coinsToJson();
        JSONObject jsonPHealth = player.toJson();
        JSONObject jsonRoomNum = game.toJson();

        JSONArray gameJsonArr = new JSONArray();
        gameJsonArr.put(jsonInventory);
        gameJsonArr.put(jsonCoins);
        gameJsonArr.put(jsonPHealth);
        gameJsonArr.put(jsonRoomNum);

        JSONObject gameJson = new JSONObject();
        gameJson.put("game", gameJsonArr);

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
