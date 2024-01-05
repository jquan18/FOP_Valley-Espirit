package Y1S1.FOP_Valley2.src;
import Y1S1.fopvalletyunzhe.src.Monsters;
import Y1S1.FOP_Valley2.src.Player;
import Y1S1.FOP_Valley2.src.Slime;
import Y1S1.FOP_Valley2.src.battleSystem;

public class testerBattleSystem {
	public static void main(String[] args) {
		Player player = new Player(false, "Alice", "paladin");
		Monsters monsters = new Slime();
		battleSystem battle = new battleSystem(player, monsters);



		battle.startBattle();
	}
}
