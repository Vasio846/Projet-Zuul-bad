import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import pkg_Commands.Parser;
import pkg_Commands.Command;

import pkg_RoomPack.Room;

import pkg_ItemPack.Item;

import pkg_NpcPack.Npc;

/**
 * Game engine
 */
public class GameEngine
{
    private Parser                aParser;
    private HashMap<String, Room> aRoomList;
    private ArrayList<Room>       aRandRooms;
    private UserInterface         aGui;
    private Player                aPlayer1;
    private int                   aSuspicion;
    private Room                  aTabletCharge; // memory for tp tablet
    private String                aAlea; // pour truquer les random en mode test
    private HashMap<String, Npc>  aNpcList;

    /**
     * Constructor for objects of class GameEngine
     * 
     */
    public GameEngine()
    {
        this.aParser        = new Parser();
        this.aRoomList      = new HashMap<String, Room>();
        this.aRandRooms     = new ArrayList<Room>();
        this.aNpcList       = new HashMap<String, Npc>();
        this.aPlayer1       = new Player();
        this.createRooms();
        this.aSuspicion     = 0;
        this.aTabletCharge  = null;
        this.aAlea          = null;
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
        Room vDiningRoom = new Room("in the dining room", "Images/DiningRoom.jpg");
        Room vKitchen = new Room("in the kitchen", "Images/Kitchen.jpg");
        Room vLivingRoom = new Room("in the living room", "Images/LivingRoom.jpg");
        Room vOffice = new Room("in the office", "Images/Office.jpg");
        
        // 2nd floor  
        Room vHall2 = new Room("on the second floor", "Images/Hall2.jpg");
        Room vBedroom = new Room("in the bedroom", "Images/Bedroom.jpg");
        Room vBathroom = new Room("in the bathroom", "Images/Bathroom.jpg");
        
        // basement 
        Room vCellar = new Room("in the cellar", "Images/Cellar.jpg");
        
        // special
        Room vMirror = new Room("mirror", null);
        
        // setting exits
        vHall.setExit("east", vDiningRoom, false);
        vHall.setExit("west", vLivingRoom, false);
        vHall.setExit("up", vHall2, false);
        
        vDiningRoom.setExit("north", vKitchen, false);
        vDiningRoom.setExit("west", vHall, false);
        
        vKitchen.setExit("south", vDiningRoom, false);
        vKitchen.setExit("down", vCellar, false);
        
        vLivingRoom.setExit("north", vOffice, false);
        vLivingRoom.setExit("east", vHall, false);
        
        vOffice.setExit("east", vKitchen, true); // one way exit
        vOffice.setExit("south", vLivingRoom, false);
        
        vHall2.setExit("north", vBedroom, true); // locked by red key
        vHall2.setExit("east", vBathroom, false);
        vHall2.setExit("down", vHall, false);
        
        vBedroom.setExit("south", vHall2, false);
        
        vBathroom.setExit("west", vHall2, false);
        
        vCellar.setExit("up", vKitchen, false);
        
        // adjusting doors
        vOffice.getDoor("east").unlockDoor();
        vOffice.getDoor("east").oneWay();
        
        // adjusting lights
        vCellar.lightsOff();
        
        // setting items
        vHall.addItem("vase", "an ancient vase", 35, 200);
        vHall.addItem("chair", "a wooden chair", 50, 10);
        vDiningRoom.addItem("rock", "a big rock", 200, 0);
        vLivingRoom.addItem("red_key", "a small red key", 5, 15); // opens bedroom
        vKitchen.addItem("pizza", "a pineapple pizza", 15, -500);
        vCellar.addItem("water_bucket", "a bucket of stagnant water", 40, 20);
        
        // creating Npcs
        Npc vRat = new Npc("rat", vKitchen, true);
        Npc vCat = new Npc("cat", vLivingRoom, false);
        
        // setting Npcs
        vKitchen.addNpc("rat", vRat);
        vLivingRoom.addNpc("cat", vCat);
        
        // creating room Hashmap
        this.aRoomList.put("Hall", vHall);
        this.aRoomList.put("DiningRoom", vDiningRoom);
        this.aRoomList.put("Kitchen", vKitchen);
        this.aRoomList.put("LivingRoom", vLivingRoom);
        this.aRoomList.put("Office", vOffice);
        this.aRoomList.put("SecondHall", vHall2);
        this.aRoomList.put("Bedroom", vBedroom);
        this.aRoomList.put("Bathroom", vBathroom);
        this.aRoomList.put("Cellar", vCellar);
        this.aRoomList.put("Mirror", vMirror);
        
        // creating npc Hashmap
        this.aNpcList.put("rat", vRat);
        this.aNpcList.put("cat", vCat);
        
        // rooms for randomizer 
        Collections.addAll(aRandRooms, vHall, vDiningRoom, vKitchen, vLivingRoom, vOffice, vHall2, vBathroom);
        
        // choosing starting room
        this.aPlayer1.setCurrentRoom (vHall);
    }// createRooms()
    
        private void printWelcome()
    {
        this.aGui.print("\n");
        this.aGui.println("Find the mansion's treasures.");
        this.aGui.println("Don't let anybody notice you.");
        this.aGui.println("Exit the mansion once you've collected enough treasures");
        this.aGui.print("\n");
        this.aGui.println("Type 'help' if you need help.");
        this.aGui.print("\n");
        
        this.printLocationInfo();
        
        if ( this.aPlayer1.getCurrentRoom().getImageName() != null ) 
            this.aGui.showImage( this.aPlayer1.getCurrentRoom().getImageName() );
    }// printWelcome()
    
        private void printLocationInfo()
    {
        if( !this.aPlayer1.getCurrentRoom().getLights() ) {
            this.aGui.print("\n");
            this.aGui.println("It's too dark.");
            this.aGui.println("You can't see anything. ");
            this.aGui.print("\n");
            return;
        }
        
        this.aGui.print("\n");
        this.aGui.println( this.aPlayer1.getCurrentRoom().getLongDescription());
        this.aGui.print("\n");
    }// printLocationInfo()
    
    private void moveNpc()
    {
        for ( String vNpc : this.aNpcList.keySet() ) {
            if ( this.aNpcList.get(vNpc).moves() ) ( this.aNpcList.get(vNpc) ).move();
        }
    }
    
    private void lose()
    {
        this.aGui.print("\n");
        this.aGui.println("You were not discreet enough.");
        this.aGui.println("The police have been alerted to your presence.");
        this.aGui.println("Game over.");
        this.aGui.print("\n");
        this.endGame();
    }
    
    private void exit()
    {
        if ( !this.aPlayer1.getCurrentRoom().getDescription().equals("in the main hall") ) {
            this.aGui.print("\n");
            this.aGui.println("You are not near the exit.");
            this.aGui.println("Return to the main hall to exit.");
            this.aGui.print("\n");
            return;
        }
        this.aGui.print("\n");
        this.aGui.println("You have exited the mansion.");
        this.aGui.println("No one noticed you, good job !");
        this.aGui.println("You have collected " + this.aPlayer1.getTotalValue() + " points.");
        this.aGui.print("\n");
        this.endGame();
    }
    
    private void endGame()
    {
        this.aGui.print("\n");
        this.aGui.println( "Thank you for playing. Good bye. ");
        this.aGui.enable( false );
    }// endGame()
    
    
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
        else if ( vCommandWord.equals( "back" ) ) {
            this.back();
        }   
        else if ( vCommandWord.equals( "use" ) ) 
            this.use( vCommand );
        else if ( vCommandWord.equals( "look" ) ) {
            this.look( vCommand );
        }
        else if ( vCommandWord.equals( "inventory" ) ) {
            this.showInventory();
        }
        else if ( vCommandWord.equals( "take" ) ) {
            this.take( vCommand );
        }
        else if ( vCommandWord.equals( "drop" ) ) {
            this.drop( vCommand );
        }  
        else if ( vCommandWord.equals( "scream" ) ) {
            this.aGui.println("you scream loudly.");
            this.aGui.println("...");
            this.aGui.println("but nothing happened...");
        }
        else if ( vCommandWord.equals( "exit" ) ) {
            this.exit();
        }     
        else if ( vCommandWord.equals( "quit" ) ) {
            if ( vCommand.hasSecondWord() )
                this.aGui.println( "Quit what?" );
            else {   
                this.aGui.showImage( "Images/ThankYouForPlaying.jpg" );
                this.endGame();
            }
        }
        else if ( vCommandWord.equals( "test" ) ) {
            this.test( vCommand );
        }
        else if ( vCommandWord.equals( "alea" ) ) {
            this.alea( vCommand );
        }
        this.moveNpc();
    }
    
    private void printHelp()
    {
        this.aGui.print("\n");
        this.aGui.println("Find the mansion's treasures.");
        this.aGui.println("Don't let anybody notice you.");
        this.aGui.print("\n");
        this.aGui.println("Your command words are: ");
        this.aGui.println(aParser.getPublicCommands()); // utilise Parser pour print qqchose dépendant de CommandWords
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
        
        if ( vDirection.equals("back") ) this.back(); // pour autoriser "go back"
        
        Room vNextRoom = this.aPlayer1.getCurrentRoom().getExit(vDirection);
        
        // Testing if the second word is a valid direction :
        if(vNextRoom == null){
            this.aGui.println("You can't go this way.");
            this.aGui.print("\n");
            return;
        }
        
        if ( this.aPlayer1.getCurrentRoom().hasDoor(vDirection) ) { 
            if ( this.aPlayer1.getCurrentRoom().directionLocked(vDirection) ) {
                if (this.aPlayer1.getCurrentRoom().getDescription().equals("on the second floor")) {
                    if (this.itemInInventory("red_key")) {
                        this.aGui.println("You unlocked the door with the red key.");
                        this.aPlayer1.getCurrentRoom().getDoor(vDirection).unlockDoor();
                        this.goRoom(pDirection);
                        return;
                    }
                }
                this.aGui.print("\n");
                this.aGui.println("The door is locked.");
                this.aGui.print("\n");
                return;
            }
            
            if ( this.aPlayer1.getCurrentRoom().getDoor(vDirection).isOneWay() ){
                this.aPlayer1.deletePreviousRooms();
            }
        }
        
        this.aPlayer1.goNextRoom( vNextRoom );
        
        if ( this.aPlayer1.getCurrentRoom().getDescription().equals("mirror") ) {
            this.aPlayer1.deletePreviousRooms(); // empecher back()
            this.goRandom();
        }
        
        this.printLocationInfo();
        if ( this.aPlayer1.getCurrentRoom().getImageName() != null ) 
            this.aGui.showImage( this.aPlayer1.getCurrentRoom().getImageName() );
        
        if ( this.aPlayer1.isOverMaxWeight() ) {
            this.suspicion( 10 );
            this.aGui.println("You are carrying too much.");
            this.aGui.println("You make noise while moving." + "\n");
        }
    }// goRoom(.)
    
    private void goRandom()
    {
        Random r = new Random();
        int vN = r.nextInt( this.aRandRooms.size() + 1 ); // genere un int aleatoire
        Room vDestination;
        if (this.aAlea != null) vDestination = this.aRoomList.get(this.aAlea);
        else vDestination = this.aRandRooms.get(vN); // choisis une Room aleatoire en fonction de l'int
        this.aPlayer1.goNextRoom( vDestination ); 
    }// goRandom()
    
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
    
    private void use(final Command pAction)
    {
        if(pAction.hasSecondWord() == false) {
            this.aGui.print("\n");
            this.aGui.println("Use what ? ");
            this.aGui.print("\n");
            return;
        }
        
        String vAction = pAction.getSecondWord();
        if ( vAction.equals("switch") || vAction.equals("lights") ) {
            if (this.aPlayer1.getCurrentRoom().switchLights() ) {
                this.aGui.println("lights on");
                this.aGui.print("\n");
            }
            else {
                this.aGui.println("lights off");
                this.aGui.print("\n");
            }
        }
        else if ( vAction.equals("Spotion") ) {
            if ( this.itemInInventory("Spotion") ) {
                this.aGui.print("\n");
                this.aGui.println("You drink the strength potion.");
                this.aGui.println("Your Max Weight increases!");
                this.aGui.print("\n");
                this.aPlayer1.addMaxWeight(100);
            }
            else return;
        }
        else if ( vAction.equals("tablet") ) {
            if ( this.itemInInventory("tablet") ) {
                if ( this.aTabletCharge == null ) {
                    this.aGui.print("\n");
                    this.aGui.println("You use the strange stone tablet.");
                    this.aGui.println("The runes light up but nothing happens...");
                    this.aGui.print("\n");
                    this.aTabletCharge = this.aPlayer1.getCurrentRoom();
                }
                else {
                    this.aGui.print("\n");
                    this.aGui.println("You use the strange stone tablet again.");
                    this.aGui.println("You are surrounded by bright light.");
                    this.aGui.println("You open your eyes and find yourself in the room where you first used the tablet.");
                    this.aGui.print("\n");
                    this.aPlayer1.goNextRoom( this.aTabletCharge );
                    this.printLocationInfo();
                    if ( this.aPlayer1.getCurrentRoom().getImageName() != null ) 
                        this.aGui.showImage( this.aPlayer1.getCurrentRoom().getImageName() );
                    this.aTabletCharge = null;
                }
            }
        }
        else if ( vAction.equals("water_bucket") ) {
            if ( this.itemInInventory("water_bucket") ) {
                this.aGui.print("\n");
                this.aGui.println("You drink the stagnant water bucket.");
                this.aGui.println("You are feeling unwell...");
                this.aGui.println("...\n...\n...");
                this.aGui.println("You have died of dysentery.");
                this.aGui.showImage( "Images/dysentry.jpg" );
                this.aGui.print("\n");
                this.endGame();
            }
        }
        else if ( vAction.equals("rat") ) {
            if (this.aPlayer1.getCurrentRoom().getDescription().equals( "in the living room" )) {
                if ( this.itemInInventory("rat") ) {
                    this.aGui.print("\n");
                    this.aGui.println("You give the rat to the cat.");
                    this.aGui.println("The cat eats it and runs away");
                    this.aGui.println("...\n...\n...");
                    this.aGui.println("There is a pearl left where the cat was sitting.");
                    this.aGui.print("\n");
                    this.aRoomList.get("LivingRoom").addItem("pearl", "a shiny pearl", 10, 70);
                }
            }
        }
    }// use(.)
    
    /**
     * fonction qui vérifie si un objet est dans l'inventaire du Player
     * 
     * @param pItem Item name
     */
    private boolean itemInInventory(final String pItem)
    {
        if ( this.aPlayer1.hasItem(pItem) )
            return true;
        else {
            this.aGui.print("\n");
            this.aGui.println("You don't have this item in your inventory.");
            this.aGui.print("\n");
            return false;
        }
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
            if( !this.aPlayer1.getCurrentRoom().getLights() ) { // tester si on peut voir
                this.aGui.print("\n");
                this.aGui.println("It's too dark.");
                this.aGui.println("You can't see anything. ");
                this.aGui.print("\n");
                return;
            }
            
            this.aGui.println( this.aPlayer1.getCurrentRoom().getItemsDescription() );
            this.aGui.println("\n");
            return;
        }
        else if (vAction.equals("inventory")) {
            this.showInventory();
        }
        else if (vAction.equals("wardrobe")) {
            if (this.aPlayer1.getCurrentRoom().getDescription().equals("in the bedroom")) {
                this.aGui.println("There is a strange mirror inside the wardrobe.");
                this.aGui.println("It's almost as if you could walk inside..." + "\n");
                this.aRoomList.get("Bedroom").setExit("mirror", aRoomList.get("Mirror"), false);
                this.printLocationInfo();
            }
            else this.aGui.println("What do you want to look at ?" + "\n");
        }
        else if (vAction.equals("desk")) {
            if (this.aPlayer1.getCurrentRoom().getDescription().equals("in the office")) {
                this.aGui.println("You find a few items in the desk's drawers." + "\n"); 
                this.aRoomList.get("Office").addItem("tablet", "a strange stone tablet", 50, 150);
                this.aRoomList.get("Office").addItem("coin_pouch", "a large coin pouch", 20, 200);
                this.aRoomList.get("Office").addItem("Spotion", "a strengthening potion", 10, 400);
                this.printLocationInfo();
            }
            else this.aGui.println("What do you want to look at ?" + "\n");
        }
        else if (vAction.equals("chest")) {
            if (this.aPlayer1.getCurrentRoom().getDescription().equals("in the bedroom")) {
                this.aGui.println("You find gold ingots in the chest !" + "\n"); 
                this.aRoomList.get("Bedroom").addItem("ingots", "a pile of golden ingots", 200, 1000);
                this.printLocationInfo();
            }
            else this.aGui.println("What do you want to look at ?" + "\n");
        }
        else if (vAction.equals("cat")) {
            if (this.aPlayer1.getCurrentRoom().getDescription().equals("in the living room")) {
                this.aGui.println("The cat looks hungry." + "\n"); 
                this.printLocationInfo();
            }
            else this.aGui.println("What do you want to look at ?" + "\n");
        }
        else this.aGui.println("What do you want to look at ?" + "\n");
    }
    
    private void showInventory()
    {
        if ( this.aPlayer1.emptyInv() ) {
            this.aGui.print("\n");
            this.aGui.println("Your inventory is empty.");
            this.aGui.print("\n");
            return;
        }
        
        String vS = this.aPlayer1.itemList();
        String[] vItems = vS.split("\\W+");
        
        for ( String vIt : vItems ) {
            this.aGui.println( this.aPlayer1.searchInventory(vIt).getLongDescription() );
        }
    }
    
    /**
     * @param pCommand Command object indicating the item
     */
    private void take(final Command pCommand)
    {
        if(pCommand.hasSecondWord() == false) {
            this.aGui.print("\n");
            this.aGui.println("Take what ? ");
            this.aGui.print("\n");
            return;
        }
        
        String vItemName = pCommand.getSecondWord();
        
        if (vItemName.equals("rat")) {
            if ( this.aPlayer1.getCurrentRoom().getDescription().equals( this.aNpcList.get("rat").getCurrentRoom().getDescription() ) ) {
                this.aPlayer1.pickUpItem(new Item("rat", "a dead rat", 10, 0));
                this.aPlayer1.getCurrentRoom().removeNpc("rat");
                this.aNpcList.remove("rat");
                this.aGui.print("\n");
                this.aGui.println("You have caught the rat. ");
                this.aGui.print("\n");
                return;
            }
        }
        
        try { 
            Item vItem = this.aPlayer1.getCurrentRoom().getItem(vItemName);
            boolean vWeight = this.aPlayer1.pickUpItem(vItem);
            this.aPlayer1.getCurrentRoom().removeItem(vItemName); //retire l'objet de la Room
        
            this.aGui.print("\n");
            this.aGui.println("You have taken the " + vItemName + ".");
            this.aGui.print("\n");
            
            if ( vWeight ) {
                this.aGui.print("\n");
                this.aGui.println("You are carrying too may items. ");
                this.aGui.print("\n");
            }
        }
        catch (Exception vE) {
            this.aGui.print("\n");
            this.aGui.println("there is no " + vItemName + " here.");
            this.aGui.print("\n");
        }
    }
    
    /**
     * @param pCommand Command object indicating the item
     */
    private void drop(final Command pCommand)
    {
        if(pCommand.hasSecondWord() == false) {
            this.aGui.print("\n");
            this.aGui.println("Drop what ? ");
            this.aGui.print("\n");
            return;
        }
        
        String vItemName = pCommand.getSecondWord();
        try { 
            Item vItem = this.aPlayer1.searchInventory(vItemName);
            this.aPlayer1.getCurrentRoom().addItem(vItemName, vItem.getDescription(), vItem.getWeight(), vItem.getValue() ); 
            this.aPlayer1.dropItem(vItem);
            
            this.aGui.print("\n");
            this.aGui.println("You have dropped the " + vItemName + ".");
            this.aGui.print("\n");
        }
        catch (Exception vE) {
            this.aGui.print("\n");
            this.aGui.println("there is no " + vItemName + " in your inventory.");
            this.aGui.print("\n");
        }
    }
    
    
    /**
     * permet de lancer un test
     * 
     * @param pFichier nom du fichier txt contenant les instructions ligne par ligne
     */
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
    
    /**
     * commande pour truquer les tirages aleatoires
     * n'est utilisee que dans les tests
     */
    private void alea( final Command pCommand ) // commande reservee aux tests
    {
        if (pCommand.hasSecondWord()) {
            String vS = pCommand.getSecondWord();
            this.aAlea = vS;
        }
        else this.aAlea = null;
    }
    
    // ********* MODIFICATIONS **********
    
    private void suspicion(final int pSuspicion)
    {
        this.aSuspicion = this.aSuspicion + pSuspicion;
        if (this.aSuspicion >= 50) this.lose();
    }
}

