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
			MapDesignAndPlayerMovement map = null;
            if (!saveGame.isNewRegistered()) {
				// Existing player
				player = new Player(saveGame);
				map = new MapDesignAndPlayerMovement(saveGame.getcurrentLocationA(), saveGame.getcurrentLocationB());
			} else {
				// New player
                player = createPlayer(scanner);
				map = new MapDesignAndPlayerMovement(1, 20);
            }

            if (player != null) {
				// map.map_main();
                // Monsters monsters = new Slime(); // Replace with actual monster instantiation
				// battleSystem battleSystem = new battleSystem(player, monsters);

				// // saveGame.loadPlayerProgress();
				// battleSystem.startBattle();
				// saveGame.savePlayerProgress(
				// 		player.getArchetypeName(),
				// 		player.getLevel(),
				// 		player.getExperiencePoint(),
				// 		player.gethealthPoints(),
				// 		player.getmanaPoints(),
				// 		map.getA(),
				// 		map.getB()
				// );

				while (true) {
					map.map_main(player);
					System.out.println("Do you want to continue? (Y/N)");
					if (scanner.nextLine().equalsIgnoreCase("N")) {
						saveGame.savePlayerProgress(
							player.getArchetypeName(),
							player.getLevel(),
							player.getExperiencePoint(),
							player.gethealthPoints(),
							player.getmanaPoints(),
							map.getA(),
							map.getB()
						);
						break;
					}
				}
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
