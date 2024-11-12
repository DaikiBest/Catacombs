package ui;

import model.*;
import persistence.*;
import java.util.Random;

//Represents the game's GUI
public class GameGUI {
    private Room room;

    private Player player;
    private Inventory inventory;
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

        room = new Room(player, roomHandler.getRoomNum(), this);
        runGame();
    }

    // EEFECTS: run the game. Player progresses encounter by encounter, and every 5
    // rooms enters a crossroads.
    // When the loop is broken (notOver not equals the string "true"), then it means
    // that the game must end and
    // either the player won, or the player died.
    public void runGame() {
        while (notOver.equals("true")) {
            int roomNumber = roomHandler.getRoomNum();
            if (roomNumber % 5 == 0 && roomNumber != 0) { // crossroads
                room.toCrossroads();
            } else {
                room.toDoor();
            }
        }

        if (notOver.equals("victory")) {
            // WIN
            System.out.println("\u001B[1m\nYou are victorious in your quest!");
        } else {
            // GAME OVER
            System.out.println("\nGame Over");
        }
    }

    // EFFECTS: determine the type of encounter: either a goblin, orc, chest, or shop.
    public void nextEncounter() {
        // int next = RANDOM.nextInt(10) + 1;
        int next = 8;
        roomHandler.increaseRoomNum();
        room.exitDoor();
        room.exitCrossroads();
        if (next <= 4) { // 1-4 40%
            GameCharacter goblin = new Goblin();
            enemyEncounter(goblin);

        } else if (next <= 7) { // 5-7 30%
            GameCharacter orc = new Orc();
            enemyEncounter(orc);

        } else if (next == 8) { // only 8: 10%
            System.out.println("Loot!");
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
}
