package Y1S1.FOP_Valley2.src;

import java.util.*;
//Should extends to Player

class LevelSystem {
	int[] RequiredEXP = new int[35];
	int currentEXP, previousLevel, currentLevel;
	int firstSpell = 1, secondSpell = 1; // Chance of player to learn spell
	int chance_upgrade = 4;
	int new_point = 0;
	private Player player;

	public LevelSystem(Player player) {
		RequiredEXP();
		this.player = player;
		currentEXP = player.getExperiencePoint();
		currentLevel = player.getLevel();
	}

	public void RequiredEXP() {
		for (int i = 0, j = 5; i < RequiredEXP.length; i++) {
			if (j >= 600) { // 20 EXP point in 31-35 level
				RequiredEXP[i] = j;
				j += 40;
			} else if (j >= 300) {// 15 EXP ponit in 21-30 level
				RequiredEXP[i] = j;
				j += 30;
			} else if (j >= 100) { // 10 EXP point in 11-20 level
				RequiredEXP[i] = j;
				j += 20;
			} else { // only need 5 EXP point to upgrade level in 1-10 level
				RequiredEXP[i] = j;
				j += 10;
			}
		}
	}

	public void addEXP(int EXP) {
		System.out.println("You earn " + EXP + " EXP points");
		currentEXP += EXP;
		player.addExperiencePoint(EXP);
		LevelUp();
	}

	public void LevelUp() {
		Scanner sc = new Scanner(System.in);
		int i = 0;
		previousLevel = currentLevel;
		while (i < RequiredEXP.length && currentEXP > RequiredEXP[i]) {
			currentLevel = i + 1;
			i++;
		}
		player.setLevel(i + 1);
		System.out.println("You are Level " + (i + 1) + " now");
		Extra_Points();
		System.out.println("\n(Press any key to continue...)");
		sc.nextLine();
		// Unlock_Spell();
	}

	public void Unlock_Spell() {

		if (currentEXP >= 25 && firstSpell == 1) { // Level 5
			// Learn First Spell
			System.out.println("Ancient powers resurface within you.");
			firstSpell--;
			// Put the Player Spell
		} else if (currentEXP >= 50 && secondSpell == 1) { // Level 10
			// Learn Second Spell
			System.out.println("Ancient powers resurface within you.");
			secondSpell--;
			// Put the Player Spell
		} else if ((currentEXP >= 100 && chance_upgrade == 4) || (currentEXP >= 150 && chance_upgrade == 3)
				|| (currentEXP >= 225 && chance_upgrade == 2) || (currentEXP >= 300 && chance_upgrade == 1)) {// Level
																												// 15 20
																												// 25 30
			System.out.println("Bag: Spell Enhancement Props x1");
			System.out.println("It's time to strengthen your spells.");
			chance_upgrade--;
			// Allow player to choose the spell need want to upgrade, (CD decrease and
			// damage increase)
		} else if (currentEXP == 400) {
			System.out.println("Powerful magic throughout your body.");
			// Upgrade two of the spells
		}
	}

	public void Extra_Points() { // Six basic attributes point increase randomly when level up
		Random rand = new Random();
		String enchance = "";
		new_point = currentLevel - previousLevel;
		for (; new_point > 0; new_point--) {
			String player_archetype= player.getArchetypeName();
			switch (player_archetype) {
				case "archer":
					String[] attributes_archer = { "healthPoints",  "physicalAttack"};
					enchance = attributes_archer[rand.nextInt(2)];
					break;

				case "paladin":
					String[] attributes_paladin ={ "healthPoints", "manaPoints", "physicalDefense", "magicalDefense", "physicalAttack"};
					enchance = attributes_paladin[rand.nextInt(5)];
					break;

				case "warrior" :
					String[] attributes_warrior = {"healthPoints", "physicalDefense", "magicalDefense","physicalAttack"};
					enchance = attributes_warrior[rand.nextInt(4)];
					break;

				case "mage" :
					String[] attributes_mage = {"manaPoints","magicalDefense","magicalAttack"};
					enchance = attributes_mage[rand.nextInt(3)];
					break;

				case "rogue" :
					String[] attributes_rogue = {"healthPoints",  "physicalAttack", "physicalDefense"};
					enchance = attributes_rogue[rand.nextInt(3)];
					break;
				default:
					break;

			}
			switch (enchance) {
				case "healthPoints":
					System.out.println("Your Health Point increased!");
					player.levelupHealthPoints(); // Increase healthPoints
					break;
				case "manaPoints":
					System.out.println("Your Mana Points increased!");
					player.levelupManaPoints(); // Increase manaPoints
					break;
				case "physicalDefense":
					System.out.println("Your Physical Defense become stronger!");
					player.levelupPhysicalDefense(); // Increase physicalDefense
					break;
				case "magicalDefense":
					System.out.println("Your Magical Defense become stronger!");
					player.levelupMagicalDefense(); // Increase magicalDefense
					break;
				case "physicalAttack":
					System.out.println("Your Physical Attack become stronger!");
					player.levelupPhysicalAttack(); // Increase physicalAttack
					break;
				case "magicalAttack":
					System.out.println("Your Magical Attack become stronger!");
					player.levelupMagicalAttack(); // Increase magicalAttack
					break;
				default:
					break;
			}
		}

	}

	// Use for test Level System
	public void getCurrentEXP() {
		System.out.println("Current EXP: " + currentEXP);
	}

	public void setCurrentEXP(int EXP) {
		this.currentEXP = EXP;
	}
	public String getCurrentLevel() {
		int i = 0;
		previousLevel = currentLevel;
		while (currentEXP > RequiredEXP[i]) {
			currentLevel = i + 1;
			i++;
		}
		return "Level " + (i+1);
	}
}
