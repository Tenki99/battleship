package edu.duke.tq22.battleship;

import java.util.LinkedHashSet;

public class Carrier<T> extends BasicShip<T>{

    /**
     * This method generates a hash set of coordinates for a rectangle ship, which
     * is used in the constructor of the abstract class BasicShip.
     *
     * @param place the upperleft coordinate of the ship
     */
    static LinkedHashSet<Coordinate> makeCoords(Placement place) throws IllegalArgumentException{
        LinkedHashSet<Coordinate> set = new LinkedHashSet<>();
        Coordinate upperLeft = place.getWhere();
        int row = upperLeft.getRow(), col = upperLeft.getCol();
        char orientation = place.getOrientation();

        if (orientation == 'U') {
            for (int i = 0; i < 4; i++){
                set.add(new Coordinate(row + i, col));
            }
            for (int i = 0; i < 3; i++){
                set.add(new Coordinate(row + 2 + i, col + 1));
            }
        } else if (orientation == 'D') {
            for (int i = 3; i >= 0; i--){
                set.add(new Coordinate(row + 1 + i, col + 1));
            }
            for (int i = 2; i >= 0; i--){
                set.add(new Coordinate(row + i, col));
            }
        } else if (orientation == 'L') {
            for (int i = 0; i < 4; i++){
                set.add(new Coordinate(row + 1, col + i));
            }
            for (int i = 0; i < 3; i++){
                set.add(new Coordinate(row, col + 2 + i));
            }
        } else if (orientation == 'R') {
            for (int i = 3; i >= 0; i--){
                set.add(new Coordinate(row, col + 1 + i));
            }
            for (int i = 2; i >= 0; i--){
                set.add(new Coordinate(row + 1, col + i));
            }
        } else {
            throw new IllegalArgumentException("The orientation should be left(L), right(R), up(U) or down(D), but it is "
                    + orientation + ".");
        }

        return set;
    }

    /**
     * Constructor a rectangle ship with a collection of its coordinates,
     * and its display information.
     *
     * @param place            is the upper left coordinate of the ship
     * @param myDisplayInfo    is the display information from the view of self
     * @param enemyDisplayInfo is the display information for the view of enemy
     */
    public Carrier(Placement place, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(makeCoords(place), myDisplayInfo, enemyDisplayInfo);
    }

    /**
     * A simplified constructor for the convenience of test.
     *
     * @param place is the upper left coordinate of the ship
     * @param data  is the information to display for not hit part
     * @param onHit is the information to display for hit part
     */
    public Carrier(Placement place, T data, T onHit) {
        this(place, new SimpleShipDisplayInfo<>(data, onHit),
                new SimpleShipDisplayInfo<>(null, data));
    }

    /**
     * Get the name of the given ship.
     *
     * @return The name string of give ship.
     */
    @Override
    public String getName() {
        return "Carrier";
    }


}
