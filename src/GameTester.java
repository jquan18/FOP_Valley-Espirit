package Y1S1.FOP_Valley2.src;

import java.util.Scanner;

public class GameTester {

    public static void main(String[] args) {
        SaveGame saveGame = new SaveGame();
        Scanner scanner = new Scanner(System.in);

        if (!saveGame.isLoggedIn()) {
            saveGame.promptRegisterLogin(); // Prompt for registration or login
        }

        if (saveGame.isLoggedIn()) {
            Player player = null;
            if (!saveGame.isNewRegistered()) {
                 player = new Player(saveGame.getName(), saveGame.getArchetypeName());
				player = new Player(saveGame);
            } else {
                player = createPlayer(scanner);
            }

            if (player != null) {
                Monsters monsters = new Slime(); // Replace with actual monster instantiation
				battleSystem battleSystem = new battleSystem(player, monsters);

				// saveGame.loadPlayerProgress();
				battleSystem.startBattle();
				saveGame.savePlayerProgress(
						player.getArchetypeName(),
						player.getLevel(),
						player.getExperiencePoint(),
						player.gethealthPoints(),
						player.getmanaPoints(),
						1,1
				);
                saveGame.closeConnection();
            }
        } else {
            System.out.println("Login failed. Exiting the game.");
        }
    }

    private static Player createPlayer(Scanner scanner) {
        System.out.println("Enter player name: ");
        String playerName = scanner.nextLine();

        System.out.println("Enter player archetype (e.g., Archer, Paladin, Warrior): ");
        String archetype = scanner.nextLine();

        return new Player(playerName, archetype);
    }
}
