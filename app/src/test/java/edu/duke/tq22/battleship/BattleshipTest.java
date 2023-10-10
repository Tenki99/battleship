package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class BattleshipTest {
    @Test
    public void test_makeCoords() {
        Placement where1 = new PlacementFour("C3U");
        Placement where2 = new PlacementFour("C3D");
        Placement where3 = new PlacementTwo("C3V");
        HashSet<Coordinate> actual1 = Battleship.makeCoords(where1);
        HashSet<Coordinate> actual2 = Battleship.makeCoords(where2);

        assertTrue(actual1.contains(new Coordinate(3, 3)));
        assertTrue(actual1.contains(new Coordinate(2, 4)));
        assertTrue(actual1.contains(new Coordinate(3, 5)));
        assertTrue(actual1.contains(new Coordinate(3, 4)));

        assertTrue(actual2.contains(new Coordinate(2, 3)));
        assertTrue(actual2.contains(new Coordinate(2, 4)));
        assertTrue(actual2.contains(new Coordinate(2, 5)));
        assertTrue(actual2.contains(new Coordinate(3, 4)));

        assertThrows(IllegalArgumentException.class, ()->Battleship.makeCoords(where3));
    }

    @Test
    public void test_constructor() {
        Placement where1 = new PlacementFour("C3L");
        Placement where2 = new PlacementFour("C3R");
        Battleship<Character> rs1 = new Battleship<>(where1, 's', '*');
        Battleship<Character> rs2 = new Battleship<>(where2, 's', '*');

        assertTrue(rs1.occupiesCoordinates(new Coordinate(2, 4)));
        assertTrue(rs1.occupiesCoordinates(new Coordinate(3, 4)));
        assertTrue(rs1.occupiesCoordinates(new Coordinate(4, 4)));
        assertTrue(rs1.occupiesCoordinates(new Coordinate(3, 3)));
        assertFalse(rs1.occupiesCoordinates(new Coordinate(5, 4)));

        assertTrue(rs2.occupiesCoordinates(new Coordinate(2, 3)));
        assertTrue(rs2.occupiesCoordinates(new Coordinate(3, 3)));
        assertTrue(rs2.occupiesCoordinates(new Coordinate(4, 3)));
        assertTrue(rs2.occupiesCoordinates(new Coordinate(3, 4)));
        assertFalse(rs2.occupiesCoordinates(new Coordinate(5, 4)));
    }
}