package edu.duke.tq22.battleship;

/**
 * This class stores the information of how one ship is
 * placed on the board, including the coordinate of its
 * upper left part and whether it's placed vertically or horizontally.
 */
public abstract class Placement {

    private final Coordinate where;

    public Coordinate getWhere() {
        return where;
    }

    private final char orientation;

    public char getOrientation() {
        return orientation;
    }

    /**
     * Construct a Placement, give the coordinate of upper left part and
     * orientation.
     *
     * @param where the coordinate of upper left part
     * @param o     orientation of the ship
     * @throws IllegalArgumentException if the orientation is not 'H' or 'V' or
     *                                  their lower case
     */
    public Placement(Coordinate where, char o) {
        this.where = where;
        this.orientation = Character.toUpperCase(o);
    }

    /**
     * Construct a Placement, given a string describing the coordinate and orientation
     * (e.g. "A2V" means row = 0, col = 2, with vertical orientation).
     *
     * @param s Input string with the first digit for row number, second
     *          digit for column number and third digit for orientation
     * @throws IllegalArgumentException if the input is illegal
     */
    public Placement(String s) {
        if (s.length() != 3) { // check the length
            throw new IllegalArgumentException(
                    "The length of input for constructing a Placement should be 3, but it is " + s.length() + ".\n");
        }

        this.where = new Coordinate(s.substring(0, 2).toUpperCase());
        char o = s.charAt(2);
        this.orientation = Character.toUpperCase(o);
    }

    /**
     * This method overrides the equals() method,
     * which returns true if two Placements are the same.
     *
     * @param o the other object to compare with this
     * @return true if two Coordinates are equal, false if not equal
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Placement p = (Placement) o;
            return where.equals(p.where) && orientation == p.orientation;
        }
        return false;
    }

    /**
     * This method overrides the toString method,
     * which return the Placement in a String format.
     *
     * @return the coordinate in a String format.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(where.toString());
        s.append(orientation);
        return s.toString();
    }

    /**
     * This method overrides the hashCode method,
     * which generates the hash code for each Placement
     * for the convenience to put Placements into hashMap or hashSet later.
     *
     * @return the hash code for the Coordinate
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * This method checks whether the orientation of a placement is legal;
     *
     * @param o the checked orientation
     * @throws IllegalArgumentException if the orientation is illegal
     */
    protected abstract void checkOrientation(char o) throws IllegalArgumentException;

}
