package Y1S1.FOP_Valley2.src;

import java.io.*;
import java.util.Scanner;

public class TextInfo {

	public void get_Cover() {
		String currentWorkingDir = System.getProperty("user.dir");
		String relativePath = currentWorkingDir + "/Y1S1/FOP_Valley2/resources/ASCII_art/Cover.txt";
		// Read ASCII art from file
		String asciiArt = readFromFile(relativePath);

		// ANSI escape code for red color
		String redColorCode = "\u001B[31m";

		// ANSI escape code to reset color
		String resetColorCode = "\u001B[0m";

		// Combine color code, ASCII art, and reset code
		String redAsciiArt = redColorCode + asciiArt + resetColorCode;

		// Print the red ASCII art
		System.out.println(redAsciiArt);
	}

	public void get_Select_Archetypes() {

		String currentWorkingDir = System.getProperty("user.dir");
		String relativePath = currentWorkingDir + "/Y1S1/FOP_Valley2/resources/Archetype/Select_Archetypes.txt";

		String selectArchetypes = readFromFile(relativePath);
		System.out.println(selectArchetypes);
	}

	public void get_Archetypes_Info(int num) {
		String currentWorkingDir = System.getProperty("user.dir");
		String[] archetype = { "Archer_Info.txt", "Paladin_Info.txt", "Warrior_Info.txt", "Mage_Info.txt",
				"Rogue_Info.txt" };
		String relativePath = currentWorkingDir + "/Y1S1/FOP_Valley2/resources/Archetype/" + archetype[num - 1];

		String archetypesInfo = readFromFile(relativePath);
		System.out.println(archetypesInfo);
	}

	private static String readFromFile(String fileName) {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	// Storyline
	public void startPrintStory() {
		try {
			String s = "\033[0;30mThe year is \033[0;97m\u001B[1m2024 AC\033[0m\033[0;30m(After \033[0;31mCatacylsm\033[0;30m).\r\n"
					+ //
					"Ever since \033[0;31mthe Catacylsm\033[0;30m tore the planet asunder, \033[0;32mthe Earth\033[0;30m has revealed what hid underground: countless mazes and labyrinths, all interconnected with each other with no end in sight.\r\n"
					+ //
					"These mazes are filled with many different kinds of monsters, all with a taste for humans.\r\n" + //
					"They had never once left the mazes, but \033[0;31mthe Cataclysm\033[0;30m gave way to multiple holes that lead to the civilization on the surface.\r\n"
					+ //
					"One by one, these monsters came out from their dark undergound hallways and waged war on humans.\r\n"
					+ //
					"But monsters weren't the only things to come out of the mazes.\r\n" + //
					"Magic flowed out from these mystic paths, giving birth to a different breed of humans: \033[1;93m\033[0;100mthe Archetypes.\033[0m\033[0;30m\r\n"
					+ //
					"So strong were \033[1;97mthe Archetypes\033[0;30m that not only did they defend themselves from the monsters, they were able to venture within the mysterious mazes and create underground human colonies. \r\n"
					+ //
					"However, 1 part of the maze has never been touched before: \033[1;91m\u001B[4mFOP Valley\033[0m\033[0;30m, the centre of \033[0;32mthe Earth\033[0;30m.\r\n"
					+ //
					"Strong and primal magic flowed from within, as if \033[0;31mthe Earth\033[0;30m was ready to give its secrets to anyone who dared to come, yet every party that ventured to \033[1;91m\u001B[4mFOP Valley\033[0m\033[0;30m would never be seen or heard from again.\r\n"
					+ //
					"In the year \033[0;97m\u001B[1m2019 AC\033[0m\033[0;30m, the leader of \033[1;97mthe Archetypes\033[0;30m gathered every single \033[1;97mArchetype\033[0;30m human in the world in a final quest to explore the centre of \033[0;32mthe Earth\033[0;30m.\r\n"
					+ //
					"They would all disappear without a trace. \r\n" + //
					"But one day, you wake up in the middle of these dark halls.\r\n" + //
					"You don't remember who you are or how you got there, but you know that you are within the fabled halls of the \033[1;91m\u001B[4mFOP Valley\033[0m\033[0;30m.\r\n"
					+ //
					"You can feel that whatever caused \033[0;31mthe Cataclysm\033[0;30m is offering you an opportunity, not just to escape, but to know the truth.\r\n"
					+ //
					"The question is, are you ready?";
			char[] c = s.toCharArray();
			for (int i = 0; i < c.length; i++) {
				System.out.print(c[i]);
				Thread.sleep(20);
			}
		} catch (Exception a) {
			a.printStackTrace();
		}
	}

	public void printLoseStory(String name) {
		try {
			String s = "The last thing you see is the gaping maw of the monster in front of you as it slowly approaches, ready to devour you whole.\r\n"
					+ //
					"As your consciousness fades away, you think of how far you've gone, and how much further you could go into the halls of \u001B[4m\033[0;31mFOP Valley\033[0m\033[0;30m. \r\n"
					+ //
					"However, before your soul could leave your body, a voice spoke to you. \r\n" + //
					"\"\033[1;34m" + name
					+ "\033[0m\033[0;30m, your time has not come yet. \033[0;31mThe Earth\033[0;30m has chosen \033[1;37m\u001B[4myou\033[0m\033[0;30m, and it is unwilling to let you leave so easily. Arise once more, and conquer \u001B[4m\033[0;31mFOP Valley\033[0m\033[0;30m once and for all.\"\n";
			char[] c = s.toCharArray();
			for (int i = 0; i < c.length; i++) {
				System.out.print(c[i]);
				Thread.sleep(30);
			}
		} catch (Exception a) {
			a.printStackTrace();
		}
	}

	public void printWinStory() {
		try {
			String s1 = "\033[0;30mYou walk towards the exit, hoping to discover what lies behind the halls of \033[0;31mFOP Valley\033[0;30m.\r\n"
					+ //
					"As soon as you step in, suddenly you feel as if your body is ejected through a vacuum, travelling at unimaginable speeds.\r\n"
					+ //
					"Then, moments later, you find yourself floating in what looks like the void.\r\n" + //
					"Darkness surrounds you, yet you feel a familiar sensation behind you; a sensation that feels like everything you have ever known.\r\n"
					+ //
					"You turn around, and then you see it.";
			char[] c1 = s1.toCharArray();
			for (int i = 0; i < c1.length; i++) {
				System.out.print(c1[i]);
				Thread.sleep(30);
			}
			String earth = "\n              _-\033[0;32mo#&&*''''?d:>b\033[0m\\_\r\n" +
					"        _o\033[0;32m/\"`''\033[44m  \033[0m\033[0;32m'',, dMF9MMMMM\033[0mHo\r\n" +
					"     .m\033[0;32mo&#'\033[44m        \033[0m\033[0;32m`\"MbHMMMMMMMMMMMH\033[0mo.\r\n" +
					"   .o\"\" '\033[44m         \033[0m\033[0;32mvodM*$&&HMMMMMMMMMM\033[0m?.\r\n" +
					"  ,'\033[44m              \033[0m\033[0;32m$M&ood,~'`(&##MMMMM\033[0mMH\\r\n" +
					" /\033[44m               \033[0m\033[0;32m,MMMMMMM#b?#bobMMMMHMM\033[0mML\r\n" +
					"&\033[44m              \033[0m\033[0;32m?MMMMMMMMMMMMMMMMM7MMM$R*\033[0mHk\r\n" +
					"?$.\033[44m            \033[0m\033[0;32m:MMMMMMMMMMMMMMMMMMM/HMMM|`\033[0m*L\r\n" +
					"|\033[44m               \033[0m\033[0;32m|MMMMMMMMMMMMMMMMMMMMbMH'\033[44m   \033[0mT,\r\n" +
					"$H\033[0;32m#:\033[44m            \033[0m\033[0;32m`*MMMMMMMMMMMMMMMMMMMMb#}'\033[44m  \033[0m`?\r\n"
					+
					"]M\033[0;32mMH#\033[44m             \033[0m\033[0;32m\"\"*\"\"\"\"*#MMMMMMMMMMMMM'\033[44m    \033[0m-\r\n"
					+
					"MM\033[0;32mMMMb_\033[44m                   \033[0m\033[0;32m|MMMMMMMMMMMP'\033[44m     \033[0m:\r\n"
					+
					"HM\033[0;32mMMMMMMHo\033[44m                 \033[0m\033[0;32m`MMMMMMMMMT\033[44m       \033[0m.\r\n"
					+
					"?M\033[0;32mMMMMMMMP\033[44m                  \033[0m\033[0;32m9MMMMMMMM}\033[44m       \033[0m-\r\n"
					+
					"-?\033[0;32mMMMMMMM\033[44m                  \033[0m\033[0;32m|MMMMMMMMM?,d-\033[44m    \033[0m'\r\n"
					+
					":|\033[0;32mMMMMMM-\033[44m                 \033[0m\033[0;32m`MMMMMMMT .M|.\033[44m   \033[0m:\r\n"
					+
					".9\033[0;32mMMM[\033[44m                    \033[0m\033[0;32m&MMMMM*' `'\033[44m    \033[0m.\r\n" +
					" :9\033[0;32mMMk\033[44m                    \033[0m\033[0;32m`MMM#\"\033[44m        \033[0m-\r\n" +
					"   &M}\033[44m                     \033[0m\033[0;32m`\033[44m          \033[0m.-\r\n" +
					"    `&.\033[44m                             \033[0m.\r\n" +
					"      `~,\033[44m   .                     \033[0m./\r\n" +
					"          .\033[44m _                  \033[0m.-\r\n" +
					"            '`--._,dd###pp=\"\"\033[0m'\"\r\n";
			System.out.println(earth);
			Thread.sleep(2000);
			String s2 = "\033[0;30mEarth, in all its glory.\r\n" + //
					"But something doesn't feel right.\r\n" + //
					"\033[0;31mThis Earth\033[0;30m seems more than a planet. \r\n" + //
					"It seemed like it was a living being itself.\r\n" + //
					"Or rather, it seemed like it was containing a living being, as if that being was its power source.\r\n"
					+ //
					"\"\033[0;31mYou've realized, haven't you? That ironically, life on Earth needs life itself to create more life.\033[0;30m\"\r\n"
					+ //
					"You look around, trying to look for the voice, but there was nothing.\r\n" + //
					"As if that voice was inside your head. \r\n" + //
					"\"\033[0;31mI was created at the same time as the Earth. Ever since my creation, I have been trying to break free from Earth's centre where I was sealed.\033[0;30m\" \r\n"
					+ //
					"\"\033[0;31mBut I realized that if I escaped, life on Earth would perish. This planet needed a living being at its core to replicate life.\033[0;30m\"\r\n"
					+ //
					"\"\033[0;31mI was the reason there was life on Earth. So, I sought to look for a replacement.\033[0;30m\"\r\n"
					+ //
					"\"\033[0;31mI created pathways for the humans to come to me and take my power. These would become the mazes.\033[0;30m\"\r\n"
					+ //
					"\"\033[0;31mThen, slowly but surely, I pushed out magic, which is concentrated life energy, from the underground to the surface which caused the Cataclysm.\033[0;30m\"\r\n"
					+ //
					"\"\033[0;31mEventually, the Archetypes found me. But none have been strong enough to control my power, and they withered away.\033[0;30m\"\r\n"
					+ //
					"\"\033[0;31mAre you strong enough? Come and prove your strength to me.\033[0;30m\"\r\n" + //
					"All of a sudden, you can feel a near-infinite amount of magic flow into your body.\r\n" + //
					"Your mind struggled to prevent itself from breaking, and you could feel your body about to explode.\r\n"
					+ //
					"After what seemes like an endless amount of time, the pain subsided.\r\n" + //
					"You opened your eyes, and you could see every living being on you.\r\n" + //
					"You could feel every gust of wind, every drop of water, every grain of sand.\r\n" + //
					"Everything.\r\n" + //
					"\033[0mFor you had now become the Earth itself.\r\n" + //
					"The End.";
			char[] c2 = s2.toCharArray();
			for (int i = 0; i < c2.length; i++) {
				System.out.print(c2[i]);
				Thread.sleep(30);
			}
		} catch (Exception a) {
			a.printStackTrace();
		}
	}

	public void printSavegame() {
		String s1 = "\nDo you wish to save your journey and the fate of Earth?"
				+ "\nType \'Y\' to save your progress or \'N\' to exit without saving :";
		char[] c1 = s1.toCharArray();
		try {
			for (int i = 0; i < c1.length; i++) {
				System.out.print(c1[i]);
				Thread.sleep(30);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void clearScreen() {
		// Check the operating system
		String os = System.getProperty("os.name").toLowerCase();

		try {
			if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
				// For Linux/Unix/Mac
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			} else if (os.contains("win")) {
				// For Windows
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
