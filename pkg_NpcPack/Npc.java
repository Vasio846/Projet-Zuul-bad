package pkg_NpcPack;

import java.util.Random;
import java.util.ArrayList;

import pkg_RoomPack.Room;
import pkg_ItemPack.ItemList;
import pkg_ItemPack.Item;

/**
 * Character class
 *
 * @author Jeremy Augier
 */
public class Npc
{
    private String   aName;
    private ItemList aInventory;
    private Room     aCurrentRoom;
    private boolean  aMoving;

    /**
     * Constructeur d'objets de classe Character
     */
    public Npc(final String pName, final Room pCurrentRoom, final boolean pMoving)
    {
        this.aName        = pName;
        this.aCurrentRoom = pCurrentRoom;
        this.aInventory   = new ItemList();
        this.aCurrentRoom = pCurrentRoom;
        this.aMoving      = pMoving;
    }
    
    // ******** INVENTORY ********
    
    public Item searchInventory( final String pName )
    {
        return this.aInventory.getItem(pName);
    }
    
    public void pickUpItem( final Item pItem )
    {
        this.aInventory.addItem(pItem);
    }
    
    public void dropItem( final Item pItem )
    {
        this.aInventory.removeItem(pItem.getName());
    }
    
    // ******** ACCESSEURS *******
    
    public String getName()
    {
        return this.aName;
    }
    
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }
    
    // ********* DEPLACEMENT ********
    
    public void move()
    {
        ArrayList<String> vS = this.aCurrentRoom.getExitSet();
        Random r = new Random();
        int vN = r.nextInt( vS.size() );
        this.aCurrentRoom.removeNpc(this.aName);
        goRoom( this.aCurrentRoom.getExit(vS.get(vN)) );
        this.aCurrentRoom.addNpc( this.aName, this);
    }
    
    public void goRoom( final Room pRoom )
    {
        this.aCurrentRoom = pRoom;
    }
    
    // ********* TESTS ***************
    
    public boolean moves()
    {
        return this.aMoving;
    }
}



