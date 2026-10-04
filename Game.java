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
        // 1st floor
        Room vHall = new Room("in the main hall");
        Room vDiningRoom = new Room("in the dining room");
        Room vKitchen = new Room("in the kitchen");
        Room vLivingRoom = new Room("in the living room");
        Room vOffice = new Room("in the office");
        
        // 2nd floor
        Room vHall2 = new Room("above the main hall");
        Room vBedroom = new Room("in the bedroom");
        
        // basement
        Room vCellar = new Room("in the cellar");
        
        vHall.setExit("east", vDiningRoom);
        vHall.setExit("west", vLivingRoom);
        vHall.setExit("stairs", vHall2);
        
        vDiningRoom.setExit("north", vKitchen);
        vDiningRoom.setExit("west", vHall);
        
        vKitchen.setExit("south", vDiningRoom);
        vKitchen.setExit("trapdoor", vCellar);
        
        vLivingRoom.setExit("north", vOffice);
        vLivingRoom.setExit("east", vHall);
        
        vOffice.setExit("east", vKitchen);
        vOffice.setExit("south", vLivingRoom);
        
        vHall2.setExit("north", vBedroom);
        vHall2.setExit("stairs", vHall);
        
        vBedroom.setExit("south", vHall2);
        
        this.aCurrentRoom = vHall;
    }// createRooms()
    
    private void goRoom(final Command pDirection)
    {
        if(pDirection.hasSecondWord() == false){
            System.out.println("Go where ?");
            return;
        }
        
        String vDirection = pDirection.getSecondWord();
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
        
        // Testing if the second word is a valid direction :
        if(vNextRoom == null){
            System.out.println("There's nothing there.");
            System.out.println();
            return;
        }
        
        this.aCurrentRoom = vNextRoom;
        this.printLocationInfo();
    }// goRoom(.)
    
    private void printLocationInfo()
    {
        System.out.println();
        System.out.println(this.aCurrentRoom.getLongDescription());
        System.out.println();
    }// printLocationInfo()
    
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        
        this.printLocationInfo();
    }// printWelcome()
    
    private void printHelp()
    {
        System.out.println("");
        System.out.println("You are lost. You are alone.");
        System.out.println("You wander around at the manor.");
        System.out.println("");
        System.out.println("Your command words are: ");
        aParser.showCommands();
        System.out.println("");
    }// printHelp()
    
    private void look()
    {
        System.out.println();
        System.out.println(this.aCurrentRoom.getLongDescription());
        System.out.println();
    }// look()
    
    private void eat()
    {
        System.out.println();
        System.out.println("You have eaten and are no longer hungry anymore.");
        System.out.println();
    }// eat()  
    
    private boolean quit(final Command pCommand)
    {
        if(pCommand.hasSecondWord() == true){
            System.out.println("Quit what ?");
            System.out.println("");
            return false;
        }
        
        return true;
    }// quit(.)
    
    private boolean processCommand(final Command pCommand)
    {
        if(pCommand.isUnknown() == true){
            System.out.println("I don't know what you mean...");
            System.out.println("");
            return false;
        }
        
        if(pCommand.getCommandWord().equals("help")){
            this.printHelp();
            return false;
        }
        if(pCommand.getCommandWord().equals("go")){
            this.goRoom(pCommand);
            return false;
        }
        if(pCommand.getCommandWord().equals("look")){
            this.look();
            return false;
        }
        if(pCommand.getCommandWord().equals("eat")){
            this.eat();
            return false;
        }
        if(pCommand.getCommandWord().equals("quit")){
            return quit(pCommand);
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
        
        System.out.println("");
        System.out.println("Thank you for playing. Good bye.");
        System.out.println("");
    }// play()
}// Game                
