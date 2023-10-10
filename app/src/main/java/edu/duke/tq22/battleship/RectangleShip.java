package edu.duke.tq22.battleship;

import java.util.LinkedHashSet;

/**
 * This class stores the display information of
 * a ship, and also the display information when it's hit.
 */
public class RectangleShip<T> extends BasicShip<T> {

    final String name;

    /**
     * This method generates a hash set of coordinates for a rectangle ship, which
     * is used in the constructor of the abstract class BasicShip.
     *
     * @param upperLeft the upperleft coordinate of the ship
     * @param width     width of the ship
     * @param height    height of the ship
     */
    static LinkedHashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        LinkedHashSet<Coordinate> set = new LinkedHashSet<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                set.add(new Coordinate(upperLeft.getRow() + j, upperLeft.getCol() + i));
            }
        }
        return set;
    }

    /**
     * Constructor a rectangle ship with a collection of its coordinates,
     * and its display information.
     *
     * @param name             is the name of the ship
     * @param upperLeft        is the upper left coordinate of the ship
     * @param width            is the width of the ship
     * @param height           is the height of the ship
     * @param myDisplayInfo    is the display information from the view of self
     * @param enemyDisplayInfo is the display information for the view of enemy
     */
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo);
        this.name = name;
    }

    /**
     * A simplified constructor for the convenience of test.
     *
     * @param name      is the name of the ship
     * @param upperLeft is the upper left coordinate of the ship
     * @param width     is the width of the ship
     * @param height    is the height of the ship
     * @param data      is the information to display for not hit part
     * @param onHit     is the information to display for hit part
     */
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<>(data, onHit),
                new SimpleShipDisplayInfo<>(null, data));
    }

    /**
     * A simplified constructor for the convenience of test with size 1x1.
     *
     * @param upperLeft is the upper left coordinate of the ship
     * @param data      is the information to display for not hit part
     *                  *  @param onHit is the information to display for hit part
     */
    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("testShip", upperLeft, 1, 1, data, onHit);
    }

    /**
     * Get the name of the given ship.
     *
     * @return The name string of give ship.
     */
    @Override
    public String getName() {
        return name;
    }

}
