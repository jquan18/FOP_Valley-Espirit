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
	private boolean keepStay;


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

	public void playerSpell(Monsters monsters) {
		this.playerSpell = new PlayerSpell(this, monsters);
		this.playerSpell.startSpell();
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

	public boolean player_keepStay() {
		keepStay = true;
		return keepStay;
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
		if (healthPoints < maxhealthPoints) {
			healthPoints += 5;
		}
	}
	public void levelupManaPoints() {
		if (manaPoints < maxmanaPoints) {
			manaPoints += 5;
		}
	}
	public void levelupPhysicalDefense() {
		physicalDefense += 5;
	}
	public void levelupMagicalDefense() {
		magicalDefense += 5;
	}
	public void levelupPhysicalAttack() {
		physicalAttack += 5;
	}
	public void levelupMagicalAttack() {
		magicalAttack += 5;
	}
	public void decreaseManaPoints(int manaPoints) {
		this.manaPoints -= manaPoints;
	}

	public void replenishMana() {
		if (manaPoints == maxmanaPoints) {
			;
		}
		else if (manaPoints < maxmanaPoints){
			int replenish = 5;
			if ((manaPoints + replenish)>=maxmanaPoints) {
				manaPoints = maxmanaPoints;
			}
			else if ((manaPoints + replenish)<maxmanaPoints) {
				manaPoints += replenish;
			}
		}
	}
}
