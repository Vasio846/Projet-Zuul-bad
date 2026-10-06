
/**
 * Item class
 */
public class Item
{
    private String aName;
    private String aDescription;
    private int aWeight;
    private int aValue;

    /**
     * Constructeur d'objets de classe Item
     */
    public Item(final String pName, final String pDescription, final int pWeight, final int pValue)
    {
        this.aName        = pName;
        this.aDescription = pDescription;
        this.aWeight      = pWeight;
        this.aValue       = pValue;
    }
    
    public String getName()
    {
        return this.aName;
    }
    
    public String getDescription()
    {
        return this.aDescription;
    }
    
    public int getWeight()
    {
        return this.aWeight;
    }
    
    public int getValue()
    {
        return this.aValue;
    }
    
    public String getLongDescription()
    {
        return this.aDescription + ", wheight : " + this.aWeight;
    }
}
