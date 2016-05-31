package backEnd;

/**
 * Created by Ethan on 6/1/2015.
 */
public class BackEndAdapter {

    private BattleshipGrid player1Grid;
    private BattleshipGrid player2Grid;


    public enum GameState
    {
        GAME_NOT_BEGUN, GAME_IN_PLAY
    }

    public BackEndAdapter() {
        player1Grid = new BattleshipGrid();
        player2Grid = new BattleshipGrid();
        //DEBUG
        System.out.println("Backend ready");


    }

    // TODO - we don't need to add it to the opposite player's grid! This was the fix to the bug!
    public final boolean addShip(Ship ship, Player player){
        if(player.equals(Player.PLAYER_1)){
            System.out.println("Added.");
            return player1Grid.placeBoat(ship); // TODO explain why this has been reversed.
        } else {
            return player2Grid.placeBoat(ship);
        }
    }

    public backEnd.BattleshipBlock[][] getBlocks(Player player)
    {
        if(player.equals(Player.PLAYER_1)){
            return player1Grid.getBlocks();
        } else {
            return player2Grid.getBlocks();
        }
    }

    public AttackResponse attack(Player player, int x, int y){
        if(player.equals(Player.PLAYER_1)){
            System.out.println("Player 1 attacking player 2");
            return player1Grid.attackPosition(x, y);
        } else {
            return player2Grid.attackPosition(x, y);
        }
    }

    public int getHealthOfShip()
    {
        return 0;
    }

    public ShipStorageModel getShipStorageModel(Player player)
    {
        ShipStorageModel model;
        if(player.equals(Player.PLAYER_1)) model = player1Grid.getShipStorageModel();
        else model = player2Grid.getShipStorageModel();

        return model;
    }

    public void switchBoards(){
        System.out.println("Inside switch boards");
        BattleshipGrid tempGrid = player1Grid;
        player1Grid = player2Grid;
        player2Grid = tempGrid;
        System.out.println("done");
    }

    public void clearBoardsForStart(){
        player1Grid.setToBlankForStart();
        player2Grid.setToBlankForStart();

    }

    public String player1ToString(){
        return player1Grid.toString();
    }

    public String player2ToString(){
        return player2Grid.toString();
    }


    public boolean player1NoShipsLeft()
    {
        return player1Grid.getShipsLeft() == 0;
    }

}
