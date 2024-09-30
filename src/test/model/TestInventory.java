package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInventory {
    
    private Inventory testInventory;
    private Item testDagger;
    private Item testMace;
    private Item testCap;
    private Item testHood;
    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player();
        testInventory = new Inventory(testPlayer);
        testDagger = new Weapon("Dagger", 1, 0);
        testMace = new Weapon("Mace", 2, 2);
        testCap = new Armor("Farmer's Cap", 7, 2);
        testHood = new Armor("Thieve's Hood", 9, 4);
    }

    //test Inventory()
    @Test
    void testConstructor() {
        assertEquals("Dagger", testInventory.getItem("Dagger").getName());
        for (Item item : testInventory.getItems()) {
            assertFalse(item instanceof Armor); 
            }; //check if items of type Armor are in inventory
        assertEquals(5, testInventory.getCoins());
    }

    //tests collect()
    @Test
    void testCollect() {
        testInventory.collect(testMace, testPlayer);
        assertEquals("Mace", testInventory.getItem("Mace").getName());
    }

    @Test
    void testCollectSameWeapon() {
        testInventory.collect(testDagger, testPlayer);
        int countD = testInventory.countItem("Dagger", testInventory.getItems());
        assertEquals(2, countD);
    }

    @Test
    void testCollectManyItems() {

        testInventory.collect(testDagger, testPlayer);
        testInventory.collect(testMace, testPlayer);
        testInventory.collect(testMace, testPlayer);
        testInventory.collect(testMace, testPlayer);

        int countD = testInventory.countItem("Dagger", testInventory.getItems());
        int countM = testInventory.countItem("Mace", testInventory.getItems());

        assertEquals(2, countD);
        assertEquals(3, countM);
    }

    //tests discard()
    @Test
    void testDiscard() {
        testInventory.collect(testMace, testPlayer);
        testInventory.discard("Mace", testPlayer);
        int countM = testInventory.countItem("Mace", testInventory.getItems());
        assertEquals(0, countM);
    }

    @Test
    void testNotDiscardLastWeapon() {
        testInventory.discard("Dagger", testPlayer);
        int countD = testInventory.countItem("Dagger", testInventory.getItems());
        assertEquals(1, countD);
    }
    
    @Test
    void testDiscardOnlyOneCopy() {
        testInventory.collect(testMace, testPlayer);
        testInventory.collect(testMace, testPlayer);
        testInventory.discard("Mace", testPlayer);
        int countM = testInventory.countItem("Mace", testInventory.getItems());
        assertEquals(1, countM);
    }

    @Test
    void testNotDiscardLastWeaponNotDagger() {
        testInventory.collect(testMace, testPlayer);
        testInventory.discard("Dagger", testPlayer);
        testInventory.discard("Mace", testPlayer);
        int countM = testInventory.countItem("Mace", testInventory.getItems());
        assertEquals(1, countM);
    }

    //tests updateWeapon() 
    //collect() and discard() always call either updateWeapon() or updateArmor().
    @Test
    void testUpdateWeapon() {
        assertEquals(1, testPlayer.getDamage());
        testInventory.collect(testMace, testPlayer);
        assertEquals(2, testPlayer.getDamage());
    }

    @Test
    void testUpdateWeaponAfterDiscard() {
        testInventory.collect(testMace, testPlayer);
        testInventory.discard("Mace", testPlayer);
        assertEquals(1, testPlayer.getDamage());
    }

    @Test
    void testUpdateWeaponDuplicate() {
        testInventory.collect(testMace, testPlayer);
        testInventory.collect(testMace, testPlayer);
        assertEquals(2, testPlayer.getDamage());
    }

    //tests updateArmor()
    @Test
    void testUpdateArmor() {
        assertEquals(5, testPlayer.getHealth());
        testInventory.collect(testCap, testPlayer);
        assertEquals(7, testPlayer.getMaxHP());
    }

    @Test
    void testUpdateArmorAfterDiscard() {
        testInventory.collect(testCap, testPlayer);
        testInventory.discard("Farmer's Cap", testPlayer);
        assertEquals(5, testPlayer.getMaxHP());
    }

    @Test
    void testUpdateArmorDuplicate() {
        testInventory.collect(testCap, testPlayer);
        testInventory.collect(testCap, testPlayer);
        assertEquals(7, testPlayer.getMaxHP());
    }

}
