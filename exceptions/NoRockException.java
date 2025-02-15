package exceptions;

public class NoRockException extends Exception {
    public NoRockException() {
        super("No rock to pickaxe.");
    }
}
