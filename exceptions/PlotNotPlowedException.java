package exceptions;

public class PlotNotPlowedException extends Exception {
    public PlotNotPlowedException() {
        super("Plot is not plowed.");
    }
}
