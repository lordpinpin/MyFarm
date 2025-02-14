import java.util.Scanner;

/**
 * Driver for MyFarm (CCPROG MP).
 */
public class Driver {
    public static void main(String[] args) throws InvalidInputException, ScannerInputException {
        Scanner scanner = new Scanner(System.in);

        Game game;

        do {
            game = new Game();

            game.displayStart(scanner);

            do {
                game.displayFarmInfo();
                game.displayChoiceMenu();
                game.choiceMenu(scanner);
            } while (!game.endCheck());
        } while (game.displayEndInfo(scanner));

        scanner.close();
    }
}
