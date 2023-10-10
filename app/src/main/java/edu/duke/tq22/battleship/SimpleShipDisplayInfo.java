package edu.duke.tq22.battleship;

/**
 * This class stores the display information of
 * a ship, and also the display information when it's hit.
 */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
    T myData, onHit;

    /**
     * This constructor construct the class with the information to display
     *
     * @param myData the display information for the blocks
     * @param onHit  the display information for the sunk blocks
     */
    public SimpleShipDisplayInfo(T myData, T onHit) {
        this.myData = myData;
        this.onHit = onHit;
    }

    /**
     * This class stores the display information of
     * a ship, and also the display information when it's hit.
     *
     * @param where is the coordinate to get info from
     * @param hit   true if hit, false if not hit
     * @return the display information at a certain coordinate
     */
    @Override
    public T getInfo(Coordinate where, boolean hit) {
        if (hit) {
            return onHit;
        } else {
            return myData;
        }
    }


}
