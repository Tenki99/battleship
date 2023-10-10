package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V2ShipFactoryTest {
    @Test
    public void test_makeShip() {
        Coordinate c = new Coordinate(2, 3);
        Placement pV = new PlacementTwo(c, 'V');
        Placement pH = new PlacementTwo(c, 'H');
        Placement pU = new PlacementFour(c, 'U');
        Placement pD = new PlacementFour(c, 'D');
        Placement pL = new PlacementFour(c, 'L');
        Placement pR = new PlacementFour(c, 'R');
        V2ShipFactory factory = new V2ShipFactory();
        Ship<Character> submarine = factory.makeSubmarine(pV);
        Ship<Character> destroyer = factory.makeDestroyer(pH);
        Ship<Character> battleship1 = factory.makeBattleship(pU);
        Ship<Character> battleship2 = factory.makeBattleship(pL);
        Ship<Character> carrier1 = factory.makeCarrier(pD);
        Ship<Character> carrier2 = factory.makeCarrier(pR);
        checkShip(submarine, "Submarine", 's',
                new Coordinate(2, 3), new Coordinate(3, 3));
        checkShip(destroyer, "Destroyer", 'd',
                new Coordinate(2, 3), new Coordinate(2, 4), new Coordinate(2, 5));
        checkShip(battleship1, "Battleship", 'b',
                new Coordinate(2, 4), new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(3, 5));
        checkShip(battleship2, "Battleship", 'b',
                new Coordinate(2, 4), new Coordinate(3, 4), new Coordinate(4, 4), new Coordinate(3, 3));
        checkShip(carrier1, "Carrier", 'c',
                new Coordinate(2, 3), new Coordinate(3, 3), new Coordinate(4, 3),
                new Coordinate(3, 4), new Coordinate(4, 4), new Coordinate(5, 4), new Coordinate(6, 4));
        checkShip(carrier2, "Carrier", 'c',
                new Coordinate(3, 3), new Coordinate(2, 4), new Coordinate(2, 5),
                new Coordinate(2, 6), new Coordinate(2, 7), new Coordinate(3, 4), new Coordinate(3, 5));
    }

    private void checkShip(Ship<Character> testShip, String expectedName,
                           char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        for (Coordinate expectedCoordinate : expectedLocs) {
            assertEquals(expectedLetter, testShip.getDisplayInfoAt(expectedCoordinate, true));
        }
    }
}