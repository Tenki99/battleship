package edu.duke.tq22.battleship;

/**
 * This abstract class gives a template for placement rule checkers. The
 * generic type T is the type of display information for the game.
 */
public abstract class PlacementRuleChecker<T> {

    private final PlacementRuleChecker<T> next;

    /**
     * Constructs a placement rule checker.
     *
     * @param next is the next rule checker.
     */
    public PlacementRuleChecker(PlacementRuleChecker<T> next) {
        this.next = next;
    }

    /**
     * This method checks whether the placement obeys certain rule according
     * to the child class.
     *
     * @param theShip  is the ship to be checked
     * @param theBoard is the board the given ship is going to place on
     * @return return the error info if not legal, null if legal
     */
    protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

    /**
     * This method check the legality of a given ship's position on a given board.
     *
     * @param theShip  is the ship to be checked
     * @param theBoard is the board the given ship is going to place on
     * @return return the error info if not legal, null if legal
     */
    public String checkPlacement(Ship<T> theShip, Board<T> theBoard) {
        //if we fail our own rule: stop the placement is not legal
        String prompt = checkMyRule(theShip, theBoard);
        if (prompt != null) {
            return prompt;
        }
        //otherwise, ask the rest of the chain.
        if (next != null) {
            return next.checkPlacement(theShip, theBoard);
        }
        //if there are no more rules, then the placement is legal
        return null;
    }

}
