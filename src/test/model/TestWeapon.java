package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWeapon {
    
    Weapon testDagger;
    Weapon testMace;

    @BeforeEach
    void runBefore() {
        testDagger = new Weapon("Dagger", 1, 0);
        testMace = new Weapon("Mace", 2, 2);
    }

    @Test
    void testConstructor() {
        assertEquals("Dagger", testDagger.getName());
        assertEquals(1, testDagger.getStat());
        assertEquals(0, testDagger.getValue());
    }

    @Test
    void testConstructorMace() {
        assertEquals("Mace", testMace.getName());
        assertEquals(2, testMace.getStat());
        assertEquals(2, testMace.getValue());
    }
}
