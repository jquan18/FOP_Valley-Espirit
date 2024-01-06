package Y1S1.FOP_Valley2.src;
public class SaveGameTest {
    public static void main(String[] args) {
        // Create an instance of SaveGame
        SaveGame saveGame = new SaveGame();

        // Prompt user to register or login
        saveGame.promptRegisterLogin();

        // If logged in, proceed to save and load progress
        if (saveGame.getName() != null) {
            // Saving player progress
            saveGame.savePlayerProgress("Warrior", 5, 1000, 50, 100, 80, 10, 20);

            // Loading player progress
            saveGame.loadPlayerProgress();

            // Display player progress
            System.out.println("\nPlayer Progress:");
            System.out.println("Name: " + saveGame.getName());
            System.out.println("Archetype: " + saveGame.getArchetypeName());
            System.out.println("Level: " + saveGame.getLevel());
            System.out.println("Experience: " + saveGame.getExperiencePoints());
            System.out.println("Defense: " + saveGame.getDefense());
            System.out.println("Health Points: " + saveGame.getHealthPoints());
            System.out.println("Mana Points: " + saveGame.getManaPoints());
            System.out.println("Location A: " + saveGame.getcurrentLocationA());
            System.out.println("Location B: " + saveGame.getcurrentLocationB());
        }

        // Close the connection
        saveGame.closeConnection();
    }
}
