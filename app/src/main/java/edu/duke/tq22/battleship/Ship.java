package edu.duke.tq22.battleship;

import java.util.ArrayList;

/**
 * This interface represents any type of Ship in our Battleship game. It is
 * generic in typename T, which is the type of information the view needs to
 * display this ship.
 */
public interface Ship<T> {


    /**
     * Check if this ship occupies the given coordinate.
     *
     * @param where is the Coordinate to check if this Ship occupies
     * @return true if where is inside this ship, false if not.
     */
    boolean occupiesCoordinates(Coordinate where);

    /**
     * Check if this ship has been hit in all of its locations meaning it has been
     * sunk.
     *
     * @return true if this ship has been sunk, false otherwise.
     */
    boolean isSunk();

    /**
     * Make this ship record that it has been hit at the given coordinate. The
     * specified coordinate must be part of the ship.
     *
     * @param where specifies the coordinates that were hit.
     * @throws IllegalArgumentException if where is not part of the Ship
     */
    void recordHitAt(Coordinate where);

    /**
     * Check if this ship was hit at the specified coordinates. The coordinates must
     * be part of this Ship.
     *
     * @param where is the coordinates to check.
     * @return true if this ship as hit at the indicated coordinates, and false
     * otherwise.
     * @throws IllegalArgumentException if the coordinates are not part of this
     *                                  ship.
     */
    boolean wasHitAt(Coordinate where);


    /**
     * Get the name of the given ship.
     *
     * @return The name string of give ship.
     */
    String getName();

    /**
     * Return the view-specific information at the given coordinate. This coordinate
     * must be part of the ship.
     *
     * @param where  is the coordinate to return information for
     * @param myShip is true if displaying self board, false if displaying enemy board
     * @return The view-specific information at that coordinate.
     * @throws IllegalArgumentException if where is not part of the Ship
     */
    T getDisplayInfoAt(Coordinate where, Boolean myShip);

    /**
     * Get all the Coordinates that this Ship occupies.
     *
     * @return An Iterable with the coordinates that this Ship occupies
     */
    Iterable<Coordinate> getCoordinates();

    /**
     * Get all the Coordinates that this Ship occupies.
     *
     * @return An Iterable with the coordinates that this Ship occupies
     */
    Iterable<Coordinate> getHitCoordinates();

    /**
     * This method push the value set of the ship pieces into an Arraylist
     * and return it.
     *
     * @return an Arraylist of boolean recording whether the squares are hit or not
     */
    ArrayList<Boolean> getHitList();

    /**
     * This method synchronizes the hit situation of a ship with another ship,
     * given the Arraylist of another ship's hit situation.
     *
     * @param hitList the list of hit
     * @throws IllegalArgumentException if the two ship to synchronize is not of the same ship type
     */
    void syncHit(ArrayList<Boolean> hitList);

}
