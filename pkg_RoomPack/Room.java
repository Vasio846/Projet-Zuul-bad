package pkg_RoomPack;

import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

import pkg_ItemPack.Item;
import pkg_ItemPack.ItemList;

import pkg_NpcPack.Npc;

/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Room
{
    private String                      aDescription;
    private HashMap<String, Room>       exits;
    private String                      aImageName;
    private ItemList                    aItems;
    private boolean                     aLights;
    private HashMap<String, Door>       aDoors;
    private HashMap<String, Npc>        aChars;
    
    /**
     * Create a room
     * pDescription = text deescribing the room
     * 
     * @param pDescription String describing the room
     * @param pImage String containing path to image
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        this.exits        = new HashMap<String, Room>();
        this.aImageName   = pImage;
        this.aItems       = new ItemList();
        this.aLights      = true;
        this.aDoors       = new HashMap<String, Door>();
        this.aChars       = new HashMap<String, Npc>();
    }// Room(.)
    
    // ***** DESCRIPTION ********
    
    public String getDescription()
    {
        return this.aDescription;
    }// getDescription()
    
    public String getLongDescription()
    {
        StringBuilder vLongDescription = new StringBuilder("You are : ");
        vLongDescription.append(this.aDescription);
        vLongDescription.append(".\n" + this.getExitString() );
        vLongDescription.append(".\n" + "Items : " + this.getItemString() );
        vLongDescription.append(".\n" + "Npcs : " + this.getNpcString() );
        return vLongDescription.toString();
    }// getLongDescription()

    public String getItemsDescription()
    {
        if ( this.aItems.empty() ) {
            return "No item here.";
        }
        
        return this.aItems.getItemsDescription();
    }
    
    // ****** MODIFICATIONS *********
    
    /**
     * @param pNom item name
     * @param pDescription item description
     * @param pWeight int representing the item's weight
     */
    public void addItem(final String pNom, final String pDescription, final int pWeight, final int pValue)
    {
        this.aItems.addItem(new Item(pNom, pDescription, pWeight, pValue));
    }
    
    public void removeItem(final String pNom)
    {
        this.aItems.removeItem(pNom);
    }
    
    /**
     * @param pName Npc name
     * @param pNpc Character added to Room
     */
    public void addNpc(final String pName, final Npc pNpc)
    {
        this.aChars.put( pName, pNpc );
    }
    
    public void removeNpc(final String pName)
    {
        this.aChars.remove(pName);
    }
    
    /**
     * Define an exit for the room.
     * 
     * @param pDirection direction of the exit
     * @param pNeighbor Room linked to the exit
     * @param pDoor adds a locked door if true
     */ 
    public void setExit(final String pDirection, final Room pNeighbor, final boolean pDoor)
    {
        this.exits.put(pDirection, pNeighbor);
        if (pDoor) this.aDoors.put( pDirection,new Door() );
    }// setExit(..)
    
    public void lightsOn()
    {
        this.aLights = true;
    }
    
    public void lightsOff()
    {
        this.aLights = false;
    }
    
    /**
     * sert a allumer eteindre les lumieres
     */
    public boolean switchLights()
    {
        if ( this.getLights() ) {
            this.lightsOff();
            return false;
        }
        else {
            this.lightsOn();
            return true;
        }
    }
    
    // ******** GET *************
    
    public Room getExit(final String pDirection)
    {
        return this.exits.get(pDirection);
    }// getExit(.)
    
    public String getExitString()
    {
        StringBuilder vExits = new StringBuilder("Exits : ");
        for (String vDir: this.exits.keySet()) {
            vExits.append(vDir + "  ");
        }
        return vExits.toString();
    }// getExitString(.)
    
    public ArrayList<String> getExitSet()
    {
        ArrayList<String> vAL = new ArrayList<String>(this.exits.keySet());
        return vAL;
    }
    
    /**
     * returns String containing all Npcs in the room
     */
    public String getNpcString()
    {
        if ( this.aChars.isEmpty() ) {
            return "No one here.";
        }
        StringBuilder vNpcs = new StringBuilder("");
        for (String vNpc : this.aChars.keySet()) {
            vNpcs.append(vNpc + "  ");
        }
        return vNpcs.toString();
    }
    
    public Item getItem( final String pName )
    {
        return this.aItems.getItem( pName );
    }
    
    /**
     * returns String containing list of Item objects in the current Room
     * if there is no items : returns "No item here."
     */
    public String getItemString()
    {
        if ( this.aItems.empty() ) {
            return "No item here.";
        }
        return this.aItems.getItemString();
    }// getExitString(.)
    
    /**
     * accesseur aImageName
     */
    public String getImageName()
    {
        return this.aImageName;
    }
    
    public boolean getLights()
    {
        return this.aLights;
    }
    
    public boolean directionLocked(final String vDirection)
    {
        return this.aDoors.get(vDirection).isLocked();
    }
    
    public boolean hasDoor(final String vDirection)
    {
        return !(this.aDoors.get(vDirection) == null );
    }
    
    public Door getDoor(final String vDirection)
    { 
        return this.aDoors.get(vDirection);
    }
}// Room
