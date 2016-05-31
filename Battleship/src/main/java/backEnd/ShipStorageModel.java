package backEnd;

import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by carlg on 6/7/15.
 */
public class ShipStorageModel {

    public static final String SHIPS = "ships"; // TODO if this were a nested class, would this data member need to be public?

    private Map<Ship.BlockCollectionType, Ship> shipsInPlay;

    private PropertyChangeSupport propertyChangeSupport;

    public ShipStorageModel() {
        shipsInPlay = new TreeMap<Ship.BlockCollectionType, Ship>();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    public Map<Ship.BlockCollectionType, Ship> getShips() { // TODO had to make this public because it's not a nested class
        return shipsInPlay;
    }

    public Ship getShip(Ship.BlockCollectionType type)
    {
        return shipsInPlay.get(type);
    }

    public void addShip(Ship newShip) {
        shipsInPlay.put(newShip.getType(), newShip);
        /*
        shipsInPlay.put(newShip, newShip.name + " (" + newShip.NUM_OF_BLOCKS + ")"); // If adding a Ship object, we'd need to use newShip.getType() to get
        // its BlockCollectionType
        */
        System.out.println("Ship object added to ship list:" + newShip);
        getPropertyChangeSupport().firePropertyChange(SHIPS, null, newShip);
    }

    public void removeShip(Ship s) {
        System.out.println("Ship object is being removed from ship list");
        shipsInPlay.remove(s.getType());
        getPropertyChangeSupport().firePropertyChange(SHIPS, s, null);
    }

    public void removeShip(Ship.BlockCollectionType t) {
        System.out.println("Ship object is being removed from ship list");
        getPropertyChangeSupport().firePropertyChange(SHIPS, shipsInPlay.get(t), null);
        shipsInPlay.remove(t);
        //getPropertyChangeSupport().firePropertyChange(SHIPS, t, null);

    }

    public void updateShip(Ship.BlockCollectionType newShip)
    {
        System.out.println("Ship object is being updated in the ship list");
        getPropertyChangeSupport().firePropertyChange(SHIPS, true, shipsInPlay.get(newShip));

    }


}
