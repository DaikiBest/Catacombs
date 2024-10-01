package model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPlayer {
    
    private Player testPlayer;
    private Inventory testInventory;
    private Weapon testMace;
    private Armor testCap;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player();
        testInventory = testPlayer.getInventory();
        testMace = new Weapon("Mace", 2, 2);
        testCap = new Armor("Farmer's Cap", 7, 2);
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
