package backEnd;

/**
 * Created by Ethan on 5/31/2015.
 */
public class AttackResponse {


    public enum ValidityResponse{
        VALID_ATTACK,
        IS_OFF_BOARD,
        HAS_ALREADY_BEEN_ATTACKED,
        GAME_IS_ALREADY_OVER;
    }
    private ValidityResponse validity;
    private boolean didSinkShip;
    private boolean didHitBoat;
    private boolean gameHasBeenWon;

    private String shipAffected;

    public AttackResponse(ValidityResponse validity, boolean didSinkShip, boolean didHitBoat, boolean gameHasBeenWon) {
        this.validity = validity;
        this.didSinkShip = didSinkShip;
        this.didHitBoat = didHitBoat;
        this.gameHasBeenWon = gameHasBeenWon;
    }

    public ValidityResponse getValidity(){
        return validity;
    }

    public boolean didSinkShip() {
        return didSinkShip;
    }

    public boolean didHitBoat() {
        return didHitBoat;
    }

    public boolean gameHasBeenWon() {
        return gameHasBeenWon;
    }

    protected void setValidity(ValidityResponse validity) {
        this.validity = validity;
    }

    protected void setDidSinkShip(boolean didSinkShip) {
        this.didSinkShip = didSinkShip;
    }

    protected void setDidHitBoat(boolean didHitBoat) {
        this.didHitBoat = didHitBoat;
    }

    protected void setGameHasBeenWon(boolean gameHasBeenWon) {
        this.gameHasBeenWon = gameHasBeenWon;
    }

    public String getShipAffected() {
        return shipAffected;
    }

    public void setShipAffected(String shipAffected) {
        this.shipAffected = shipAffected;
    }
}
