 
/**
 * Classe Game - le moteur du jeu d'aventure Zuul.
 *
 * @author votre nom
 */
public class Game
{
    private Room aCurrentRoom;
    private Parser aParser;
    
    public Game()
    {
        this.createRooms();
        this.aParser = new Parser();
    }// Game()
    
    private void createRooms()
    {
        Room vHall = new Room("in the main hall");
        Room vDiningRoom = new Room("in the dining room");
        Room vKitchen = new Room("in the kitchen");
        Room vLivingRoom = new Room("in the living room");
        Room vOffice = new Room("in the office");
        
        vHall.setExits(null, vDiningRoom, null, vLivingRoom);
        vDiningRoom.setExits(vKitchen, null, null, vHall);
        vKitchen.setExits(null, null, vDiningRoom, null);
        vLivingRoom.setExits(vOffice, vHall, null, null);
        vOffice.setExits(null, vKitchen, vLivingRoom, null);
        
        this.aCurrentRoom = vHall;
    }// createRooms()
    
    private void goRoom(final Command pDirection)
    {
        if(pDirection.hasSecondWord() == false){
            System.out.println("Go where ?");
            return;
        }
        
        Room vNextRoom = null;
        String vDirection = pDirection.getSecondWord();
        
        if(vDirection.equals("north")){
            vNextRoom = this.aCurrentRoom.aNorthExit;
        }// if
        else{
            if(vDirection.equals("east")){
            vNextRoom = this.aCurrentRoom.aEastExit;
            }// if
            else{
                if(vDirection.equals("south")){
                    vNextRoom = this.aCurrentRoom.aSouthExit;
                }// if
                else{
                    if(vDirection.equals("west")){
                        vNextRoom = this.aCurrentRoom.aWestExit;
                    }// if
                    else{
                        System.out.println("Unknown direction");
                        return;
                    }// else
                }// else
            }// else
        }// else
        
        if(vNextRoom == null){
            System.out.println("There is no door !");
            return;
        }// if
        
        this.aCurrentRoom = vNextRoom;
        System.out.println(this.aCurrentRoom.getDescription());
        
        System.out.print("Exits : ");
        if (this.aCurrentRoom.aNorthExit != null){
            System.out.print("north ");
        }// if
        if (this.aCurrentRoom.aEastExit != null){
            System.out.print("east ");
        }// if
        if (this.aCurrentRoom.aSouthExit != null){
            System.out.print("south ");
        }// if
        if (this.aCurrentRoom.aWestExit != null){
            System.out.print("west ");
        }// if
        
    }// goRoom(.)
    
    private void printWelcome()
    {
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
    }// printWelcome()
    
    private void printHelp()
    {
        System.out.println("You are lost. You are alone.");
        System.out.println("You wander around at the university.");
        System.out.println("");
        System.out.println("Your command words are: ");
        System.out.println("  go quit help");
    }// printHelp()
    
    private boolean quit(final Command pCommand)
    {
        if(pCommand.hasSecondWord() == true){
            System.out.println("Quit what ?");
            return false;
        }
        
        return true;
    }// quit(.)
    
    private boolean processCommand(final Command pCommand)
    {
        if(pCommand.isUnknown() == true){
            System.out.println("I don't know what you mean");
            return false;
        }
        
        if(pCommand.getCommandWord().equals("quit")){
            return quit(pCommand);
        }
        if(pCommand.getCommandWord().equals("go")){
            return false;
        }
        if(pCommand.getCommandWord().equals("help")){
            return false;
        }
        
        System.out.println("Programmer error : unknown command !");
        return false;
    }// processCommand(.)
    
    public void play()
    {
        this.printWelcome();
        
        boolean vFinished = false;
        
        while (vFinished == false){
            Command vCommand = aParser.getCommand();
            vFinished = processCommand(vCommand);
        }// while
        
        System.out.println("Thank you for playing. Good bye.");
    }// play()
}// Game                
