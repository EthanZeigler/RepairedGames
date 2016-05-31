import backEnd.BackEndAdapter;
import backEnd.Player;

/**
 * Created by carlg on 5/29/15.
 */
public class Main {

    public static void main(String[] args)
    {

        BackEndAdapter a = new BackEndAdapter();
        MainInterface m = new MainInterface(a); // pass the adapter to the interface


        /*
        for (int i = 0; i < 4; i++) {
            a.attack(Player.PLAYER_1, 0 , i);

        }

        //a.attack(Player.PLAYER_1, 0, 5);
        a.attack(Player.PLAYER_1, 0, 4);

        m.refreshTable();
        */


    }
}
