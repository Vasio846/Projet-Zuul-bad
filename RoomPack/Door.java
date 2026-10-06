package RoomPack;

public class Door
{
    private boolean aIsLocked;
    private boolean aOneWay;
    
    /**
     * Constructeur d'objets de classe Door
     */
    public Door()
    {
        this.aIsLocked = true;
        this.aOneWay   = false;
    }
    
    public void lockDoor()
    {
        if ( !this.aIsLocked ) this.aIsLocked = true;
    }
    
    public void unlockDoor()
    {
        if ( this.aIsLocked ) this.aIsLocked = false;
    }
    
    public boolean isLocked()
    {
        return this.aIsLocked;
    }
    
    public void oneWay()
    {
        this.aOneWay = true;
    }
    
    public boolean isOneWay()
    {
        return this.aOneWay;
    }
}
