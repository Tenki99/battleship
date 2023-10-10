package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CarrierTest {
    @Test
    public void test_makeCoords() {
        Placement where1 = new PlacementFour("C3U");
        Placement where2 = new PlacementFour("C3D");
        Placement where3 = new PlacementTwo("C3H");
        HashSet<Coordinate> actual1 = Carrier.makeCoords(where1);
        HashSet<Coordinate> actual2 = Carrier.makeCoords(where2);

        assertTrue(actual1.contains(new Coordinate(2, 3)));
        assertTrue(actual1.contains(new Coordinate(3, 3)));
        assertTrue(actual1.contains(new Coordinate(4, 3)));
        assertTrue(actual1.contains(new Coordinate(5, 3)));
        assertTrue(actual1.contains(new Coordinate(4, 4)));
        assertTrue(actual1.contains(new Coordinate(5, 4)));
        assertTrue(actual1.contains(new Coordinate(6, 4)));

        assertTrue(actual2.contains(new Coordinate(2, 3)));
        assertTrue(actual2.contains(new Coordinate(3, 3)));
        assertTrue(actual2.contains(new Coordinate(4, 3)));
        assertTrue(actual2.contains(new Coordinate(3, 4)));
        assertTrue(actual2.contains(new Coordinate(4, 4)));
        assertTrue(actual2.contains(new Coordinate(5, 4)));
        assertTrue(actual2.contains(new Coordinate(6, 4)));

        assertThrows(IllegalArgumentException.class, ()->Carrier.makeCoords(where3));
    }

    @Test
    public void test_constructor() {
        Placement where1 = new PlacementFour("C3L");
        Placement where2 = new PlacementFour("C3R");
        Carrier<Character> rs1 = new Carrier<>(where1, 's', '*');
        Carrier<Character> rs2 = new Carrier<>(where2, 's', '*');

        assertTrue(rs1.occupiesCoordinates(new Coordinate(2, 5)));
        assertTrue(rs1.occupiesCoordinates(new Coordinate(2, 6)));
        assertTrue(rs1.occupiesCoordinates(new Coordinate(2, 7)));
        assertTrue(rs1.occupiesCoordinates(new Coordinate(3, 3)));
        assertTrue(rs1.occupiesCoordinates(new Coordinate(3, 4)));
        assertTrue(rs1.occupiesCoordinates(new Coordinate(3, 5)));
        assertTrue(rs1.occupiesCoordinates(new Coordinate(3, 6)));
        assertFalse(rs1.occupiesCoordinates(new Coordinate(7, 4)));

        assertTrue(rs2.occupiesCoordinates(new Coordinate(2, 4)));
        assertTrue(rs2.occupiesCoordinates(new Coordinate(2, 5)));
        assertTrue(rs2.occupiesCoordinates(new Coordinate(2, 6)));
        assertTrue(rs2.occupiesCoordinates(new Coordinate(2, 7)));
        assertTrue(rs2.occupiesCoordinates(new Coordinate(3, 3)));
        assertTrue(rs2.occupiesCoordinates(new Coordinate(3, 4)));
        assertTrue(rs2.occupiesCoordinates(new Coordinate(3, 5)));
        assertFalse(rs2.occupiesCoordinates(new Coordinate(7, 4)));



    }
}