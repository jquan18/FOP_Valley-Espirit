package Y1S1.FOP_Valley2.src;

import java.io.*;
import java.util.Scanner;

public class Text_Info {

	public void get_Cover() {

		String currentWorkingDir = System.getProperty("user.dir");
		String relativePath = currentWorkingDir + "/Assignment/rsc/Cover.txt";

		try {
			Scanner reader = new Scanner(new FileInputStream(relativePath));
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void get_Select_Archetypes() {

		String currentWorkingDir = System.getProperty("user.dir");
		String relativePath = currentWorkingDir + "/Assignment/rsc/Select_Archetypes.txt";

		try {
			Scanner reader = new Scanner(new FileInputStream(relativePath));
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void get_Archetypes_Info(int num) {
		String currentWorkingDir = System.getProperty("user.dir");
		String[] archetype = { "Archer_Info.txt", "Paladin_Info.txt", "Warrior_Info.txt", "Mage_Info.txt",
				"Rogue_Info.txt" };
		String relativePath = currentWorkingDir + "/Assignment/rsc/" + archetype[num - 1];
		try {
			Scanner reader = new Scanner(new FileInputStream(relativePath));
			while (reader.hasNextLine()) {
				String line = reader.nextLine();
				System.out.println(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
