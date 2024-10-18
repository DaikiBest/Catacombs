package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlayer {
    
    private Player testPlayer;
    private Inventory testInventory;
    private ItemFactory itemFactory;
    private Item testMace;
    private Item testCap;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player();
        itemFactory = new ItemFactory();
        testInventory = testPlayer.getInventory();
        testMace = itemFactory.makeMace();
        testCap = itemFactory.makeCap();

        testInventory.collect(itemFactory.makeDagger(), testPlayer);
    }

    @Test
    void testConstructor() {
        assertEquals(5, testPlayer.getHealth());
        assertEquals(5, testPlayer.getMaxHP());
        assertEquals(1, testPlayer.getDamage());
    }

    @Test
    void testHealFullHP() {
        testPlayer.heal();
        assertEquals(5, testPlayer.getHealth());
    }

    @Test
    void testHeal() {
        testPlayer.setMaxHP(6);
        testPlayer.setHealth(5);
        assertEquals(5, testPlayer.getHealth());
        assertEquals(6, testPlayer.getMaxHP());
        testPlayer.heal();
        assertEquals(6, testPlayer.getHealth());
    }

    @Test
    void testRollDice() {
        int roll = testPlayer.rollDice(1);
        assertEquals(6, roll);
    }

    //tests updateWeapon() 
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
        assertEquals(7, testPlayer.getHealth());
    }

    @Test
    void testUpdateArmorAfterDiscard() {
        testInventory.collect(testCap, testPlayer);
        testInventory.discard("Farmer's Cap", testPlayer);
        assertEquals(5, testPlayer.getMaxHP());
        assertEquals(5, testPlayer.getHealth());
    }

    @Test
    void testUpdateArmorNotIncreaseHP() {
        testInventory.collect(testCap, testPlayer);
        testPlayer.setHealth(1);
        testInventory.discard("Farmer's Cap", testPlayer);
        assertEquals(5, testPlayer.getMaxHP());
        assertEquals(1, testPlayer.getHealth()); //updated, but did not increase
    }

    @Test
    void testUpdateArmorDuplicate() {
        testInventory.collect(testCap, testPlayer);
        testInventory.collect(testCap, testPlayer);
        assertEquals(7, testPlayer.getMaxHP());
    }
}
