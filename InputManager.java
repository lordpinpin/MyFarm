import exceptions.ScannerException;

import java.util.ArrayList;
import java.util.Scanner;

public class InputManager {

    /**
     * Reads the next non-empty letter from the input and returns it as an uppercase character.
     */
    public static char getCharInput(Scanner scanner) throws ScannerException {
        char choice;
        do {
            System.out.print("  ");
            String input = scanner.next();
            if (input.isEmpty()) {
                throw new ScannerException();
            }
            choice = input.charAt(0);
            scanner.nextLine(); // consume the rest of the line
            if (!Character.isLetter(choice)) {
                System.out.println("  Invalid input. Please enter a letter.");
            }
        } while (!Character.isLetter(choice));
        return Character.toUpperCase(choice);
    }

    /**
     * Reads two integer values from input and validates them as plot coordinates.
     */
    public static ArrayList<Integer> getPlotInput(Scanner scanner, Farm farm) {
        ArrayList<Integer> numbers = new ArrayList<>();
        String[] strs;
        do {
            System.out.print("  ");
            String line = scanner.nextLine();
            strs = line.trim().split("\\s+");
            for (String str : strs) {
                try {
                    numbers.add(Integer.parseInt(str));
                } catch (NumberFormatException e) {
                    // Ignore invalid number tokens.
                }
            }
            if (numbers.size() <= 1) {
                System.out.println("  Invalid input.");
                numbers.clear();
            } else if (!farm.isValidPlot(numbers.get(0), numbers.get(1))) {
                System.out.println("  Invalid inputs.");
                numbers.clear();
            }
        } while (numbers.isEmpty());
        return numbers;
    }
}