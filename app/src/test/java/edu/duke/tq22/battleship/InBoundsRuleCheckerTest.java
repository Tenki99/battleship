package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InBoundsRuleCheckerTest {
    @Test
    public void test_checkMyRule() {
        InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20, 'X');
        AbstractShipFactory<Character> factory = new V1ShipFactory();
        Ship<Character> carrier = factory.makeCarrier(new PlacementTwo("A7H"));
        Ship<Character> destroyer = factory.makeDestroyer(new PlacementTwo("t7V"));
        Coordinate c1 = new Coordinate(-1, 1);
        Coordinate c2 = new Coordinate(1, -1);
        Ship<Character> submarine1 = factory.makeSubmarine(new PlacementTwo(c1, 'V'));
        Ship<Character> submarine2 = factory.makeSubmarine(new PlacementTwo(c2, 'H'));
        Ship<Character> submarine3 = factory.makeSubmarine(new PlacementTwo("A7H"));
        assertEquals("That placement is invalid: the ship goes off the right of the board.",
                checker.checkMyRule(carrier, board));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.",
                checker.checkMyRule(destroyer, board));
        assertEquals("That placement is invalid: the ship goes off the top of the board.",
                checker.checkMyRule(submarine1, board));
        assertEquals("That placement is invalid: the ship goes off the left of the board.",
                checker.checkMyRule(submarine2, board));
        assertNull(checker.checkMyRule(submarine3, board));
    }
}