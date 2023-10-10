package edu.duke.tq22.battleship;

/**
 * This class checks whether a ship collides with any ship already on
 * the board. The generic type T is the type of display information
 * for the game.
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

    /**
     * Constructs a rule checker that checks whether a placement collides
     * with the current ships on the board.
     *
     * @param next is the next rule checker.
     */
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    /**
     * This method checks whether the placement of ship collides with
     * other ships.
     *
     * @param theShip  is the ship to be checked
     * @param theBoard is the board the given ship is going to place on
     * @return return the error info if not legal, null if legal
     */
    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c : theShip.getCoordinates()) {
            if (theBoard.whatIsAtSelf(c) != null) {
                return "That placement is invalid: the ship overlaps another ship.";
            }
        }
        return null;
    }
}
