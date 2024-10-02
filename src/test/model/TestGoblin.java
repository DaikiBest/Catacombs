package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGoblin {
    
    Goblin testGoblin;

    @BeforeEach
    void runBefore() {
        testGoblin = new Goblin();
    }

    @Test
    void testRollDice() {
        for (int i = 0; i < 300 ; i++) {
            int roll = testGoblin.rollDice();
            assertTrue(4 <= roll & roll <= 8);
        }
    }
}
