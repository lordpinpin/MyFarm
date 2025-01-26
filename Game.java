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
    private int error;

    /**
     * Constructor for Game.
     */
    public Game(){
        farm = new Farm();
        farmer = new Farmer();
    }

    /**
     * An error message generated based on the error code at given time.
     * The list of errors are:
     * <ul>
     *     <li> 0 - No error
     *     <li> 1 - Not enough money
     *     <li> 2 - Invalid input
     *     <li> 3 - No available plots to do action
     *     <li> 4 - Withered crop
     *     <li> 5 - No crop
     *     <li> 6 - Not plowed
     *     <li> 7 - No rocks
     *     <li> 8 - Tree adjacency error
     *     <li> 9 - Scanner error
     *     <li> 10 - Plot has crop
     *     <li> 11 - Plot has matured already
     *     <li> 12 - Plot has already been plowed
     *     <li> 13 - Crop not mature yet
     *     <li> 14 - Plot has rock
     * </ul>
     */
    public void errorMessage(){
        switch(error){
            case 0:
                break;
            case 1: // Lack of money
                System.out.println("  Not enough objectCoins.");
                System.out.println();
                break;
            case 2: // Invalid input
                System.out.println("  Invalid input.");
                System.out.println();
                break;
            case 3: // No valid plots.
                System.out.println("  No available plots to use action on.");
                System.out.println();
                break;
            case 4: // Withered crop.
                System.out.println("  Crop has withered..");
                System.out.println();
                break;
            case 5: // No crop.
                System.out.println("  No crop in plot.");
                System.out.println();
                break;
            case 6: // Not plowed.
                System.out.println("  Plot is not plowed.");
                System.out.println();
                break;
            case 7: // No rocks.
                System.out.println("  No rock to pickaxe.");
                System.out.println();
                break;
            case 8: // Tree adjacency.
                System.out.println("  Trees need all adjacent plots empty.");
                System.out.println();
                break;
            case 9: // Scanner error.
                System.out.println("  Reenter choice.");
                System.out.println();
                break;
            case 10: // Plot already has crop in it.
                System.out.println("  Plot already has crop in it.");
                System.out.println();
                break;
            case 11: // Plot has matured already.
                System.out.println("  Plot cannot be watered or fertilized at harvest date.");
                System.out.println();
                break;
            case 12: // Plot already plowed.
                System.out.println("  Plot already plowed.");
                System.out.println();
                break;
            case 13: // Not mature crop yet
                System.out.println("  Crop in plot has not matured yet.");
                System.out.println();
                break;
            case 14: // Plot has rock.
                System.out.println("  Plot has rock in it.");
                System.out.println();
                break;
        }
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
        char choice = charInput(scanner);
        return choice == 'N';
    }

    /**
     * Detects input from the player and performs the given action that the player chose.
     * Appropriate error messages will be displayed when an action cannot be performed.
     * @param scanner the Scanner object used to detect input from the player.
     */
    public void choiceMenu(Scanner scanner) {

        ArrayList<Integer> xy = new ArrayList<>(2);

        System.out.println();
        errorMessage();
        error = 0;

        System.out.println("  Choose an action.");

        char choice = charInput(scanner);
        System.out.println();

        switch (choice) {
            case 'P': // PLOW action
                if (!farm.plowedFarmCheck()) {
                    System.out.println("  Which plot to plow?");
                    plotInput(scanner, xy);
                    error = farmer.plow(farm.getPlot(xy.get(0),xy.get(1)), day);
                } else {
                    error = 3;
                }
                break;
            case 'T': // PLANT action
                if (farm.plantFarmCheck()) {
                    System.out.println("  Which plot to plant in?");
                    plotInput(scanner, xy);
                    error = farm.getPlot(xy.get(0),xy.get(1)).plantCheck();
                    if (error == 0) {
                        plantInput(scanner, xy.get(0), xy.get(1));
                    }
                } else {
                    error = 3;
                }
                break;
            case 'W': // WATER action
                if (farm.cropFarmCheck(day)) {
                    System.out.println("  Which plot to water?");
                    plotInput(scanner, xy);
                    error = farmer.water(farm.getPlot(xy.get(0),xy.get(1)), day);
                }
                else {
                    error = 3;
                }
                break;
            case 'F': // FERTILIZE action
                if (farm.cropFarmCheck(day)) {
                    System.out.println("  Which plot to fertilize?");
                    plotInput(scanner, xy);
                    if (farmer.coinCheck(10, false)) {
                        error = farmer.fertilize(farm.getPlot(xy.get(0),xy.get(1)), day);
                    } else {
                        error = 1;
                    }
                }
                else {
                    error = 3;
                }
                break;
            case 'S': // SHOVEL action
                System.out.println("  Which plot to shovel?");
                plotInput(scanner, xy);
                error = farmer.shovel(farm.getPlot(xy.get(0),xy.get(1)));
                break;
            case 'X': // PICKAXE action
                if (farm.rockFarmCheck()) {
                    if (farmer.coinCheck(50, false)) {
                        error = 1;
                    } else {
                        System.out.println("  Which plot to use pickaxe on?");
                        plotInput(scanner, xy);
                        error = farmer.pickaxe(farm.getPlot(xy.get(0),xy.get(1)));
                    }
                }
                break;
            case 'R': // REGISTER action
                if (farmer.registerCheck()) {
                    farmer.displayRegister();
                    System.out.println("  Do you wish to register? Enter [Y] if so, any other character if not.");
                    char confirm = charInput(scanner);
                    if (confirm == 'Y') {
                        error = farmer.register();
                    }
                }
                break;
            case 'H': // HARVEST action
                if (farm.harvestFarmCheck(day)) {
                    System.out.println("  Which plot to harvest?");
                    plotInput(scanner, xy);
                    error = farmer.harvest(farm.getPlot(xy.get(0),xy.get(1)), day);
                    if(error == 0){
                        enterCheck(scanner);
                    }
                }
                break;
            case 'E': // END DAY action
                System.out.println("  Do you wish to advance the day? Enter [Y] if so, any other character if not.");
                char confirm = charInput(scanner);
                if (confirm == 'Y') {
                    advanceDay();
                }
                break;
            default:
                error = 2;
                break;
            }
    }
    /**
     * Detects a letter input from the player. It will take the first character from the input
     * the player has given it. Will display an error if it is not a letter.
     * @param scanner the Scanner object used to detect input from the player.
     * @return the letter that the player inputted.
     */
    public char charInput(Scanner scanner){
        char choice;
        do {
            errorMessage();
            error = 0;
            System.out.print("  ");
            choice = scanner.next().charAt(0);
            scanner.nextLine();
            if (choice == '\n') {
                error = 9;
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
    public void plantInput(Scanner scanner, int x, int y){
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

        do{
        errorMessage();
        error = 0;
        System.out.println("  Which plant do you want?");

        choice = charInput(scanner);

            switch(choice){
                case 'T':
                    if(farmer.coinCheck(5, true)){
                        farmer.plant(farm.getPlot(x, y), new Crop("Turnip", "Root", day, 2, 1, 2, 0, 1, 1, 2, 5, 6, 5));
                        choice = 'E';
                    }
                    else{
                        error = 1;
                    }
                    break;
                case 'C':
                    if(farmer.coinCheck(10, true)){
                        farmer.plant(farm.getPlot(x, y), new Crop("Carrot", "Root", day, 3, 1, 2, 0, 1, 1, 2, 10, 9, 7.5));
                        choice = 'E';
                    }
                    else{
                        error = 1;
                    }
                    break;
                case 'P':
                    if(farmer.coinCheck(20, true)){
                        farmer.plant(farm.getPlot(x, y), new Crop("Potato", "Root", day, 5, 3, 4, 1, 2, 1, 10, 20, 3, 12.5));
                        choice = 'E';
                    }
                    else{
                        error = 1;
                    }
                    break;
                case 'R':
                    if(farmer.coinCheck(5, true)){
                        farmer.plant(farm.getPlot(x, y), new Crop("Rose", "Flower", day, 1, 1, 2, 0, 1, 1, 1, 5, 5, 2.5));
                        choice = 'E';
                    }
                    else{
                        error = 1;
                    }
                    break;
                case 'U':
                    if(farmer.coinCheck(10, true)){
                        farmer.plant(farm.getPlot(x, y), new Crop("Turnips", "Flower", day, 2, 2, 3, 0, 1, 1, 1, 10, 9, 5));
                        choice = 'E';
                    }
                    else{
                        error = 1;
                    }
                    break;
                case 'S':
                    if(farmer.coinCheck(20, true)){
                        farmer.plant(farm.getPlot(x, y), new Crop("Sunflower", "Flower", day, 3, 2, 3, 1, 2, 1, 1, 20, 19, 7.5));
                        choice = 'E';
                    }
                    else{
                        error = 1;
                    }
                    break;
                case 'M':
                    if (farm.adjacentPlotCheck(x, y)){
                        error = 8;
                    }
                    else if(!farmer.coinCheck(100, true)){
                        error = 1;
                    }
                    else{
                        farmer.plant(farm.getPlot(x, y), new Crop("Mango", "Tree", day, 10, 7, 7, 4, 4, 5, 15, 100, 8, 25));
                        choice = 'E';
                    }
                    break;
                case 'A':
                    if (farm.adjacentPlotCheck(x, y)){
                        error = 8;
                    }
                    else if(!farmer.coinCheck(200, true)){
                        error = 1;
                    }
                    else{
                        farmer.plant(farm.getPlot(x, y), new Crop("Apple", "Tree", day, 10, 7, 7, 5, 5, 10, 15, 200, 5, 25));
                        choice = 'E';
                    }
                    break;
                case 'E':
                    break;
                default:
                    error = 2;
                    break;
            }
        } while(choice != 'E');
    }
}
