package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBattle {
    
    private Battle testBattle;
    private Player player;
    private Enemy goblin;

    @BeforeEach
    void runBefore() {
        testBattle = new Battle();
        player = new Player();
        goblin = new Goblin();
    }

    @Test
    void testConstructor() {
        //stub
    }

    @Test
    void testDiceHandler() {
        String battleOutcome = testBattle.diceHandler();
        if (testBattle.getPlayerRoll() > testBattle.getEnemyRoll()) {
            assertEquals("won", battleOutcome);
        }
        else if (testBattle.getPlayerRoll() < testBattle.getEnemyRoll()) {
            assertEquals("loss", battleOutcome);
        } else {
            assertEquals("tie", battleOutcome);
        }
    }
}
