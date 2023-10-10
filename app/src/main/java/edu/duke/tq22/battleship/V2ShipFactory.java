package edu.duke.tq22.battleship;

public class V2ShipFactory implements AbstractShipFactory<Character> {
    /**
     * Make a submarine.
     *
     * @param where specifies the location and orientation of the ship to make
     * @return the Ship created for the submarine.
     */
    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        return createRectangleShip(where, 1, 2, 's', "Submarine");
    }

    /**
     * Make a destroyer.
     *
     * @param where specifies the location and orientation of the ship to make
     * @return the Ship created for the destroyer.
     */
    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createRectangleShip(where, 1, 3, 'd', "Destroyer");
    }

    /**
     * Make a battleship.
     *
     * @param where specifies the location and orientation of the ship to make
     * @return the Ship created for the battleship.
     */
    @Override
    public Ship<Character> makeBattleship(Placement where) {
        return new Battleship<>(where, 'b', '*');
    }

    /**
     * Make a carrier.
     *
     * @param where specifies the location and orientation of the ship to make
     * @return the Ship created for the carrier.
     */
    @Override
    public Ship<Character> makeCarrier(Placement where) {
        return new Carrier<>(where, 'c', '*');
    }


    /**
     * create a ship with type and placement given
     *
     * @param where  specifies the location and orientation of the ship to make
     * @param w      width of the ship
     * @param h      height of the ship
     * @param letter display information of the ship
     * @param name   name of the ship
     * @return the Ship created
     */
    protected Ship<Character> createRectangleShip(Placement where, int w, int h, char letter, String name) {
        if (where.getOrientation() == 'V') {
            return new RectangleShip<>(name, where.getWhere(), w, h, letter, '*');
        } else {
            return new RectangleShip<>(name, where.getWhere(), h, w, letter, '*');
        }
    }
}
