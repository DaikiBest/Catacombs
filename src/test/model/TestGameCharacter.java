package model;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Before;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGameCharacter {
    
    GameCharacter testCharacter;

    @BeforeEach
    void runBefore() {
        testCharacter = new GameCharacter();
    }

    @Test
    void testConstructor() {

    }
}
