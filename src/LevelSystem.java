package Y1S1.FOP_Valley2.src;

import java.util.*;
//Should extends to Player

class LevelSystem {
	int[] RequiredEXP = new int[35];
	int currentEXP = 0, previousLevel = 0, currentLevel = 0;
	int firstSpell = 1, secondSpell = 1; // Chance of player to learn spell
	int chance_upgrade = 4;
	int new_point = 0;
	Player player;

	String[] attributes = { "healthPoints", "manaPoints", "physicalDefense", "magicalDefense", "physicalAttack",
			"magicalAttack" };

	public LevelSystem(Player player) {
		RequieredEXP();
		this.player = player;

	}

	public void RequieredEXP() {
		for (int i = 0, j = 5; i < RequiredEXP.length; i++) {
			if (j >= 300) { // 20 EXP point in 31-35 level
				RequiredEXP[i] = j;
				j += 20;
			} else if (j >= 150) {// 15 EXP ponit in 21-30 level
				RequiredEXP[i] = j;
				j += 15;
			} else if (j >= 50) { // 10 EXP point in 11-20 level
				RequiredEXP[i] = j;
				j += 10;
			} else { // only need 5 EXP point to upgrade level in 1-10 level
				RequiredEXP[i] = j;
				j += 5;
			}
		}
	}

	public void addEXP(int EXP) {
		System.out.println("You earn " + EXP + " EXP points");
		currentEXP += EXP;
		LevelUp();
	}

	public void LevelUp() {
		int i = 0;
		previousLevel = currentLevel;
		while (currentEXP > RequiredEXP[i]) {
			currentLevel = i + 1;
			i++;
		}
		System.out.println("You are Level " + (i + 1) + " now");
		Extra_Points();
		System.out.println("");
		Unlock_Spell();
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
			enchance = attributes[rand.nextInt(6)];
			switch (enchance) {
				case "healthPoints":
					System.out.println("Your healthPoint become stronger!");
					player.levelupHealthPoints(); // Increase healthPoints
					break;
				case "manaPoints":
					System.out.println("Your manaPoints become stronger!");
					player.levelupManaPoints(); // Increase manaPoints
					break;
				case "physicalDefense":
					System.out.println("Your physicalDefense become stronger!");
					player.levelupPhysicalDefense(); // Increase physicalDefense
					break;
				case "magicalDefense":
					System.out.println("Your magicalDefense become stronger!");
					player.levelupMagicalDefense(); // Increase magicalDefense
					break;
				case "physicalAttack":
					System.out.println("Your physicalAttack become stronger!");
					player.levelupPhysicalAttack(); // Increase physicalAttack
					break;
				case "magicalAttack":
					System.out.println("Your magicalAttack become stronger!");
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

}
