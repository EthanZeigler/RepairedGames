package backEnd;

import com.ethanzeigler.jGameGUI.sound.AudioClip;
import sun.audio.AudioPlayer;

/**
 * Created by ethanzeigler on 5/19/15.
 */
public class BattleshipGrid {

    private boolean gameIsOver = false;
    private BattleshipBlock[][] blocks = new BattleshipBlock[10][10];

    private ShipStorageModel shipStorageModel = new ShipStorageModel();


    public BattleshipGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                blocks[i][j] = new BattleshipBlock(i, j, Ship.BlockCollectionType.WATER_BLOCK);
            }
        }
    }

    /**
     * Adds the boat to the grid
     *
     * @param ship backEnd.Ship to add
     * @return true if valid position and has been added, false if boat was not placed and intersects with another
     */
    public boolean placeBoat(Ship ship) {
        System.out.println("placing ship");
        // validate location

        try {
            for (int i = 0; i < ship.getType().NUM_OF_BLOCKS; i++) {
                if (ship.isHorizontal()) { // place it horizontally: the first index (the row) does not change, but the
                    // second one (the column) does
                    if (blocks[ship.getBlockOriginY()][ship.getBlockOriginX() + i].getBlockCollectionType() !=
                            Ship.BlockCollectionType.WATER_BLOCK) {
                        return false;
                    }
                } else {
                    // place it vertically: the first index (the row) does change, but the second one (the column) does not
                    if (blocks[ship.getBlockOriginY() + i][ship.getBlockOriginX()].getBlockCollectionType() !=
                            Ship.BlockCollectionType.WATER_BLOCK) {
                        return false;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error caught");
            return false;
        }

        // is a valid position, place boat
        for (int i = 0; i < ship.getType().NUM_OF_BLOCKS; i++) {
            if (ship.isHorizontal()) { // place it horizontally: the first index (the row) does not change, but the
                // second one (the column) does
                blocks[ship.getBlockOriginY()][ship.getBlockOriginX() + i] =
                        new BattleshipBlock(ship.getBlockOriginX() + i, ship.getBlockOriginY(), ship.getType());
                blocks[ship.getBlockOriginY()][ship.getBlockOriginX() + i].setStatus(Status.PLACED);
            } else {
                // place it vertically: the first index (the row) does change, but the second one (the column) does not
                blocks[ship.getBlockOriginY() + i][ship.getBlockOriginX()] =
                        new BattleshipBlock(ship.getBlockOriginX(), ship.getBlockOriginY() + i, ship.getType());
                blocks[ship.getBlockOriginY() + i][ship.getBlockOriginX()].setStatus(Status.PLACED);
            }
        }

        shipStorageModel.addShip(ship);

        return true;
    }

    public AttackResponse attackPosition(int x, int y) {
        System.out.println("Inside attack position method");
        AttackResponse response = new AttackResponse(AttackResponse.ValidityResponse.VALID_ATTACK, false, false, false);
        BattleshipBlock block;
        try {
            block = blocks[y][x];

        } catch (ArrayIndexOutOfBoundsException e) {
            return new AttackResponse(AttackResponse.ValidityResponse.IS_OFF_BOARD, false, false, false);
        }

        if (block.isHit()) {
            return new AttackResponse(AttackResponse.ValidityResponse.HAS_ALREADY_BEEN_ATTACKED, false, false, false);
        } else if (gameIsOver) {
            return new AttackResponse(AttackResponse.ValidityResponse.GAME_IS_ALREADY_OVER, false, false, false);
        }

        // is valid, attack block

        block.setHit(true); // hit as in clicked, not necessarily that we hit a ship

        Ship.BlockCollectionType type = block.getBlockCollectionType();
        System.out.println(type);

        if (type != Ship.BlockCollectionType.WATER_BLOCK)
        {
            response.setDidHitBoat(true);
            response.setDidSinkShip(shipStorageModel.getShip(type).decrementRemainingHits());
            if (response.didSinkShip()) updateBlockStatusAfterSinking(shipStorageModel.getShip(type));
            response.setValidity(AttackResponse.ValidityResponse.VALID_ATTACK);
            new AudioClip("explosion.wav").playOnce();

        } else
        {
            new AudioClip("splash.wav").playOnce();
            response.setValidity(AttackResponse.ValidityResponse.VALID_ATTACK);
            response.setDidHitBoat(false);
            blocks[y][x].setStatus(Status.MISS); // TODO consider moving this to an else if below the if statement
        }


        // health is 5
        // hit -> health is 4, old value is 5
        // hit -> health is 3, old value is 4
        // hit -> health is 2, old value is 3
        // hit -> health is 1, old value is 2
        // hit -> health is 0, old value is NULL because we call the remove method. This method includes the following
        // line:
        //         getPropertyChangeSupport().firePropertyChange(SHIPS, shipsInPlay.get(t), null);
        // shipsInPlay.get(t) will return the new version of the ship (after being attacked), so of course its
        // health will be zero. Also, there will be no oldValue (it will be null); this is how the event handler
        // in the MainInterface class knows what to do (remove the sunk ship from the list model)

        if (response.didSinkShip())
        {
            response.setShipAffected(type.name);
            //shipsInPlay.remove(Ship.BlockCollectionType.AIRCRAFT_CARRIER.ordinal());
            shipStorageModel.removeShip(type); // TODO or instead of removing it, invalidate it, and then have the custom list data model act accordingly ----------------------------------
            System.out.println("REMOVING " + type.ordinal());
        }

        // TODO explain why I added this and what the alternative was (inside the switch statement)
        if (response.didHitBoat() && !response.didSinkShip())
        // or deal with it AFTER the response is returned by this method!
        {
            System.out.println("Hit a ship!");
            blocks[y][x].setStatus(Status.HIT);
            response.setShipAffected(type.name);
            shipStorageModel.updateShip(type);
        }

        boolean oneIsAlive = false;
        for (Ship s : shipStorageModel.getShips().values())
        {
            if (!s.isSunk())
            {
                oneIsAlive = true;
                break;
            }
        }
        if (!oneIsAlive) {
            response.setGameHasBeenWon(true);
            this.gameIsOver = true;
        }

        //updateStatusArray();

        System.out.println(response.didHitBoat());
        return response;
    }

    public void updateBlockStatusAfterSinking(Ship s) {
        int x = s.getBlockOriginX(), y = s.getBlockOriginY();
        boolean isHoriz = s.isHorizontal();
        for (int i = 0; i < s.getType().NUM_OF_BLOCKS; i++) {
            blocks[y + (isHoriz ? 0 : i)][x + (isHoriz ? i : 0)].setStatus(Status.SUNK);
            System.out.println("Updating block at x of " + (x + (isHoriz ? i : 0)) + " and y of " +
                    (y + (isHoriz ? 0 : i)));
        }
    }

    public BattleshipBlock[][] getBlocks() {
        return blocks;
    }

    public ShipStorageModel getShipStorageModel()
    {
        return shipStorageModel;
    }

    public int getShipsLeft()
    {
        return shipStorageModel.getShips().size();
    }


    // We could actually figure out which blocks are the ones representing placed ships and only reset them
    protected void setToBlankForStart(){
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                BattleshipBlock block = blocks[y][x];
                if(block.getStatus() == Status.PLACED) {
                    block.setStatus(Status.BLANK);
                }
            }
        }
    }


    // TODO make a method that returns the ships so we can access them from outside the class

}
