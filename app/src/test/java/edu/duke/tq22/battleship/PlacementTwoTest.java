package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementTwoTest {
    @Test
    public void test_constructor() {
        Coordinate c = new Coordinate("A9");
        Placement p1 = new PlacementTwo(c, 'V');
        Placement p2 = new PlacementTwo(c, 'h');
        assertEquals(p1.getWhere(), c);
        assertEquals(p1.getOrientation(), 'V');
        assertEquals(p2.getWhere(), c);
        assertEquals(p2.getOrientation(), 'H');
    }

    @Test
    public void test_constructor_error_orientation() {
        Coordinate c = new Coordinate("A9");
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo(c, 'A'));
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo(c, 'b'));
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo(c, '?'));
    }

    @Test
    void test_string_constructor_valid_cases() {
        Placement p1 = new PlacementTwo("B3V");
        assertEquals(1, p1.getWhere().getRow());
        assertEquals(3, p1.getWhere().getCol());
        assertEquals('V', p1.getOrientation());
        Placement p2 = new PlacementTwo("D5h");
        assertEquals(3, p2.getWhere().getRow());
        assertEquals(5, p2.getWhere().getCol());
        assertEquals('H', p2.getOrientation());
        Placement p3 = new PlacementTwo("a9H");
        assertEquals(0, p3.getWhere().getRow());
        assertEquals(9, p3.getWhere().getCol());
        assertEquals('H', p3.getOrientation());
        Placement p4 = new PlacementTwo("z0v");
        assertEquals(25, p4.getWhere().getRow());
        assertEquals(0, p4.getWhere().getCol());
        assertEquals('V', p4.getOrientation());

    }

    @Test
    public void test_string_constructor_error_cases() {
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo("000"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo("AAA"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo("@0@"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo("[0]"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo("A/V"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo("A0?"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo("A0"));
        assertThrows(IllegalArgumentException.class, () -> new PlacementTwo("A0VV"));
    }
}