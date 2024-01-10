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
	private battleSystem battle;

	public PlayerSpell(Player player, Monsters monsters, battleSystem battle) {
		this.player = player;
		this.monsters = monsters;
		this.battle = battle;
	}

	public boolean startSpell() {
		// Load spells based on player archetype upon initialization
		loadSpells(player.getArchetypeName());
		return displaySpellChoices();
	}

	private void loadSpells(String playerArchetype) {
		try {
			String currentWorkingDir = System.getProperty("user.dir");
			String relativePath = currentWorkingDir + "/Y1S1/FOP_Valley2/resources/spells.txt";
			Scanner reader = new Scanner(new FileInputStream(relativePath));

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
		boolean gotSpell = false;

		System.out.println("<< Spell Choice >>");
		for (int i = 0; i < 3; i++) {
			if (spellName[i] != null && level >= levelRequired[i] && cooldownCheck(i + 1) && manaPoints >= getManaCost(i + 1)) {
				// Here green color from line 68 to 70
				System.out.println();
				System.out.println("[" + (i + 1) + "] :" + spellName[i]);
				System.out.println("Spell Description: " + spellDescription[i]);
				System.out.println("Cooldown: " + cdTurn[i]);
				System.out.println();

				gotSpell = true;
			} else if (level < levelRequired[i]) {
				// Here red color
				System.out.println("[" + (i + 1) + "] unlock at level " + levelRequired[i] + ".");
			} else if (!cooldownCheck(i + 1)) {
				// Here blue color
				System.out.println("[" + (i + 1) + "] is on cooldown. \nAnother " + spellCooldowns[i] + " turn(s) left.");
			} else if (manaPoints < getManaCost(i + 1)) {
				// Here yellow color
				System.out.println("You do not have enough mana to use spell [A" + (i + 1) + "] .");
			} else {
				System.out.println("No spells available for this archetype.");
			}
		}

		if (gotSpell) {
			int spellIndex;
			while (true) {
				System.out.println("Enter the spell you want to use: \n");
				spellIndex = input.nextInt();
				if (spellIndex >= 1 && spellIndex <= 3) {
					return castSpell(spellIndex, monsters);
				} else {
					System.out.println("Invalid spell choice.");
				}
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
				// int damage = calculateSpellDamage(spellIndex, monster);
				switch (spellIndex) {
					case 1:
						firstSpell();
						break;
					case 2:
						secondSpell();
						break;
					case 3:
						thirdSpell();
						break;
					default:
						break;
				}
				System.out.println("You used spell " + spellName[index] + "!");
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

	// Spell:
	public String getFirstSpellName() {
		return spellName[0];
	}

	public String getSecondSpellName() {
		return spellName[1];
	}

	public String getThirdSpellName() {
		return spellName[2];
	}

	public void firstSpell() {
		switch (spellName[0]) {
			case "Earthquake":
				monsters.causeRealDamage();
				break;

			case "Fireball":
				monsters.flameEffect();
				break;

			case "Stealing":
				monsters.stealPoints();
				player.stealPoints();
				break;

			case "Justice":
				battle.setSkipRound(3);
				break;

			case "Multishot":
				monsters.multiShot();
				break;

			default:
				System.out.println("Error 1");
		}
	}

	public void secondSpell() {
		switch (spellName[1]) {
			case "Roaring":
				player.roaringEffect();
				break;

			case "Absolute_Treatment":
				player.causeAbsoluteTreatmen();
				break;

			case "Gamblers":
				monsters.joinGame();
				break;

			case "Invincibility":
				player.InvincibilityEffect();
				break;

			case "Treatment":
				player.causeAbsoluteTreatmen();
				break;

			default:
				break;
		}
	}

	public void thirdSpell() {
		switch (spellName[2]) {
			case "Hemophile":
				player.HemophileEffect();
				break;
			case "Frozen":
				battle.setSkipRound(5);
				System.out.println("Monster is frozen, unable to act[4 round]");
				break;
			case "Control":
				monsters.controlDamage();
				break;

			case "God":
				monsters.clearPoints();
				break;

			case "Full_blow":
				monsters.fullBlow();
				break;

			default:
				break;
		}
	}
}
