package Y1S1.FOP_Valley2.src;

import java.util.*;

public class Monsters {
	String name;
	int healthPoints, manaPoints;
	int maxhealthPoints, maxmanaPoints;
	int physicalDefense, magicalDefense;
	int physicalAttack, magicalAttack;

	private boolean flaming = false;
	private int flamingTime = 0;

	public Monsters (String name) {
		this.name = name;
	}
	public Monsters(String name, int totalPoints) {
		this.name = name;
		distributePoints(totalPoints);
	}

	public void distributePoints(int total_Points) {
		Random rand = new Random();

		healthPoints = rand.nextInt(total_Points / 6) + 5;
		manaPoints = rand.nextInt(total_Points / 6) + 5;
		physicalDefense = rand.nextInt(total_Points / 6) + 5;
		magicalDefense = rand.nextInt(total_Points / 6) + 5;
		physicalAttack = rand.nextInt(total_Points / 6) + 5;
		magicalAttack = rand.nextInt(total_Points / 6) + 5;

		int remain_Points = total_Points
				- (healthPoints + manaPoints + physicalAttack + magicalAttack + physicalDefense + magicalDefense);
		if (remain_Points > 0) {
			int PointsPerAttributes = remain_Points / 6;
			healthPoints += PointsPerAttributes;
			manaPoints += PointsPerAttributes;
			physicalDefense += PointsPerAttributes;
			magicalDefense += PointsPerAttributes;
			physicalAttack += PointsPerAttributes;
			magicalAttack += PointsPerAttributes;
		}
		maxhealthPoints = (healthPoints * 10);
		maxmanaPoints = (manaPoints * 10);
		healthPoints = maxhealthPoints;
		manaPoints = maxmanaPoints;

		// printAttributes();
	}

	public void printAttributes() {
		System.out.println(" Attributes:");
		System.out.println("Health Points: " + healthPoints);
		System.out.println("Mana Points: " + manaPoints);
		System.out.println("Physical Defense: " + physicalDefense);
		System.out.println("Magical Defense: " + magicalDefense);
		System.out.println("Physical Attack: " + physicalAttack);
		System.out.println("Magical Attack: " + magicalAttack);
		System.out.println();
	}

	public void monstersDamaged(int damage, String attack_type) {
		int defense_type;
		if (attack_type.equals("Physical")) {
			defense_type = physicalDefense;
		} else {
			defense_type = magicalDefense;
		}
		int realDamage = damage - ((defense_type / 20) * new Random().nextInt(8));
		healthPoints = Math.max(0, healthPoints - realDamage);
		System.out.println("You attacked the Monster and causing " + realDamage + " damage");
	}

	public void monstersAttack(Player player) {
		String attack_type;
		int attack_value;
		if (this.physicalAttack > this.magicalAttack) {
			attack_value = this.physicalAttack;
			attack_type = "Physical";
		} else {
			attack_value = this.magicalAttack;
			attack_type = "Magical";
		}
		int damage = new Random().nextInt(11) * (attack_value / 10);
		if (player.playerDefense() == true) {
			damage = (int) (damage / 2);
		}
		player.playerDamaged(damage, attack_type);

	}

	public void monstersSpell(Player player) {
		String attack_type;
		int attack_value;
		if (this.physicalAttack > this.magicalAttack) {
			attack_value = this.physicalAttack;
			attack_type = "Physical";
		} else {
			attack_value = this.magicalAttack;
			attack_type = "Magical";
		}
		int damage = (new Random().nextInt(11) * (attack_value / 10)) * 2;
		if (player.playerDefense() == true)
			damage = (int) (damage / 2);
		player.playerDamaged(damage, attack_type);
	}

	public void monster_replenishMana() {
		if (manaPoints == maxmanaPoints) {
			;
		}
		else if (manaPoints < maxmanaPoints) {
			int replenish = 3;
			if ((manaPoints + replenish)>=maxmanaPoints) {
				manaPoints = maxmanaPoints;
			}
			else if ((manaPoints + replenish)<maxmanaPoints) {
				manaPoints += replenish;
			}
		}
	}

	public void displayMonstersStatus() {
		System.out.println(name);
		System.out.println("--HP:[" + getHpBar() + "](" + healthPoints + "/" + maxhealthPoints + ")");
		System.out.println("--MP:[" + getMpBar() + "](" + manaPoints + "/" + maxmanaPoints + ")");
	}

	public String getHpBar() {
		int barLength = 10;
		int bar = (int) Math.round((double) healthPoints / maxhealthPoints * barLength);
		return "[" + "=".repeat(bar) + " ".repeat(barLength - bar) + "]";
	}

	public String getMpBar() {
		int barLength = 10;
		int bar = (int) Math.round((double) manaPoints / maxmanaPoints * barLength);
		return "[" + "=".repeat(bar) + " ".repeat(barLength - bar) + "]";
	}

	// Accessors
	public String getName() {
		return name;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public int getManaPoints() {
		return manaPoints;
	}

	public int getPhysicalDefense() {
		return physicalDefense;
	}

	public int getMagicalDefense() {
		return magicalDefense;
	}

	public int getPhysicalAttack() {
		return physicalAttack;
	}

	public int getMagicalAttack() {
		return magicalAttack;
	}

	public boolean isAlive() {
		return healthPoints > 0;
	}

	public void decreaseManaPoints() {
		manaPoints -= 15;
	}

	/*For Spell function */
	public void monstersEffect() {
		if (flaming == true && flamingTime>0) {//More than one round
			int damage = new Random().nextInt(7)+5;
			healthPoints -= damage;
			flamingTime--;
			System.out.println("Monster is taking fire damage, "+damage+" damage");
		}
	}
	public void fullBlow() {
		int damage = 50 + new Random().nextInt(50);
		healthPoints -= damage;
		System.out.println("Monsters is attacked by full bow arrow with "+ damage + " damage.");
	}

	public void causeRealDamage() {
		int realDamage = new Random().nextInt(25)+20;
		healthPoints -= realDamage;
		System.out.println("Monsters was plunged into the ground and take " + realDamage + " of damage!");
	}

	public void flameEffect() {
		flaming = true;
		flamingTime = 3;
	}

	public void stealPoints() {

		if (healthPoints<10) {
			healthPoints = 0;
		}
		else {
			healthPoints -= 10;
		}

		if (manaPoints < 20) {
			manaPoints = 0;
		}
		else {
			manaPoints -= 20;
		}
	}


	public void multiShot() {
		for (int i=0; i<5; i++) {
			int damage = new Random().nextInt(8);
			healthPoints -= damage;
			System.out.println("Monsters take " + damage + " damage from arrows");
		}
	}

	public void joinGame() {
		System.out.println("Starting gambling with monster.");
		int destiny = new Random().nextInt(5);
		int man = new Random().nextInt(5);
		if (destiny == man) {
			System.out.println("You win!");
			healthPoints = 0;
		}
		System.out.println("You lose!");
	}

	public void clearPoints() {
		healthPoints /= 2;
		manaPoints = 0;
	}

	public void controlDamage() {
		int damage = new Random().nextInt(20) + 10;
		int total = damage * (new Random().nextInt(3) + 1);
		this.healthPoints -= total;
		System.out.println("The monster attacked itself and causing " + total + " damage.");
	}
	/*End */

}

class Slime extends Monsters {
	public Slime() {
		super("Slime",60);
	}
}

class Spider extends Monsters {
	public Spider() {
		super("Spider", 70);
	}
}

class Skeleton_Warrior extends Monsters {
	public Skeleton_Warrior() {
		super("Skeleton Warrior", 80);
	}
}

class Giant extends Monsters {
	public Giant() {
		super("Giant", 130);
	}
}

class Witch extends Monsters {
	public Witch() {
		super("Witch", 140);
	}
}

class Gargoyle extends Monsters {
	public Gargoyle() {
		super("Gargoyle", 150);
	}
}

class Dragon extends Monsters {
	public Dragon() {
		super("Dragon", 500);
	}
}
