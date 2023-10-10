package edu.duke.tq22.battleship;

import java.io.*;
import java.util.*;
import java.util.function.Function;

import static java.lang.Math.abs;

public class TextPlayer {
    private final String name;
    private boolean isComputer;
    private final Board<Character> theBoard;
    private final BoardTextView view;
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final AbstractShipFactory<Character> shipFactory;
    private final ArrayList<String> shipsToPlace;
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
    private final HashSet<String> VHships;
    final HashMap<String, Function<TextPlayer, String>> actionFns;
    private int moveChance, sonarChance;
    private final HashSet<Coordinate> sonarCoords;


    /**
     * This is a constructor for an TextPlayer, with the name, board, input reader
     * printStream and shipFactory given.
     *
     * @param name        is the name of the player
     * @param theBoard    is the Board for the game, currently only board taking
     *                    Character is allowed.
     * @param inputSource is the Reader for reading input from user.
     * @param out         is the PrintStream to which the program will print its
     *                    output
     * @param shipFactory is the factory to make ships
     */
    public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> shipFactory,
                      int moveChance, int sonarChance) {
        this.name = name;
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputSource;
        this.out = out;
        boolean error;
        do {
            error = false;
            try {
                this.isComputer = readPlayerType(name);
            } catch (IOException | IllegalArgumentException e) {
                out.println(e.getMessage());
                error = true;
            }
        } while (error);
        this.shipFactory = shipFactory;
        this.shipsToPlace = new ArrayList<>();
        setupShipCreationList();
        this.shipCreationFns = new HashMap<>();
        setupShipCreationMap();
        this.VHships = new HashSet<>();
        setupVHShipList();
        this.actionFns = new HashMap<>();
        setupActionMap();
        this.moveChance = moveChance;
        this.sonarChance = sonarChance;
        this.sonarCoords = new HashSet<>();
        setupSonarCoords();
    }

    /**
     * This method set up the hashMap mapping the ship name to function that
     * make the ship.
     */
    protected void setupShipCreationMap() {
        shipCreationFns.put("Submarine", shipFactory::makeSubmarine);
        shipCreationFns.put("Destroyer", shipFactory::makeDestroyer);
        shipCreationFns.put("Battleship", shipFactory::makeBattleship);
        shipCreationFns.put("Carrier", shipFactory::makeCarrier);
    }

    /**
     * This method set up the list of the ships to be placed.
     */
    protected void setupShipCreationList() {
        shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    }

    /**
     * This method set up the list of the ships that are oriented vertically
     * or horizontally.
     */
    protected void setupVHShipList() {
        VHships.add("Submarine");
        VHships.add("Destroyer");
    }

    /**
     * This method set up the hashMap mapping the action input to function that
     * execute the action.
     */
    protected void setupActionMap() {
        actionFns.put("F", this::doAttackingPhase);
        actionFns.put("M", this::doMovePhase);
        actionFns.put("S", this::doSonarPhase);
    }

    /**
     * This method set up basic list of the coordinates covered by the sonar.
     */
    protected void setupSonarCoords() {
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                if (abs(i + j) <= 3 && abs(i - j) <= 3) {
                    sonarCoords.add(new Coordinate(i, j));
                }
            }
        }
    }

    /**
     * This method reads a string line from user and return it.
     *
     * @param prompt The prompt for reminding the user to give the input.
     * @return the string read from the user
     * @throws EOFException when no input is read from the player
     */
    public String readStringLine(String prompt) throws IOException {
        if (!isComputer) {
            out.println(prompt);
        }
        String str = inputReader.readLine();
        if (str == null) {
            throw new EOFException("The input is not allowed to be empty!");
        }
        return str;
    }

    /**
     * This method reads the player type from user and return the Boolean to
     * initialize isComputer according to the string given.
     *
     * @param name The prompt for reminding the user to give the input.
     * @return true for computer player, false for human player
     * @throws IOException            if no input is read from the user
     * @throws IllegalFormatException if the player type is not y or n
     */
    public boolean readPlayerType(String name) throws IOException, IllegalArgumentException {
        String str = readStringLine("Would you like to set Player " + name + " as a computer player? [y/n]").toLowerCase();

        if (str.equals("y")) {
            return true;
        } else if (str.equals("n")) {
            return false;
        } else {
            throw new IllegalArgumentException("Please type 'y' for yes or 'n' for no!");
        }
    }

    /**
     * This method reads the coordinate string from user and return the Coordinate
     * according to the string given.
     *
     * @param prompt The prompt for reminding the user to give the input.
     * @return the coordinate read from the user
     * @throws IOException            if no input is read from the player
     * @throws IllegalFormatException if the string is not legal for a Coordinate
     */
    public Coordinate readCoordinate(String prompt) throws IOException, IllegalArgumentException {
        Coordinate c = new Coordinate(readStringLine(prompt));
        if (c.getRow() >= theBoard.getHeight() || c.getCol() >= theBoard.getWidth()) {
            throw new IllegalArgumentException("The coordinate is off the board.");
        }
        return c;
    }

    /**
     * This method reads the placement string from user and return the Placement
     * according to the string given.
     *
     * @param shipName the name of the ship to place
     * @param prompt   The prompt for reminding the user to give the input.
     * @return the placement read from the user
     * @throws EOFException             when no input is read from the player
     * @throws IllegalArgumentException when the string is not legal for a placement
     */
    public Placement readPlacement(String shipName, String prompt) throws IOException, IllegalArgumentException {
        String str = readStringLine(prompt);
        if (VHships.contains(shipName)) {
            return new PlacementTwo(str);
        } else {
            return new PlacementFour(str);
        }

    }

    /**
     * This method reads the action type string from user and return the action type
     * according to the string given.
     *
     * @param prompt The prompt for reminding the user to give the input.
     * @return the action type read from the user
     * @throws EOFException             when no input is read from the player
     * @throws IllegalArgumentException when the string is not legal for an action type
     */
    public String readAction(String prompt) throws IOException, IllegalArgumentException {
        String action = readStringLine(prompt);

        if (!action.equalsIgnoreCase("F") && !action.equalsIgnoreCase("M") && !action.equalsIgnoreCase("S")) {
            throw new IllegalArgumentException("The action should be Fire(F), Move(M) or Sonar(S), but the input is ".concat(action) + ".");
        }
        if (action.equalsIgnoreCase("M") && moveChance <= 0) {
            throw new IllegalArgumentException("Your chances for moving ships have been used up!");
        }
        if (action.equalsIgnoreCase("S") && sonarChance <= 0) {
            throw new IllegalArgumentException("Your chances for sonar scan have been used up!");
        }
        return action;
    }

    /**
     * This method generates a random Coordinate.
     *
     * @return the random Coordinate
     */
    public Coordinate randomCoordinate() {
        Random random = new Random();
        int r = random.nextInt(theBoard.getHeight());
        int c = random.nextInt(theBoard.getWidth());
        return new Coordinate(r, c);
    }

    /**
     * This method generates a random Placement.
     *
     * @param shipName the ship to place
     * @return the random Placement
     */
    public Placement randomPlacement(String shipName) {
        Random random = new Random();
        Coordinate where = randomCoordinate();
        String oList = "VHUDLR";
        if (VHships.contains(shipName)) {
            return new PlacementTwo(where, oList.charAt(random.nextInt(2)));
        } else {
            return new PlacementFour(where, oList.charAt(random.nextInt(4) + 2));
        }
    }

    /**
     * This method generates a random action type.
     * 17 / 20 possibility to fire
     * 1 / 10 possibility to move
     * 1 / 20 possibility to use sonar (if chances are not used up)
     *
     * @return the random Coordinate
     */
    public String randomAction() {
        Random random = new Random();
        String actionList = "F".repeat(17);
        if (moveChance > 0) {
            actionList += "MM";
        }
        if (sonarChance > 0) {
            actionList += "S";
        }
        return String.valueOf(actionList.charAt(random.nextInt(actionList.length())));
    }

    /**
     * This method places a Destroyer on the BattleShipBoard according to the input
     * instruction from the user.
     *
     * @param shipName is the name of the ship
     * @param createFn is the function to make the ship by the factory
     * @throws IOException              if
     * @throws IllegalArgumentException if any error is detected by the checker or from illegal from read
     */
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException, IllegalArgumentException {

        // get the placement
        Placement place;
        if (isComputer) {
            place = randomPlacement(shipName);
        } else {
            place = readPlacement(shipName, "Player " + name + " where do you want to place a " + shipName + "?");
        }

        // make the ship
        Ship<Character> ship = createFn.apply(place);

        // place the ship
        String errorInfo = theBoard.tryAddShip(ship);
        if (errorInfo != null) {
            throw new IllegalArgumentException(errorInfo);
        }
        if (!isComputer) {
            out.print(view.displayMyOwnBoard());
        }
    }

    /**
     * This method starts a placement phase for the player.
     * If any exception is thrown from the doOnePlacement, user will
     * be asked to do it again.
     */
    public void doPlacementPhase() {
        if (!isComputer) {
            out.print(view.displayMyOwnBoard());
            out.print("--------------------------------------------------------------------------------\n" +
                    "Player " + name + ": you are going to place the following ships (which are all\n" +
                    "rectangular). For each ship, type the coordinate of the upper left\n" +
                    "side of the ship, followed by either H (for horizontal) or V (for\n" +
                    "vertical).  For example M4H would place a ship horizontally starting\n" +
                    "at M4 and going to the right.  You have\n" +
                    "\n" +
                    "2 \"Submarines\" ships that are 1x2 \n" +
                    "3 \"Destroyers\" that are 1x3\n" +
                    "3 \"Battleships\" that are 1x4\n" +
                    "2 \"Carriers\" that are 1x6\n" +
                    "--------------------------------------------------------------------------------\n");
        }
        for (String shipName : shipsToPlace) {
            boolean error;
            do {
                error = false;
                try {
                    doOnePlacement(shipName, shipCreationFns.get(shipName));
                } catch (IOException | IllegalArgumentException e) {
                    if (!isComputer) {
                        out.println(e.getMessage());
                    }
                    error = true;
                }
            } while (error);
        }
    }

    /**
     * This method starts an action phase for the player.
     * User will be asked to choose the type of action if the chances of move
     * and sonar haven't been used up. If any exception is thrown, user will
     * be asked to do it again.
     *
     * @return game over announcement string, null if the game is not over
     */
    public String doActionPhase(TextPlayer enemy) {
        out.print("---------------------------------------------------------------------------\n" +
                "Player " + name + "'s turn:\n");
        if (moveChance == 0 && sonarChance == 0) {
            return this.doAttackingPhase(enemy);
        }
        String actionStr = null;
        if (isComputer) {
            actionStr = randomAction();
        } else {
            boolean error;
            do {
                error = false;
                try {
                    actionStr = readAction(makeActionPrompt()).toUpperCase();
                } catch (IOException | IllegalArgumentException e) {
                    if (!isComputer) {
                        out.println(e.getMessage());
                    }
                    error = true;
                }
            } while (error);
        }
        return actionFns.get(actionStr).apply(enemy);
    }

    /**
     * This method makes the prompt to print before the action phase.
     *
     * @return the prompt to print before the action phase
     */
    public String makeActionPrompt() {
        String prompt = "Possible actions for Player " + name + ":\n" +
                "\n" +
                " F Fire at a square\n";
        if (moveChance > 0) {
            prompt += " M Move a ship to another square (" + moveChance + " remaining)\n";
        }
        if (sonarChance > 0) {
            prompt += " S Sonar scan (" + sonarChance + " remaining)\n";
        }
        prompt += "\n" +
                "Player " + name + ", what would you like to do?\n" +
                "---------------------------------------------------------------------------";
        return prompt;
    }

    /**
     * This method start the attack phase of the player.
     *
     * @param enemy is the enemy player to be attack
     * @return the result string, null if the player hasn't won yet.
     */
    public String doAttackingPhase(TextPlayer enemy) {

        // display the board
        if (!isComputer) {
            out.print(view.displayMyBoardWithEnemyNextToIt(enemy.view, "Your Ocean", "Player " + enemy.name + "'s ocean"));
        }

        // player choose the position to fire
        Coordinate where;
        boolean error;
        do {
            error = false;
            try {
                if (isComputer) {
                    where = randomCoordinate();
                } else {
                    where = readCoordinate("Player " + name + ", where do you want to fire at?");
                }
                printFireResult(enemy.theBoard.fireAt(where), where, enemy);
            } catch (IOException | IllegalArgumentException e) {
                if (!isComputer) {
                    out.println(e.getMessage());
                }
                error = true;
            }
        } while (error);

        // check if the player wins
        out.print("---------------------------------------------------------------------------\n");
        if (enemy.theBoard.checkLose()) {
            if (isComputer && !enemy.isComputer) {
                return "You loses the game.\n";
            } else if (!isComputer && enemy.isComputer) {
                return "You wins the game.\n";
            } else {
                return "Player " + name + " wins the game!!!\n";
            }
        } else {
            return null;
        }
    }

    /**
     * This method prints out the fire result of an attack.
     *
     * @param ship  the ship hit, null if missed
     * @param where the coordinated hit
     * @param enemy the enemy player
     */
    protected void printFireResult(Ship<Character> ship, Coordinate where, TextPlayer enemy) {
        if (isComputer) {
            String pos = (char) ((int) 'A' + where.getRow()) + String.valueOf((char) ((int) '0' + where.getCol()));
            if (ship != null) {
                if (enemy.isComputer) {
                    out.print("Player " + name + " hit Player " + enemy.name + "'s " + ship.getName() + " at " + pos + "!\n");
                } else {
                    out.print("Player " + name + " hit Your " + ship.getName() + " at " + pos + "!\n");
                }
            } else {
                out.print("Player" + name + " missed at " + pos + "!\n");
            }
        } else {
            if (ship != null) {
                out.print("You hit a " + ship.getName() + "!\n");
            } else {
                out.print("You missed!\n");
            }
        }
    }

    /**
     * Start the move phase to move a ship to another place.
     *
     * @param enemy the enemy player
     * @return the result string, null if the player hasn't won yet.
     */
    public String doMovePhase(TextPlayer enemy) {
        if (!isComputer) {
            out.print(view.displayMyOwnBoard());
        }

        boolean error;
        Ship<Character> oldShip = null;
        do {
            error = false;
            try {
                oldShip = removeOldShip();
            } catch (IOException | IllegalArgumentException e) {
                if (!isComputer) {
                    out.println(e.getMessage());
                }
                error = true;
            }
        } while (error);

        do {
            error = false;
            try {
                placeNewShip(oldShip);
            } catch (IOException | IllegalArgumentException e) {
                if (!isComputer) {
                    out.println(e.getMessage());
                }
                error = true;
            }
        } while (error);

        moveChance--;
        if (!isComputer) {
            out.print(view.displayMyOwnBoard());
        }
        return null;
    }

    /**
     * This method reads a coordinate from the player, and remove the ship the
     * coordinate belongs to from the board.
     *
     * @return the ship removed
     * @throws IOException              if any problem with input reading
     * @throws IllegalArgumentException if the input coordinate is not a coordinate of a ship
     */
    protected Ship<Character> removeOldShip() throws IOException, IllegalArgumentException {
        // read the coordinate
        Coordinate oldWhere;
        if (isComputer) {
            oldWhere = randomCoordinate();
        } else {
            oldWhere = readCoordinate("Player " + name +
                    ", which ship do you want to move?\nPlease type any coordinate which is a part of the ship you want.");
        }

        // find the ship
        Ship<Character> oldShip = theBoard.findShip(oldWhere);
        if (oldShip == null) {
            throw new IllegalArgumentException("Your input coordinate does not occupy any ship!");
        }

        // remove the ship from board
        theBoard.removeShip(oldShip, true);

        return oldShip;
    }

    /**
     * This method read a placement from the player and place the ship to the
     * given placement.
     *
     * @param oldShip the ship removed
     * @throws IOException              if any problem with input reading
     * @throws IllegalArgumentException f the input placement is not legal
     */
    protected void placeNewShip(Ship<Character> oldShip) throws IOException, IllegalArgumentException {
        // get new placement
        String shipName = oldShip.getName();
        Placement newPlace;
        if (isComputer) {
            newPlace = randomPlacement(shipName);
        } else {
            newPlace = readPlacement(shipName, "Player " + name + ", please type the destination placement of the movement.");
        }

        // make new ship
        Ship<Character> newShip = shipCreationFns.get(shipName).apply(newPlace);
        newShip.syncHit(oldShip.getHitList());

        // place the new ship on the board
        String errorInfo = theBoard.tryAddShip(newShip);
        if (errorInfo != null) {
            throw new IllegalArgumentException(errorInfo);
        }
        theBoard.hideShip(newShip);
    }

    /**
     * This method start the sonar detection phase of the player.
     *
     * @param enemy is the enemy player
     * @return the result string, null if the player hasn't won yet.
     */
    public String doSonarPhase(TextPlayer enemy) {
        // display the board
        if (!isComputer) {
            out.print(enemy.view.displayEnemyBoard());
        }

        // get the centre
        Coordinate centre = new Coordinate(0, 0);
        if (isComputer) {
            centre = randomCoordinate();
        } else {
            boolean error;
            do {
                error = false;
                try {
                    centre = readCoordinate("Player " + name + ", please type the centre coordinate of the sonar scan.");
                } catch (IOException | IllegalArgumentException e) {
                    if (!isComputer) {
                        out.println(e.getMessage());
                    }
                    error = true;
                }
            } while (error);
        }

        // count the square number
        int submarineCount = 0, destroyerCount = 0, battleshipCount = 0, carrierCount = 0;
        for (Coordinate c : sonarCoords) {
            int row = centre.getRow() + c.getRow(), col = centre.getCol() + c.getCol();
            if (row < 0 || row >= theBoard.getHeight() || col < 0 || col >= theBoard.getWidth()) {
                continue;
            }
            Coordinate where = new Coordinate(row, col);
            Character info = enemy.theBoard.whatIsAtSelf(where);
            if (info == null) {
                continue;
            } else if (info == 's') {
                submarineCount++;
            } else if (info == 'd') {
                destroyerCount++;
            } else if (info == 'b') {
                battleshipCount++;
            } else if (info == 'c') {
                carrierCount++;
            }
        }

        // print the result
        if (!isComputer) {
            out.print("---------------------------------------------------------------------------\n" +
                    "Submarines occupy " + submarineCount + " squares\n" +
                    "Destroyers occupy " + destroyerCount + " squares\n" +
                    "Battleships occupy " + battleshipCount + " squares\n" +
                    "Carriers occupy " + carrierCount + " square\n");
        }

        sonarChance--;
        return null;
    }
}
