package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

//Represents the game's GUI
public class GameGUI {
    private Room room;

    private Player player;
    private Inventory inventory;
    private int roomNum;
    private String notOver;

    private RoomHandler roomHandler;
    private ItemFactory itemFactory;
    private BattleHandler battleHandler;
    private ShopHandler shopHandler;

    private static final String JSON_STORE = "./data/catacombs.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final Random RANDOM = new Random();

    // EFFECTS: Create the game GUI and start the game
    public GameGUI() {
        roomHandler = new RoomHandler();
        itemFactory = new ItemFactory();
        battleHandler = new BattleHandler();
        shopHandler = new ShopHandler();

        player = new Player();
        inventory = player.getInventory();
        inventory.collect(itemFactory.makeDagger(), player); // start with a dagger
        notOver = "true";

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        room = new Room(player, this, roomHandler);
        runGame();
    }

    // EEFECTS: run the game. Player progresses encounter by encounter, and every 5
    // rooms enters a crossroads.
    // When the loop is broken (notOver not equals the string "true"), then it means
    // that the game must end and
    // either the player won, or the player died.
    public void runGame() {
        room.updatePlayerData(player, roomHandler.getRoomNum());
        roomNum = roomHandler.getRoomNum();
        if (roomNum % 5 == 0 && roomNum != 0) { // crossroads
            room.toCrossroads();
        } else {
            room.toDoor();
        }

        if (notOver.equals("victory")) {
            // WIN
            System.out.println("\u001B[1m\nYou are victorious in your quest!");
        } else if (notOver.equals("loss")) {
            // GAME OVER
            System.out.println("\nGame Over");
        }
    }

    // EFFECTS: determine the type of encounter: either a goblin, orc, chest, or shop.
    public void nextEncounter() {
        roomHandler.increaseRoomNum();
        room.updatePlayerData(player, roomHandler.getRoomNum());
        room.exitDoor();
        room.exitCrossroads();
        
        int next = RANDOM.nextInt(10) + 1;
        // next = 8;
        if (next <= 4) { // 1-4 40%
            GameCharacter goblin = new Goblin();
            enemyEncounter(goblin);

        } else if (next <= 7) { // 5-7 30%
            GameCharacter orc = new Orc();
            enemyEncounter(orc);

        } else if (next == 8) { // only 8: 10%
            lootEncounter();

        } else { // 9-10 20%
            shopEncounter();
        }
    }

    private void enemyEncounter(GameCharacter enemy) {
        room.beginEncounter();
    }

    private void lootEncounter() {
        room.beginLoot();
    }

    private void shopEncounter() {
        room.beginShop();
    }

    public void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(player, roomNum);
            jsonWriter.close();
            System.out.println("Succesfully saved the game to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    public void load() {
        try {
            player = new Player();
            Inventory inventory = player.getInventory();
            inventory = player.getInventory();
            jsonReader.read(player, inventory, roomHandler);
            System.out.println("Succesfully loaded the game from " + JSON_STORE + "\n");
            room.updatePlayerData(player, roomHandler.getRoomNum());
        } catch (IOException e) {
            System.out.println("Unable to read file from: " + JSON_STORE);
        }
    }
}
