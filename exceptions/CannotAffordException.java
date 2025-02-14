package exceptions;

public class CannotAffordException extends Exception {
    public CannotAffordException() {
        super("Not enough objectCoins.");
    }
}
