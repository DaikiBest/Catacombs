package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlayer {
    
    private Player testPlayer;
    private Inventory testInventory;
    private ItemHandler itemHandler;
    private Item testMace;
    private Item testCap;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player();
        itemHandler = new ItemHandler();
        testInventory = testPlayer.getInventory();
        testMace = itemHandler.makeMace();
        testCap = itemHandler.makeCap();
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
        for (int i = 0; i < 300 ; i++) {
            int roll = testPlayer.rollDice();
            assertTrue(1 <= roll & roll <= 20);
        }
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
