package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RectangleShipTest {

    @Test
    public void test_makeCoords() {
        Coordinate upperleft = new Coordinate(2, 3);
        HashSet<Coordinate> actual = RectangleShip.makeCoords(upperleft, 4, 1);
        assertTrue(actual.contains(new Coordinate(2, 3)));
        assertTrue(actual.contains(new Coordinate(2, 4)));
        assertTrue(actual.contains(new Coordinate(2, 5)));
        assertTrue(actual.contains(new Coordinate(2, 6)));
    }

    @Test
    public void test_constructor() {
        Coordinate upperleft = new Coordinate(2, 3);
        RectangleShip<Character> rs = new RectangleShip<>("submarine", upperleft, 4, 1, 's', '*');
        assertTrue(rs.occupiesCoordinates(new Coordinate(2, 3)));
        assertTrue(rs.occupiesCoordinates(new Coordinate(2, 4)));
        assertTrue(rs.occupiesCoordinates(new Coordinate(2, 5)));
        assertTrue(rs.occupiesCoordinates(new Coordinate(2, 6)));
        assertFalse(rs.occupiesCoordinates(new Coordinate(5, 4)));
    }
}
