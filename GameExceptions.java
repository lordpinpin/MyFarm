public class GameExceptions extends Exception {
    public GameExceptions(String message) {
        super(message);
    }
}

//    public void errorMessage(){
//        switch(error){
//            case 0:
//                break;
//            case 1: // Lack of money
//                System.out.println("  Not enough objectCoins.");
//                System.out.println();
//                break;
//            case 2: // Invalid input
//                System.out.println("  Invalid input.");
//                System.out.println();
//                break;
//            case 3: // No valid plots.
//                System.out.println("  No available plots to use action on.");
//                System.out.println();
//                break;
//            case 4: // Withered crop.
//                System.out.println("  Crop has withered..");
//                System.out.println();
//                break;
//            case 5: // No crop.
//                System.out.println("  No crop in plot.");
//                System.out.println();
//                break;
//            case 6: // Not plowed.
//                System.out.println("  Plot is not plowed.");
//                System.out.println();
//                break;
//            case 7: // No rocks.
//                System.out.println("  No rock to pickaxe.");
//                System.out.println();
//                break;
//            case 8: // Tree adjacency.
//                System.out.println("  Trees need all adjacent plots empty.");
//                System.out.println();
//                break;
//            case 9: // Scanner error.
//                System.out.println("  Reenter choice.");
//                System.out.println();
//                break;
//            case 10: // Plot already has crop in it.
//                System.out.println("  Plot already has crop in it.");
//                System.out.println();
//                break;
//            case 11: // Plot has matured already.
//                System.out.println("  Plot cannot be watered or fertilized at harvest date.");
//                System.out.println();
//                break;
//            case 12: // Plot already plowed.
//                System.out.println("  Plot already plowed.");
//                System.out.println();
//                break;
//            case 13: // Not mature crop yet
//                System.out.println("  Crop in plot has not matured yet.");
//                System.out.println();
//                break;
//            case 14: // Plot has rock.
//                System.out.println("  Plot has rock in it.");
//                System.out.println();
//                break;
//        }
//    }

/**
 * Custom exceptions for different game errors.
 */

// Specific Exceptions
class NotEnoughMoneyException extends GameExceptions {
    public NotEnoughMoneyException() {
        super("Not enough objectCoins.");
    }
}

class InvalidInputException extends GameExceptions {
    public InvalidInputException() {
        super("Invalid input.");
    }
}

class NoAvailablePlotException extends GameExceptions {
    public NoAvailablePlotException() {
        super("No available plots to use action on.");
    }
}

class WitheredCropInPlotException extends GameExceptions {
    public WitheredCropInPlotException() {
        super("Crop has withered...");
    }
}

class NoCropInPlotException extends GameExceptions {
    public NoCropInPlotException() {
        super("No crop in plot.");
    }
}

class PlotNotPlowedException extends GameExceptions {
    public PlotNotPlowedException() {
        super("Plot is not plowed.");
    }
}

class NoRockInPlotException extends GameExceptions {
    public NoRockInPlotException() {
        super("No rock to pickaxe.");
    }
}

class TreeAdjacencyException extends GameExceptions {
    public TreeAdjacencyException() {
        super("Trees need all adjacent plots empty.");
    }
}

class ScannerInputException extends GameExceptions {
    public ScannerInputException() {
        super("Reenter choice.");
    }
}

class PlotAlreadyHasCropException extends GameExceptions {
    public PlotAlreadyHasCropException() {
        super("Plot already has a crop in it.");
    }
}

class PlotMaturedException extends GameExceptions {
    public PlotMaturedException() {
        super("Plot cannot be watered or fertilized at harvest date.");
    }
}

class PlotAlreadyPlowedException extends GameExceptions {
    public PlotAlreadyPlowedException() {
        super("Plot already plowed.");
    }
}

class CropNotMatureException extends GameExceptions {
    public CropNotMatureException() {
        super("Crop in plot has not matured yet.");
    }
}

class PlotHasRockException extends GameExceptions {
    public PlotHasRockException() {
        super("Plot has a rock in it.");
    }
}
