package edu.duke.tq22.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class V1ShipFactoryTest {

    @Test
    public void test_makeShip() {
        Coordinate c = new Coordinate(2, 3);
        Placement pV = new PlacementTwo(c, 'V');
        Placement pH = new PlacementTwo(c, 'H');
        V1ShipFactory factory = new V1ShipFactory();
        Ship<Character> submarine = factory.makeSubmarine(pV);
        Ship<Character> destroyer = factory.makeDestroyer(pH);
        Ship<Character> battleship = factory.makeBattleship(pV);
        Ship<Character> carrier = factory.makeCarrier(pH);
        checkShip(submarine, "Submarine", 's',
                new Coordinate(2, 3), new Coordinate(3, 3));
        checkShip(destroyer, "Destroyer", 'd',
                new Coordinate(2, 3), new Coordinate(2, 4), new Coordinate(2, 5));
        checkShip(battleship, "Battleship", 'b',
                new Coordinate(2, 3), new Coordinate(3, 3), new Coordinate(4, 3), new Coordinate(5, 3));
        checkShip(carrier, "Carrier", 'c',
                new Coordinate(2, 3), new Coordinate(2, 4), new Coordinate(2, 5),
                new Coordinate(2, 6), new Coordinate(2, 7), new Coordinate(2, 8));
    }

    private void checkShip(Ship<Character> testShip, String expectedName,
                           char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        for (Coordinate expectedCoordinate : expectedLocs) {
            assertEquals(expectedLetter, testShip.getDisplayInfoAt(expectedCoordinate, true));
        }
    }
}
