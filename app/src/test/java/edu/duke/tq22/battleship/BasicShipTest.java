package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicShipTest {
    @Test
    public void test_hit() {
        Coordinate upperleft = new Coordinate(2, 3);
        RectangleShip<Character> rs = new RectangleShip<>("submarine", upperleft, 4, 1, 's', '*');
        rs.recordHitAt(new Coordinate(2, 3));
        rs.recordHitAt(new Coordinate(2, 5));
        assertTrue(rs.wasHitAt(new Coordinate(2, 3)));
        assertFalse(rs.wasHitAt(new Coordinate(2, 4)));
        assertTrue(rs.wasHitAt(new Coordinate(2, 5)));
        assertFalse(rs.wasHitAt(new Coordinate(2, 6)));
        assertThrows(IllegalArgumentException.class, () -> rs.recordHitAt(new Coordinate(3, 3)));
        assertThrows(IllegalArgumentException.class, () -> rs.wasHitAt(new Coordinate(2, 7)));
    }

    @Test
    public void test_sunk() {
        Coordinate upperleft = new Coordinate(2, 3);
        RectangleShip<Character> rs = new RectangleShip<>("submarine", upperleft, 4, 1, 's', '*');
        assertFalse(rs.isSunk());
        rs.recordHitAt(new Coordinate(2, 3));
        assertFalse(rs.isSunk());
        rs.recordHitAt(new Coordinate(2, 4));
        assertFalse(rs.isSunk());
        rs.recordHitAt(new Coordinate(2, 5));
        assertFalse(rs.isSunk());
        rs.recordHitAt(new Coordinate(2, 6));
        assertTrue(rs.isSunk());
    }

    @Test
    public void test_displayInfo() {
        Coordinate upperleft = new Coordinate(2, 3);
        RectangleShip<Character> rs = new RectangleShip<>(upperleft, 's', '*');
        assertEquals(rs.getDisplayInfoAt(upperleft, true), 's');
        assertNull(rs.getDisplayInfoAt(upperleft, false));
        rs.recordHitAt(upperleft);
        assertEquals(rs.getDisplayInfoAt(upperleft, true), '*');
        assertEquals(rs.getDisplayInfoAt(upperleft, false), 's');
    }
}
