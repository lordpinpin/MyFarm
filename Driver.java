import exceptions.CannotAffordException;
import exceptions.InvalidInputException;
import exceptions.NoAvailablePlotsException;
import exceptions.ScannerException;
import exceptions.TreeAdjacencyException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char newGameChoice = 'N';

        // Outer loop for restarting the game
        do {
            // Create a new game instance
            GameManager gameManager = new GameManager();

            // Display the starting screen using the UIManager
            UIManager.displayStartScreen(scanner);

            // Main game loop
            while (!gameManager.endCheck()) {
                UIManager.displayFarmInfo(
                        gameManager.getDay(),
                        gameManager.getFarmer(),
                        gameManager.getFarm()
                );
                UIManager.displayChoiceMenu(
                        gameManager.getFarm(),
                        gameManager.getFarmer(),
                        gameManager.getDay()
                );

                try {
                    gameManager.choiceMenu(scanner);
                } catch (NoAvailablePlotsException | CannotAffordException |
                         InvalidInputException | TreeAdjacencyException e) {
                    System.err.println(e.getMessage());
                }
            }

            // Display the end screen and ask if the player wants a new game
            UIManager.displayEndScreen(gameManager.getFarm(), gameManager.getDay());
            try {
                newGameChoice = InputManager.getCharInput(scanner);
            } catch (ScannerException e) {
                System.err.println("Error: " + e.getMessage());
                break;
            }
        } while (newGameChoice == 'N');  // 'N' means "new game"

        scanner.close();
    }
}