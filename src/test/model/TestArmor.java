package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestArmor {
    
    private Armor testCap;
    private Armor testHood;

    @BeforeEach
    void runBefore() {
        testCap = new Armor("Farmer's Cap", 7, 2);
        testHood = new Armor("Thieve's Hood", 9, 4);
    }

    @Test
    void testConstructor() {
        assertEquals("Farmer's Cap", testCap.getName());
        assertEquals(7, testCap.getStat());
        assertEquals(2, testCap.getValue());
    }

    @Test
    void testConstructorHood() {
        assertEquals("Thieve's Hood", testHood.getName());
        assertEquals(9, testHood.getStat());
        assertEquals(4, testHood.getValue());
    }
}
