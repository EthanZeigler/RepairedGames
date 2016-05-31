package backEnd;

/**
 * Created by ethanzeigler on 5/19/15.
 * Note to Ulmer- there is a lot of commented out code here because we had originally planned on using images, but found an easier way
 */
public class Ship {
    public enum BlockCollectionType { // had to make public
        AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
        BATTLESHIP(4, "Battleship"),
        SUBMARINE(3, "Submarine"),
        PATROL_BOAT(2, "Patrol Boat"),
        DESTROYER(3, "Destroyer"),
        WATER_BLOCK(1, "Water Block");

        public final int NUM_OF_BLOCKS; // TODO is the public ok?
        public final String name;

        BlockCollectionType(int numOfBlocks, String name) {
            this.NUM_OF_BLOCKS = numOfBlocks;
            this.name = name;
        }
    }

    // private ImageIcon icon;
    private int blocksNotHit;
    private BlockCollectionType type;
    private boolean isHorizontal;
    private int blockOriginX, blockOriginY;
    // private int imageOriginX, imageOriginY;

    public Ship(BlockCollectionType type, int originX, int originY, boolean isHorizontal){
        //this.icon = getInitialImage();
        if(type.equals(BlockCollectionType.WATER_BLOCK)) throw new IllegalArgumentException("Water block is not a ship!");
        this.blocksNotHit = type.NUM_OF_BLOCKS;
        this.type = type;
        this.blockOriginX = originX;
        this.blockOriginY = originY;
        this.isHorizontal = isHorizontal;
    }

    public boolean hitBoat(){
        blocksNotHit--;
        if(blocksNotHit < 1){
            return true;
        } else return false;
    }

    //<editor-fold desc="Unneccesary code">
   /* private ImageIcon getInitialImage(){
        switch (this.type){
            case AIRCRAFT_CARRIER:
                if(isHorizontal); *//*pic*//*
                else; *//*pic*//*
                break;
            case BATTLESHIP:
                if(isHorizontal);*//*pic*//*
                else; *//*pic*//*
                break;
            case SUBMARINE:
                if(isHorizontal); *//*pic*//*
                else; *//*pic*//*
                break;
            case DESTROYER:
                if(isHorizontal);
                else;
                break;
            case PATROL_BOAT:
                if(isHorizontal); *//*pic*//*
                else; *//*pic*//*
                break;
        }
        return null;
    }

    private ImageIcon getSecondImage(){
        return null;
    }*/

    /*public int getImageOriginY() {
        return imageOriginY;
    }

    public ImageIcon getIcon() {
        return icon;
    }*/
    //</editor-fold>

    public int getBlocksNotHit() {
        return blocksNotHit;
    }

    public BlockCollectionType getType() {
        return type;
    }

    public String getName() { return type.name; }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public int getBlockOriginX() {
        return blockOriginX;
    }

    public int getBlockOriginY() {
        return blockOriginY;
    }

    public boolean isSunk(){
        if(blocksNotHit <= 0) return true;
        return false;
    }

    /**
     * Decreases boat's remaining lives.
     * @return true if boat is sunk
     */
    public boolean decrementRemainingHits(){
        blocksNotHit--;
        if(blocksNotHit <= 0){
            return true;
        }
        return false;
    }

    public String toString()
    {
        return this.type.name + " (" + this.blocksNotHit + ")";
    }

    //<editor-fold desc="Unneccesary code">
    /* public int getImageOriginX() {
        return imageOriginX;
    }
    */

   /* public int getImageOriginY(){
        return imageOriginY;
    }*/
    //</editor-fold>
}
