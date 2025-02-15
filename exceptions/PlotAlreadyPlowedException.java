package exceptions;

public class PlotAlreadyPlowedException extends Exception {
    public PlotAlreadyPlowedException() {
        super("Plot already plowed.");
    }
}
