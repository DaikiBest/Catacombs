package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import model.Player;
import model.RoomHandler;
import model.Inventory;
import model.ItemFactory;
import model.Item;

public class testJsonWritter {
    
    Player testPlayer;
    Inventory testInventory;
    RoomHandler testRoom;
    ItemFactory itemFactory;

    @BeforeEach
    void runBefore() {
        testRoom = new RoomHandler();
        testPlayer = new Player();
        itemFactory = new ItemFactory();
        testInventory = testPlayer.getInventory();
    }

    @Test
    void testWriterWrongFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterOneItem() {
        try {
            // Set the state to be recorded
            Item excalibur = itemFactory.makeExcalibur();
            testPlayer.setHealth(3);
            testInventory.collect(excalibur, testPlayer);
            testInventory.setCoins(9);
            testRoom.setRoomNum(6);
            testRoom.increaseRoomNum(); //roomNum is 7

            // Write
            JsonWriter writer = new JsonWriter("./data/testWriterOneItem.json");
            writer.open();
            writer.write(testPlayer, testRoom);
            writer.close();

            // Reset game state
            testPlayer.setHealth(5);
            testInventory.discard("excalibur", testPlayer);
            testInventory.setCoins(5);
            testRoom.setRoomNum(0);

            JsonReader reader = new JsonReader("./data/testWriterOneItem.json");
            testPlayer = new Player();
            testInventory = testPlayer.getInventory();
            reader.read(testPlayer, testInventory, testRoom); //updated player, inventory, and roomNum
            assertEquals(3, testPlayer.getHealth());
            assertEquals("Excalibur", testInventory.getItem("excalibur").getName());
            assertEquals(1, testInventory.getItems().size());
            assertEquals(9, testInventory.getCoins());
            assertEquals(7, testRoom.getRoomNum());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testWriterManyItems() {
        try {
            // Set the state to be recorded
            Item sword = itemFactory.makeLongsword();
            Item mace = itemFactory.makeMace();
            Item cap = itemFactory.makeCap();
            Item hood = itemFactory.makeHood();
            testPlayer.setHealth(9);
            testInventory.collect(sword, testPlayer);
            testInventory.collect(mace, testPlayer);
            testInventory.collect(cap, testPlayer);
            testInventory.collect(hood, testPlayer);
            testInventory.discard("dagger", testPlayer);
            testInventory.setCoins(0);
            testRoom.setRoomNum(1);

            // Write
            JsonWriter writer = new JsonWriter("./data/testWriterManyItems.json");
            writer.open();
            writer.write(testPlayer, testRoom);
            writer.close();

            // Change the game state
            testPlayer.setHealth(11);
            testInventory.discard("longsword", testPlayer);
            testInventory.discard("mace", testPlayer);
            testInventory.discard("farmer's cap", testPlayer);
            testInventory.discard("thieve's hood", testPlayer);
            testInventory.collect(itemFactory.makeHelmet(), testPlayer);
            testInventory.collect(itemFactory.makeExcalibur(), testPlayer);
            testInventory.setCoins(99);
            testRoom.setRoomNum(24);

            JsonReader reader = new JsonReader("./data/testWriterManyItems.json");
            testPlayer = new Player();
            testInventory = testPlayer.getInventory();
            reader.read(testPlayer, testInventory, testRoom); //updated player, inventory, and roomNum
            assertEquals(9, testPlayer.getHealth());
            assertEquals("Longsword", testInventory.getItem("longsword").getName());
            assertEquals("Mace", testInventory.getItem("mace").getName());
            assertEquals("Farmer's Cap", testInventory.getItem("farmer's cap").getName());
            assertEquals("Thieve's Hood", testInventory.getItem("thieve's hood").getName());
            assertEquals(4, testInventory.getItems().size());
            assertEquals(0, testInventory.getCoins());
            assertEquals(1, testRoom.getRoomNum());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
