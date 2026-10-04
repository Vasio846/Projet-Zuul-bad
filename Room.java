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
    private String aDescription;
    private HashMap<String, Room> exits;
    private String aImageName;
    private Item aItem;
    
    /**
     * Create a room
     * pDescription = text deescribing the room
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        this.exits = new HashMap<String, Room>();
        this.aImageName = pImage;
        this.aItem = null;
    }// Room(.)
    
    public String getDescription()
    {
        return this.aDescription;
    }// getDescription()
    
    public String getLongDescription()
    {
        StringBuilder vLongDescription = new StringBuilder("You are ");
        vLongDescription.append(".\n" + this.getExitString() );
        vLongDescription.append(".\n" + this.getItemString() );
        return vLongDescription.toString();
    }// getLongDescription()
    
    public String getItemDescription()
    {
        if (this.aItem == null) {
            return "There is no item to look at.";
        }
        return this.aItem.getLongDescription();
    }
    
    public void setItem(final String pDescription, final int pWeight)
    {
        this.aItem = new Item(pDescription, pWeight);
    }
    
    /**
     * Define an exit for the room.
     */
    public void setExit(final String pDirection, final Room pNeighbor)
    {
        this.exits.put(pDirection, pNeighbor);
    }// setExit(..)
    
    public Room getExit(final String pDirection)
    {
        return this.exits.get(pDirection);
    }// getExit(.)
    
    public String getExitString()
    {
        StringBuilder vExits = new StringBuilder("Exits : ");
        for (String vDir: this.exits.keySet()) {
            vExits.append(vDir + " ");
        }
        return vExits.toString();
    }// getExitString(.)
    
    public Item getItem()
    {
        return this.aItem;
    }
    
    public String getItemString()
    {
        if (this.aItem == null) {
            return "No item here.";
        }
        
        StringBuilder vExits = new StringBuilder("Items : ");
        vExits.append(this.aItem.getDescription());

        return vExits.toString();
    }// getExitString(.)
    
    /**
     * accesseur aImageName
     */
    public String getImageName()
    {
        return this.aImageName;
    }
}// Room
