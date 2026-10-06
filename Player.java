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
    private boolean               aStockRooms;  // détermine si les rooms sont Stackees ou pas

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
        this.aStockRooms    = true;
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
    public void goNextRoom(final Room pNextRoom)
    {        
        if ( this.aStockRooms ) this.aPreviousRooms.push(this.aCurrentRoom);
        else this.aStockRooms = true;
        
        this.aCurrentRoom = pNextRoom;
    }
    
    public boolean noPreviousRoom()
    {
        return this.aPreviousRooms.empty();
    }
    
    public Room popPreviousRoom()
    {
        return this.aPreviousRooms.pop();
    }
    
    public void deletePreviousRooms()
    {
        this.aPreviousRooms.clear();
        this.aStockRooms = false; // pour ne pas stacker la prochaine room
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
