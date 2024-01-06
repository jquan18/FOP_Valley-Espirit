package Y1S1.FOP_Valley2.src;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;

public class battleSystem {
	private Player player;
	private Monsters monsters;
	private PlayerSpell playerSpell;
	private LevelSystem playerLevel;

	public battleSystem(Player player, Monsters monsters) {
		this.player = player;
		this.monsters = monsters;
		this.playerSpell = new PlayerSpell(player, monsters);
		playerLevel = new LevelSystem(this.player);
	}

	public void startBattle() {
		System.out.println("Battle starts between " + player.getloggedInPlayerName() + " and " + monsters.getName());
		String[] m = {"Slime", "Spider", "Skeleton Warrior"};
		String[] M = {"Giant", "Witch", "Gargoyle"};
		while (player.isAlive() && monsters.isAlive() && player.player_keepStay()) {
			displayMonsterImage(monsters.getName());
			displayBattleStatus();

			playerTurn();
			if (!monsters.isAlive()) {
				System.out.println("You defeated " + monsters.getName());
				int EXP_Drops;
				for (int i=0; i<3; i++) {
					if (monsters.getName().equalsIgnoreCase(m[i])) {
						EXP_Drops = new Random().nextInt(15)+5;
						playerLevel.addEXP(EXP_Drops);
					}
					else if (monsters.getName().equalsIgnoreCase(M[i])) {
						EXP_Drops = new Random().nextInt(30)+10;
						playerLevel.addEXP(EXP_Drops);
					}
				}
				break;
			}

			monstersTurn();
			if (!player.isAlive()) {
				System.out.println("You are defeated by " + monsters.getName());
				break;
			}
			playerSpell.decreaseCooldowns();
		}
		player.displayPlayerStatus();

	}

	public void playerTurn() {
		player.playerPutDownDefend();
		System.out.println("Its your turn");

		System.out.println("S1.Attack");
		System.out.println("S2.Defend");
		System.out.println("S3.Heal");
		System.out.println("S4.Escape");
		System.out.println("S5.Use Spell");

		String choice = getUserChoice();

		switch (choice) {
			case "S1": {
				player.playerAttack(monsters);
				break;
			}

			case "S2": {
				player.playerDefending();
				break;
			}

			case "S3": {
				int healAmount = new Random().nextInt(11) + 10;
				player.playerHeal(healAmount);
				System.out.println("You healed yourself for " + healAmount + " HP!");
				break;
			}

			case "S4": {
				player.tryEscaped();
				break;
			}

			case "S5": {
				playerSpell.startSpell();
				break;
			}

			default:
				System.out.println("Invalid Choice");
		}
	}

	public String getUserChoice() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter your choice: ");
		String choice = keyboard.next();
		return choice;

	}

	public void monstersTurn() {
		Random rand = new Random();
		System.out.println("\nIt's " + monsters.getName() + "'s turn");
		int choice = rand.nextInt(3);

		if (choice == 0) { //Monster Attack
			System.out.println("Monster Attack");
			monsters.monstersAttack(player);
		}
		else if (choice == 1){ // Monster Rest
			System.out.println("Monster Rest");
		}
		else if (choice == 2) {
			System.out.println("Monster use Spell");
			monsters.monstersSpell(player);
		}

	}

	public void displayBattleStatus() {
		player.displayPlayerStatus();
		monsters.displayMonstersStatus();
	}

	public void displayMonsterImage(String monsters ){
		String[] monsters_type = {"Slime", "Spider", "Skeleton_Warrior", "Giant", "Witch", "Gargoyle"};
		for (int i=0; i<monsters_type.length; i++) {
			if (monsters.equalsIgnoreCase(monsters_type[i])) {
				String currentWorkingDir = System.getProperty("user.dir");
				String relativePath = currentWorkingDir + "/Assignment/Monster_Image/ASCII_Formal/" + monsters_type[i] +".txt";
				try {
					Scanner reader = new Scanner(new FileInputStream(relativePath));
					while (reader.hasNextLine()) {
						String line = reader.nextLine();
						System.out.println(line);
					}
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}

	}
}
