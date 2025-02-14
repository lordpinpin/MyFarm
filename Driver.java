import exceptions.CannotAffordException;
import exceptions.InvalidInputException;
import exceptions.NoAvailablePlotsException;

import java.util.Scanner;

/**
 * Driver for MyFarm (CCPROG MP).
 */
public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game game;

        do {
            game = new Game();

            game.displayStart(scanner);

            do {
                game.displayFarmInfo();
                game.displayChoiceMenu();
                try {
                    game.choiceMenu(scanner);
                } catch (NoAvailablePlotsException | CannotAffordException | InvalidInputException ignored) {}
            } while (!game.endCheck());
        } while (game.displayEndInfo(scanner));

        scanner.close();
    }
}
