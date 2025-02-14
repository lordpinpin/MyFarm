package exceptions;

public class PlotUnoccupiedException extends Exception {
    public PlotUnoccupiedException() {
        super("No crop in plot.");
    }
}
