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
    private Room aNorthExit;
    private Room aEastExit;
    private Room aSouthExit;
    private Room aWestExit;
    private Room aUpExit;
    private Room aDownExit;
    private HashMap<String, Room> exits;
    
    /**
     * Create a room
     * pDescription = text deescribing the room
     */
    public Room(final String pDescription)
    {
        this.aDescription = pDescription;
        this.exits = new HashMap<String, Room>();
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
        String vExits = "Exits : ";
        
        for (String vDir: this.exits.keySet()) {
            vExits = vExits + vDir + " ";
        }
        
        return vExits;
    }// getExitString(.)
}// Room
