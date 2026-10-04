import java.util.HashMap;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Game engine
 */
public class GameEngine
{
    private Parser                aParser;
    //private Room                  aCurrentRoom;
    //private Stack<Room>           aPreviousRooms; // stocke la liste des pièces qu'on a visité
    private HashMap<String, Room> aRoomList;
    private UserInterface         aGui;
    private Player                aPlayer1;

    /**
     * Constructor for objects of class GameEngine
     * 
     */
    public GameEngine()
    {
        this.aParser        = new Parser();
        this.aRoomList      = new HashMap<String, Room>();
        //this.aPreviousRooms = new Stack<Room>();
        this.aPlayer1       = new Player();
        this.createRooms();
    }
    
    // ******** SETUP *********** 
    
    /**
     * @param pUserInterface : UserInterface object
     */
    public void setGUI( final UserInterface pUserInterface )
    {
        this.aGui = pUserInterface;
        this.printWelcome();
    }
    
    /**
     * Creer les Rooms
     */ 
    private void createRooms()
    {
        // 1st floor
        Room vHall = new Room("in the main hall", "Images/Hall.jpg");
        Room vDiningRoom = new Room("in the dining room", "Images/blank.jpg");
        Room vKitchen = new Room("in the kitchen", "Images/blank.jpg");
        Room vLivingRoom = new Room("in the living room", "Images/blank.jpg");
        Room vOffice = new Room("in the office", "Images/blank.jpg");
        
        // 2nd floor  
        Room vHall2 = new Room("above the main hall", "Images/blank.jpg");
        Room vBedroom = new Room("in the bedroom", "Images/blank.jpg");
        
        // basement 
        Room vCellar = new Room("in the cellar", "Images/blank.jpg");
        
        // setting exits
        vHall.setExit("east", vDiningRoom);
        vHall.setExit("west", vLivingRoom);
        vHall.setExit("stairs", vHall2);
        
        vDiningRoom.setExit("north", vKitchen);
        vDiningRoom.setExit("west", vHall);
        
        vKitchen.setExit("south", vDiningRoom);
        vKitchen.setExit("trapdoor", vCellar); // will be hidden
        
        vLivingRoom.setExit("north", vOffice);
        vLivingRoom.setExit("east", vHall);
        
        vOffice.setExit("east", vKitchen); // one way exit
        vOffice.setExit("south", vLivingRoom);
        
        vHall2.setExit("north", vBedroom);
        vHall2.setExit("stairs", vHall);
        
        vBedroom.setExit("south", vHall2);
        
        vCellar.setExit("up", vKitchen);
        
        // setting items
        vHall.addItem("vase", "an ancient vase", 13);
        vHall.addItem("chair", "a wooden chair", 25);
        
        // creating room Hashmap
        this.aRoomList.put("Hall", vHall);
        this.aRoomList.put("DiningRoom", vDiningRoom);
        this.aRoomList.put("Kitchen", vKitchen);
        this.aRoomList.put("LivingRoom", vLivingRoom);
        this.aRoomList.put("Office", vOffice);
        this.aRoomList.put("SecondHall", vHall2);
        this.aRoomList.put("Bedroom", vBedroom);
        this.aRoomList.put("Cellar", vCellar);
        
        // choosing starting room
        this.aPlayer1.setCurrentRoom (vHall);
    }// createRooms()
    
        private void printWelcome()
    {
        this.aGui.print("\n");
        this.aGui.println("Welcome to President Evil!");
        this.aGui.println("Save the president from this haunted mansion.");
        this.aGui.println("Type 'help' if you need help.");
        this.aGui.print("\n");
        
        this.printLocationInfo();
        
        if ( this.aPlayer1.getCurrentRoom().getImageName() != null ) 
            this.aGui.showImage( this.aPlayer1.getCurrentRoom().getImageName() );
    }// printWelcome()
    
        private void printLocationInfo()
    {
        this.aGui.print("\n");
        this.aGui.println( this.aPlayer1.getCurrentRoom().getLongDescription());
        this.aGui.print("\n");
    }// printLocationInfo()
    
    // ****** COMMANDS *********
    
    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     * 
     * @param pCommandLine Text input by user
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
        else if ( vCommandWord.equals( "back" ) ) {
            this.back();
        }
        else if ( vCommandWord.equals( "map" ) ) {
            this.aGui.print("\n");
            this.aGui.println("You don't have a map. ");
            this.aGui.print("\n");
        }     
        else if ( vCommandWord.equals( "quit" ) ) {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else
                this.endGame();
        }
        else if ( vCommandWord.equals( "test" ) ) {
            this.test( vCommand );
        }
    }
    
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
    
    /**
     * @param pDirection String indicating next room 
     */
    private void goRoom(final Command pDirection)
    {
        if(pDirection.hasSecondWord() == false){
            this.aGui.println("Go where ?" + "\n");
            return;
        }
        
        String vDirection = pDirection.getSecondWord();
        Room vNextRoom = this.aPlayer1.getCurrentRoom().getExit(vDirection);
        
        // Testing if the second word is a valid direction :
        if(vNextRoom == null){
            this.aGui.println("You can't go this way.");
            this.aGui.print("\n");
            return;
        }
        
        this.aPlayer1.goNextRoom( pDirection );
        this.printLocationInfo();
        if ( this.aPlayer1.getCurrentRoom().getImageName() != null ) 
            this.aGui.showImage( this.aPlayer1.getCurrentRoom().getImageName() );
    }// goRoom(.)
    
    private void back()
    {
        if(this.aPlayer1.noPreviousRoom() ) {
            this.aGui.println("You can't go back." + "\n");
            return;
        }
        
        this.aPlayer1.setCurrentRoom( aPlayer1.popPreviousRoom() );
        this.printLocationInfo();
        if ( this.aPlayer1.getCurrentRoom().getImageName() != null ) 
            this.aGui.showImage( this.aPlayer1.getCurrentRoom().getImageName() );
    }
    
    /**
     * @param pAction Command object indicating what the character is looking at
     */
    private void look(final Command pAction)
    {   
        if(pAction.hasSecondWord() == false) {
            this.printLocationInfo();
            return;
        }
        
        String vAction = pAction.getSecondWord();
        if (vAction.equals("item") || vAction.equals( "items" )) {
            this.aGui.println( this.aPlayer1.getCurrentRoom().getItemsDescription() );
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
    
    private void test(final Command pFichier)
    {
        if(pFichier.hasSecondWord() == false) {
            this.aGui.print("\n");
            this.aGui.println( "test what ?" );
            this.aGui.print("\n");
            return;
        }
        
        Scanner vSc;
        String vFichier = pFichier.getSecondWord();
        
        try {
            vSc = new Scanner( new File( vFichier + ".txt" ) );
            while ( vSc.hasNextLine() ) {
                String vLine = vSc.nextLine();
                this.interpretCommand( vLine );
            }
        }
        catch ( final FileNotFoundException pNF ) {
            this.aGui.print("\n");
            this.aGui.println( "test not found" );
            this.aGui.print("\n");
        }
    }// test(.)
}

