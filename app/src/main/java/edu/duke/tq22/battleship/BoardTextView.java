package edu.duke.tq22.battleship;

import java.util.function.Function;

import static java.lang.Math.max;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the enemy's board.
 */

public class BoardTextView {

    /**
     * The Board to display
     */
    private final Board<Character> toDisplay;

    /**
     * Construct a BoardView, given to board it will display.
     *
     * @param toDisplay is the board to display
     * @throws IllegalArgumentException if the board is larger than 10x26.
     */
    public BoardTextView(Board<Character> toDisplay) {
        this.toDisplay = toDisplay;
        if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
            throw new IllegalArgumentException(
                    "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
        }
    }

    /**
     * This method displays the player's own board and enemy's board side
     * by side before the player is going to fire at enemy's board.
     *
     * @param enemyView  is the view of enemy's board
     * @param myTitle    is the title of player's own board
     * @param enemyTitle is the title of enemy's own board
     * @return the displayed string
     */
    public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myTitle, String enemyTitle) {
        // split and preparation
        String[] selfBoard = displayMyOwnBoard().split("\n");
        String[] enemyBoard = enemyView.displayEnemyBoard().split("\n");
        String sep = " ";
        int len1 = selfBoard.length, len2 = enemyBoard.length, width = toDisplay.getWidth();
        StringBuilder display = new StringBuilder();

        // title line
        display.append(sep.repeat(5)).append(myTitle);
        display.append(sep.repeat(max(3, 2 * width + 17 - myTitle.length()))).append(enemyTitle).append("\n");

        // board body
        for (int i = 0; i < len2; i++) {
            if (i < len1) {
                display.append(selfBoard[i]);
            } else {
                display.append(sep.repeat(2 * width + 3));
            }
            if (i == 0 || i == len1 - 1) {
                display.append("  ");
            } // for header line of the board
            display.append(sep.repeat(16)).append(enemyBoard[i]).append("\n");
        }
        for (int i = len2; i < len1; i++) {
            display.append(selfBoard[i]).append("\n");
        }

        return display.toString();
    }

    /**
     * This method displays a board with the function to get the display information given.
     *
     * @param getSquareFn the function to generate the display info on the board (e.g. whatIsAtSelf)
     * @return the string for displaying the board
     */
    protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
        StringBuilder ans = new StringBuilder();
        ans.append(makeHeader());
        for (int i = 0; i < toDisplay.getHeight(); i++) {
            ans.append(makeLine(i, getSquareFn));
        }
        ans.append(makeHeader());
        return ans.toString();
    }

    /**
     * This method displays player's own board by calling displayAnyBoard.
     *
     * @return the string displaying the player's own board
     */
    public String displayMyOwnBoard() {
        return displayAnyBoard(toDisplay::whatIsAtSelf);
    }

    /**
     * This method displays enemy's board by calling displayAnyBoard.
     *
     * @return the string displaying the enemy's own board
     */
    public String displayEnemyBoard() {
        return displayAnyBoard(toDisplay::whatIsAtEnemy);
    }

    /**
     * This makes the header line, e.g. 0|1|2|3|4\n
     *
     * @return the String that is the header line for the given board
     */
    String makeHeader() {
        StringBuilder ans = new StringBuilder("  ");
        String sep = "|";
        for (int i = 0; i < toDisplay.getWidth(); i++) {
            if (i > 0) { // start with no separate sign, then switch to "|" to separate
                ans.append(sep);
            }
            ans.append(i);
        }
        ans.append("\n");
        return ans.toString();
    }

    /**
     * This makes the board line, e.g. A | | | | A\n
     *
     * @param row         the number of line to be generated
     * @param getSquareFn the function to generate the display info on the board
     * @return the String that is the line number row for the given board
     */
    String makeLine(int row, Function<Coordinate, Character> getSquareFn) {
        StringBuilder ans = new StringBuilder();
        String sep = "|";
        char letter = (char) ('A' + row);

        ans.append(letter);
        ans.append(" ");
        for (int col = 0; col < toDisplay.getWidth(); col++) {
            if (col > 0) { // start with no seperate sign, then switch to "|" to seperate
                ans.append(sep);
            }
            Character cur = getSquareFn.apply(new Coordinate(row, col));
            if (cur != null) {
                ans.append(cur);
            } else {
                ans.append(" ");
            }
        }
        ans.append(" ");
        ans.append(letter);
        ans.append("\n");

        return ans.toString();

    }

}
