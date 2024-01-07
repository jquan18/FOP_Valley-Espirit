package Y1S1.FOP_Valley2.src;

import java.util.*;
import java.io.*;

class Player {

	private String loggedInPlayerName;
	private int healthPoints;
	private int maxhealthPoints;
	private int manaPoints;
	private int maxmanaPoints;
	private int physicalDefense;
	private int magicalDefense;
	private int physicalAttack;
	private int magicalAttack;
	private boolean defending;
	private int experiencePoints;
	private int level;
	private Archetypes archetype;
	private boolean keepStay = true;


	private SaveGame saveGame;
	private PlayerSpell playerSpell;

	// Constructor for creating a new player
	public Player(String playerName, String chosenArchetype) {
		loggedInPlayerName = playerName;
		archetype = chooseArchetype(chosenArchetype);
		maxhealthPoints = (archetype.healthPoints * 10);
		healthPoints = maxhealthPoints;
		maxmanaPoints = (archetype.manaPoints * 10);
		manaPoints = maxmanaPoints;
		physicalAttack = archetype.physicalAttack;
		magicalAttack = archetype.magicalAttack;
		physicalDefense = archetype.physicalDefense;
		magicalDefense = archetype.magicalDefense;
		defending = false;
		experiencePoints = 0;
		level = 1;
		displayPlayerAttributes();
	}

	public Player(SaveGame saveGame) {
		// Commented it out so can run without mysql
		saveGame.loadPlayerProgress(); // Load the saved data
		loggedInPlayerName = saveGame.getName();
		archetype = chooseArchetype(saveGame.getArchetypeName());
		maxhealthPoints = (archetype.healthPoints * 10);
		healthPoints = saveGame.getHealthPoints();
		maxmanaPoints = (archetype.manaPoints * 10);
		manaPoints = saveGame.getManaPoints();
		physicalAttack = archetype.physicalAttack;
		magicalAttack = archetype.magicalAttack;
		physicalDefense = archetype.physicalDefense;
		magicalDefense = archetype.magicalDefense;
		defending = false;
		experiencePoints = saveGame.getExperiencePoints();
		level = saveGame.getLevel();
		displayPlayerAttributes();
	}

	// Method to set the archetype based on the archetype name
	private Archetypes chooseArchetype(String archetypeName) {
		switch (archetypeName.toLowerCase()) {
			case "archer":
				return new Archer();
			case "paladin":
				return new Paladin();
			case "warrior":
				return new Warrior();
			case "mage":
				return new Mage();
			case "rogue":
				return new Rogue();
			default:
				System.out.println("Invalid archetype name!");
				return new Archer(); // Setting Archer as default
		}
	}



	public void playerDamaged(int damage, String attack_type) {
		int defense_type;
		if (attack_type.equals("Physical")) {
			defense_type = physicalDefense;
		}
		else {
			defense_type = magicalDefense;
		}
		if (damage>0) {
			int realDamage = damage - ((defense_type/10) * new Random().nextInt(8));
			if (realDamage>0) {
				healthPoints = Math.max(0, healthPoints - realDamage);
				System.out.println("You were attacked by Monster for " + realDamage + " damage");
			}
			else {
				System.out.println("Perfect defence. You didn't take any damage");
			}
		}
		else {
			System.out.println("Perfect defence. You didn't take any damage");
		}
	}

	public int playerAttack(Monsters monsters) {
		String attack_type;
		int attack_value;
		if (this.physicalAttack>this.magicalAttack)  {
			attack_value = this.physicalAttack;
			attack_type = "Physical";
		}
		else  {
			attack_value = this.magicalAttack;
			attack_type = "Magical";
		}
		int damage = new Random().nextInt(11) * (attack_value/10);
		monsters.monstersDamaged(damage, attack_type);
		return damage;
	}

	public void playerDefending() {
		defending = true;
	}
	public void playerPutDownDefend() {
		defending = false;
	}
	public boolean playerDefense() {
		return defending;
	}

	public void playerHeal(int amount) {
		int current_mana = getmanaPoints();
		if ((current_mana - 10)>=0) {
			decreaseManaPoints(current_mana - (current_mana-10));
			this.healthPoints += amount;
			if (this.healthPoints > maxhealthPoints) {
				this.healthPoints = maxhealthPoints;
			}
		}
		else {
			System.out.println("You have not enought mana.");
		}
	}

	public void tryEscaped() {
		Random rand = new Random();
		int chance = rand.nextInt(2);
		if (chance == 0) {
			System.out.println("Escaped successfully");
			keepStay = false;
		}
		else {
			System.out.println("Failed to escape");
		}
	}

	public boolean getPlayerKeepStay() {
		// keepStay = true;
		return this.keepStay;
	}

	public void setPlayerKeepStay(boolean k) {
		this.keepStay = k;
	}

	public void displayPlayerStatus() {
		System.out.println(loggedInPlayerName);
		System.out.println("--HP:[" + getHpBar() + "](" + this.healthPoints + "/" + maxhealthPoints + ")");
		System.out.println("--MP:[" + getmanaPointsBar() + "](" + manaPoints + "/" + maxmanaPoints + ")");
	}

	public void displayPlayerAttributes() {
		System.out.println("\nhealtPoints : " + healthPoints);
		System.out.println("manaPoints : " + manaPoints);
		System.out.println("physicalAttack : " + getphysicalAttack());
		System.out.println("magicalAttack : " + getmagicalAttack());
		System.out.println("physicalDefense : " + getphysicalDefense());
		System.out.println("magicalDefense : " + getmagicalDefense());
		System.out.println("level : " + getLevel());
		System.out.println("experiencePoints : " + getExperiencePoint());
	}

	public String getHpBar() {
		int barLength = 10;
		int bar = (int) Math.round((double) this.healthPoints / maxhealthPoints * barLength);
		return "[" + "=".repeat(bar) + " ".repeat(barLength - bar);
	}

	public String getmanaPointsBar() {
		int barLength = 10;
		int bar = (int) Math.round((double) manaPoints / maxmanaPoints * barLength);
		return "[" + "=".repeat(bar) + " ".repeat(barLength - bar);
	}

	public String getloggedInPlayerName() {
		return loggedInPlayerName;
	}
	public int gethealthPoints() {
		return this.healthPoints;
	}
	public int getMaxhealthPoints() {
		return this.maxhealthPoints;
	}
	public int getmanaPoints() {
		return manaPoints;
	}
	public int getMaxmanaPoints() {
		return maxmanaPoints;
	}
	public int getphysicalAttack() {
		return physicalAttack;
	}
	public int getmagicalAttack() {
		return magicalAttack;
	}
	public int getphysicalDefense() {
		return physicalDefense;
	}
	public int getmagicalDefense() {
		return magicalDefense;
	}
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getArchetypeName() {
		return archetype.getClass().getSimpleName().toLowerCase();
	}

	public boolean isDefending() {
		return defending;
	}

	public boolean isAlive() {
		return healthPoints > 0;
	}

	public int getExperiencePoint() {
		return experiencePoints;
	}

	public void addExperiencePoint(int experiencePoints) {
		this.experiencePoints += experiencePoints;
	}

	public void levelupHealthPoints() {
		int tmp = maxhealthPoints - healthPoints;
		if ((healthPoints + 10)>=maxhealthPoints) {
			healthPoints += tmp;
		} else {
			healthPoints += 10;
		}
	}
	public void levelupManaPoints() {
		int tmp = maxmanaPoints - manaPoints;
		if ((manaPoints + 10)>=maxmanaPoints) {
			manaPoints = tmp;
		} else {
			manaPoints += 10;
		}
	}
	public void levelupPhysicalDefense() {
		physicalDefense += 2;
	}
	public void levelupMagicalDefense() {
		magicalDefense += 2;
	}
	public void levelupPhysicalAttack() {
		physicalAttack += 2;
	}
	public void levelupMagicalAttack() {
		magicalAttack += 2;
	}
	public void decreaseManaPoints(int manaPoints) {
		this.manaPoints -= manaPoints;
	}

	public void replenishMana() {
		if (manaPoints == maxmanaPoints) {
			return;
		}
		else if (manaPoints < maxmanaPoints){
			int replenish = 2;
			if ((manaPoints + replenish)>=maxmanaPoints) {
				manaPoints = maxmanaPoints;
			}
			else if ((manaPoints + replenish)<maxmanaPoints) {
				manaPoints += replenish;
			}
		}

	}

	/*Spell Effect */
	public void stealPoints() {
		if (manaPoints >= maxmanaPoints) {
			manaPoints = maxmanaPoints;
		} else {
			manaPoints += 20;
		}
		if (healthPoints >= maxhealthPoints) {
			manaPoints = maxhealthPoints;
		} else {
			healthPoints += 10;
		}
	}
	private int RoaringTime;
	private boolean Roaring=false;
	private boolean Invincibility=false;
	private int InvincibilityTime;
	private boolean Hemophile=false;
	private int HemophileTime;

	public void playerEffect() {
		if (Roaring == true) {
			if (RoaringTime == 2) {
				physicalAttack *= 2;
			}
			else if (RoaringTime == 1) {
				;
			}
			else if (RoaringTime == 0) {
				physicalAttack /= 2;
				roaringEnd();
				System.out.println("Roaring Effect End");
			}
			RoaringTime--;
		}

		if (Invincibility == true) {
			if (InvincibilityTime == 2) {
				physicalDefense *= 10;
				magicalDefense *= 10;
			}
			else if (InvincibilityTime == 1) {
				;
			}
			else if (InvincibilityTime == 0) {
				physicalDefense /= 10;
				magicalDefense /= 10;
				InvincibilityEnd();
				System.out.println("Invincibility Effect End");
			}
			InvincibilityTime--;
		}

		if (Hemophile == true) {
			if (HemophileTime == 5) {
				physicalAttack += 4;
				physicalDefense += 4;
				healthPoints += 15;
				if (healthPoints>maxhealthPoints)
				healthPoints = maxhealthPoints;
			}
			else if (HemophileTime == 4 || HemophileTime == 3 || HemophileTime == 2 || HemophileTime == 1) {
				physicalAttack += 4;
				physicalDefense += 4;
				healthPoints += 15;
				if (healthPoints>maxhealthPoints)
				healthPoints = maxhealthPoints;
			}
			else if (HemophileTime == 0) {
				physicalAttack -= 20;
				physicalDefense -= 20;
				System.out.println("Hemophile Effect End");
				}
				HemophileTime--;
			}
		}

		public void roaringEffect() {
			System.out.println("Player's physical damage increases dramatically[2 round] ");
			Roaring = true;
			RoaringTime = 2;
		}
		public void roaringEnd() {
			Roaring = false;
		}

		public void InvincibilityEffect() {
			System.out.println("You will not receive any damaged now[2 round]");
			Invincibility = true;
			InvincibilityTime = 2;
		}
		public void InvincibilityEnd() {
			Invincibility = false;
		}

		public void HemophileEffect() {
			System.out.println("Player's physicalAttack, physicalDefense, healthPoints increase continuously[5 round]");
			Hemophile = true;
			HemophileTime = 5;
		}
		public void HemophileEnd() {
			Hemophile = false;
		}


		public void causeAbsoluteTreatmen() {
			healthPoints = maxhealthPoints;
		}
}
