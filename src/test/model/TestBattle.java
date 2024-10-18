package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBattle {
    
    private BattleHandler testBattle;
    private Player testPlayer;
    private Goblin testGoblin;
    private Orc testOrc;
    private Inventory testInventory;
    private ItemFactory itemFactory = new ItemFactory();
    private Item testExcalibur;

    @BeforeEach
    void runBefore() {
        testBattle = new BattleHandler();
        testPlayer = new Player();
        testGoblin = new Goblin();
        testOrc = new Orc();
        testInventory = testPlayer.getInventory();
        testExcalibur = itemFactory.makeExcalibur();

        testInventory.collect(itemFactory.makeDagger(), testPlayer);
    }

    @Test
    void testDiceHandlerWin() {
        String battleOutcome = testBattle.diceHandler(testPlayer, testGoblin, 8, 7);
        assertEquals(1, testGoblin.getHealth());
        assertEquals("win", battleOutcome);
    }

    @Test
    void testDiceHandlerWinKill() {
        testBattle.diceHandler(testPlayer, testGoblin, 8, 7);
        assertTrue(testGoblin.getAlive());
        testBattle.diceHandler(testPlayer, testGoblin, 8, 7);
        assertEquals(0, testGoblin.getHealth());
        assertFalse(testGoblin.getAlive());
    }

    @Test
    void testDiceHandlerWinOverkill() {
        testInventory.collect(testExcalibur, testPlayer);
        testBattle.diceHandler(testPlayer, testGoblin, 8, 7);
        assertEquals(-3, testGoblin.getHealth());
        assertFalse(testGoblin.getAlive());
    }

    @Test
    void testDiceHandlerLose() {
        String battleOutcome = testBattle.diceHandler(testPlayer, testGoblin, 7, 8);
        assertEquals(4, testPlayer.getHealth());
        assertEquals("lose", battleOutcome);
    }

    @Test
    void testDiceHandlerLoseDie0HP() {
        testPlayer.setHealth(2);
        assertTrue(testPlayer.getAlive());
        testBattle.diceHandler(testPlayer, testOrc, 7, 8);
        assertFalse(testPlayer.getAlive());
        testPlayer.setHealth(0);
    }

    @Test
    void testDiceHandlerLoseDieNegHP() {
        testPlayer.setHealth(1);
        assertTrue(testPlayer.getAlive());
        testBattle.diceHandler(testPlayer, testOrc, 7, 8);
        assertFalse(testPlayer.getAlive());
        testPlayer.setHealth(-1);
    }

    @Test
    void testDiceHandlerTie() {
        String battleOutcome = testBattle.diceHandler(testPlayer, testGoblin, 7, 7);
        assertEquals(2, testGoblin.getHealth());
        assertEquals(5, testPlayer.getHealth());
        assertEquals("tie", battleOutcome);
    }

    @Test
    void testGiveRewards() {
        testBattle.giveRewards(testInventory, testGoblin);
        assertEquals(6, testInventory.getCoins()); //give right amount of coins
    }

    @Test
    void testGiveRewardsOrc() {
        testBattle.giveRewards(testInventory, testOrc);
        assertEquals(8, testInventory.getCoins()); //give right amount of coins
    }
}