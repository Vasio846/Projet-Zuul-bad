import java.util.HashMap;

/**
 * Classe gérant les listes d'items.
 *
 * @author Jeremy
 */
public class ItemList
{
    // variables d'instance - remplacez l'exemple qui suit par le vôtre
    private HashMap<String,Item>  aInventory;

    /**
     * Constructeur d'objets de classe ItemList
     */
    public ItemList()
    {
        this.aInventory = new HashMap<String, Item>();
    }
    
    // ******** MODIFICATIONS ********
    
    public void addItem( final Item pItem )
    {
        this.aInventory.put(pItem.getName(), pItem);
    }
    
    public void removeItem( final String pName )
    {
        this.aInventory.remove(pName);
    }
    
    // ********** GET **********
    
    public Item getItem( final String pName )
    {
        return this.aInventory.get(pName);
    }
    
    public String getItemsDescription()
    {
        StringBuilder vItems = new StringBuilder("Items : \n");
        for (String vItem : this.aInventory.keySet()) {
            vItems.append( aInventory.get(vItem).getLongDescription() + "\n" );
        }
        return vItems.toString();
    }
    
    public String getItemString()
    {
        StringBuilder vItems = new StringBuilder("");
        for (String vItem : this.aInventory.keySet()) {
            vItems.append(vItem + "  ");
        }
        return vItems.toString();
    }
    
    public int getTotalWeight()
    {
        int vWeight = 0;
        for ( String vItem : this.aInventory.keySet() ) {
            vWeight = vWeight + this.getItem(vItem).getWeight();
        }
        return vWeight;
    }
    
    // ********* TESTS **********
    
    public boolean hasItem(final String pItem)
    {
        return true;
    }
    
    public boolean empty()
    {
        return this.aInventory.isEmpty();
    }
}
