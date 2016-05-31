import backEnd.*;
import com.ethanzeigler.jGameGUI.sound.AudioClip;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by carlg on 5/29/15.
 *
 *
 * Represents a main user interface for a Battleship program. Contains a class relating to the table displaying the
 * game data, and one for the table's rendering mechanism.
 */

// Set boolean isListening. When you switch placing p,ayers, set to false. set back to true after first click(Closing window)
public class MainInterface extends JFrame implements PropertyChangeListener {
    private JPanel rootpanel;
    private JTable table1;
    private JScrollPane scrollPane;
    private JButton button1;
    private JList<Ship> yourUnsunkList;
    private JList<Ship> enemyUnsunkList;
    private BackEndAdapter ad = new BackEndAdapter();
    private BackEndAdapter.GameState state = BackEndAdapter.GameState.GAME_NOT_BEGUN;

    private MyTableModel player1Data, player2Data;
    private MouseAdapter initialMouseAdapter, mainMouseAdapter;
    private MyTableModel tempData, dataSave; // hasAttacked will be true
    private DefaultListModel<Ship> p1UnsunkShipsThatP2NeedsToSink, p2UnsunkShipsThatP1NeedsToSink; // had said DefaultListModel<String>()
    private Player currentPlayer = Player.PLAYER_1; // start with player 1
    private boolean hasAttacked = false;
    private boolean extraAttack = false;
    private int shipsPlaced = 0;


    private Timer timer;
    private ActionListener buttonPressed;

    private ShipStorageModel p1UnsunkShipsThatP2NeedsToSinkList, p2UnsunkShipsThatP1NeedsToSinkList;
    private List<Ship.BlockCollectionType> types;

    // These do not need to be accessed directly through the adapter class

    /**
     * This nested class represents the data stored in the table. It provides methods for accessing and setting
     * values in the table, as well as obtaining information on the number of rows and columns.
     *
     */
    static class MyTableModel extends AbstractTableModel { // should this nested class be public or private?

        /**
         * Represents one of the four possible statuses for a block on the board. Each possible status is associated
         * with a Color object; each cell on the board with a particular status is given its background color
         * using its status's associated Color object
         */

        /*public enum Status // this should be moved/merged with the enum in Status.java
        {
            BLANK (new Color(50, 100, 255)),
            MISS (new Color(0, 0, 0)),
            HIT (new Color(100, 255, 100)),
            SUNK (new Color(200, 0 , 0));

            private final Color color; // Doesn't need to be private; it's final (can't be changed)
            Status(Color c)
            {
                this.color = c;
            }
            public Color getColor() { return color; }
        }*/

        /**
         * The names of the columns in the table. Note that the first one has been left blank to accommodate
         * the "header column" which contains the row numbers instead of having any background color.
         */
        private String[] columnNames = {"", "A", "B", "C", "D",
                "E", "F", "G", "H", "I", "J"};


        /**
         * The data stored in the table. Note that this must be an Object array because the objects in our table
         * are not of one class. Most of the objects are of the Status "class", but the leftmost column
         * (the "header column") contains Integer objects. This is also the reason for the 10-by-11 dimensions
         * of the array as opposed to 10-by-10.
         */
        private Object[][] data = new Object[10][11];

        /**
         * Initializes all the cells to blank.
         */
        public MyTableModel()
        {
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 11; j++)
                {
                    if (j == 0) data[i][j] = (i+1);
                    else data[i][j] = Status.BLANK;
                }
            }
            //System.out.println(Status.BLANK);
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            //if (col < 2) {
                return false;
           // } else {
           //     return true;
           // }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }


    }

    /**
     * This static nested class represents a custom manner in which to render the cells in a table. A table
     * using this class as its renderer will cause all cells backed by Status "objects" to have as their
     * background color whatever color is associated with the Status "object" (using that status object's Color
     * property).
     * Note:
     * 1. This is not linked to a particular table. Instead, after creating a table object, we call the
     * setDefaultRenderer method, passing in the name of the class we want cells that contain objects of that class
     * to use the second argument, an instance of a TableCellRendering-implementing class, to format.
     *
     * 2. This class "is-a" JLabel, which is a Component (indirectly). Thus, methods like setBackground work because
     * they are technically being called on a JLabel.
     */
    public class ColorRenderer extends JLabel
            implements TableCellRenderer {

        boolean isBordered = true;

        private final ImageIcon ICON =  new ImageIcon(MainInterface.this.getClass().getResource("pic.jpg"));
        //private final ImageIcon[][] background;

        public ColorRenderer(boolean isBordered) {
            this.isBordered = isBordered;
            setOpaque(true); //MUST do this for background to show up.
        }

        /**
         * This method is called for each cell in the table. It determines how to render them.
         * @param table:        the JTable to work with
         * @param s:            the Color object to which to set the background color of this cell
         * @param isSelected:   whether this cell is selected by the user
         * @param hasFocus:     whether this cell has focus
         * @param row:          the row of this cell
         * @param column:       the column of this cell
         * @return              The component object representing this cell. This could be, for example, a JLabel.
         */
        public Component getTableCellRendererComponent(
                JTable table, Object s,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            setIcon(null); // DO NOT delete
            Color newColor = ((Status) s).getColor();

            setBackground(newColor);
            //System.out.println(s);
            if (s == Status.MISS) { // Remember: we have no information about the actual ship
                setIcon(ICON);
                //setBackground(new Color(0, 255, 0));
                System.out.println(row + " " + column);
            }

          /*  setToolTipText("RGB value: " + newColor.getRed() + ", "
                    + newColor.getGreen() + ", "
                    + newColor.getBlue());*/
            return this;
        }
    }


    /**
     * Constructor for the MainInterface class. This creates a frame via super() (since MainInterface extends JPanel),
     * and sets its content pane and close operation.
     *
     */
    public MainInterface(BackEndAdapter ad)

    {
        super("Main Frame");
        setContentPane(this.rootpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.ad = ad;
        //this.types = ad.getShipStorageModel().getShips().values()
             //   .toArray(new Ship.BlockCollectionType[ad.getShipStorageModel(Player.PLAYER_1).getShips().size()]);
        this.types = Arrays.asList(Ship.BlockCollectionType.values()); // TODO this cast is not allowed
        for (Ship.BlockCollectionType t : types) System.out.println(t);
        this.types = this.types.subList(0, this.types.size()-1); // remove the WATER_BLOCK
        for (Ship.BlockCollectionType t : types) System.out.println(t);


        //button1.setEnabled(false);


        this.p1UnsunkShipsThatP2NeedsToSink = new DefaultListModel<Ship>();
        this.p2UnsunkShipsThatP1NeedsToSink = new DefaultListModel<Ship>();
        this.p1UnsunkShipsThatP2NeedsToSinkList = ad.getShipStorageModel(Player.PLAYER_1); // we want changes made in player 2's ship storage model to be reflected in the list of ships that player 1 has to sink.
        this.p2UnsunkShipsThatP1NeedsToSinkList = ad.getShipStorageModel(Player.PLAYER_2); // vice versa
        for (Ship ship : p1UnsunkShipsThatP2NeedsToSinkList.getShips().values()) {
            p1UnsunkShipsThatP2NeedsToSink.addElement(ship);
        }
        for (Ship ship : p2UnsunkShipsThatP1NeedsToSinkList.getShips().values()) {
            p2UnsunkShipsThatP1NeedsToSink.addElement(ship);
        }
        yourUnsunkList.setModel(p1UnsunkShipsThatP2NeedsToSink);
        enemyUnsunkList.setModel(p2UnsunkShipsThatP1NeedsToSink);


        p1UnsunkShipsThatP2NeedsToSinkList.getPropertyChangeSupport().addPropertyChangeListener(ShipStorageModel.SHIPS, this);
        p2UnsunkShipsThatP1NeedsToSinkList.getPropertyChangeSupport().addPropertyChangeListener(ShipStorageModel.SHIPS, this);


        init();


        // TODO Why can we initialize these in the constructor, but we can't initialize the ListModels in the constructor?
        // Those seem to have to be initialized in the createUIComponents() method below.
        this.player1Data = new MyTableModel();
        this.player2Data = new MyTableModel(); // these should go inside an array.
        this.tempData = new MyTableModel(); // this will NEVER be able to be modified, because the hasAttacked will
        // prevent the user from doing so

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextTurn();
                new AudioClip("switch.wav").playOnce();
            }
        };
        timer = new Timer(3000, taskPerformer);
        timer.setRepeats(false);

    }

    private void init()
    {

        // Should this be declared within the createUIComponents method?
        // Consider putting this into a nested class and then setting the button's action listener to a new instance
        // of that class
        // TODO should we make another, separate button click listener for when the game is in session?
        this.buttonPressed = new ActionListener() {
            // TODO the alternative to this approach would be to have two separate action listener objects and to swap them
            // depending on the state of the turn (whether or not the player has made a valid attack this turn)

            public void actionPerformed(ActionEvent e) {
                System.out.println("Button pressed.");
                if (state == BackEndAdapter.GameState.GAME_IN_PLAY) {
                    if (hasAttacked) {
                        timer.start();
                        dataSave = (MyTableModel) table1.getModel();
                        table1.setModel(tempData);
                        System.out.println("Next Turn in 3 seconds.");
                        JOptionPane.showMessageDialog(MainInterface.this, "Next turn in 3 seconds.",
                                "Next Turn", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        System.out.println("You haven't made a valid attack yet.");
                        JOptionPane.showMessageDialog(MainInterface.this, "You haven't made a valid attack yet.",
                                "No Valid Attack Made", JOptionPane.WARNING_MESSAGE);

                    }
                }

            }


        };
        button1.addActionListener(buttonPressed);


        initialMouseAdapter = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);


                System.out.println("Inside initial");
                // code to place ships on click

                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();
                // do some action if appropriate column

                System.out.println("Row: " + row + " col " + (column - 1));

                if (shipsPlaced == 5)
                {
                    if(currentPlayer == Player.PLAYER_2) {
                        JOptionPane.showMessageDialog(MainInterface.this, "Start game!");
                        System.out.println("The adapter says that Player 1's grid is " + ad.player1ToString());

                        switchState();
                        System.out.println("The adapter says that Player 1's grid is " + ad.player1ToString());


                    } else {
                        changeModel();
                        System.out.println("It's now player's turn");
                        shipsPlaced = 0;
                        JOptionPane.showMessageDialog(MainInterface.this, "next player");//Language.getMessage(Language.Message.CHANGE_PLAYER_PROMPT, Player.PLAYER_2));
                    }
                    changeListModel();
                    changePlayer();
                }

                // This needs to be an else if. Otherwise, after finishing the execution of the above if statement,
                // shipsPlaced will be 0, which is < 5, so even though the 1st if statement below will not be run, the
                // else if below it will. But if we make the below statement an else if, it can't be run if the
                // above if statement is run.

                else if (currentPlayer == Player.PLAYER_1 && shipsPlaced < 5) {
                    placeNextShip(row, column, !e.isControlDown());
                }
                else if (shipsPlaced < 5)
                {
                    System.out.println("Placing next ship!");
                    placeNextShip(row, column, !e.isControlDown());

                }
            }
        };

        mainMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    // do some action if appropriate column

                    System.out.println("Row: " + row + " col " + (column - 1));
                    System.out.println(table1.getValueAt(row, column));
                    if (!hasAttacked) {

                        System.out.println("The adapter says that Player 1's grid is " + ad.player1ToString());

                        AttackResponse response = ad.attack(currentPlayer, column - 1, row); // TODO ask if this is ok
                        if (response.getValidity() == AttackResponse.ValidityResponse.VALID_ATTACK) {
                            enemyUnsunkList.updateUI();// TODO look into this ----------------------------------------------------------
                            hasAttacked = true;
                            if (response.didSinkShip()) {
                                System.out.println("Sunk " + response.getShipAffected());

                            }

                            refreshTable();

                            if (response.gameHasBeenWon())
                            {
                                System.out.println("GAME OVER");
                                JOptionPane.showMessageDialog(MainInterface.this, "GAME OVER");
                                if (ad.player1NoShipsLeft()) {
                                    JOptionPane.showMessageDialog(MainInterface.this, "WINNER: Player 1");
                                } else {
                                    JOptionPane.showMessageDialog(MainInterface.this, "WINNER: Player 2");

                                }
                                System.exit(0);
                            }

                            if (extraAttack) {
                                // TODO move this code into the button listener method. But then we also need to give it the AttackResponse
                                // ONLY if this is a valid attack do we want to change players

                                hasAttacked = false;
                            } else {
                            }

                            // You have one attack left AFTER this one
                            // You have zero attacks left AFTER this one
                        }
                    }


                }

            }
        };
        JOptionPane.showMessageDialog(MainInterface.this, "Instructions:\n" +
                "To place boats, click on a cell. When control is down, the boat will be placed downward starting from the cell clicked on.\n" +
                "When control is not down, boat will be placed starting from clicked cell and will go to the right.");

        table1.addMouseListener(initialMouseAdapter);



        beginGame();

    } // end init method


    private void beginGame()
    {

    }

    /**
     * Initializes specific UI components created within MainInterface.form. In particular, UI components for which we
     * would like to perform additional operations to initialize them.
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here

        button1 = new JButton();
        button1.setBackground(Color.RED);
        button1.setText("Hi");


        table1 = new JTable(new MyTableModel());
        table1.setDefaultRenderer(Status.class,
                new ColorRenderer(true)); // All of the cells in the table (except those in the first column are backed
                    // by Color objects (Representing one of the statuses from the enum))
        table1.setRowHeight(40);


        // TODO apparently it won't be necessary to make a separate utility method to locate the index of the list element with the specified string!

        yourUnsunkList = new JList<Ship>();
        enemyUnsunkList = new JList<Ship>();


        // TODO add another mouse listener for when the users are placing ships




    } // end create custom UI components method


    private void placeNextShip(int row, int col, boolean horizontal)
    {
        // get the Ship via the Map
        System.out.println(col + " is the column in the actual table");
        System.out.println("Will create a Ship of type " + types.get(shipsPlaced));
        Ship nextShip = new Ship(types.get(shipsPlaced), col-1, row, horizontal);


        if (ad.addShip(nextShip, currentPlayer)) {
            System.out.println("Valid. Adding.");
            shipsPlaced++;
        }
        else
        {
            System.out.println("Invalid. Try again.");
        }

        refreshTable();

    }

    public void cellUpdate(int x, int y, Status value)
    {
        table1.setValueAt(value, x, y);
    }

    public void refreshTable()
    {
        BattleshipBlock[][] blocks;
        if (state == BackEndAdapter.GameState.GAME_NOT_BEGUN)
            blocks = ad.getBlocks(currentPlayer == Player.PLAYER_1 ? Player.PLAYER_1 : Player.PLAYER_2);
        else
            blocks = ad.getBlocks(currentPlayer);


        for (int i = 0; i < 10; i++)
        {
            for (int j = 1; j < 11; j++)
            {
                cellUpdate(i, j, blocks[i][j - 1].getStatus());
                // Maybe the interface should directly access the back end to get the data? No.
            }
        }

    }

    public void changeModel()
    {
        changeTableModel();
        changeListModel();
    }

    private void changeTableModel()
    {
        if (currentPlayer == Player.PLAYER_1)
        {
            player1Data = dataSave;
            table1.setModel(player2Data);
        }
        else
        {
            player2Data = dataSave;
            table1.setModel(player1Data);
        }
    }

    private void changeListModel()
    {
        if (currentPlayer == Player.PLAYER_1)
        {
            yourUnsunkList.setModel(p2UnsunkShipsThatP1NeedsToSink);
            enemyUnsunkList.setModel(p1UnsunkShipsThatP2NeedsToSink); // the enemy unsunk is what player 2, whose turn it now is, has yet to sink
        }
        else
        {
            yourUnsunkList.setModel(p1UnsunkShipsThatP2NeedsToSink);
            enemyUnsunkList.setModel(p2UnsunkShipsThatP1NeedsToSink);
        }
    }

    private void changePlayer()
    {
        currentPlayer = currentPlayer == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1;
        hasAttacked = false;
    }

    private void nextTurn()
    {
        changeModel();
        changePlayer();
    }


    private void switchState()
    {
        state = BackEndAdapter.GameState.GAME_IN_PLAY;
        table1.removeMouseListener(initialMouseAdapter);
        table1.addMouseListener(mainMouseAdapter);

        //yourUnsunkList.setModel(p2UnsunkShipsThatP1NeedsToSink);
        //enemyUnsunkList.setModel(p1UnsunkShipsThatP2NeedsToSink);

        ad.switchBoards();
        ad.clearBoardsForStart();


        // TODO fix!!!!!
        //p1UnsunkShipsThatP2NeedsToSink = ad.getShipStorageModel(Player.PLAYER_1);
        //yourUnsunkList.setModel(); // we've switched the meaning of the ship models. Now p1UnsunkShipsThatP2NeedsToSink is linked to player 1's grid.
        //enemyUnsunkList.setModel();

        player1Data = new MyTableModel();
        player2Data = new MyTableModel();

        refreshTable();

        JOptionPane.showMessageDialog(MainInterface.this, "To attack, click the desired cell. An X designates a miss, orange a hit, and red a sunken ship.\n" +
                "When the player is done, hit Turn Finished to switch players.");
    }

    public void propertyChange(final PropertyChangeEvent evt) {
        System.out.println("*****************************************************    property changed");

        if (evt.getSource() == p1UnsunkShipsThatP2NeedsToSinkList) { // TODO make a Player class and make it have a ShipStorageModel
            System.out.println("The p1UnsunkShipsThatP2NeedsToSinkList model");
            if (ShipStorageModel.SHIPS.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    p1UnsunkShipsThatP2NeedsToSink.removeElement(evt.getOldValue());
                    System.out.println("It should be removed.");
                    System.out.println(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    p1UnsunkShipsThatP2NeedsToSink.addElement((Ship) evt.getNewValue()); // had to add the typecast to String because it's a DefaultListModel<String>
                }
                else if (evt.getOldValue() != null) //&& evt.getNewValue() != null)
                {
                    System.out.println("Modifying Property");
                    System.out.println(evt.getOldValue());
                    System.out.println(evt.getOldValue());

                    int index = p1UnsunkShipsThatP2NeedsToSink.indexOf(evt.getNewValue()); // this **does** work!
                    System.out.println("index: " + index);
                    p1UnsunkShipsThatP2NeedsToSink.set(index, (Ship) evt.getNewValue());
                }
            }
        }

        if (evt.getSource() == p2UnsunkShipsThatP1NeedsToSinkList) { // TODO make a Player class and make it have a ShipStorageModel
            System.out.println("The p2UnsunkShipsThatP1NeedsToSinkList model");
            if (ShipStorageModel.SHIPS.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    p2UnsunkShipsThatP1NeedsToSink.removeElement(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    p2UnsunkShipsThatP1NeedsToSink.addElement((Ship) evt.getNewValue()); // had to add the typecast to String because it's a DefaultListModel<String>
                }
                else if (evt.getOldValue() != null && evt.getNewValue() != null)
                {
                    System.out.println("Modifying Property");
                    System.out.println(evt.getOldValue());
                    System.out.println(evt.getOldValue());

                    int index = p2UnsunkShipsThatP1NeedsToSink.indexOf(evt.getNewValue()); // this **does** work!
                    System.out.println("index: " + index);
                    p2UnsunkShipsThatP1NeedsToSink.set(index, (Ship) evt.getNewValue());
                }
            }
        }
    }


}
