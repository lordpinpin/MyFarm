package exceptions;

public class NoAvailablePlotsException extends Exception {
    public NoAvailablePlotsException() {
        super("No available plots to use action on.");
    }
}
