package Y1S1.FOP_Valley2.src;

import java.util.*;

public class Main_class {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Text_Info s1 = new Text_Info();

		/* This part is allow user to choose their archetype and turn into map */
		s1.get_Cover();
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
					String player_archetype = archetype[arche - 1];
				}
				break;
			}
		}

		MapDesignAndPlayerMovement.map_main(); // static method

	}
}
