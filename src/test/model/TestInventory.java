package model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;


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
        testInventory = new Inventory();
        testDagger = new Weapon("Dagger", 1, 0);
        testMace = new Weapon("Mace", 2, 2);
        testCap = new Armor("Farmer's Cap", 2, 2);
        testHood = new Armor("Thieve's Hood", 4, 4);
        testPlayer = new Player();
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
        testInventory.collect(testMace);
        assertEquals("Mace", testInventory.getItem("Mace").getName());
    }

    @Test
    void testCollectSameWeapon() {
        testInventory.collect(testDagger);
        int countD = testInventory.countItem("Dagger", testInventory.getItems());
        assertEquals(2, countD);
    }

    @Test
    void testCollectManyItems() {

        testInventory.collect(testDagger);
        testInventory.collect(testMace);
        testInventory.collect(testMace);
        testInventory.collect(testMace);

        int countD = testInventory.countItem("Dagger", testInventory.getItems());
        int countM = testInventory.countItem("Mace", testInventory.getItems());

        assertEquals(2, countD);
        assertEquals(3, countM);
    }

    //tests discard()
    @Test
    void testDiscard() {
        testInventory.collect(testMace);
        testInventory.discard("Mace");
        int countM = testInventory.countItem("Mace", testInventory.getItems());
        assertEquals(0, countM);
    }

    @Test
    void testNotDiscardLastWeapon() {
        testInventory.discard("Dagger");
        int countD = testInventory.countItem("Dagger", testInventory.getItems());
        assertEquals(1, countD);
    }

    @Test
    void testNotDiscardLastWeaponNotDagger() {
        testInventory.collect(testMace);
        testInventory.discard("Dagger");
        testInventory.discard("Mace");
        int countM = testInventory.countItem("Mace", testInventory.getItems());
        assertEquals(1, countM);
    }

    //tests updateWeapon() 
    //collect() and discard() should call updateWeapon() and updateArmor()
    @Test
    void testUpdateWeapon() {
        assertEquals(1, testPlayer.getDamage());
        testInventory.collect(testMace);
        assertEquals(2, testPlayer.getDamage());
    }

    @Test
    void testUpdateWeaponAfterDiscard() {
        testInventory.collect(testMace);
        testInventory.discard("Mace");
        assertEquals(1, testPlayer.getDamage());
    }

    @Test
    void testUpdateWeaponDuplicate() {
        testInventory.collect(testMace);
        testInventory.collect(testMace);
        assertEquals(2, testPlayer.getDamage());
    }

    //tests updateArmor()
    @Test
    void testUpdateArmor() {
        assertEquals(5, testPlayer.getHealth());
        testInventory.collect(testCap);
        assertEquals(7, testPlayer.getHealth());
    }

    @Test
    void testUpdateArmorAfterDiscard() {
        testInventory.collect(testCap);
        testInventory.discard("Farmer's Cap");
        assertEquals(5, testPlayer.getHealth());
    }

    @Test
    void testUpdateArmorDuplicate() {
        testInventory.collect(testCap);
        testInventory.collect(testCap);
        assertEquals(7, testPlayer.getHealth());
    }

}
