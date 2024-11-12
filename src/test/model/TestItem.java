package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestItem {
    Item testDagger;
    Item testMace;
    Item testCap;
    Item testHelmet;
    ItemFactory itemFactory;

    @BeforeEach
    void runBefore() {
        itemFactory = new ItemFactory();
        testDagger = itemFactory.makeItem("Dagger");
        testMace = itemFactory.makeItem("Mace");
        testCap = itemFactory.makeItem("fArmEr's CAp");
        testHelmet = itemFactory.makeItem("KNIGHT'S HELMET");
    }

    @Test
    void testConstructorDagger() {
        assertEquals("Dagger", testDagger.getName());
        assertEquals(1, testDagger.getStat());
        assertEquals(1, testDagger.getValue());
        assertEquals(0, testDagger.getRefine());
    }

    @Test
    void testConstructorHelmet() {
        assertEquals("Knight's Helmet", testHelmet.getName());
        assertEquals(12, testHelmet.getStat());
        assertEquals(6, testHelmet.getValue());
        assertEquals(0, testDagger.getRefine());
    }

    @Test
    void testRefine() {
        testDagger.refine();
        assertEquals(1, testDagger.getRefine());
        testDagger.refine();
        assertEquals(2, testDagger.getRefine());
    }

    @Test
    void testRefinePastMaxRefine() {
        testDagger.refine();
        testDagger.refine();
        testDagger.refine();
        assertEquals(3, testDagger.getRefine());
        testDagger.refine();
        testDagger.refine();
        assertEquals(3, testDagger.getRefine());
    }
}
