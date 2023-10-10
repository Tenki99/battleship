package edu.duke.tq22.battleship;

/**
 * This class offers the Board for the Battleship game.
 * It supports methods to try to add ships on the board,
 * check what is at a certain position, fire at a position,
 * or check if the player loses the game.
 */
public interface Board<T> {
    int getWidth();

    int getHeight();

    /**
     * This method checks the validity of the placement and add the ship to the list
     * and return true if the placement is ok, and return false if it was invalid
     * (and thus not actually placed).
     *
     * @param toAdd is the ship to add on the board
     * @return error message for adding the ship, null if no error happens
     */
    String tryAddShip(Ship<T> toAdd);

    /**
     * This method returns the display info on self board by calling whatIsAt.
     *
     * @param where is the coordinate to check
     * @return the display info for the checked coordinate on self board
     */
    T whatIsAtSelf(Coordinate where);

    /**
     * This method returns the display info on enemy board by calling whatIsAt.
     *
     * @param where is the coordinate to check
     * @return the display info for the checked coordinate on enemy board
     */
    T whatIsAtEnemy(Coordinate where);

    /**
     * This method fire at a coordinate on the board. Record hit if hit, or
     * add it to the missed list if missing.
     *
     * @param where is the coordinate to fire
     * @return the ship that is hit, return null if missed
     */
    Ship<T> fireAt(Coordinate where);

    /**
     * This method check whether a player lose the game by checking if
     * all the ships on the board are sunk.
     *
     * @return true if lose the game, false if not lose
     */
    Boolean checkLose();

    /**
     * This method try to find the ship which a given coordinate belongs to,
     * return null if it belongs to no ship
     *
     * @param where is the coordinate
     * @return the ship which where belongs to, return null if it belongs to no ship
     */
    Ship<T> findShip(Coordinate where);

    /**
     * This method removes a given ship from the board. If invisible is true, it could hide this
     * movement in front of the enemy.
     *
     * @param toRemove  the ship to be removed
     * @param invisible true if this movement should be hided in front of the enemy
     * @throws IllegalArgumentException if the ship is not on the board yet
     */
    void removeShip(Ship<T> toRemove, boolean invisible);

    /**
     * This method hides a ship in front of the enemy.
     *
     * @param toHide the ship to hide
     */
    void hideShip(Ship<T> toHide);
}
