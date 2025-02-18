import exceptions.*;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * <p>
 * This class represents the core game loop of the game MYFARM.
 * <p>
 * The Game starts by displaying the current stats, farm and the actions available to be played. The
 * Farmer (player) can decide what to do from here. Most of these actions will affect the Farm, which
 * contains a field of Plots where Crops can be planted in after plowing. These Crops will have different
 * costs, needs and payoffs.
 * <p>
 * The main aim in the Game is to grow these Crops on the Plots and harvest them to gain objectCoins
 * and EXP. Gaining enough EXP will let the player level up and unlock upgrades that can make their
 * harvests more profitable. Meanwhile, these objectCoins are used to pay for various upgrades, actions
 * and the Crops to be planted.
 * <p>
 * The Game has no set end, but if all Plots contain Crops that are withered or if the Farmer
 * does not have enough objectCoins to buy any seeds with no active Crops in the farm.
 */
public class Game {
    private int day = 1;
    private final Farmer farmer;
    private final Farm farm;
    
    private String error;

    /**
     * Constructor for Game.
     */
    public Game(){
        farm = new Farm();
        farmer = new Farmer();
    }

    /**
     * Asks the player to press enter to continue.
     * @param scanner the Scanner object to detect player input.
     */

    public void enterCheck(Scanner scanner){
        System.out.println("  Press /ENTER/ to continue.");
        System.out.print("  ");
        scanner.nextLine();
    }



    /**
     * Checks if the Game should end based on two conditions, if all Plots have withered Crops or
     * if the Farmer does not have enough money to buy Crops and has no active Crops in the farm
     * @return true if one of the conditions is met for the Game to end and false if not.
     */
    public boolean endCheck(){
        if(farm.witherFarmCheck(day)){ // Will be modified to check for all plots for final MP
            return true;
        }
        else return !farmer.coinCheck(5, true) && !(farm.cropFarmCheck(day));
    }

    /**
     * Advances the day. Updates the wither status for all crops.
     */
    public void advanceDay(){
        day++;
        farm.witherUpdate(day);
    }

    /**
     * Displays the beginning message for the Game to confirm if the player is ready.
     * @param scanner the Scanner object used to detect input from the player.
     */
    public void displayStart(Scanner scanner){
        System.out.println("  --------  MY FARM  --------  ");
        System.out.println("  Press /ENTER/ to start game.  ");
        System.out.print("  ");
        scanner.nextLine();
    }
    /**
     * Displays the stats for the farm and the Farmer. It displays the title of the Game,
     * the current day, the Farmer's current title, their current number of objectCoins,
     * their current level and total EXP, and an overview of the plots in the field using
     * on a different symbols.
     * It will also display when the player has levelled up, or when a crop has become available
     * for harvesting.
     */
    public void displayFarmInfo(){ // Temp UI
        boolean levelUpdate = farmer.levelCheck();

        System.out.println();
        System.out.println("  --------  MY FARM  --------  ");
        System.out.println("  DAY: " + day);
        System.out.println("  TITLE: " + farmer.getType());
        System.out.println("  OBJECTCOINS: " + farmer.getObjectCoins());
        System.out.println("  LEVEL: " + farmer.getLevel());
        System.out.println("  TOTAL EXP: " + farmer.getTotalExp());
        System.out.println("  FARM: ");
        System.out.println("  " + farm.getPlot(0, 0).getCharStatus(day)); // Temp display

        farmer.levelUpdate(levelUpdate);
        System.out.println();
        farm.harvestUpdate(day);
    }

    /**
     * Displays the different actions the player/Farmer can take for their farm. Certain
     * actions will not be displayed if it cannot be done, though certain core actions
     * remain visible even if not viable at the moment.
     */
    public void displayChoiceMenu(){ // Temp UI
        System.out.println();
        System.out.println("  --------  ACTIONS  --------  ");
        System.out.println("  [P]LOW (Plow a plot) ");
        System.out.println("  PLAN[T] (Plant a crop in plowed plot)");
        System.out.println("  [W]ATERING CAN (Water a plot) ");
        System.out.println("  [F]ERTILIZER (Fertilize a plot) ");
        System.out.println("  [S]HOVEL (Shovel a plot) ");
        if(farm.rockFarmCheck()){
            System.out.println("  PICKA[X]E (Mine a rock on plot) ");
        }
        if(farmer.registerCheck()){
            System.out.println("  [R]EGISTER (Next title is available for registration) ");
        }
        if(farm.harvestFarmCheck(day)){
            System.out.println("  [H]ARVEST (Harvest a matured crop.)");
        }
        System.out.println("  [E]ND DAY (Advances the day)");
    }

    /**
     * Displays the ending screen when the Farmer has run into one of the end-game conditions.
     * It also asks the player if they would like to make a new game or quit.
     * @param scanner the Scanner object used to detect input from the player.
     * @return true if the player chooses to create a new game and false if not.
     */
    public boolean displayEndInfo(Scanner scanner){ // Temp UI
        System.out.println();
        if(farm.witherFarmCheck(day)){
            System.out.println("         All your plots had withered crops!");
        }
        else{
            System.out.println("         You ran out of money to buy seeds!");
        }
        System.out.println();
        System.out.println("                        GAME OVER");
        System.out.println("         It was day " + day + " when the game ended.");
        System.out.println("  Press [N] for a new game, or any other character to quit.");
        char choice = 0;
        try {
            choice = charInput(scanner);
        } catch (ScannerException e) {
            error = e.getMessage();
        }
        return choice == 'N';
    }

    /**
     * Detects input from the player and performs the given action that the player chose.
     * Appropriate error messages will be displayed when an action cannot be performed.
     * @param scanner the Scanner object used to detect input from the player.
     */
    public void choiceMenu(Scanner scanner) throws NoAvailablePlotsException, CannotAffordException, InvalidInputException {

        ArrayList<Integer> xy = new ArrayList<>(2);

        System.out.println();
        printError();
        error = "";

        System.out.println("  Choose an action.");

        char choice = 0;
        try {
            choice = charInput(scanner);
        } catch (ScannerException e) {
            error = e.getMessage();
        }
        System.out.println();

        switch (choice) {
            case 'P': // PLOW action
                if (!farm.plowedFarmCheck()) {
                    System.out.println("  Which plot to plow?");
                    plotInput(scanner, xy);
                    try {
                        farmer.plow(farm.getPlot(xy.get(0),xy.get(1)), day);
                    } catch (CropWitheredException | PlotAlreadyPlowedException | PlotAlreadyOccupiedException e) {
                        error = e.getMessage();
                    }
                } else {
                    throw new NoAvailablePlotsException();
                }
                break;
            case 'T': // PLANT action
                if (farm.plantFarmCheck()) {
                    System.out.println("  Which plot to plant in?");
                    plotInput(scanner, xy);
                    try {
                        farm.getPlot(xy.get(0),xy.get(1)).plantCheck();
                    } catch (PlotHasRockException | PlotAlreadyOccupiedException | PlotNotPlowedException e) {
                        error = e.getMessage();
                    }
                    try {
                        plantInput(scanner, xy.get(0), xy.get(1));
                    } catch (InvalidInputException | CannotAffordException | TreeAdjacencyException e) {
                        error = e.getMessage();
                    }
                } else {
                    throw new NoAvailablePlotsException();
                }
                break;
            case 'W': // WATER action
                if (farm.cropFarmCheck(day)) {
                    System.out.println("  Which plot to water?");
                    plotInput(scanner, xy);
                    try {
                        farmer.water(farm.getPlot(xy.get(0),xy.get(1)), day);
                    } catch (CropWitheredException | PlotUnoccupiedException | PlotNotPlowedException |
                             PlotAlreadyMaturedException e) {
                        error = e.getMessage();
                    }
                }
                else {
                    throw new NoAvailablePlotsException();
                }
                break;
            case 'F': // FERTILIZE action
                if (farm.cropFarmCheck(day)) {
                    System.out.println("  Which plot to fertilize?");
                    plotInput(scanner, xy);
                    if (farmer.coinCheck(10, false)) {
                        try {
                            farmer.fertilize(farm.getPlot(xy.get(0),xy.get(1)), day);
                        } catch (CropWitheredException | PlotUnoccupiedException | PlotNotPlowedException |
                                 PlotAlreadyMaturedException e) {
                            error = e.getMessage();
                        }
                    } else {
                        throw new CannotAffordException();
                    }
                }
                else {
                    throw new NoAvailablePlotsException();
                }
                break;
            case 'S': // SHOVEL action
                System.out.println("  Which plot to shovel?");
                plotInput(scanner, xy);
                try {
                    farmer.shovel(farm.getPlot(xy.get(0),xy.get(1)));
                } catch (CannotAffordException e) {
                    error = e.getMessage();
                }
                break;
            case 'X': // PICKAXE action
                if (farm.rockFarmCheck()) {
                    if (farmer.coinCheck(50, false)) {
                        throw new CannotAffordException();
                    } else {
                        System.out.println("  Which plot to use pickaxe on?");
                        plotInput(scanner, xy);
                        try {
                            farmer.pickaxe(farm.getPlot(xy.get(0),xy.get(1)));
                        } catch (NoRockException | CannotAffordException e) {
                            error = e.getMessage();
                        }
                    }
                }
                break;
            case 'R': // REGISTER action
                if (farmer.registerCheck()) {
                    farmer.displayRegister();
                    System.out.println("  Do you wish to register? Enter [Y] if so, any other character if not.");
                    char confirm = 0;
                    try {
                        confirm = charInput(scanner);
                    } catch (ScannerException e) {
                        error = e.getMessage();
                    }
                    if (confirm == 'Y') {
                        try {
                            farmer.register();
                        } catch (CannotAffordException e) {
                            error = e.getMessage();
                        }
                    }
                }
                break;
            case 'H': // HARVEST action
                if (farm.harvestFarmCheck(day)) {
                    System.out.println("  Which plot to harvest?");
                    plotInput(scanner, xy);
                    farmer.harvest(farm.getPlot(xy.get(0),xy.get(1)), day);
                    enterCheck(scanner);
                }
                break;
            case 'E': // END DAY action
                System.out.println("  Do you wish to advance the day? Enter [Y] if so, any other character if not.");
                char confirm = 0;
                try {
                    confirm = charInput(scanner);
                } catch (ScannerException e) {
                    error = e.getMessage();
                }
                if (confirm == 'Y') {
                    advanceDay();
                }
                break;
            default:
                throw new InvalidInputException();
            }
    }
    /**
     * Detects a letter input from the player. It will take the first character from the input
     * the player has given it. Will display an error if it is not a letter.
     * @param scanner the Scanner object used to detect input from the player.
     * @return the letter that the player inputted.
     */
    public char charInput(Scanner scanner) throws ScannerException {
        char choice;
        do {
            printError();
            error = "";
            System.out.print("  ");
            choice = scanner.next().charAt(0);
            scanner.nextLine();
            if (choice == '\n') {
                throw new ScannerException();
            }
        }while(!(choice >= 'a' && choice <= 'z') && !(choice >= 'A' && choice <= 'Z'));
        choice = Character.toUpperCase(choice);
        return choice;
    }

    /**
     * Detects a two number input from the player. Will display an error if it is not two numbers,
     * or that the input is not a valid coordinate for a Plot in the farm.
     * @param scanner the Scanner object used to detect input from the player.
     * @param numbers the ArrayList that will contain the inputs taken from the player.
     */
    public void plotInput(Scanner scanner, ArrayList<Integer> numbers){
        numbers.clear();
        String[] strs;
        do{
            System.out.print("  ");
            String lines = scanner.nextLine();
            strs = lines.trim().split("\\s+");

            if(strs.length > 0) {
                for (String str : strs) {
                    try {
                        numbers.add(Integer.parseInt(str));
                    } catch (NumberFormatException ignored){
                    }
                }
            }

            if(numbers.size() <= 1){
                System.out.println("  Invalid input.");
                numbers.clear();
            }
            else if(!farm.isValidPlot(numbers.get(0), numbers.get(1))){
                System.out.println("  Invalid inputs.");
                numbers.clear();
            }
        } while (numbers.isEmpty());

    }

    /**
     * Displays the various Crops that can be planted on a Plot. The player can exit if they want to,
     * or choose a Crop which will require a certain amount of objectCoins.
     * @param scanner the Scanner object used to detect input from the player.
     * @param x the row where the Plot to be planted is contained
     * @param y the column where the Plot to be planted is contained.
     */
    public void plantInput(Scanner scanner, int x, int y) throws InvalidInputException, CannotAffordException, TreeAdjacencyException {
        char choice;

        System.out.println();
        System.out.println("  |    NAME     |  TYPE  | DAYS | WATER NEEDS | FERTI NEEDS | PRODUCT | PRICE |  EXP  | COST |");
        System.out.println("  | [T]urnip    |  Root  |  2   |    1 - 2    |    0 - 1    |  1 - 2  |   6   | 5     | " + (5 - farmer.getSeedCostReduction()) + "    ");
        System.out.println("  | [C]arrot    |  Root  |  3   |    1 - 2    |    0 - 1    |  1 - 2  |   9   | 7.5   | " + (10 - farmer.getSeedCostReduction()) + "   ");
        System.out.println("  | [P]otato    |  Root  |  5   |    3 - 4    |    1 - 2    |  1 - 10 |   3   | 12.5  | " + (20 - farmer.getSeedCostReduction()) + "   ");
        System.out.println("  | [R]ose      | Flower |  1   |    1 - 2    |    0 - 1    |    1    |   5   | 2.5   | " + (5 - farmer.getSeedCostReduction()) + "    ");
        System.out.println("  | T[u]rnips   | Flower |  2   |    2 - 3    |    0 - 1    |    1    |   9   | 5     | " + (10 - farmer.getSeedCostReduction()) + "   ");
        System.out.println("  | [S]unflower | Flower |  3   |    2 - 3    |    1 - 2    |    1    |  19   | 7.5   | " + (20 - farmer.getSeedCostReduction()) + "   ");
        System.out.println("  | [M]ango     |  Tree  |  10  |    7 - 7    |    4 - 4    |  5 - 15 |   8   | 25    | " + (100 - farmer.getSeedCostReduction()) + "  ");
        System.out.println("  | [A]pple     |  Tree  |  10  |    7 - 7    |    5 - 5    | 10 - 15 |   5   | 25    | " + (200 - farmer.getSeedCostReduction()) + "  ");
        System.out.println("    [E]xit    ");
        System.out.println();

        try {
            do{
                printError();
                error = "";
                System.out.println("  Which plant do you want?");


                choice = charInput(scanner);

                switch(choice){
                    case 'T':
                        if(farmer.coinCheck(5, true)){
                            farmer.plant(farm.getPlot(x, y), CropFactory.createCrop("Turnip", day));
                            choice = 'E';
                        }
                        else throw new CannotAffordException();
                        break;
                    case 'C':
                        if(farmer.coinCheck(10, true)){
                            farmer.plant(farm.getPlot(x, y), CropFactory.createCrop("Carrot", day));
                            choice = 'E';
                        }
                        else throw new CannotAffordException();
                        break;
                    case 'P':
                        if(farmer.coinCheck(20, true)){
                            farmer.plant(farm.getPlot(x, y), CropFactory.createCrop("Potato", day));
                            choice = 'E';
                        }
                        else throw new CannotAffordException();
                        break;
                    case 'R':
                        if(farmer.coinCheck(5, true)){
                            farmer.plant(farm.getPlot(x, y), CropFactory.createCrop("Rose", day));
                            choice = 'E';
                        }
                        else throw new CannotAffordException();
                        break;
                    case 'U':
                        if(farmer.coinCheck(10, true)){
                            farmer.plant(farm.getPlot(x, y), CropFactory.createCrop("Turnips", day));
                            choice = 'E';
                        }
                        else throw new CannotAffordException();
                        break;
                    case 'S':
                        if(farmer.coinCheck(20, true)){
                            farmer.plant(farm.getPlot(x, y), CropFactory.createCrop("Sunflower", day));
                            choice = 'E';
                        }
                        else throw new CannotAffordException();
                        break;
                    case 'M':
                        if (farm.adjacentPlotCheck(x, y)){
                            throw new TreeAdjacencyException();
                        }
                        else if(!farmer.coinCheck(100, true)){
                            throw new CannotAffordException();
                        }
                        else{
                            farmer.plant(farm.getPlot(x, y), CropFactory.createCrop("Mango", day));
                            choice = 'E';
                        }
                        break;
                    case 'A':
                        if (farm.adjacentPlotCheck(x, y)){
                            throw new TreeAdjacencyException();
                        }
                        else if(!farmer.coinCheck(200, true)){
                            throw new CannotAffordException();
                        }
                        else{
                            farmer.plant(farm.getPlot(x, y), CropFactory.createCrop("Apple", day));
                            choice = 'E';
                        }
                        break;
                    case 'E':
                        break;
                    default:
                        throw new InvalidInputException();
                }

            } while(choice != 'E');
        } catch (ScannerException e) {
            error = e.getMessage();
        }
    }
    
    private void printError() {
        if (error != null && (!error.isEmpty() || !error.equals(""))) {
            System.err.println("Error: " + error);
        }
    }
}
