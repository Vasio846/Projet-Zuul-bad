 
/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Room
{
    private String aDescription;
    public Room aNorthExit;
    public Room aEastExit;
    public Room aSouthExit;
    public Room aWestExit;
    
    public Room(final String pDescription)
    {
        this.aDescription = pDescription;
    }// Room(.)
    
    public String getDescription()
    {
        return this.aDescription;
    }// getDescription()
    
    public void setExits(final Room pNorthExit, final Room pEastExit, final Room pSouthExit, final Room pWestExit)
    {
        this.aNorthExit = pNorthExit;
        this.aEastExit = pEastExit;
        this.aSouthExit = pSouthExit;
        this.aWestExit = pWestExit;
    }// setExits(....)
    
}// Room
