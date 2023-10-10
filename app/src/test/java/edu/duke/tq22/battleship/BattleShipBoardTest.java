package edu.duke.tq22.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
    @Test
    public void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }

    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, 0, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(0, 20, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(10, -5, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<>(-8, 20, 'X'));

    }

    @Test
    public void test_try_add_ship() {
        Board<Character> b = new BattleShipBoard<>(10, 20,
                new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null)), 'X');
        AbstractShipFactory<Character> factory = new V1ShipFactory();
        Ship<Character> s1 = new RectangleShip<>(new Coordinate("C9"), 's', '*');
        Ship<Character> s2 = factory.makeBattleship(new PlacementTwo("d9h"));
        assertNull(b.tryAddShip(s1));
        assertEquals("That placement is invalid: the ship overlaps another ship.",
                b.tryAddShip(s1));
        assertEquals("That placement is invalid: the ship goes off the right of the board.",
                b.tryAddShip(s2));
    }

    @Test
    public void test_whatIsAt() {

        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, 'X');
        Coordinate c = new Coordinate("C9");
        Ship<Character> s = new RectangleShip<>(c, 's', '*');
        Character[][] expected1 = new Character[10][20];
        Character[][] expected2 = new Character[10][20];
        checkWhatIsAtSelfBoard(b, expected1);
        checkWhatIsAtEnemyBoard(b, expected2);
        b.tryAddShip(s);
        expected1[2][9] = 's';
        checkWhatIsAtSelfBoard(b, expected1);
        b.fireAt(c);
        expected2[2][9] = 's';
        expected1[2][9] = '*';
        checkWhatIsAtEnemyBoard(b, expected2);
        b.fireAt(new Coordinate("A0"));
        expected2[0][0] = 'X';
        checkWhatIsAtEnemyBoard(b, expected2);
        checkWhatIsAtSelfBoard(b, expected1);
    }

    @Test
    public void test_fireAt() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, 'X');
        AbstractShipFactory<Character> factory = new V1ShipFactory();
        Ship<Character> s = factory.makeBattleship(new PlacementTwo("C9V"));
        b.tryAddShip(s);
        assertSame(s, b.fireAt(new Coordinate("C9")));
        assertFalse(s.isSunk());
        assertSame(s, b.fireAt(new Coordinate("D9")));
        assertFalse(s.isSunk());
        assertSame(s, b.fireAt(new Coordinate("E9")));
        assertFalse(s.isSunk());
        assertSame(s, b.fireAt(new Coordinate("F9")));
        assertTrue(s.isSunk());
    }

    private <T> void checkWhatIsAtSelfBoard(BattleShipBoard<T> b, T[][] expected) {

        for (int i = 0; i < b.getWidth(); i++) {
            for (int j = 0; j < b.getHeight(); j++) {
                assertEquals(b.whatIsAtSelf(new Coordinate(i, j)), expected[i][j]);
            }
        }

    }

    private <T> void checkWhatIsAtEnemyBoard(BattleShipBoard<T> b, T[][] expected) {

        for (int i = 0; i < b.getWidth(); i++) {
            for (int j = 0; j < b.getHeight(); j++) {
                assertEquals(expected[i][j], b.whatIsAtEnemy(new Coordinate(i, j)));
            }
        }

    }

    @Test
    public void test_checkLose() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 20, 'X');
        AbstractShipFactory<Character> factory = new V1ShipFactory();
        Ship<Character> s = factory.makeBattleship(new PlacementTwo("C9V"));
        b.tryAddShip(s);
        assertFalse(b.checkLose());
        assertSame(s, b.fireAt(new Coordinate("C9")));
        assertSame(s, b.fireAt(new Coordinate("D9")));
        assertSame(s, b.fireAt(new Coordinate("E9")));
        assertSame(s, b.fireAt(new Coordinate("F9")));
        assertTrue(b.checkLose());
    }

}
