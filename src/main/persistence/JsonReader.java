package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Inventory;
import model.ItemFactory;
import model.Player;
import model.RoomHandler;

// Represents a reader that reads the game state JSON file and updates the game information accordingly
public class JsonReader {

    private String source;
    
    // EFFECTS: constructs a reader to read from the source file
    public JsonReader(String source) {
        this.source = source;
    }

    // MODIFIES: player, roomHandler (roomNumber)
    // EFFECTS: reads the JSON file to retrieve the saved game state
    // throws an exception if file is not found.
    public void read(Player player, Inventory inventory, RoomHandler roomHandler) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        parseItems(jsonObject, inventory, player);
        parseGameData(jsonObject, player, inventory, roomHandler);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parse game state from the JSON file
    private void parseGameData(JSONObject jsonObject, Player player, Inventory inventory, RoomHandler roomHandler) {
        int health = jsonObject.getInt("health");
        int coins = jsonObject.getInt("coins");
        int roomNumber = jsonObject.getInt("roomNumber");

        player.setHealth(health);
        inventory.setCoins(coins);
        roomHandler.setRoomNum(roomNumber);
    }

    // MODIFIES: inventory
    // EFFECTS: parses and adds the saved items to the player inventory from JSON
    private void parseItems(JSONObject jsonObject, Inventory inventory, Player player) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(nextItem, inventory, player);
        }
    }

    // MODIFIES: inventory
    // EFFECTS: parse and adds individual item to player inventory from JSON
    private void addItem(JSONObject jsonObject, Inventory inventory, Player player) {
        ItemFactory itemFactory = new ItemFactory();
        String name = jsonObject.getString("name");
        inventory.collect(itemFactory.makeItem(name), player);
    }

}
