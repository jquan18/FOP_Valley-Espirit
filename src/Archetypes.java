package Y1S1.FOP_Valley2.src;

public class Archetypes {
	int healthPoints, manaPoints;
	int physicalDefense, magicalDefense;
	int physicalAttack, magicalAttack;

	public Archetypes(int healthPoints, int manaPoints, int physicalDefense, int magicalDefense, int physicalAttack,
			int magicalAttack) {
		this.healthPoints = healthPoints;
		this.manaPoints = manaPoints;
		this.physicalDefense = physicalDefense;
		this.magicalDefense = magicalDefense;
		this.physicalAttack = physicalAttack;
		this.magicalAttack = magicalAttack;
	}
}

class Archer extends Archetypes {
	public Archer() {
		super(15, 15, 12, 5, 18, 5);
	}

}

class Paladin extends Archetypes {
	public Paladin() {
		super(20, 5, 15, 10, 15, 5);
	}
}

class Warrior extends Archetypes {
	public Warrior() {
		super(15, 5, 10, 10, 20, 10);
	}
}

class Mage extends Archetypes {
	public Mage() {
		super(10, 20, 5, 10, 5, 20);
	}
}

class Rogue extends Archetypes {
	public Rogue() {
		super(10, 10, 13, 13, 22, 2);
	}
}
