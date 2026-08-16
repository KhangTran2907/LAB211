package Main;

import controller.ClubManagement;
import controller.PlayerManagement;
import utils.Inputter;

public class FootballClubManagement {

    public static void main(String[] args) {
        Inputter inputter = new Inputter();
        ClubManagement clubManagement = new ClubManagement();
        PlayerManagement playerManagement = new PlayerManagement(clubManagement);
        
        final String CLUBS_FILE = "clubs.txt";
        final String PLAYERS_FILE = "players.txt";

        // Load data on startup
        System.out.println("Loading data on startup...");
        boolean clubLoad = clubManagement.loadData(CLUBS_FILE);
        boolean playerLoad = playerManagement.loadData(PLAYERS_FILE);
        if (clubLoad && playerLoad) {
            System.out.println("Load data successfully!");
        } else {
            System.out.println("Data files might not exist yet or formatting is incorrect.");
        }

        int choice;
        do {
            System.out.println("\n====== EUROPEAN ELITE LEAGUE (EEL) ======");
            System.out.println("1. List of all clubs");
            System.out.println("2. Add a new club");
            System.out.println("3. Search for a club by ID");
            System.out.println("4. Update a club by ID");
            System.out.println("5. List of all clubs with budget <= input value");
            System.out.println("6. List all players sorted by club names and shirt number");
            System.out.println("7. Search players by partial player name");
            System.out.println("8. Add a new player");
            System.out.println("9. Remove a player with ID");
            System.out.println("10. Update a player with an ID");
            System.out.println("11. List all players by a specific position");
            System.out.println("12. Save data to files");
            System.out.println("13. Quit program");
            System.out.println("==========================================");

            choice = inputter.getInt("Enter your choice (1-13): ");

            switch (choice) {
                case 1:
                    clubManagement.displayAll();
                    break;
                case 2:
                    clubManagement.add();
                    break;
                case 3:
                    clubManagement.searchClub();
                    break;
                case 4:
                    clubManagement.updateClub();
                    break;
                case 5:
                    clubManagement.listByBudget();
                    break;
                case 6:
                    playerManagement.displayAll();
                    break;
                case 7:
                    playerManagement.searchByName();
                    break;
                case 8:
                    playerManagement.add();
                    break;
                case 9:
                    playerManagement.removePlayer();
                    break;
                case 10:
                    playerManagement.updatePlayer();
                    break;
                case 11:
                    playerManagement.listByPosition();
                    break;
                case 12:
                    playerManagement.saveAllData(CLUBS_FILE, PLAYERS_FILE);
                    break;
                case 13:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select from 1 to 13.");
            }
        } while (choice != 13);
    }
}
