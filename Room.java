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
    
    /**
     * Create a room
     * pDescription = text deescribing the room
     */
    public Room(final String pDescription, final String pImage)
    {
        this.aDescription = pDescription;
        this.exits = new HashMap<String, Room>();
        this.aImageName = pImage;
    }// Room(.)
    
    public String getDescription()
    {
        return this.aDescription;
    }// getDescription()
    
    public String getLongDescription()
    {
        return "You are " + aDescription + ".\n" + this.getExitString();
    }// getLongDescription()
    
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
    
    /**
     * accesseur aImageName
     */
    public String getImageName()
    {
        return this.aImageName;
    }
}// Room
