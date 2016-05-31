import dataApi.DataFile;
import backEnd.Player;
import backEnd.Ship;

/**
 * Created by ericgiovannini on 6/4/15.
 */
public class Language {
    private static DataFile file = new DataFile("language", "txt");
    public enum Message{
        /**
         * No additional requirements
         */
        MISS,
        /**
         * No additional requirements
         */
        HIT,
        /**
         * Requires ship type sunk
         */
        SINK,
        /**
         * Requires player type next player
         */
        CHANGE_PLAYER_PROMPT,
        /**
         * requires player type winner
         */
        GAME_OVER,
        /**
         * requires ship type placed
         */
        PLACE_SHIP,
        /**
         * no requirements
         */
        INVALID_SHIP_PLACE,
        /**
         * no requirements
         */
        BLOCK_ALREADY_ATTACKED;
    }

    public static String getMessage(Message message){
        return getMessage(message.toString(), Player.PLAYER_1, Ship.BlockCollectionType.BATTLESHIP);
    }

    public static String getMessage(Message message, Player playerInQuestion){
        return getMessage(message.toString(), playerInQuestion, Ship.BlockCollectionType.BATTLESHIP);
    }

    public static String getMessage(Message message, Ship.BlockCollectionType shipType){
        return getMessage(message.toString(), Player.PLAYER_1, shipType);
    }

    public static String getMessage(String message, Player playerInQuestion, Ship.BlockCollectionType shipType){
        String output = file.getString(message);
        output.replace("{PLAYER}", playerInQuestion == Player.PLAYER_1 ? "player 1" : "player 2");
        output.replace("{SHIPTYPE}", shipType.name);
        return output;
    }

}
