package edu.duke.tq22.battleship;

/**
 * This interface represents an Abstract Factory pattern for Ship creation.
 */
public interface AbstractShipFactory<T> {
    /**
     * Make a submarine.
     *
     * @param where specifies the location and orientation of the ship to make
     * @return the Ship created for the submarine.
     */
    Ship<T> makeSubmarine(Placement where);

    /**
     * Make a battleship.
     *
     * @param where specifies the location and orientation of the ship to make
     * @return the Ship created for the battleship.
     */
    Ship<T> makeBattleship(Placement where);

    /**
     * Make a carrier.
     *
     * @param where specifies the location and orientation of the ship to make
     * @return the Ship created for the carrier.
     */
    Ship<T> makeCarrier(Placement where);

    /**
     * Make a destroyer.
     *
     * @param where specifies the location and orientation of the ship to make
     * @return the Ship created for the destroyer.
     */
    Ship<T> makeDestroyer(Placement where);

}