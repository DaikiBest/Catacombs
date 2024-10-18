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
 
public class TestJsonWriter {
    
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
            setGameState(3, 9, 6);
            testInventory.collect(excalibur, testPlayer);
            testRoom.increaseRoomNum(); //roomNum is 7

            // Write
            JsonWriter writer = new JsonWriter("./data/testWriterOneItem.json");
            writer.open();
            writer.write(testPlayer, testRoom);
            writer.close();

            // Reset game state
            setGameState(5, 5, 0);
            testInventory.discard("excalibur", testPlayer);

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
            setGameState(9, 0, 1);
            testInventory.collect(itemFactory.makeLongsword(), testPlayer);
            testInventory.collect(itemFactory.makeHood(), testPlayer);

            // Write
            JsonWriter writer = new JsonWriter("./data/testWriterManyItems.json");
            writer.open();
            writer.write(testPlayer, testRoom);
            writer.close();

            // Change the game state
            setGameState(11, 99, 24);
            testInventory.discard("longsword", testPlayer);
            testInventory.collect(itemFactory.makeHelmet(), testPlayer);

            JsonReader reader = new JsonReader("./data/testWriterManyItems.json");
            testPlayer = new Player();
            testInventory = testPlayer.getInventory();
            reader.read(testPlayer, testInventory, testRoom); //updated player, inventory, and roomNum
            assertEquals(9, testPlayer.getHealth());
            assertEquals("Longsword", testInventory.getItem("longsword").getName());
            assertEquals("Thieve's Hood", testInventory.getItem("thieve's hood").getName());
            assertEquals(2, testInventory.getItems().size());
            assertEquals(0, testInventory.getCoins());
            assertEquals(1, testRoom.getRoomNum());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    private void setGameState(int hp, int coins, int roomNum) {
        testPlayer.setHealth(hp);
        testInventory.setCoins(coins);
        testRoom.setRoomNum(roomNum);
    }
}
