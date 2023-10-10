package edu.duke.tq22.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {

    @Test
    public void test_equals() {
        Coordinate c1 = new Coordinate("C9");
        Coordinate c2 = new Coordinate("G2");
        Placement p1 = new PlacementTwo(c1, 'V');
        Placement p2 = new PlacementTwo(c1, 'v');
        Placement p3 = new PlacementTwo(c2, 'V');
        Placement p4 = new PlacementTwo(c1, 'H');
        Placement p5 = new PlacementTwo("C9V");
        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
        assertNotEquals(p1, c1);
        assertEquals(p1, p5);
    }

    @Test
    public void test_toString() {
        Coordinate c1 = new Coordinate("C9");
        Placement p1 = new PlacementTwo(c1, 'V');
        Placement p2 = new PlacementTwo(c1, 'h');
        assertEquals(p1.toString(), "(2, 9)V");
        assertEquals(p2.toString(), "(2, 9)H");
    }

    @Test
    public void test_hashCode() {
        Coordinate c1 = new Coordinate("C9");
        Coordinate c2 = new Coordinate("G2");
        Placement p1 = new PlacementTwo(c1, 'V');
        Placement p2 = new PlacementTwo(c1, 'v');
        Placement p3 = new PlacementTwo(c2, 'V');
        Placement p4 = new PlacementTwo(c1, 'H');
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertNotEquals(p1.hashCode(), p4.hashCode());
    }


}
