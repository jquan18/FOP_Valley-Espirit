package Y1S1.FOP_Valley2.src;

/*
This class is used to save and load game progress for a player.

The class has the following attributes:
level = player level
healthPoints = player health points
manaPoints = player mana points
currentLocation = player current location

player_name is unique use it as primary key
promptRegisterLogin() = registerUser() or loginUser()
registerUser() = register a new user into table players and then login the user
loginUser() = login an existing user
savePlayerProgress() = save logged in player progress
loadPlayerProgress() = load logged in player progress
closeConnection() = close connection to MySQL database
*/

import java.sql.*;
import java.util.Scanner;

public class SaveGame {
	protected int level;
	protected int healthPoints;
	protected int manaPoints;
	protected int currentLocationA;
	protected int currentLocationB;

	private Connection connection;
	private boolean isLoggedIn;
	private String loggedInPlayerName;
	private Scanner sc = new Scanner(System.in);

	// Constructor establish connection to MySQL database
	public SaveGame() {
		isLoggedIn = false;
		loggedInPlayerName = null;
		connectToDatabase();
	}

	// Connect to MySQL database
	private void connectToDatabase() {
		try {
			// hosted on localhost at port 3306, username, password
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/savegamedb", "root", "P@ssword.123");
		} catch (SQLException e) {
			System.out.println("Error: Unable to connect to MySQL database.");
			e.printStackTrace();
		}
	}

	// Prompt user to register or login
	public void promptRegisterLogin() {

		System.out.println("Please enter your choice: " +
				"\n1. Register" +
				"\n2. Login" +
				"\n3. Exit" +
				"\nEnter your choice: ");

		int choice = sc.nextInt();

		switch (choice) {
			case 1: // Register
				registerUser();
				break;
			case 2: // Login
				loginUser();
				break;
			default: // Exit
				break;
		}
	}

	// Method overloading to register a new user
	public void registerUser() {
		while (true) {
			System.out.println("Please enter your username: ");
			String username = sc.next();
			System.out.println("Please enter your password: ");
			String password = sc.next();

			if (registerUser(username, password)) {
				System.out.println("User registered successfully.");
				break;
			} else {
				System.out.println("Do you want to try again? (Y/N)");
				char choice = sc.next().charAt(0);
				if (choice == 'N') {
					break;
				}
			}
		}
	}

	// Register a new user into table players
	public boolean registerUser(String playerName, String password) {
		try {
			String query = "INSERT INTO players (player_name, password_hash) VALUES (?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, playerName);
			preparedStatement.setString(2, password);
			int rowsInserted = preparedStatement.executeUpdate();

			if (rowsInserted > 0) {
				loginUser(playerName, password); // After done registering, login the user
				return true; // User registered successfully
			}
		} catch (SQLIntegrityConstraintViolationException e) { // player_name is unique
			System.out.println("Error: Username already exists."); // Username already exists
		} catch (SQLException e) {
			System.out.println("Error: Unable to register user.");
			e.printStackTrace();
		}
		return false; // User registration failed
	}

	// Method overloading to login an existing user
	public void loginUser() {
		while (true) {
			System.out.println("Please enter your username: ");
			String username = sc.next();
			System.out.println("Please enter your password: ");
			String password = sc.next();

			if (loginUser(username, password)) {
				System.out.println("User logged in successfully.");
				break;
			} else {
				System.out.println("Do you want to try again? (Y/N)");
				char choice = sc.next().charAt(0);
				if (choice == 'N') {
					break;
				}
			}
		}
	}

	// Login an existing user
	public boolean loginUser(String playerName, String password) {
		try {
			String query = "SELECT * FROM players WHERE player_name = ? AND password_hash = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, playerName);
			preparedStatement.setString(2, password);

			ResultSet resultSet = preparedStatement.executeQuery();

			// the resultSet.next() returns row returned based on the query
			// if got one row, means the user exists and credentials are correct
			if (resultSet.next()) {
				isLoggedIn = true;
				loggedInPlayerName = playerName;
				return true;
			} else {
				System.out.println("Error: Invalid username or password.");
			}
		} catch (SQLException e) {
			System.out.println("Error: Unable to login user.");
			e.printStackTrace();
		}

		return false;
	}

	// Save game progress for the logged-in player
	public boolean savePlayerProgress(int level, int healthPoints, int manaPoints, int currentLocationA,
			int currentLocationB) {
		if (isLoggedIn) { // Check if user is logged in
			try {
				String query = "REPLACE INTO player_progress (player_name, level, health_points, mana_points, current_location_a, current_location_b) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, loggedInPlayerName);
				preparedStatement.setInt(2, level);
				preparedStatement.setInt(3, healthPoints);
				preparedStatement.setInt(4, manaPoints);
				preparedStatement.setInt(5, currentLocationA);
				preparedStatement.setInt(6, currentLocationB);

				preparedStatement.executeUpdate();
				return true;
			} catch (SQLException e) {
				System.out.println("Error: Unable to save player progress.");
				e.printStackTrace();
			}
		} else { // User is not logged in
			System.out.println("Please log in to save player progress.");
		}
		return false;
	}

	// Method load game progress for a player
	public boolean loadPlayerProgress() {
		if (isLoggedIn) {
			try {
				String query = "SELECT * FROM player_progress WHERE player_name = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, loggedInPlayerName);

				ResultSet resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					// Retrieve player progress from the result set
					level = resultSet.getInt("level");
					healthPoints = resultSet.getInt("health_points");
					manaPoints = resultSet.getInt("mana_points");
					currentLocationA = resultSet.getInt("current_location_a");
					currentLocationB = resultSet.getInt("current_location_b");
					return true;
				} else {
					System.out.println("Error: No progress found for the player.");
				}
			} catch (SQLException e) {
				System.out.println("Error: Unable to load player progress.");
				e.printStackTrace();
			}
		} else {
			System.out.println("Please log in to load player progress.");
		}
		return false;
	}

	// Reset player progress
	public void resetPlayerProgress() {
		if (isLoggedIn) {
			try {
				String query = "REPLACE INTO player_progress (player_name, level, health_points, mana_points, current_location_a, current_location_b) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, loggedInPlayerName);
				preparedStatement.setInt(2, 1);
				preparedStatement.setInt(3, 100);
				preparedStatement.setInt(4, 100);
				preparedStatement.setInt(5, 1);
				preparedStatement.setInt(6, 20);

				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.println("Error: Unable to reset player progress.");
				e.printStackTrace();
			}
		} else {
			System.out.println("Please log in to reset player progress.");
		}
	}

	// Close the connection when done
	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("Error: Unable to close connection.");
			e.printStackTrace();
		}
	}

	// Accessors
	public String getName() {
		return loggedInPlayerName;
	}

	public int getLevel() {
		return level;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public int getManaPoints() {
		return manaPoints;
	}

	public int getcurrentLocationA() {
		return currentLocationA;
	}

	public int getcurrentLocationB() {
		return currentLocationB;
	}
}
