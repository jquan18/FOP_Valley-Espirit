package Y1S1.FOP_Valley2.src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MapDesignAndPlayerMovement {

	private int a, b;// P location, a row, b column
	private final String ANSI_RESET = "\u001B[0m";
	private final String ANSI_RED = "\u001B[31m";

	private Player player;
	private TextInfo textInfo = new TextInfo();

	public MapDesignAndPlayerMovement(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public void map_main(Player player) {
		this.player = player;

		char[][] map = readMatrix();

		if (map == null) {
			map = Map();
			saveMatrix(map);
		}
		Move(map);
		saveMatrix(map);
	}

	public char[][] Map() {
		Random random = new Random();

		char[][] map1 = new char[40][40];// Define map size

		for (int i = 0; i < map1.length; i++) {// Initialize all points of the map
			for (int j = 0; j < map1.length; j++) {
				map1[i][j] = ' ';
			}
		}
		// Random
		for (int i = 1; i < map1.length; i++) {// Set obstacle
			for (int j = random.nextInt(10); j < map1.length; j += random.nextInt(25)) {
				map1[i][j] = '#';
			}
		}

		for (int i = 1; i < map1.length; i += random.nextInt(5) + 2) {// Set small monster
			for (int j = random.nextInt(40); j < map1.length; j += random.nextInt(40)) {
				map1[i][j] = 'm';
			}
		}
		for (int i = 1; i < map1.length; i += random.nextInt(6) + 2) {// Set Stronger monster
			for (int j = random.nextInt(40); j < map1.length; j += random.nextInt(40) + 40) {
				map1[i][j] = 'M';
			}
		}
		for (int i = 1; i < 4; i++) {// Spawn point protection
			for (int j = 18; j < 22; j++) {
				map1[i][j] = ' ';
			}
		}

		for (int i = 0; i < map1.length; i++) {// Set map border
			for (int j = 0; j < map1.length; j++) {
				if (i == 0) {
					map1[i][j] = '#';
					if (j > 18 && j < 22) {
						map1[i][j] = '=';
					}
				}
				if (i == 38) {
					map1[i][j] = ' ';
					if (j == 20 || j == 21) {
						map1[i][j] = 'D';
					}
				}

				if (i == 39) {
					map1[i][j] = '#';
					if (j == 20 || j == 21) {
						map1[i][j] = '.';
					}
				}
			}
			map1[i][0] = '#';
			map1[i][map1.length - 1] = '#';
		}
		return map1;
	}

	public char[][] readMatrix() {
		try (ObjectInputStream in = new ObjectInputStream(
				new FileInputStream("/home/jquan18/UM/Y1S1/FOP_Valley2/resources/map/" + player.getloggedInPlayerName() + "_Map.dat"))) {
			return (char[][]) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Creating new map.....");
		}
		return null;
	}

	public void saveMatrix(char[][] matrix) {
		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream("/home/jquan18/UM/Y1S1/FOP_Valley2/resources/map/" + player.getloggedInPlayerName() + "_Map.dat"))) {
			out.writeObject(matrix);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void MapDisplay(char[][] map) {// Display map/print method
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (i == a && j == b) {
					System.out.print(ANSI_RED + 'P' + ANSI_RESET + ' ');
				} else {
					System.out.print(map[i][j] + " ");
				}
			}
			System.out.println();
		}
	}

	public void Move(char[][] map) {
		int n = 0, qui = 0;// n to store the number of movements
		boolean judge;
		String movement;
		Scanner scanner = new Scanner(System.in);

		System.out.println("================================= Initial Map =================================");
		MapDisplay(map);

		System.out.println("-------------------------------------------------------------------------------");
		System.out.println("  W\nA S D   to move the character\n" +
		"-------------------------------------------------------------------------------" +
		"\nW: Move Up One Square" +
		"\nA: Move Left One Square" +
		"\nS: Move Down One Square" +
		"\nD: Move Right One Square\n" +
		"-------------------------------------------------------------------------------" +
		"\nP is your current location");

		do {// Loop for receiving user movement input.
			n++;

			// Check if player is dead
			if (!player.isAlive()) {
				break;
			}

			do {// verifying the input validation


				System.out.print("-------------------------------------------------------------------------------\n" +
						"Enter the movement instruction(Enter quit to quit): ");
				movement = scanner.nextLine().toLowerCase();
				if (movement.equals("quit")) {
					System.out.println("Quit successful");
					qui = -1;
					break;
				}
				judge = false;

				for (int m = 0; m < movement.length(); m++) {
					char ch = movement.charAt(m);
					if ("wasd".indexOf(ch) == -1) {
						judge = true;
						System.out.println("There are wrong characters in your input. Accepted input character(W,A,S,D,quit)");
						break;
					}
				}
			} while (judge);

			if (qui == -1)
				break;

			System.out.println("-------------------------------------------------------------------------------");

			for (int k = 0; k < movement.length(); k++) {
				if (movement.charAt(k) == 'w') {
					if (map[a - 1][b] == '#') {
						System.out.println("\nYou met an obstacle at number " + (k + 1)
								+ " movement. Please take another route\n");
						break;
					}
					if (map[a - 1][b] == 'M') {
						System.out.println("Encountered a Big Monster at number " + (k + 1) + " movement.");
						callBigMonsters();
						a--;
						break;
					}
					if (map[a - 1][b] == 'm') {
						System.out.println("Encountered a Little Monster at number " + (k + 1) + " movement.");
						callLittleMonsters();
						a--;
						break;
					}
					if (map[a - 1][b] == 'D') {
						System.out.println("Encountered Boss Monster at number " + (k + 1) + " movement.");
						callBossMonster();
						a--;
						break;
					}
					map[a][b] = ' ';
					a--;
				}
				if (movement.charAt(k) == 'a') {
					if (map[a][b - 1] == '#') {
						System.out.println("\nYou met an obstacle at number " + (k + 1)
								+ " movement. Please take another route\n");
						break;
					}
					if (map[a][b - 1] == 'M') {
						System.out.println("Encountered a Big Monster at number " + (k + 1) + " movement.");
						callBigMonsters();
						b--;
						break;
					}
					if (map[a][b - 1] == 'm') {
						System.out.println("Encountered a Little Monster at number " + (k + 1) + " movement.");
						callLittleMonsters();
						b--;
						break;
					}
					if (map[a][b - 1] == 'D') {
						System.out.println("Encountered Boss Monster at number " + (k + 1) + " movement.");
						callBossMonster();
						a--;
						break;
					}
					map[a][b] = ' ';
					b--;
				}
				if (movement.charAt(k) == 's') {
					if (map[a + 1][b] == '#') {
						System.out.println("\nYou met an obstacle at number " + (k + 1)
								+ " movement. Please take another route\n");
						break;
					}
					if (map[a + 1][b] == 'M') {
						System.out.println("Encountered a Big Monster at number " + (k + 1) + " movement.");
						callBigMonsters();
						a++;
						break;
					}
					if (map[a + 1][b] == 'm') {
						System.out.println("Encountered a Little Monster at number " + (k + 1) + " movement.");
						callLittleMonsters();
						a++;
						break;
					}
					if (map[a + 1][b] == 'D') {
						System.out.println("Encountered Boss Monster at number " + (k + 1) + " movement.");
						callBossMonster();
						a--;
						break;
					}
					map[a][b] = ' ';
					a++;
				}
				if (movement.charAt(k) == 'd') {
					if (map[a][b + 1] == '#') {
						System.out.println("\nYou met an obstacle at number " + (k + 1)
								+ " movement. Please take another route\n");
						break;
					}
					if (map[a][b + 1] == 'M') {
						System.out.println("Encountered a Big Monster at number " + (k + 1) + " movement.");
						callBigMonsters();
						b++;
						break;
					}
					if (map[a][b + 1] == 'm') {
						System.out.println("Encountered a Little Monster at number " + (k + 1) + " movement.");
						callLittleMonsters();
						b++;
						break;
					}
					if (map[a][b + 1] == 'D') {
						System.out.println("Encountered Boss Monster at number " + (k + 1) + " movement.");
						callBossMonster();
						a--;
						break;
					}
					map[a][b] = ' ';
					b++;
				}
			}
			if (a == 39 && (b == 20 || b == 21)) {
				textInfo.printWinStory();
				break;
			}
			if (player.isAlive()) {
				System.out.println("Now, player is at row " + (a + 1) + " column " + (b + 1));
				System.out.println(
						"================================= Map after " + n + " movement =================================");
				MapDisplay(map);
			}
		} while (true);
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	private void callBossMonster() {
		Monsters monsters = new Dragon();
		battleSystem battleSystem = new battleSystem(player, monsters);
		battleSystem.startBattle();
	}

	private void callBigMonsters() {
		String[] m = { "Giant", "Witch", "Gargoyle" };
		Random rand = new Random();
		String chosenMonster = m[rand.nextInt(3)];
		Monsters monsters = createMonster(chosenMonster);
		battleSystem battleSystem = new battleSystem(player, monsters);
		battleSystem.startBattle();
	}

	private void callLittleMonsters() {
		String[] m = { "Slime", "Spider", "Skeleton Warrior" };
		Random rand = new Random();
		String chosenMonster = m[rand.nextInt(3)];
		Monsters monsters = createMonster(chosenMonster);
		battleSystem battleSystem = new battleSystem(player, monsters);
		battleSystem.startBattle();
	}

	private Monsters createMonster(String monsterName) {
		switch (monsterName) {
			case "Giant":
				return new Giant();
			case "Witch":
				return new Witch();
			case "Gargoyle":
				return new Gargoyle();
			case "Slime":
				return new Slime();
			case "Spider":
				return new Spider();
			case "Skeleton Warrior":
				return new Skeleton_Warrior();
			default:
				return null;
		}
	}
}
