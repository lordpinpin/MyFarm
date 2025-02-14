package exceptions;

public class PlotAlreadyOccupiedException extends Exception {
    public PlotAlreadyOccupiedException() {
        super("Plot already has crop in it.");
    }
}
