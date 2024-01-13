package Y1S1.FOP_Valley2.src;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		TextInfo textInfo = new TextInfo();
		SaveGame saveGame = new SaveGame();
		Scanner sc = new Scanner(System.in);

		textInfo.clearScreen();
		if (!saveGame.isLoggedIn()) {
			textInfo.get_Cover();
			saveGame.promptRegisterLogin(); // Prompt for registration or login
			textInfo.clearScreen();
		}

		if (saveGame.isLoggedIn()) {
			textInfo.get_Cover();
			Player player = null;
			MapDesignAndPlayerMovement map = null;
			if (!saveGame.isNewRegistered()) {
				// Existing player
				textInfo.clearScreen();
				System.out.println("Welcome back, " + saveGame.getName() + "!");
				player = new Player(saveGame);
				map = new MapDesignAndPlayerMovement(saveGame.getcurrentLocationA(), saveGame.getcurrentLocationB());
			} else {
				// New player
				textInfo.clearScreen();
				textInfo.get_Cover();
				textInfo.startPrintStory();
				System.out.println("\n(Press any key to continue...)");
				sc.nextLine();
				player = createPlayer(saveGame);
				map = new MapDesignAndPlayerMovement(1, 20);
			}

			if (player != null) {
				// Save initial player progress
				saveGame.savePlayerProgress(
						player.getArchetypeName(),
						player.getLevel(),
						player.getExperiencePoint(),
						player.gethealthPoints(),
						player.getmanaPoints(),
						map.getA(),
						map.getB());

				while (true) {
					map.map_main(player);
					if (!player.isAlive()) {
						textInfo.clearScreen();
						textInfo.get_Cover();
						textInfo.printLoseStory(player.getloggedInPlayerName());
						break;
					} else {
						textInfo.printSavegame();
						if (sc.nextLine().equalsIgnoreCase("Y")) {
							System.out.println("Saving game progress for player: " + player.getloggedInPlayerName());
							saveGame.savePlayerProgress(
									player.getArchetypeName(),
									player.getLevel(),
									player.getExperiencePoint(),
									player.gethealthPoints(),
									player.getmanaPoints(),
									map.getA(),
									map.getB());
							break;
						} else {
							System.out.println("Game progress not saved.");
							// saveGame.deletePlayerUsername();
							break;
						}
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
		TextInfo textInfo = new TextInfo();
		String playerName = saveGame.getName();
		String player_archetype = "";

		String verify = "NO";
		while (true) {
			// Initial game interface
			// Should print a cover image of the adventure game
			// Archetype Selection
			textInfo.get_Select_Archetypes();
			System.out.print("Enter : ");
			int arche = sc.nextInt();
			if (arche < 1 || arche > 5) {
				System.out.println("Invalid Input.");
			} else {
				textInfo.get_Archetypes_Info(arche);
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
