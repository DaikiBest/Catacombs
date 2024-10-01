package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBattle {
    
    private BattleHandler testBattle;
    private Player testPlayer;
    private Goblin testGoblin;
    private Orc testOrc;
    private Inventory testInventory;

    @BeforeEach
    void runBefore() {
        testBattle = new BattleHandler();
        testPlayer = new Player();
        testGoblin = new Goblin();
        testOrc = new Orc();
        testInventory = testPlayer.getInventory();
    }

    @Test
    void testDiceHandler() {
        String battleOutcome = testBattle.diceHandler(testPlayer, testGoblin);
        if (testBattle.getPlayerRoll() > testBattle.getEnemyRoll()) {
            assertEquals("won", battleOutcome);
        }
        else if (testBattle.getPlayerRoll() < testBattle.getEnemyRoll()) {
            assertEquals("loss", battleOutcome);
        } else {
            assertEquals("tie", battleOutcome);
        }
    }

    @Test
    void testEndEncounter() {
        testBattle.endEncounter(testInventory, testGoblin);
        assertEquals(6, testInventory.getCoins()); //give right amount of coins
    }

    @Test
    void testEndEncounterOrc() {
        testBattle.endEncounter(testInventory, testOrc);
        assertEquals(8, testInventory.getCoins()); //give right amount of coins
    }
}