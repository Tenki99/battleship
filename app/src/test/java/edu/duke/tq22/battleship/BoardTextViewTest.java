package edu.duke.tq22.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class BoardTextViewTest {

    @Test
    public void test_display_empty_2by2() {

        String expectedHeader = "  0|1\n";
        String expected = expectedHeader +
                "A  |  A\n" +
                "B  |  B\n" +
                expectedHeader;

        emptyBoardHelper(2, 2, expectedHeader, expected);
    }

    @Test
    public void test_display_empty_3by2() {

        String expectedHeader = "  0|1|2\n";
        String expected = expectedHeader +
                "A  | |  A\n" +
                "B  | |  B\n" +
                expectedHeader;

        emptyBoardHelper(3, 2, expectedHeader, expected);
    }

    @Test
    public void test_display_empty_3by5() {

        String expectedHeader = "  0|1|2\n";
        String expected = expectedHeader +
                "A  | |  A\n" +
                "B  | |  B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  | |  E\n" +
                expectedHeader;

        emptyBoardHelper(3, 5, expectedHeader, expected);
    }

    /**
     * This method helps the test of empty board generation with different sizes
     */

    private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
        Board<Character> b1 = new BattleShipBoard<>(w, h, 'X');
        BoardTextView view = new BoardTextView(b1);
        assertEquals(expectedHeader, view.makeHeader());
        assertEquals(expectedBody, view.displayMyOwnBoard());
    }

    @Test
    public void test_invalid_board_size() {
        Board<Character> wideBoard = new BattleShipBoard<>(11, 20, 'X');
        Board<Character> tallBoard = new BattleShipBoard<>(10, 27, 'X');
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    }

    @Test
    public void test_nonempty_board() {
        Board<Character> b = new BattleShipBoard<>(4, 3, 'X');

        Ship<Character> s1 = new RectangleShip<>(new Coordinate("B2"), 's', '*');
        b.tryAddShip(s1);
        BoardTextView view1 = new BoardTextView(b);
        String expected1 = "  0|1|2|3\n" +
                "A  | | |  A\n" +
                "B  | |s|  B\n" +
                "C  | | |  C\n" +
                "  0|1|2|3\n";
        assertEquals(expected1, view1.displayMyOwnBoard());

        Ship<Character> s2 = new RectangleShip<>(new Coordinate("C3"), 's', '*');
        Ship<Character> s3 = new RectangleShip<>(new Coordinate("A0"), 's', '*');
        b.tryAddShip(s2);
        b.tryAddShip(s3);
        BoardTextView view2 = new BoardTextView(b);
        String expected2 = "  0|1|2|3\n" +
                "A s| | |  A\n" +
                "B  | |s|  B\n" +
                "C  | | |s C\n" +
                "  0|1|2|3\n";
        assertEquals(expected2, view2.displayMyOwnBoard());
        String expected3 = "  0|1|2|3\n" +
                "A  | | |  A\n" +
                "B  | | |  B\n" +
                "C  | | |  C\n" +
                "  0|1|2|3\n";
        assertEquals(expected3, view2.displayEnemyBoard());

        b.fireAt(new Coordinate(0, 0));
        b.fireAt(new Coordinate(1, 0));
        String expected4 = "  0|1|2|3\n" +
                "A s| | |  A\n" +
                "B X| | |  B\n" +
                "C  | | |  C\n" +
                "  0|1|2|3\n";
        assertEquals(expected4, view2.displayEnemyBoard());
    }

    @Test
    public void test_displayMyBoardWithEnemyNextToIt() throws IOException {
        String myHeader = "Your ocean";
        String enemyHeader = "Player B's ocean";
        BoardTextView view1 = new BoardTextView(new BattleShipBoard<>(10, 20, 'X'));
        BoardTextView view2 = new BoardTextView(new BattleShipBoard<>(10, 19, 'X'));
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("test_display2board1.txt");
        String expected1 = new String(expectedStream.readAllBytes());
        assertEquals(expected1, view1.displayMyBoardWithEnemyNextToIt(view1, myHeader, enemyHeader));
        expectedStream = getClass().getClassLoader().getResourceAsStream("test_display2board2.txt");
        String expected2 = new String(expectedStream.readAllBytes());
        assertEquals(expected2, view1.displayMyBoardWithEnemyNextToIt(view2, myHeader, enemyHeader));
        expectedStream = getClass().getClassLoader().getResourceAsStream("test_display2board3.txt");
        String expected3 = new String(expectedStream.readAllBytes());
        assertEquals(expected3, view2.displayMyBoardWithEnemyNextToIt(view1, myHeader, enemyHeader));
    }
}
