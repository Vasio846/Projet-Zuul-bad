import java.util.HashMap;
import java.util.Stack;

/**
 * Classe gérant le Player
 *
 * @author Jeremy
 */
public class Player
{
    private int                   aHealth;
    private int                   aMaxWeight; 
    private ItemList              aInventory;
    private Room                  aCurrentRoom;
    private Stack<Room>           aPreviousRooms;

    /**
     * Constructeur d'objets de classe Player
     */
    public Player()
    {
        this.aHealth        = 50;
        this.aMaxWeight     = 200;
        this.aInventory     = new ItemList();
        this.aCurrentRoom   = new Room( "void", null );
        this.aPreviousRooms = new Stack<Room>();
    }
    
    public void addMaxWeight( final int pWeight )
    {
        this.aMaxWeight = this.aMaxWeight + pWeight;
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
        return this.aInventory.getItem(pName);
    }
    
    public boolean pickUpItem( final Item pItem )
    {
        this.aInventory.addItem(pItem);
        
        if ( this.isOverMaxWeight() )
            return true;
        return false;
    }
    
    public void dropItem( final Item pItem )
    {
        this.aInventory.removeItem(pItem.getName());
    }
    
    public boolean isOverMaxWeight()
    {
        int vWeight = this.aInventory.getTotalWeight();
        return vWeight > this.aMaxWeight;
    }
    
    public String itemList()
    {
        return this.aInventory.getItemString();
    }
    
    public boolean emptyInv()
    {
        if ( this.aInventory.empty() )
            return true;
        return false;
    }
    
    public boolean hasItem(final String pItem)
    {
        return this.aInventory.hasItem(pItem);
    }
}
