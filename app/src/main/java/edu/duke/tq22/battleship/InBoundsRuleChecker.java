package edu.duke.tq22.battleship;

/**
 * This class checks whether a ship goes out of the bound of the board. The
 * generic type T is the type of display information for the game.
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

    /**
     * Constructs a rule checker that checks whether a placement goes out
     * of the bound of board.
     *
     * @param next is the next rule checker.
     */
    public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    /**
     * This method checks whether the placement of ship is within
     * the bound of board.
     *
     * @param theShip  is the ship to be checked
     * @param theBoard is the board the given ship is going to place on
     * @return return the error info if not legal, null if legal
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        int maxRow = theBoard.getHeight() - 1, maxCol = theBoard.getWidth() - 1;
        for (Coordinate c : theShip.getCoordinates()) {
            int row = c.getRow(), col = c.getCol();
            if (row > maxRow) {
                return "That placement is invalid: the ship goes off the bottom of the board.";
            } else if (col > maxCol) {
                return "That placement is invalid: the ship goes off the right of the board.";
            } else if (row < 0) {
                return "That placement is invalid: the ship goes off the top of the board.";
            } else if (col < 0) {
                return "That placement is invalid: the ship goes off the left of the board.";
            }
        }
        return null;
    }

}
