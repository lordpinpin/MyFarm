import java.util.Scanner;

public class UIManager {

    public static void displayStartScreen(Scanner scanner) {
        System.out.println("  --------  MY FARM  --------  ");
        System.out.println("  Press /ENTER/ to start game.  ");
        System.out.print("  ");
        scanner.nextLine();
    }

    public static void displayFarmInfo(int day, Farmer farmer, Farm farm) {
        // Check for level update before printing
        boolean levelUpdate = farmer.levelCheck();

        System.out.println();
        System.out.println("  --------  MY FARM  --------  ");
        System.out.println("  DAY: " + day);
        System.out.println("  TITLE: " + farmer.getType());
        System.out.println("  OBJECTCOINS: " + farmer.getObjectCoins());
        System.out.println("  LEVEL: " + farmer.getLevel());
        System.out.println("  TOTAL EXP: " + farmer.getTotalExp());
        System.out.println("  FARM: ");
        // Temporary display of a single plot
        System.out.println("  " + farm.getPlot(0, 0).getCharStatus(day));

        // Update level and harvest status
        farmer.levelUpdate(levelUpdate);
        System.out.println();
        farm.displayHarvestableCrop(day);
    }

    public static void displayChoiceMenu(Farm farm, Farmer farmer, int day) {
        System.out.println();
        System.out.println("  --------  ACTIONS  --------  ");
        System.out.println("  [P]LOW (Plow a plot) ");
        System.out.println("  PLAN[T] (Plant a crop in plowed plot)");
        System.out.println("  [W]ATERING CAN (Water a plot) ");
        System.out.println("  [F]ERTILIZER (Fertilize a plot) ");
        System.out.println("  [S]HOVEL (Shovel a plot) ");
        if (farm.hasRock()) {
            System.out.println("  PICKA[X]E (Mine a rock on plot) ");
        }
        if (farmer.registerCheck()) {
            System.out.println("  [R]EGISTER (Next title is available for registration) ");
        }
        if (farm.hasHarvestableCrop(day)) {
            System.out.println("  [H]ARVEST (Harvest a matured crop.)");
        }
        System.out.println("  [E]ND DAY (Advances the day)");
    }

    public static void displayEndScreen(Farm farm, int day) {
        System.out.println();
        if (farm.isFullOfWitheredCrops(day)) {
            System.out.println("         All your plots had withered crops!");
        } else {
            System.out.println("         You ran out of money to buy seeds!");
        }
        System.out.println();
        System.out.println("                        GAME OVER");
        System.out.println("         It was day " + day + " when the game ended.");
        System.out.println("  Press [N] for a new game, or any other character to quit.");
    }

    public static void printEnterCheck(Scanner scanner) {
        System.out.println("  Press /ENTER/ to continue.");
        System.out.print("  ");
        scanner.nextLine();
    }

    public static void printError(String error) {
        if (error != null && !error.isEmpty()) {
            System.err.println("Error: " + error);
        }
    }

    public static void displayPlantOptions(Farmer farmer) {
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
    }
}
