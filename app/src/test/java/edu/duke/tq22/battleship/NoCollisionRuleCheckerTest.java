package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoCollisionRuleCheckerTest {
    @Test
    public void test_NoCollisionRuleChecker() {
        NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20, 'X');
        AbstractShipFactory<Character> factory = new V1ShipFactory();
        Ship<Character> submarine = factory.makeSubmarine(new PlacementTwo("E7H"));
        Ship<Character> carrier = factory.makeCarrier(new PlacementTwo("A7V"));
        Ship<Character> destroyer = factory.makeDestroyer(new PlacementTwo("A7V"));
        assertNull(checker.checkPlacement(submarine, board));
        board.tryAddShip(submarine);
        assertEquals("That placement is invalid: the ship overlaps another ship.",
                checker.checkPlacement(carrier, board));
        assertNull(checker.checkPlacement(destroyer, board));
    }

    @Test
    public void test_CombinationChecker() {
        NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(new InBoundsRuleChecker<>(null));
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 20, 'X');
        AbstractShipFactory<Character> factory = new V1ShipFactory();
        Ship<Character> submarine = factory.makeSubmarine(new PlacementTwo("E7H"));
        Ship<Character> carrier1 = factory.makeCarrier(new PlacementTwo("A7V"));
        Ship<Character> carrier2 = factory.makeCarrier(new PlacementTwo("A8H"));
        Ship<Character> destroyer = factory.makeDestroyer(new PlacementTwo("A7V"));
        assertNull(checker.checkPlacement(submarine, board));
        board.tryAddShip(submarine);
        assertEquals("That placement is invalid: the ship overlaps another ship.",
                checker.checkPlacement(carrier1, board));
        assertNull(checker.checkPlacement(destroyer, board));
        assertEquals("That placement is invalid: the ship goes off the right of the board.",
                checker.checkPlacement(carrier2, board));
    }

}