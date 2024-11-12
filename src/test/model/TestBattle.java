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
    private Item testCrown;

    @BeforeEach
    void runBefore() {
        testBattle = new BattleHandler();
        testPlayer = new Player();
        testGoblin = new Goblin();
        testOrc = new Orc();
        testInventory = testPlayer.getInventory();
        testExcalibur = itemFactory.makeExcalibur();
        testCrown = itemFactory.makeCrown();

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

    @Test
    void testRefineWeaponBoost() {
        testInventory.collect(testExcalibur, testPlayer);
        testExcalibur.refine();
        testBattle.diceHandler(testPlayer, testGoblin, 6, 7);//7-7
        assertEquals(2, testGoblin.getHealth());
        assertEquals(5, testPlayer.getHealth());
        testExcalibur.refine();
        testExcalibur.refine();
        testExcalibur.refine();
        testBattle.diceHandler(testPlayer, testGoblin, 5, 8);//8-8
        assertEquals(2, testGoblin.getHealth());
        assertEquals(5, testPlayer.getHealth());
        testBattle.diceHandler(testPlayer, testGoblin, 4, 8);//7-8
        assertEquals(2, testGoblin.getHealth());
        assertEquals(4, testPlayer.getHealth());
    }

    @Test
    void testRefineArmorBoost() {
        testInventory.collect(testCrown, testPlayer);
        testCrown.refine();
        testBattle.diceHandler(testPlayer, testGoblin, 6, 7);//6-6
        assertEquals(2, testGoblin.getHealth());
        assertEquals(15, testPlayer.getHealth());
        testCrown.refine();
        testCrown.refine();
        testCrown.refine();
        testBattle.diceHandler(testPlayer, testGoblin, 5, 8);//5-5
        assertEquals(2, testGoblin.getHealth());
        assertEquals(15, testPlayer.getHealth());
        testBattle.diceHandler(testPlayer, testGoblin, 4, 8);//4-5
        assertEquals(2, testGoblin.getHealth());
        assertEquals(14, testPlayer.getHealth());
        
        testInventory.collect(testExcalibur, testPlayer);
        testExcalibur.refine();
        testExcalibur.refine();
        testBattle.diceHandler(testPlayer, testGoblin, 3, 8);//5-5
        assertEquals(2, testGoblin.getHealth());
        assertEquals(14, testPlayer.getHealth());
    }

    @Test
    void testRefineTopEdge() {
        testInventory.collect(testCrown, testPlayer);
        testInventory.collect(testExcalibur, testPlayer);
        testExcalibur.refine();
        testExcalibur.refine();
        testExcalibur.refine();
        testBattle.diceHandler(testPlayer, testGoblin, 20, 20);//20-20
        assertEquals(2, testGoblin.getHealth());
        assertEquals(15, testPlayer.getHealth());
    }

    @Test
    void testRefineBotEdge() {
        testInventory.collect(testCrown, testPlayer);
        testInventory.collect(testExcalibur, testPlayer);
        testCrown.refine();
        testCrown.refine();
        testCrown.refine();
        testBattle.diceHandler(testPlayer, testGoblin, 0, 0);//0-0
        assertEquals(2, testGoblin.getHealth());
        assertEquals(15, testPlayer.getHealth());
    }
}