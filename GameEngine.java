import java.util.HashMap;

/**
 * Game engine
 */
public class GameEngine
{
    private Parser                aParser;
    private Room                  aCurrentRoom;
    private HashMap<String, Room> aRoomList;
    private UserInterface         aGui;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine()
    {
        this.aParser = new Parser();
        this.aRoomList = new HashMap<String, Room>();
        this.createRooms();
    }
    
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();
    }

    private void createRooms()
    {
        // 1st floor
        Room vHall = new Room("in the main hall", "Hall.jpg");
        Room vDiningRoom = new Room("in the dining room", "diningRoom.jpg");
        Room vKitchen = new Room("in the kitchen", "Kitchen.jpg");
        Room vLivingRoom = new Room("in the living room", "blank.jpg");
        Room vOffice = new Room("in the office", "blank.jpg");
        
        // 2nd floor  
        Room vHall2 = new Room("above the main hall", "blank.jpg");
        Room vBedroom = new Room("in the bedroom", "blank.jpg");
        
        // basement 
        Room vCellar = new Room("in the cellar", "blank.jpg");
        
        // setting exits
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
        
        vCellar.setExit("up", vKitchen);
        
        // setting items
        vHall.setItem("vase", 13);
        
        // creating room Hashmap
        this.aRoomList.put("Hall", vHall);
        this.aRoomList.put("DiningRoom", vDiningRoom);
        this.aRoomList.put("Kitchen", vKitchen);
        this.aRoomList.put("LivingRoom", vLivingRoom);
        this.aRoomList.put("Office", vOffice);
        this.aRoomList.put("SecondHall", vHall2);
        this.aRoomList.put("Bedroom", vBedroom);
        this.aRoomList.put("Cellar", vCellar);
        
        // choosing current room
        this.aCurrentRoom = vHall;
    }// createRooms()
    
    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    public void interpretCommand( final String pCommandLine ) 
    {
        this.aGui.println( "> " + pCommandLine );
        Command vCommand = this.aParser.getCommand( pCommandLine );

        if ( vCommand.isUnknown() ) {
            this.aGui.println( "I don't know what you mean..." );
            return;
        }

        String vCommandWord = vCommand.getCommandWord();
        if ( vCommandWord.equals( "help" ) )
            this.printHelp();
        else if ( vCommandWord.equals( "go" ) )
            this.goRoom( vCommand );
        else if ( vCommandWord.equals( "look" ) ) {
            this.look( vCommand );
        }            
        else if ( vCommandWord.equals( "eat" ) ) {
            this.aGui.print("\n");
            this.aGui.println("You are not hungry anymore. ");
            this.aGui.print("\n");
        }
        else if ( vCommandWord.equals( "quit" ) ) {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else
                this.endGame();
        }
    }
    
    private void printWelcome()
    {
        this.aGui.print("\n");
        this.aGui.println("Welcome to President Evil!");
        this.aGui.println("Save the president from this haunted mansion.");
        this.aGui.println("Type 'help' if you need help.");
        this.aGui.print("\n");
        
        this.printLocationInfo();
    }// printWelcome()
    
        private void printLocationInfo()
    {
        this.aGui.print("\n");
        this.aGui.println(this.aCurrentRoom.getLongDescription());
        this.aGui.print("\n");
    }// printLocationInfo()
    
    private void printHelp()
    {
        this.aGui.print("\n");
        this.aGui.println("You are lost. You are alone.");
        this.aGui.println("You wander around at the manor.");
        this.aGui.print("\n");
        this.aGui.println("Your command words are: ");
        this.aGui.println(aParser.getCommands()); // utilise Parser pour print qqchose dépendant de CommandWords
        this.aGui.print("\n");
    }// printHelp()
    
    private void goRoom(final Command pDirection)
    {
        if(pDirection.hasSecondWord() == false){
            this.aGui.println("Go where ?");
            this.aGui.print("\n");
            return;
        }
        
        String vDirection = pDirection.getSecondWord();
        Room vNextRoom = this.aCurrentRoom.getExit(vDirection);
        
        // Testing if the second word is a valid direction :
        if(vNextRoom == null){
            this.aGui.println("You can't go this way.");
            this.aGui.print("\n");
            return;
        }
        
        this.aCurrentRoom = vNextRoom;
        this.printLocationInfo();
    }// goRoom(.)
    
    private void look(final Command pAction)
    {   
        if(pAction.hasSecondWord() == false) {
            this.printLocationInfo();
            return;
        }
        
        String vAction = pAction.getSecondWord();
        if (vAction.equals( "item" )) {
            this.aGui.println( this.aCurrentRoom.getItemDescription() );
            this.aGui.println("\n");
            return;
        }
        else this.aGui.println("What do you want to look at ?" + "\n");
    }
    
    private void endGame()
    {
        this.aGui.println( "Thank you for playing. Good bye. ");
        this.aGui.enable( false );
    }// endGame()
}
