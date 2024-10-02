package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDarkWizard {
    
    DarkWizard testDarkWizard;

    @BeforeEach
    void runBefore() {
        testDarkWizard = new DarkWizard();
    }

    @Test
    void testConstructor() {
        assertEquals("Dark Wizard", testDarkWizard.getName());
        assertEquals(8, testDarkWizard.getHealth());
        assertEquals(5, testDarkWizard.getDamage());
    }

    @Test
    void testRollDice() {
        for (int i = 0; i < 300 ; i++) {
            int roll = testDarkWizard.rollDice();
            assertTrue(10 <= roll & roll <= 20);
        }
    }
}
