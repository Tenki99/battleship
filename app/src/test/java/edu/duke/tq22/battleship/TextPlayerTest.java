package edu.duke.tq22.battleship;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class TextPlayerTest {
    @Test
    public void test_read_placement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "B2H\nC8v\na4U\n", bytes);
        String prompt = "Please enter a location for a ship:";
        Placement[] expected = new Placement[3];
        expected[0] = new PlacementTwo(new Coordinate(1, 2), 'H');
        expected[1] = new PlacementTwo(new Coordinate(2, 8), 'V');
        expected[2] = new PlacementFour(new Coordinate(0, 4), 'U');

        Placement p1 = player.readPlacement("Submarine", prompt);
        assertEquals(p1, expected[0]); // did we get the right Placement back
        assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
        bytes.reset(); // clear out bytes for next time around

        Placement p2 = player.readPlacement("Destroyer", prompt);
        assertEquals(p2, expected[1]); // did we get the right Placement back
        assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
        bytes.reset(); // clear out bytes for next time around

        Placement p3 = player.readPlacement("Battleship", prompt);
        assertEquals(p3, expected[2]); // did we get the right Placement back
        assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
        bytes.reset(); // clear out bytes for next time around
    }

    @Test
    public void test_read_placement_illegal() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "", bytes);
        String prompt = "Please enter a location for a ship:";
        assertThrows(EOFException.class, () -> player.readPlacement("Submarine", prompt));
    }


    @Test
    public void test_doOnePlacement() throws IOException, IllegalArgumentException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "B2H\nC8v\na4h\n", bytes);
        for (int i = 1; i <= 3; i++) {
            player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
            compare_with_file(bytes.toString(), "test_doOnePlacement_output" + i + ".txt");
            bytes.reset(); // clear out bytes for next time around
        }
    }

    @Test
    public void test_doOnePlacement_illegal() throws IOException, IllegalArgumentException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player1 = createTextPlayer(10, 20, "B9H\n", bytes);
        TextPlayer player2 = createTextPlayer(10, 20, "T9v\n", bytes);
        TextPlayer player3 = createTextPlayer(10, 20, "a4v\na4v\n", bytes);
        assertThrows(IllegalArgumentException.class,
                () -> player1.doOnePlacement("Destroyer", player1.shipCreationFns.get("Destroyer")));
        assertThrows(IllegalArgumentException.class,
                () -> player2.doOnePlacement("Destroyer", player2.shipCreationFns.get("Destroyer")));
        player3.doOnePlacement("Destroyer", player3.shipCreationFns.get("Destroyer"));
        assertThrows(IllegalArgumentException.class,
                () -> player3.doOnePlacement("Destroyer", player3.shipCreationFns.get("Destroyer")));
    }

    @Disabled
    @Test
    public void test_doPlacementPhase() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "A0V\nB1H\n\nC3V\nF4V\nH5H\nO9V\nL4H\nT0H\nP0H\nN7V\n", bytes);
        player.doPlacementPhase();
        compare_with_file(bytes.toString(), "test_player_doPlacementPhase_output.txt");
    }

    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(w, h, 'X');
        V2ShipFactory shipFactory = new V2ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory,3, 3);
    }

    private void compare_with_file(String actual, String expectedPath) throws IOException {
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream(expectedPath);
        assert expectedStream != null;
        String expected = new String(expectedStream.readAllBytes());
        assertEquals(expected, actual);
    }

    @Test
    public void test_read_coordinate() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "B2\nC8\na4\n", bytes);
        String prompt = "Please enter a coordinate to fire:";
        Coordinate[] expected = new Coordinate[3];
        expected[0] = new Coordinate(1, 2);
        expected[1] = new Coordinate(2, 8);
        expected[2] = new Coordinate(0, 4);
        for (Coordinate expectedCoordinate : expected) {
            Coordinate c = player.readCoordinate(prompt);
            assertEquals(expectedCoordinate, c); // did we get the right Placement back
            assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
            bytes.reset(); // clear out bytes for next time around
        }
    }

    @Test
    public void test_read_coordinate_illegal() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "", bytes);
        String prompt = "Please enter a coordinate to fire:";
        assertThrows(EOFException.class, () -> player.readCoordinate(prompt));
    }

    @Test
    public void test_doAttackPhase() throws IOException, IllegalArgumentException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player1 = createTextPlayer(10, 20, "B2H\nC3\n\nD3\nE3\n", bytes);
        TextPlayer player2 = createTextPlayer(10, 20, "C3V\nG3\nB2\nG4\n", bytes);

        player1.doOnePlacement("Destroyer", player1.shipCreationFns.get("Destroyer"));
        player2.doOnePlacement("Destroyer", player2.shipCreationFns.get("Destroyer"));
        bytes.reset();

        assertNull(player1.doAttackingPhase(player2));
        assertNull(player2.doAttackingPhase(player1));
        assertNull(player1.doAttackingPhase(player2));
        assertNull(player2.doAttackingPhase(player1));
        assertEquals("Player A wins the game!!!\n", player1.doAttackingPhase(player2));
        compare_with_file(bytes.toString(), "test_doAttackPhase_output.txt");
    }

}
