import exceptions.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager {
    private int day = 1;
    private final Farmer farmer;
    private final Farm farm;
    private String error = "";

    public GameManager() {
        farm = new Farm();
        farmer = new Farmer();
    }

    /**
     * Checks if the game should end.
     */
    public boolean endCheck() {
        if (farm.isFullOfWitheredCrops(day)) {
            return true;
        } else {
            return !farmer.coinCheck(5, true) && !farm.hasUnmaturedCrop(day);
        }
    }

    /**
     * Advances the day and updates crop wither status.
     */
    public void advanceDay() {
        day++;
        farm.hasWitheredCrops(day);
    }

    public int getDay() {
        return day;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public Farm getFarm() {
        return farm;
    }

    /**
     * Processes the player's menu choice.
     */
    public void choiceMenu(Scanner scanner)
            throws NoAvailablePlotsException, CannotAffordException, InvalidInputException, TreeAdjacencyException {
        ArrayList<Integer> xy;
        UIManager.printError(error);
        error = "";
        System.out.println("  Choose an action.");

        char choice = 0;
        try {
            choice = InputManager.getCharInput(scanner);
        } catch (ScannerException e) {
            error = e.getMessage();
        }
        System.out.println();

        switch (choice) {
            case 'P': // PLOW action
                if (!farm.hasPlowablePlot()) {
                    System.out.println("  Which plot to plow?");
                    xy = InputManager.getPlotInput(scanner, farm);
                    try {
                        farmer.plow(farm.getPlot(xy.get(0), xy.get(1)), day);
                    } catch (CropWitheredException | PlotAlreadyPlowedException | PlotAlreadyOccupiedException e) {
                        error = e.getMessage();
                    }
                } else {
                    throw new NoAvailablePlotsException();
                }
                break;
            case 'T': // PLANT action
                if (farm.hasPlantablePlot()) {
                    System.out.println("  Which plot to plant in?");
                    xy = InputManager.getPlotInput(scanner, farm);
                    try {
                        farm.getPlot(xy.get(0), xy.get(1)).isPlantable();
                    } catch (PlotHasRockException | PlotAlreadyOccupiedException | PlotNotPlowedException e) {
                        error = e.getMessage();
                    }
                    plantInput(scanner, xy.get(0), xy.get(1));
                } else {
                    throw new NoAvailablePlotsException();
                }
                break;
            case 'W': // WATER action
                if (farm.hasUnmaturedCrop(day)) {
                    System.out.println("  Which plot to water?");
                    xy = InputManager.getPlotInput(scanner, farm);
                    try {
                        farmer.water(farm.getPlot(xy.get(0), xy.get(1)), day);
                    } catch (CropWitheredException | PlotUnoccupiedException | PlotNotPlowedException | PlotAlreadyMaturedException e) {
                        error = e.getMessage();
                    }
                } else {
                    throw new NoAvailablePlotsException();
                }
                break;
            case 'F': // FERTILIZE action
                if (farm.hasUnmaturedCrop(day)) {
                    System.out.println("  Which plot to fertilize?");
                    xy = InputManager.getPlotInput(scanner, farm);
                    if (farmer.coinCheck(10, false)) {
                        try {
                            farmer.fertilize(farm.getPlot(xy.get(0), xy.get(1)), day);
                        } catch (CropWitheredException | PlotUnoccupiedException | PlotNotPlowedException | PlotAlreadyMaturedException e) {
                            error = e.getMessage();
                        }
                    } else {
                        throw new CannotAffordException();
                    }
                } else {
                    throw new NoAvailablePlotsException();
                }
                break;
            case 'S': // SHOVEL action
                System.out.println("  Which plot to shovel?");
                xy = InputManager.getPlotInput(scanner, farm);
                try {
                    farmer.shovel(farm.getPlot(xy.get(0), xy.get(1)));
                } catch (CannotAffordException e) {
                    error = e.getMessage();
                }
                break;
            case 'X': // PICKAXE action
                if (farm.hasRock()) {
                    if (farmer.coinCheck(50, false)) {
                        throw new CannotAffordException();
                    } else {
                        System.out.println("  Which plot to use pickaxe on?");
                        xy = InputManager.getPlotInput(scanner, farm);
                        try {
                            farmer.pickaxe(farm.getPlot(xy.get(0), xy.get(1)));
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
                        confirm = InputManager.getCharInput(scanner);
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
                if (farm.hasHarvestableCrop(day)) {
                    System.out.println("  Which plot to harvest?");
                    xy = InputManager.getPlotInput(scanner, farm);
                    farmer.harvest(farm.getPlot(xy.get(0), xy.get(1)), day);
                    UIManager.printEnterCheck(scanner);
                }
                break;
            case 'E': // END DAY action
                System.out.println("  Do you wish to advance the day? Enter [Y] if so, any other character if not.");
                char confirm = 0;
                try {
                    confirm = InputManager.getCharInput(scanner);
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
     * Processes the planting input, displaying crop options and checking purchase conditions.
     */
    private void plantInput(Scanner scanner, int x, int y)
            throws InvalidInputException, CannotAffordException, TreeAdjacencyException {
        char choice;
        UIManager.displayPlantOptions(farmer);
        try {
            do {
                UIManager.printError(error);
                error = "";
                System.out.println("  Which plant do you want?");
                choice = InputManager.getCharInput(scanner);
                switch (choice) {
                    case 'T':
                        if (farmer.coinCheck(5, true)) {
                            farmer.plant(farm.getPlot(x, y), CropFactory.createTurnip(day));
                            choice = 'E';
                        } else {
                            throw new CannotAffordException();
                        }
                        break;
                    case 'C':
                        if (farmer.coinCheck(10, true)) {
                            farmer.plant(farm.getPlot(x, y), CropFactory.createCarrot(day));
                            choice = 'E';
                        } else {
                            throw new CannotAffordException();
                        }
                        break;
                    case 'P':
                        if (farmer.coinCheck(20, true)) {
                            farmer.plant(farm.getPlot(x, y), CropFactory.createPotato(day));
                            choice = 'E';
                        } else {
                            throw new CannotAffordException();
                        }
                        break;
                    case 'R':
                        if (farmer.coinCheck(5, true)) {
                            farmer.plant(farm.getPlot(x, y), CropFactory.createRose(day));
                            choice = 'E';
                        } else {
                            throw new CannotAffordException();
                        }
                        break;
                    case 'U':
                        if (farmer.coinCheck(10, true)) {
                            farmer.plant(farm.getPlot(x, y), CropFactory.createTurnips(day));
                            choice = 'E';
                        } else {
                            throw new CannotAffordException();
                        }
                        break;
                    case 'S':
                        if (farmer.coinCheck(20, true)) {
                            farmer.plant(farm.getPlot(x, y), CropFactory.createSunflower(day));
                            choice = 'E';
                        } else {
                            throw new CannotAffordException();
                        }
                        break;
                    case 'M':
                        if (farm.hasEmptyAdjacentPlots(x, y)) {
                            throw new TreeAdjacencyException();
                        } else if (!farmer.coinCheck(100, true)) {
                            throw new CannotAffordException();
                        } else {
                            farmer.plant(farm.getPlot(x, y), CropFactory.createMango(day));
                            choice = 'E';
                        }
                        break;
                    case 'A':
                        if (farm.hasEmptyAdjacentPlots(x, y)) {
                            throw new TreeAdjacencyException();
                        } else if (!farmer.coinCheck(200, true)) {
                            throw new CannotAffordException();
                        } else {
                            farmer.plant(farm.getPlot(x, y), CropFactory.createApple(day));
                            choice = 'E';
                        }
                        break;
                    case 'E':
                        break;
                    default:
                        throw new InvalidInputException();
                }
            } while (choice != 'E');
        } catch (ScannerException e) {
            error = e.getMessage();
        }
    }
}