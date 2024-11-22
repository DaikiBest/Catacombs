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
    private ItemFactory itemFactory = new ItemFactory();

    @BeforeEach
    void runBefore() {
        testPlayer = new Player();
        testInventory = testPlayer.getInventory();
        testDagger = itemFactory.makeItem("dagger");
        testMace = itemFactory.makeItem("mace");
        testCap = itemFactory.makeItem("farmer's cap");
        testHood = itemFactory.makeItem("thieve's hood");

        testInventory.collect(itemFactory.makeItem("Dagger"), testPlayer);
    }

    //test Inventory()
    @Test
    void testConstructor() {
        assertEquals("Dagger", testInventory.getItem("Dagger").getName());
        for (Item item : testInventory.getItems()) {
            assertFalse(item instanceof Armor); 
        } //check if items of type Armor are in inventory
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
        int countD = testInventory.selectItem("Dagger").size();
        assertEquals(2, countD);
    }

    @Test
    void testCollectManyItems() {

        testInventory.collect(testDagger, testPlayer);
        testInventory.collect(testMace, testPlayer);
        testInventory.collect(testMace, testPlayer);
        testInventory.collect(testMace, testPlayer);

        int countD = testInventory.selectItem("Dagger").size();
        int countM = testInventory.selectItem("Mace").size();

        assertEquals(2, countD);
        assertEquals(3, countM);
    }

    @Test
    void testCollectArmor() {
        testInventory.collect(testCap, testPlayer);
        testInventory.collect(testHood, testPlayer);
        assertEquals("Farmer's Cap", testInventory.getItem("Farmer's Cap").getName());
        assertEquals("Thieve's Hood", testInventory.getItem("Thieve's Hood").getName());
    }

    //tests discard()
    @Test
    void testDiscard() {
        testInventory.collect(testMace, testPlayer);
        testInventory.discard(1, testPlayer);
        assertEquals(null, testInventory.getItem("Mace"));
    }
    
    @Test
    void testDiscardOnlyOneCopy() {
        testInventory.collect(testMace, testPlayer);
        testInventory.collect(testMace, testPlayer);
        testInventory.discard(1, testPlayer);
        int countM = testInventory.selectItem("Mace").size();
        assertEquals(1, countM);
    }

    @Test
    void testDiscardArmor() {
        testInventory.collect(testCap, testPlayer);
        testInventory.collect(testHood, testPlayer);
        testInventory.discard(2, testPlayer);
        testInventory.discard(1, testPlayer);
        assertEquals(null, testInventory.getItem("Farmer's Cap"));
        assertEquals(null, testInventory.getItem("Thieve's Hood"));
    }

    @Test
    void testGetEquippedWeapon() {
        Item testMace2 = itemFactory.makeItem("mace");
        Item testMace3 = itemFactory.makeItem("mace");
        testInventory.collect(testMace, testPlayer);
        testInventory.collect(testMace2, testPlayer);
        testInventory.collect(testMace3, testPlayer);
        testMace.refine();
        testMace2.refine();
        testMace2.refine();
        testMace2.refine();
        testMace2.refine();
        testMace3.refine();
        testMace3.refine();
        assertEquals(testMace2, testInventory.getEquippedWeapon());
        assertEquals(3, testMace2.getRefine());
    }

    @Test
    void testGetEquippedArmor() {
        Item testCap2 = itemFactory.makeItem("farmer's cap");
        Item testCap3 = itemFactory.makeItem("farmer's cap");
        testInventory.collect(testCap, testPlayer);
        testInventory.collect(testCap2, testPlayer);
        testInventory.collect(testCap3, testPlayer);
        testCap.refine();
        testCap2.refine();
        testCap3.refine();
        testCap3.refine();
        assertEquals(2, testCap3.getRefine());
        assertEquals(testCap3, testInventory.getEquippedArmor());
    }

    @Test
    void testGetEquippedArmorEdgeCases() {
        assertEquals(null, testInventory.getEquippedArmor());
        testInventory.collect(testCap, testPlayer);
        assertEquals(testCap, testInventory.getEquippedArmor());
        testInventory.collect(testHood, testPlayer);
        testInventory.collect(testCap, testPlayer);
        assertEquals(testHood, testInventory.getEquippedArmor());
    }

    @Test
    void testGetEquippedWeaponEdgeCases() {
        testInventory.collect(testMace, testPlayer);
        assertEquals(testMace, testInventory.getEquippedWeapon());
    }
}