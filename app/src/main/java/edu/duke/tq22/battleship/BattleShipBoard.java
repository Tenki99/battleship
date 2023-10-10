package edu.duke.tq22.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class inherit from Board which offers the Board
 * for the Battleship game.
 * It supports methods to try to add ships on the board,
 * check what is at a certain position, fire at a position,
 * or check if the player loses the game.
 */
public class BattleShipBoard<T> implements Board<T> {
    private final int width;

    public int getWidth() {
        return width;
    }

    private final int height;

    public int getHeight() {
        return height;
    }

    private final ArrayList<Ship<T>> myShips;
    private final PlacementRuleChecker<T> placementChecker;
    private final HashSet<Coordinate> enemyMisses;
    private final T missInfo;
    private final HashMap<Coordinate, T> hitBeforeMove; // coordinates hit before movement
    private final HashSet<Coordinate> HidedCoords; // coordinates to hide in enemy view

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height
     *
     * @param w                is the width of the newly constructed board.
     * @param h                is the height of the newly constructed board.
     * @param placementChecker is the checker for checking legality of ship position
     * @param missInfo         is the information to display when the attack is missed
     * @throws IllegalArgumentException if the width or height are less than or
     *                                  equal to zero.
     */
    public BattleShipBoard(int w, int h, PlacementRuleChecker<T> placementChecker, T missInfo) {
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is" + w);
        }

        if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's height must be positive but is" + h);
        }
        this.width = w;
        this.height = h;
        this.myShips = new ArrayList<>();
        this.placementChecker = placementChecker;
        this.enemyMisses = new HashSet<>();
        this.missInfo = missInfo;
        this.hitBeforeMove = new HashMap<>();
        this.HidedCoords = new HashSet<>();
    }

    /**
     * A simplifies version of the constructor for the convenience of test.
     *
     * @param w        is the width of the newly constructed board.
     * @param h        is the height of the newly constructed board.
     * @param missInfo is the information to display when the attack is missed
     */
    public BattleShipBoard(int w, int h, T missInfo) {
        this(w, h, new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null)), missInfo);
    }

    /**
     * This method checks the validity of the placement and add the ship to the list
     * and return true if the placement is ok, and return false if it was invalid
     * (and thus not actually placed).
     *
     * @param toAdd is the ship to add on the board
     * @return error message for adding the ship, null if no error happens
     */
    public String tryAddShip(Ship<T> toAdd) {
        String errorInfo = placementChecker.checkPlacement(toAdd, this);
        if (errorInfo != null) {
            return errorInfo;
        }
        myShips.add(toAdd);
        return null;
    }

    /**
     * This method returns the display information at the given coordinate.
     *
     * @param where the coordinate to check
     * @return the display information at given coordinate
     */
    protected T whatIsAt(Coordinate where, boolean isSelf) {

        if (!isSelf) {
            if (enemyMisses.contains(where)) {
                return missInfo;
            } else if (hitBeforeMove.containsKey(where)) {
                return hitBeforeMove.get(where);
            } else if (HidedCoords.contains(where)) {
                return null;
            }
        }

        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(where)) {
                return s.getDisplayInfoAt(where, isSelf);
            }
        }
        return null;
    }

    /**
     * This method returns the display info on self board by calling whatIsAt.
     *
     * @param where is the coordinate to check
     * @return the display info for the checked coordinate on self board
     */
    public T whatIsAtSelf(Coordinate where) {
        return whatIsAt(where, true);
    }

    /**
     * This method returns the display info on enemy board by calling whatIsAt.
     *
     * @param where is the coordinate to check
     * @return the display info for the checked coordinate on enemy board
     */
    public T whatIsAtEnemy(Coordinate where) {
        return whatIsAt(where, false);
    }

    /**
     * This method fire at a coordinate on the board. Record hit if hit, or
     * add it to the missed list if missing. Those hided effect caused by the
     * movement will be cancelled if fired.
     *
     * @param where is the coordinate to fire
     * @return the ship that is hit, return null if missed
     */
    public Ship<T> fireAt(Coordinate where) {
        HidedCoords.remove(where);
        hitBeforeMove.remove(where);
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(where)) {
                s.recordHitAt(where);
                enemyMisses.remove(where);
                return s;
            }
        }
        enemyMisses.add(where);
        return null;
    }

    /**
     * This method check whether a player lose the game by checking if
     * all the ships on the board are sunk.
     *
     * @return true if lose the game, false if not lose
     */
    public Boolean checkLose() {
        for (Ship<T> s : myShips) {
            if (!s.isSunk()) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method try to find the ship which a given coordinate belongs to,
     * return null if it belongs to no ship
     *
     * @param where is the coordinate
     * @return the ship which where belongs to, return null if it belongs to no ship
     */
    public Ship<T> findShip(Coordinate where) {
        for (Ship<T> s : myShips) {
            if (s.occupiesCoordinates(where)) {
                return s;
            }
        }
        return null;
    }

    /**
     * This method removes a given ship from the board. If invisible is true, it could hide this
     * movement in front of the enemy.
     *
     * @param toRemove  the ship to be removed
     * @param invisible true if this movement should be hided in front of the enemy
     * @throws IllegalArgumentException if the ship is not on the board yet
     */
    @Override
    public void removeShip(Ship<T> toRemove, boolean invisible) throws IllegalArgumentException {
        if (!myShips.remove(toRemove)) {
            throw new IllegalArgumentException("The ship to remove is not on the board yet!");
        }
        if (invisible) {
            for (Coordinate c : toRemove.getHitCoordinates()) {
                hitBeforeMove.put(c, toRemove.getDisplayInfoAt(c, false));
            }
        }
    }

    /**
     * This method hides a ship in front of the enemy.
     *
     * @param toHide the ship to hide
     */
    @Override
    public void hideShip(Ship<T> toHide) {
        for (Coordinate c : toHide.getCoordinates()) {
            HidedCoords.add(c);
        }
    }
}
