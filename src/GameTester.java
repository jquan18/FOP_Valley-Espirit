package Y1S1.FOP_Valley2.src;

import java.util.Scanner;

public class GameTester {

	public static void main(String[] args) {
		Text_Info s1 = new Text_Info();
		SaveGame saveGame = new SaveGame();
		Scanner scanner = new Scanner(System.in);

		s1.get_Cover();
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
				player = createPlayer(saveGame);
				map = new MapDesignAndPlayerMovement(1, 20);
			}

			if (player != null) {
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
								map.getB());
						break;
					}
				}
				saveGame.closeConnection();

			}
		} else {
			System.out.println("Login failed. Exiting the game.");
		}
	}

	private static Player createPlayer(SaveGame saveGame) {
		Scanner sc = new Scanner(System.in);
		Text_Info s1 = new Text_Info();
		// System.out.println("Enter play-+
		er name: ");
		// String playerName = sc.nextLine();
		String playerName = saveGame.getName();
		String player_archetype = "";

		String verify = "NO";
		while (true) {
			// Initial game interface
			// Should print a cover image of the adventure game
			// Archetype Selection
			s1.get_Select_Archetypes();
			System.out.print("Enter : ");
			int arche = sc.nextInt();
			if (arche < 1 || arche > 5) {
				System.out.println("Invalid Input.");
			} else {
				s1.get_Archetypes_Info(arche);
				System.out.print("YES / NO : ");
				verify = sc.next();
				if (verify.equalsIgnoreCase("YES")) {
					String[] archetype = { "Archer", "Paladin", "Warrior", "Mage", "Rogue" };
					player_archetype = archetype[arche - 1];
					break;
				} else {
					System.out.println("Please select again: ");
				}
			}
		}

		return new Player(playerName, player_archetype);
	}
}
