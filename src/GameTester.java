package Y1S1.FOP_Valley2.src;
import java.util.Scanner;


// Dont forget to change file path in TextInfo.java
// Dont forget to change file path in PlayerSpell.java
// Dont forget to change file path in battleSystem.java

public class GameTester {

	public static void main(String[] args) {
		TextInfo s1 = new TextInfo();
		Scanner scanner = new Scanner(System.in);

		s1.get_Cover();
		// s1.startPrintStory();
		System.out.println("\nPress any key to continue...");
		scanner.nextLine();
		Player player = createPlayer();
		MapDesignAndPlayerMovement map = new MapDesignAndPlayerMovement(1, 20);

		if (player != null) {
			while (true) {
				map.map_main(player);
				System.out.println("Do you want to continue? (Y/N)");
				if (scanner.nextLine().equalsIgnoreCase("N")) {
					System.out.println("Bye bye");
					break;
				}
			}
		} else {
			System.out.println("Login failed. Exiting the game.");
		}
	}

	private static Player createPlayer() {
		Scanner sc = new Scanner(System.in);
		TextInfo s1 = new TextInfo();
		System.out.println("Enter player name: ");
		String playerName = sc.nextLine();
		String player_archetype = "";

		String verify = "NO";
		while (true) {
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
