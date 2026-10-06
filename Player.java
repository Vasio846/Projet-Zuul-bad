import java.util.HashMap;
import java.util.Stack;

/**
 * Décrivez votre classe Player ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Player
{
    private int                   aHealth;
    private HashMap<String,Item>  aInventory;
    private Room                  aCurrentRoom;
    private Stack<Room>           aPreviousRooms;

    /**
     * Constructeur d'objets de classe Player
     */
    public Player()
    {
        this.aHealth = 50;
        this.aInventory = new HashMap<String, Item>();
        this.aCurrentRoom = new Room( "void", null );
        this.aPreviousRooms = new Stack<Room>();
    }
    
    //  ************* ROOM ************
    
    /**
     * @param pRoom Room to set
     */
    public void setCurrentRoom( final Room pRoom )
    {
        this.aCurrentRoom = pRoom;
    }
    
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }
    
    /**
     * @param pDirection direction of next Room 
     */
    public void goNextRoom(final Command pDirection)
    {
        String vDirection = pDirection.getSecondWord();
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
        
        this.aPreviousRooms.push(this.aCurrentRoom);
        this.aCurrentRoom = vNextRoom;
    }
    
    public boolean noPreviousRoom()
    {
        return this.aPreviousRooms.empty();
    }
    
    public Room popPreviousRoom()
    {
        return this.aPreviousRooms.pop();
    }
    
    // ********** INVENTORY **************
    
    public Item searchInventory( final String pName )
    {
        return this.aInventory.get(pName);
    }
    
    public void pickUpItem( final Item pItem )
    {
        this.aInventory.put(pItem.getName(), pItem);
    }
    
    public void dropItem( final Item pItem )
    {
        this.aInventory.remove(pItem.getName());
    }
}
