package edu.duke.tq22.battleship;

import java.util.*;

/**
 * This abstract class represents any shape of Ship in our Battleship game. It is
 * generic in typename T, which is the type of information the view needs to
 * display this ship.
 */
public abstract class BasicShip<T> implements Ship<T> {

    protected LinkedHashMap<Coordinate, Boolean> myPieces;
    protected ShipDisplayInfo<T> myDisplayInfo;
    protected ShipDisplayInfo<T> enemyDisplayInfo;

    /**
     * Construct a BasicShip with given collection of Coordinates and how the view
     * will display this ship.
     *
     * @param where            is the collection of Coordinates of this ship
     * @param myDisplayInfo    is the information the view needs to display the ships
     * @param enemyDisplayInfo is the information the view to display the enemy ships
     */
    public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        this.myDisplayInfo = myDisplayInfo;
        this.enemyDisplayInfo = enemyDisplayInfo;
        myPieces = new LinkedHashMap<>();
        for (Coordinate c : where) {
            myPieces.put(c, false);
        }
    }

    /**
     * Check if this ship occupies the given coordinate.
     *
     * @param where is the Coordinate to check if this Ship occupies
     * @return true if where is inside this ship, false if not.
     */
    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return myPieces.containsKey(where);
    }

    /**
     * Check if this ship has been hit in all of its locations meaning it has been
     * sunk.
     *
     * @return true if this ship has been sunk, false otherwise.
     */
    @Override
    public boolean isSunk() {
        for (boolean hit : myPieces.values()) {
            if (!hit) {
                return false;
            }
        }
        return true;
    }

    /**
     * Make this ship record that it has been hit at the given coordinate. The
     * specified coordinate must be part of the ship.
     *
     * @param where specifies the coordinates that were hit.
     * @throws IllegalArgumentException if where is not part of the Ship
     */
    @Override
    public void recordHitAt(Coordinate where) throws IllegalArgumentException {
        checkCoordinateInThisShip(where);
        myPieces.put(where, true);
    }

    /**
     * Check if this ship was hit at the specified coordinates. The coordinates must
     * be part of this Ship.
     *
     * @param where is the coordinates to check.
     * @return true if this ship as hit at the indicated coordinates, and false otherwise.
     * @throws IllegalArgumentException if the coordinates are not part of this
     *                                  ship.
     */
    @Override
    public boolean wasHitAt(Coordinate where) throws IllegalArgumentException {
        checkCoordinateInThisShip(where);
        return myPieces.get(where);
    }

    /**
     * Return the view-specific information at the given coordinate. This coordinate
     * must be part of the ship.
     *
     * @param where  is the coordinate to return information for
     * @param myShip is true if displaying self board, false if displaying enemy board
     * @return The view-specific information at that coordinate.
     * @throws IllegalArgumentException if where is not part of the Ship
     */
    @Override
    public T getDisplayInfoAt(Coordinate where, Boolean myShip) throws IllegalArgumentException {
        if (myShip) {
            return myDisplayInfo.getInfo(where, wasHitAt(where));
        } else {
            return enemyDisplayInfo.getInfo(where, wasHitAt(where));
        }
    }

    /**
     * Check if this coordinate is part of the ship. Throw IllegalArgumentException if not.
     *
     * @param where is the coordinates to check.
     * @throws IllegalArgumentException if the coordinates are not part of this
     *                                  ship.
     */
    protected void checkCoordinateInThisShip(Coordinate where) throws IllegalArgumentException {
        if (!myPieces.containsKey(where)) {
            throw new IllegalArgumentException("The given Coordinate " + where.toString() + " is not part of the ship!\n");
        }
    }

    /**
     * Get all the Coordinates that this Ship occupies.
     *
     * @return An Iterable with the coordinates that this Ship occupies
     */
    @Override
    public Iterable<Coordinate> getCoordinates() {
        return myPieces.keySet();
    }

    /**
     * Get all the Coordinates that this Ship occupies.
     *
     * @return An Iterable with the coordinates that this Ship occupies
     */
    @Override
    public Iterable<Coordinate> getHitCoordinates() {
        HashSet<Coordinate> hitCoords = new HashSet<>();
        for (Coordinate c : myPieces.keySet()) {
            if (myPieces.get(c)) {
                hitCoords.add(c);
            }
        }
        return hitCoords;
    }

    /**
     * This method push the value set of the ship pieces into an Arraylist
     * and return it.
     *
     * @return an Arraylist of boolean recording whether the squares are hit or not
     */
    @Override
    public ArrayList<Boolean> getHitList() {
        ArrayList<Boolean> hitList = new ArrayList<>();
        for (Map.Entry<Coordinate, Boolean> e : myPieces.entrySet()) {
            hitList.add(e.getValue());
        }
        return hitList;
    }

    /**
     * This method synchronizes the hit situation of a ship with another ship,
     * given the Arraylist of another ship's hit situation.
     *
     * @param hitList the list of hit
     * @throws IllegalArgumentException if the two ship to synchronize is not of the same ship type
     */
    @Override
    public void syncHit(ArrayList<Boolean> hitList) throws IllegalArgumentException {
        if (hitList.size() != myPieces.size()) {
            throw new IllegalArgumentException("The two ship to synchronize is not of the same ship type!");
        }
        Iterator<Map.Entry<Coordinate, Boolean>> iter = myPieces.entrySet().iterator();
        int count = 0;
        while (iter.hasNext()) {
            myPieces.put(iter.next().getKey(), hitList.get(count++));
        }
    }
}
