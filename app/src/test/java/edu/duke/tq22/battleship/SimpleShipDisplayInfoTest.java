package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleShipDisplayInfoTest {

    @Test
    void test_getInfo() {
        SimpleShipDisplayInfo<Character> info = new SimpleShipDisplayInfo<>('s', '*');
        Coordinate c = new Coordinate("A8");
        assertEquals(info.getInfo(c, true), '*');
        assertEquals(info.getInfo(c, false), 's');
    }
}