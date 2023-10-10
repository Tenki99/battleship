package edu.duke.tq22.battleship;

public class PlacementTwo extends Placement{

    /**
     * Construct a Placement, give the coordinate of upper left part and
     * orientation.
     *
     * @param where the coordinate of upper left part
     * @param o     orientation of the ship
     * @throws IllegalArgumentException if the orientation is not 'H' or 'V' or
     *                                  their lower case
     */
    public PlacementTwo(Coordinate where, char o) {
        super(where, o);
        checkOrientation(o);
    }

    /**
     * Construct a Placement, given a string describing the coordinate and orientation
     * (e.g. "A2V" means row = 0, col = 2, with vertical orientation).
     *
     * @param s Input string with the first digit for row number, second
     *          digit for column number and third digit for orientation
     * @throws IllegalArgumentException if the input is illegal
     */
    public PlacementTwo(String s) {
        super(s);
        checkOrientation(s.charAt(2));
    }

    /**
     * This method checks whether the orientation of a placement is legal;
     *
     * @param o the checked orientation
     * @throws IllegalArgumentException if the orientation is illegal
     */
    @Override
    protected void checkOrientation(char o) throws IllegalArgumentException {
        char oUpper = getOrientation();
        if (oUpper != 'H' && oUpper != 'V') {
            throw new IllegalArgumentException(
                    "The orientation should be either vertical(V) or horizontal(H), but it is " + o + ".\n");
        }
    }
}
