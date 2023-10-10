package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementFourTest {
    @Test
    public void test_constructor() {
        Coordinate c = new Coordinate("A9");
        Placement p1 = new PlacementFour(c, 'L');
        Placement p2 = new PlacementFour(c, 'r');
        assertEquals(p1.getWhere(), c);
        assertEquals(p1.getOrientation(), 'L');
        assertEquals(p2.getWhere(), c);
        assertEquals(p2.getOrientation(), 'R');
    }

    @Test
    public void test_constructor_error_orientation() {
        Coordinate c = new Coordinate("A9");
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour(c, 'V'));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour(c, 'h'));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour(c, 'b'));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour(c, '?'));
    }

    @Test
    void test_string_constructor_valid_cases() {
        Placement p1 = new PlacementFour("B3L");
        assertEquals(1, p1.getWhere().getRow());
        assertEquals(3, p1.getWhere().getCol());
        assertEquals('L', p1.getOrientation());
        Placement p2 = new PlacementFour("D5r");
        assertEquals(3, p2.getWhere().getRow());
        assertEquals(5, p2.getWhere().getCol());
        assertEquals('R', p2.getOrientation());
        Placement p3 = new PlacementFour("a9U");
        assertEquals(0, p3.getWhere().getRow());
        assertEquals(9, p3.getWhere().getCol());
        assertEquals('U', p3.getOrientation());
        Placement p4 = new PlacementFour("z0d");
        assertEquals(25, p4.getWhere().getRow());
        assertEquals(0, p4.getWhere().getCol());
        assertEquals('D', p4.getOrientation());

    }

    @Test
    public void test_string_constructor_error_cases() {
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour("A0V"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour("000"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour("AAA"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour("@0@"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour("[0]"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour("A/V"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour("A0?"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour("A0"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementFour("A0VV"));
    }
}