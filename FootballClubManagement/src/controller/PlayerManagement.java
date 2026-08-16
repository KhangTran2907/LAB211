package controller;

import model.Club;
import model.Player;
import utils.Inputter;

import utils.Acceptable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerManagement implements IManager<Player> {
    private List<Player> playerList;
    private ClubManagement clubManagement; // Dependency to access Club data
    private Inputter inputter;

    public PlayerManagement(ClubManagement clubManagement) {
        this.playerList = new ArrayList<>();
        this.clubManagement = clubManagement;
        this.inputter = new Inputter();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    // Core search logic used internally
    @Override
    public Player searchById(String id) {
        for (Player p : playerList) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    // Helper: Validate if a shirt number already exists in a specific club
    private boolean isShirtNumberExist(String clubId, int shirtNumber, String excludePlayerId) {
        for (Player p : playerList) {
            if (p.getClubId().equalsIgnoreCase(clubId) && p.getShirtNumber() == shirtNumber) {
                // When updating, we ignore the shirt number of the player being updated
                if (excludePlayerId == null || !p.getId().equalsIgnoreCase(excludePlayerId)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Function 6: List all players in ascending order of club names; if same club, sort by shirt number ascending.
    @Override
    public void displayAll() {
        if (playerList.isEmpty()) {
            System.out.println("The player list is currently empty!");
            return;
        }

        // Create a copy to sort without changing the original order in the list
        List<Player> sortedList = new ArrayList<>(playerList);

        Collections.sort(sortedList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                Club c1 = clubManagement.searchById(p1.getClubId());
                Club c2 = clubManagement.searchById(p2.getClubId());
                
                String clubName1 = (c1 != null) ? c1.getClubName() : "";
                String clubName2 = (c2 != null) ? c2.getClubName() : "";

                // Sort by Club Name Ascending
                int nameCompare = clubName1.compareToIgnoreCase(clubName2);
                if (nameCompare != 0) {
                    return nameCompare;
                }
                // If same club, sort by Shirt Number Ascending
                return Integer.compare(p1.getShirtNumber(), p2.getShirtNumber());
            }
        });

        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-10s | %-20s | %-15s | %-5s\n", 
                "Player ID", "Player Name", "Club ID", "Club Name", "Position", "Shirt");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for (Player p : sortedList) {
            Club c = clubManagement.searchById(p.getClubId());
            String clubName = (c != null) ? c.getClubName() : "Unknown";
            System.out.printf("%-10s | %-20s | %-10s | %-20s | %-15s | %-5d\n", 
                    p.getId(), p.getName(), p.getClubId(), clubName, p.getPosition(), p.getShirtNumber());
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");
    }

    // Function 8: Add a new player
    @Override
    public void add() {
        System.out.println("--- Add New Player ---");
        String id = inputter.inputAndLoop("Enter Player ID (Pxxxx): ", Acceptable.PLAYER_ID_VALID);
        if (searchById(id) != null) {
            System.out.println("This player ID already exists!");
            System.out.println("Add player failed.");
            return;
        }

        System.out.println("\n--- Available Clubs ---");
        clubManagement.displayAll();
        
        String clubId = inputter.getStringNonEmpty("Enter Club ID to assign: ");
        if (clubManagement.searchById(clubId) == null) {
            System.out.println("This club does not exist!");
            System.out.println("Add player failed.");
            return;
        }

        String name = inputter.getStringNonEmpty("Enter Player Name: ");
        String position = inputter.getPosition("Enter Position (Goalkeeper, Defender, Midfielder, Forward, Winger): ", false);
        
        int shirtNumber;
        while (true) {
            shirtNumber = inputter.getInt("Enter Shirt Number (1-99): ", 1, 99);
            if (isShirtNumberExist(clubId, shirtNumber, null)) {
                System.out.println("This shirt number already exists in this club!");
                System.out.println("Add player failed.");
                return; // Abort addition as per constraint behavior
            }
            break;
        }

        Player newPlayer = new Player(id, clubId, name, position, shirtNumber);
        playerList.add(newPlayer);
        System.out.println("Player added successfully!");
    }

    // Function 10: Update a player by ID (Core logic)
    @Override
    public void update(String id) {
        Player player = searchById(id);
        if (player == null) {
            System.out.println("This player does not exist!");
            return;
        }

        System.out.println("--- Updating Player " + player.getId() + " (Press Enter to skip field) ---");
        
        String newName = inputter.getStringAllowEmpty("Update Player Name [" + player.getName() + "]: ");
        if (!newName.isEmpty()) {
            player.setName(newName);
        }

        String newPosition = inputter.getPosition("Update Position [" + player.getPosition() + "]: ", true);
        if (!newPosition.isEmpty()) {
            player.setPosition(newPosition);
        }

        int newShirt = inputter.getIntAllowEmpty("Update Shirt Number [" + player.getShirtNumber() + "]: ", 1, 99);
        if (newShirt != -1) {
            if (newShirt != player.getShirtNumber() && isShirtNumberExist(player.getClubId(), newShirt, player.getId())) {
                System.out.println("This shirt number already exists in this club! Keeping old number.");
            } else {
                player.setShirtNumber(newShirt);
            }
        }

        System.out.println("Player updated successfully!");
    }

    // Function 10: Update a player by ID (UI wrapper)
    public void updatePlayer() {
        String id = inputter.getStringNonEmpty("Enter Player ID to update: ");
        update(id);
    }

    // Function 9: Remove a player with ID
    public void removePlayer() {
        System.out.println("--- Remove Player ---");
        String id = inputter.getStringNonEmpty("Enter Player ID to remove: ");
        Player player = searchById(id);
        if (player == null) {
            System.out.println("This player does not exist!");
            return;
        }
        playerList.remove(player);
        System.out.println("Player removed successfully!");
    }

    // Function 7: Search players by partial player name match
    public void searchByName() {
        System.out.println("--- Search Players by Name ---");
        String keyword = inputter.getStringNonEmpty("Enter partial player name: ");
        boolean found = false;

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-10s | %-15s | %-5s\n", "Player ID", "Player Name", "Club ID", "Position", "Shirt");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (Player p : playerList) {
            // Case-insensitive partial match
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.printf("%-10s | %-20s | %-10s | %-15s | %-5d\n", 
                        p.getId(), p.getName(), p.getClubId(), p.getPosition(), p.getShirtNumber());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No player found matching: " + keyword);
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    // Function 11: List all players by a specific position
    public void listByPosition() {
        System.out.println("--- List Players by Position ---");
        String position = inputter.getStringNonEmpty("Enter Position to search: ");
        boolean found = false;

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-10s | %-15s | %-5s\n", "Player ID", "Player Name", "Club ID", "Position", "Shirt");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (Player p : playerList) {
            // Exact match for position, but case-insensitive is better for UX
            if (p.getPosition().equalsIgnoreCase(position)) {
                System.out.printf("%-10s | %-20s | %-10s | %-15s | %-5d\n", 
                        p.getId(), p.getName(), p.getClubId(), p.getPosition(), p.getShirtNumber());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No player found with position: " + position);
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    // Function 13: Load data from file
    public boolean loadData(String filename) {
        playerList.clear(); // Clear current data
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    System.out.println("Load data failed! Invalid format in line: " + line);
                    return false;
                }
                String id = parts[0].trim();
                if (!id.matches(Acceptable.PLAYER_ID_VALID)) {
                    System.out.println("Load data failed! Invalid Player ID format: " + line);
                    return false;
                }
                String clubId = parts[1].trim();
                String name = parts[2].trim();
                String position = parts[3].trim();
                if (!position.matches("(?i)^(Goalkeeper|Defender|Midfielder|Forward|Winger)$")) {
                    System.out.println("Load data failed! Invalid Position: " + line);
                    return false;
                }
                // Standardize case
                position = position.substring(0, 1).toUpperCase() + position.substring(1).toLowerCase();
                
                int shirtNumber;
                
                try {
                    shirtNumber = Integer.parseInt(parts[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Load data failed! Shirt number must be integer: " + line);
                    return false;
                }
                if (shirtNumber < 1 || shirtNumber > 99) {
                    System.out.println("Load data failed! Shirt number must be between 1 and 99: " + line);
                    return false;
                }
                if (searchById(id) != null) {
                    System.out.println("Load data failed! Duplicate Player ID: " + line);
                    return false;
                }
                if (clubManagement.searchById(clubId) == null) {
                    System.out.println("Load data failed! Club ID does not exist: " + line);
                    return false;
                }
                if (isShirtNumberExist(clubId, shirtNumber, null)) {
                    System.out.println("Load data failed! Shirt number already exists in this club: " + line);
                    return false;
                }
                playerList.add(new Player(id, clubId, name, position, shirtNumber));
            }
            return true;
        } catch (Exception e) {
            System.out.println("Load data failed! Error reading file " + filename);
            return false;
        }
    }

    // Function 12: Save data to file
    public void saveData(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Player p : playerList) {
                pw.println(p.getId() + "," + p.getClubId() + "," + p.getName() + "," + p.getPosition() + "," + p.getShirtNumber());
            }
        } catch (Exception e) {
            System.out.println("Error saving players to " + filename);
        }
    }

    // Wrap both saves into one method to avoid logic in switch-case
    public void saveAllData(String clubFile, String playerFile) {
        clubManagement.saveData(clubFile);
        this.saveData(playerFile);
        System.out.println("Save data successfully!");
    }
}
