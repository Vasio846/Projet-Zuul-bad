
/**
 * Item class
 */
public class Item
{

    private String aDescription;
    private int aWeight;

    /**
     * Constructeur d'objets de classe Item
     */
    public Item(final String pDescription, final int pWeight)
    {
        this.aDescription = pDescription;
        this.aWeight      = pWeight;
    }
    
    public String getDescription()
    {
        return this.aDescription;
    }
    
    public int getWeight()
    {
        return this.aWeight;
    }
    
    public String getLongDescription()
    {
        return this.aDescription + ", wheight : " + this.aWeight;
    }
}
