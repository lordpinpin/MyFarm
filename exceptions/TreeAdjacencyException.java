package exceptions;

public class TreeAdjacencyException extends Exception {
    public TreeAdjacencyException() {
        super("Trees need all adjacent plots empty.");
    }
}
