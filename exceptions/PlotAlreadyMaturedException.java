package exceptions;

public class PlotAlreadyMaturedException extends Exception {
    public PlotAlreadyMaturedException() {
        super("Plot cannot be watered or fertilized at harvest date.");
    }
}
