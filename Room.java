import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Set;

/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Room
{
    private String                aDescription;
    private HashMap<String, Room> exits;
    private String                aImageName;
    private HashMap<String,Item>  aItems;
    private boolean               aLights;
    
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
        this.aItems       = new HashMap<String, Item>();
        this.aLights      = false;
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
        vLongDescription.append(".\n" + this.getItemString() );
        return vLongDescription.toString();
    }// getLongDescription()

    public String getItemsDescription()
    {
        if ( this.aItems.isEmpty() ) {
            return "No item here.";
        }
        
        StringBuilder vItems = new StringBuilder("Items : \n");
        for (String vItem: this.aItems.keySet()) {
            vItems.append( aItems.get(vItem).getLongDescription() + "\n" );
        }
        return vItems.toString();
    }
    
    // ****** MODIFICATIONS *********
    
    /**
     * @param pNom item name
     * @param pDescription item description
     * @param pWeight int representing the item's weight
     */
    public void addItem(final String pNom, final String pDescription, final int pWeight, final int pValue)
    {
        this.aItems.put(pNom, new Item(pNom, pDescription, pWeight, pValue));
    }
    
    public void removeItem(final String pNom)
    {
        this.aItems.remove(pNom);
    }
    
    /**
     * Define an exit for the room.
     */ 
    public void setExit(final String pDirection, final Room pNeighbor)
    {
        this.exits.put(pDirection, pNeighbor);
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
    public void switchLights()
    {
        if ( this.getLights() ) 
            this.lightsOff();
        else this.lightsOn();
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
    
    public Item getItem(final String pItemName)
    {
        return this.aItems.get(pItemName);
    }
    
    /**
     * returns String containing list of Item objects in the current Room
     * if there is no items : returns "No item here."
     */
    public String getItemString()
    {
        if ( this.aItems.isEmpty() ) {
            return "No item here.";
        }
        
        StringBuilder vItems = new StringBuilder("Items : ");
        for (String vItem : this.aItems.keySet()) {
            vItems.append(vItem + "  ");
        }

        return vItems.toString();
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
}// Room
