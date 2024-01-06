package Y1S1.FOP_Valley2.src;

import java.util.Scanner;
import java.io.*;

public class PlayerSpell {
	private int[] spellCooldowns = new int[] { 0, 0, 0 };
	private String[] spellName = new String[3];
	private int[] levelRequired = new int[3];
	private String[] spellDescription = new String[3];
	private int[] cdTurn = new int[3];

	private Player player;
	private Monsters monsters;

	public PlayerSpell(Player player, Monsters monsters) {
		this.player = player;
		this.monsters = monsters;
	}

	public boolean startSpell() {
		// Load spells based on player archetype upon initialization
		loadSpells(player.getArchetypeName());
		return displaySpellChoices();
	}

	private void loadSpells(String playerArchetype) {
		try {
			Scanner reader = new Scanner(new FileInputStream("/home/jquan18/UM/Y1S1/FOP_Valley2/resources/spells.txt"));
			int i = 0;
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				if (line.equals(playerArchetype)) {
					spellName[i] = reader.nextLine();
					levelRequired[i] = Integer.parseInt(reader.nextLine());
					spellDescription[i] = reader.nextLine();
					cdTurn[i] = Integer.parseInt(reader.nextLine());
					i++;
				} else {
					// Skip irrelevant spell info
					for (int j = 0; j < 4; j++) {
						reader.nextLine();
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean displaySpellChoices() {
		Scanner input = new Scanner(System.in);
		int manaPoints = player.getmanaPoints();
		int level = player.getLevel();

		System.out.println("You have 3 spells to choose from.");
		for (int i = 0; i < 3; i++) {
			if (spellName[i] != null && level >= levelRequired[i] && cooldownCheck(i + 1)
					&& manaPoints >= getManaCost(i + 1)) {
				System.out.println("Spell Choices: ");
				System.out.println("Spell Number " + (i + 1) + ":");
				System.out.println("Spell Name: " + spellName[i]);
				System.out.println("Level Required: " + levelRequired[i]);
				System.out.println("Spell Description: " + spellDescription[i]);
				System.out.println("Cooldown: " + cdTurn[i]);

				System.out.println("Enter the spell number you want to use: \n");
				int spellIndex = input.nextInt();

				return castSpell(spellIndex, monsters);
			} else if (level < levelRequired[i]) {
				System.out.println("Spell " + (i + 1) + " unlock at level " + levelRequired[i] + ".");
			} else if (!cooldownCheck(i + 1)) {
				System.out.println(
						"Spell " + (i + 1) + " is on cooldown. \nAnother " + spellCooldowns[i] + " turn(s) left.");
			} else if (manaPoints < getManaCost(i + 1)) {
				System.out.println("You do not have enough mana to use spell " + (i + 1) + ".");
			} else {
				System.out.println("No spells available.");
			}
		}
		return false;
	}

	public boolean castSpell(int spellIndex, Monsters monster) {
		int manaPoints = player.getmanaPoints();
		int level = player.getLevel();

		if (spellIndex >= 1 && spellIndex <= 3) {
			int index = spellIndex - 1;
			if (level >= levelRequired[index] && cooldownCheck(spellIndex) && manaPoints >= getManaCost(spellIndex)) {
				int damage = calculateSpellDamage(spellIndex, monster);
				System.out.println("You used spell " + spellName[index] + "!");
				System.out.println("You dealt " + damage + " damage to the monster!");
				startCooldown(spellIndex, cdTurn[index]);
				player.decreaseManaPoints(getManaCost(spellIndex));
				return true;
			} else {
				System.out.println("You are not high enough level or the spell is on cooldown or you lack mana.");
			}
		} else {
			System.out.println("Invalid spell choice.");
		}
		return false;
	}

	private int getManaCost(int spellIndex) {
		if (spellIndex == 1) {
			return 10;
		} else if (spellIndex == 2) {
			return 20;
		} else {
			return 30;
		}
	}

	private int calculateSpellDamage(int spellIndex, Monsters monster) {
		int damage = 0;
		int numberOfAttacks = spellIndex == 1 ? 2 : (spellIndex == 2 ? 5 : 8);
		for (int i = 0; i < numberOfAttacks; i++) {
			damage += player.playerAttack(monster);
		}
		return damage;
	}

	private boolean cooldownCheck(int spellIndex) {
		return spellCooldowns[spellIndex - 1] == 0;
	}

	private void startCooldown(int spellIndex, int cooldownTurns) {
		spellCooldowns[spellIndex - 1] = cooldownTurns;
	}

	public void decreaseCooldowns() {
		for (int i = 0; i < spellCooldowns.length; i++) {
			if (spellCooldowns[i] > 0) {
				spellCooldowns[i]--;
			}
		}
	}
}
