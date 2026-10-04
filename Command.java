/**
 * Classe Command - une commande du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Command
{
    private String aCommandWord;
    private String aSecondWord;
    
    public Command(final String pCommandWord, final String pSecondWord)
    {
        this.aCommandWord = pCommandWord;
        this.aSecondWord = pSecondWord;
    }// Command(..)
    
    /**
     * Renvoie le CommandWord
     */
    public String getCommandWord()
    {
        return this.aCommandWord;
    }// getCommandWord()
    
    /**
     * Renvoie le deuxieme mot
     */
    public String getSecondWord()
    {
        return this.aSecondWord;
    }// getSecondWord()
    
    /**
     * Renvoie true si il y a un second mot
     */
    public boolean hasSecondWord()
    {
        return this.aSecondWord != null;
    }// hasSecondWord()
    
    /**
     * Renvoie true si la commande n'est pas connue
     */
    public boolean isUnknown()
    {
        return this.aCommandWord == null;
    }// isUnknown()
    
} // Command
