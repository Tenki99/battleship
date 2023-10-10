package edu.duke.tq22.battleship;

/**
 * This interface represents the information to display a ship.
 */
public interface ShipDisplayInfo<T> {

    /**
     * This class stores the display information of
     * a ship, and also the display information when it's hit.
     *
     * @param where is the coordinate to get info from
     * @param hit   true if hit, false if not hit
     * @return the display information at a certain coordinate
     */
    T getInfo(Coordinate where, boolean hit);

}

