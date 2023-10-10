package edu.duke.tq22.battleship;

/**
 * This class gives the coordinate of a point on the board.
 */
public class Coordinate {

    private final int row;

    public int getRow() {
        return row;
    }

    private final int col;

    public int getCol() {
        return col;
    }

    /**
     * Construct a Coordinate, given to its row number and column number.
     *
     * @param r row number
     * @param c column number
     * @throws IllegalArgumentException if the row or column number is negative.
     */
    public Coordinate(int r, int c) {

//    if (r < 0) {
//      throw new IllegalArgumentException(
//          "The row number of the coordinate must be non-negative, but is " + r + "\n");
//    }
//    if (c < 0) {
//      throw new IllegalArgumentException(
//          "The column number of the coordinate must be non-negative, but is " + c + "\n");
//    }

        this.row = r;
        this.col = c;
    }

    /**
     * Construct a Coordinate, given a string describing the row and column number
     * (e.g. "A2" means row = 0, col = 2).
     *
     * @param descr Input string with the first digit for row number and second
     *              digit for column number
     * @throws IllegalArgumentException if the input is illegal
     */
    public Coordinate(String descr) {

        if (descr.length() != 2) {
            throw new IllegalArgumentException(
                    "The length of the string coordinate should be 2, but is " + descr.length() + ".");
        }
        descr = descr.toUpperCase();
        int r = descr.charAt(0) - 'A', c = descr.charAt(1) - '0';

        if (r < 0 || r >= 26) {
            throw new IllegalArgumentException(
                    "The row number should be an uppercase letter from 'A' to 'Z', but is " + descr.charAt(0) + ".");
        }

        if (c < 0 || c >= 10) {
            throw new IllegalArgumentException(
                    "The column number should be an integer from 0 to 9, but is " + descr.charAt(1) + ".");
        }

        this.row = r;
        this.col = c;
    }

    /**
     * This method overrides the equals() method,
     * which returns true if two Coordinates are the same.
     *
     * @param o the other object to compare with this
     * @return true if two Coordinates are equal, false if not equal
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) { // check if o and this have same class type
            Coordinate c = (Coordinate) o;
            return row == c.row && col == c.col;
        }
        return false;
    }

    /**
     * This method overrides the toString method,
     * which return the coordinate in a String format.
     *
     * @return the coordinate in a String format.
     */
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    /**
     * This method overrides the hashCode method,
     * which generates the hash code for each Coordinates
     * for the convenience to put Coordinates into hashMap or hashSet later.
     *
     * @return the hash code for the Coordinate
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
