package Y1S1.FOP_Valley2.src;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MapDesignAndPlayerMovement {

    public static void map_main() {
        char[][] map = Map();
        Move(map);
    }

    public static int a = 1, b = 20;//P location, a row, b column
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";


    public static char[][] Map() {
        Random random = new Random();

        char[][] map1 = new char[40][40];//Define map size

        for (int i = 0; i < map1.length; i++) {//Initialize all points of the map
            for (int j = 0; j < map1.length; j++) {
                map1[i][j] = ' ';
            }
        }
        //Random
        for (int i = 1; i < map1.length; i++) {//Set obstacle
            for (int j = random.nextInt(10); j < map1.length; j += random.nextInt(25)) {
                map1[i][j] = '#';
            }
        }


        for (int i = 1; i < map1.length; i+= random.nextInt(5)+2) {//Set small monster
            for (int j = random.nextInt(40); j < map1.length; j += random.nextInt(40)) {
                map1[i][j] = 'm';
            }
        }
        for (int i = 1; i < map1.length; i+= random.nextInt(6)+2) {//Set Stronger monster
            for (int j = random.nextInt(40); j < map1.length; j += random.nextInt(40)+40) {
                map1[i][j] = 'M';
            }
        }
        for (int i = 1; i < 4; i++) {//Spawn point protection
            for (int j = 18; j < 22; j++) {
                map1[i][j] = ' ';
            }
        }

        for(int i = 0;i < map1.length; i++) {//Set map border
            for(int j = 0; j < map1.length; j++) {
                if(i == 0) {
                    map1[i][j] = '#';
                    if(j > 18 && j < 22) {
                        map1[i][j] = '=';
                    }
                }

                if(i == 39) {
                    map1[i][j] = '#';
                    if(j == 20 || j == 21) {
                        map1[i][j] = '.';
                    }
                }
            }
            map1[i][0] = '#';
            map1[i][map1.length - 1] = '#';
        }
        return map1;
    }



    public static void MapDisplay(char[][] map) {//Display map/print method
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(i == a && j == b) {
                    System.out.print(ANSI_RED + 'P' + ANSI_RESET + ' ');
                }
                else {
                    System.out.print(map[i][j] + " ");
                }
            }
            System.out.println();
        }
    }



    public static void Move(char[][] map) {
        int n = 0, qui = 0;//n to store the number of movements
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

        do {//Loop for receiving user movement input.
            n++;

            do {//verifying the input validation
                System.out.print("-------------------------------------------------------------------------------\n" +
                        "Enter the movement instruction(Enter quit to quit): ");
                movement = scanner.nextLine().toLowerCase();
                if(movement.equals("quit")) {
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

            if(qui == -1)
                break;

            System.out.println("-------------------------------------------------------------------------------");

            for(int k = 0; k < movement.length(); k++) {
                if(movement.charAt(k) == 'w') {
                    if (map[a - 1][b] == '#') {
                        System.out.println("\nYou met an obstacle at number " + (k + 1) + " movement. Please take another route\n");
                        break;
                    }
                    if( map[a - 1][b] == 'M') {
                        System.out.println("Encountered a Monster at number " + (k + 1) + " movement.");
                        a--;
                        break;
                    }
                    if(map[a - 1][b] == 'm') {
                        System.out.println("Encountered a Little Monster at number " + (k + 1) + " movement.");
                    if (map[a][b] == 'm' || map[a][b] == 'M') {
                        if (map[a][b] == 'm') {

                        }
                    }
                        a--;
                        break;
                    }
                    map[a][b] = ' ';
                    a--;
                }
                if (movement.charAt(k) == 'a') {
                    if(map[a][b - 1] == '#') {
                        System.out.println("\nYou met an obstacle at number " + (k + 1) + " movement. Please take another route\n");
                        break;
                    }
                    if(map[a][b - 1] == 'M') {
                        System.out.println("Encountered a Monster at number " + (k + 1) + " movement.");
                        b--;
                        break;
                    }
                    if(map[a][b - 1] == 'm') {
                        System.out.println("Encountered a Little Monster at number " + (k + 1) + " movement.");
                        b--;
                        break;
                    }
                    map[a][b] = ' ';
                    b--;
                }
                if (movement.charAt(k) == 's') {
                    if(map[a + 1][b] == '#') {
                        System.out.println("\nYou met an obstacle at number " + (k + 1) + " movement. Please take another route\n");
                        break;
                    }
                    if(map[a + 1][b] == 'M') {
                        System.out.println("Encountered a Monster at number " + (k + 1) + " movement.");
                        a++;
                        break;
                    }
                    if(map[a + 1][b] == 'm') {
                        System.out.println("Encountered a Little Monster at number " + (k + 1) + " movement.");
                        a++;
                        break;
                    }
                    map[a][b] = ' ';
                    a++;
                }
                if (movement.charAt(k) == 'd') {
                    if(map[a][b + 1] == '#') {
                        System.out.println("\nYou met an obstacle at number " + (k + 1) + " movement. Please take another route\n");
                        break;
                    }
                    if(map[a][b + 1] == 'M') {
                        System.out.println("Encountered a Monster at number " + (k + 1) + " movement.");
                        b++;
                        break;
                    }
                    if(map[a][b + 1] == 'm') {
                        System.out.println("Encountered a Little Monster at number " + (k + 1) + " movement.");
                        b++;
                        break;
                    }
                    map[a][b] = ' ';
                    b++;
                }
            }
            System.out.println("Now, player is at row " + (a + 1) +" column "+ (b + 1));
            System.out.println("================================= Map after " + n + " movement =================================");
            MapDisplay(map);
        } while (true);
    }
}
