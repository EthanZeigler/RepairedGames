package backEnd;

/**
 * Created by ethanzeigler on 5/19/15.
 */
public class BattleshipBlock {

    private int x,y;
    private boolean hit = false; // TODO clarify meaning
    private Ship.BlockCollectionType blockCollectionType;
    private Status status; // TODO show this addition and explain its use

    public BattleshipBlock(int x, int y, Ship.BlockCollectionType blockCollectionType) {
        this.x = x;
        this.y = y;
        this.blockCollectionType = blockCollectionType; // had forgotten to initialize this in the constructor!

        this.status = Status.BLANK;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public Ship.BlockCollectionType getBlockCollectionType() {
        return blockCollectionType;
    }

    public Status getStatus() { return status; }

    public void setStatus (Status status) { this.status = status; }
}
