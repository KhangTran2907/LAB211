package controller;

import model.Club;
import utils.Inputter;

import utils.Acceptable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ClubManagement implements IManager<Club> {
    private List<Club> clubList;
    private Inputter inputter;

    public ClubManagement() {
        this.clubList = new ArrayList<>();
        this.inputter = new Inputter();
    }

    // Provide access to the list for File IO and PlayerManagement
    public List<Club> getClubList() {
        return clubList;
    }

    // Function 1: List of all clubs
    @Override
    public void displayAll() {
        if (clubList.isEmpty()) {
            System.out.println("The club list is currently empty!");
            return;
        }
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-20s | %-10s\n", "Club ID", "Club Name", "Sponsor Brand", "Budget");
        System.out.println("----------------------------------------------------------------------------------");
        for (Club club : clubList) {
            System.out.println(club);
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    // Core search logic used internally and for validation
    @Override
    public Club searchById(String id) {
        for (Club club : clubList) {
            if (club.getClubId().equalsIgnoreCase(id)) {
                return club;
            }
        }
        return null;
    }

    // Function 3: Search for a club by ID (UI wrapper)
    public void searchClub() {
        String id = inputter.getStringNonEmpty("Enter Club ID to search: ");
        Club club = searchById(id);
        if (club == null) {
            System.out.println("This club does not exist!");
        } else {
            System.out.println("----------------------------------------------------------------------------------");
            System.out.printf("%-10s | %-20s | %-20s | %-10s\n", "Club ID", "Club Name", "Sponsor Brand", "Budget");
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println(club);
            System.out.println("----------------------------------------------------------------------------------");
        }
    }

    // Function 2: Add a new club
    @Override
    public void add() {
        System.out.println("--- Add New Club ---");
        String id = inputter.inputAndLoop("Enter Club ID (CL-xxxx): ", Acceptable.CLUB_ID_VALID);
        
        // Constraint: Unique Club ID
        if (searchById(id) != null) {
            System.out.println("This club ID already exists!");
            System.out.println("Add club failed.");
            return;
        }

        String name = inputter.getStringNonEmpty("Enter Club Name: ");
        String sponsor = inputter.getStringNonEmpty("Enter Sponsor Brand: ");
        
        double budget;
        do {
            budget = inputter.getDouble("Enter Budget (> 0): ");
            if (budget <= 0) {
                System.out.println("Budget must be greater than 0.");
            }
        } while (budget <= 0);

        Club newClub = new Club(id, name, sponsor, budget);
        clubList.add(newClub);
        System.out.println("Club added successfully!");
    }

    // Function 4: Update a club by ID (Core logic)
    @Override
    public void update(String id) {
        Club club = searchById(id);
        if (club == null) {
            System.out.println("This club does not exist!");
            return;
        }

        System.out.println("--- Updating Club " + club.getClubId() + " (Press Enter to skip field) ---");
        
        // Allow empty input means skipping the update for that field
        String newName = inputter.getStringAllowEmpty("Update Club Name [" + club.getClubName() + "]: ");
        if (!newName.isEmpty()) {
            club.setClubName(newName);
        }

        String newSponsor = inputter.getStringAllowEmpty("Update Sponsor Brand [" + club.getSponsorBrand() + "]: ");
        if (!newSponsor.isEmpty()) {
            club.setSponsorBrand(newSponsor);
        }

        double newBudget = inputter.getDoubleAllowEmpty("Update Budget [" + club.getBudget() + "]: ");
        if (newBudget != -1.0) { // -1.0 means user skipped
            if (newBudget <= 0) {
                System.out.println("Budget must be greater than 0. Keeping the old budget.");
            } else {
                club.setBudget(newBudget);
            }
        }

        System.out.println("Club updated successfully!");
    }
    
    // Function 4: Update a club by ID (UI wrapper)
    public void updateClub() {
        String id = inputter.getStringNonEmpty("Enter Club ID to update: ");
        update(id);
    }

    // Function 5: List of all clubs with budget <= input value
    public void listByBudget() {
        System.out.println("--- Search Clubs by Budget ---");
        double maxBudget = inputter.getDouble("Enter maximum budget: ");
        boolean found = false;
        
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-20s | %-10s\n", "Club ID", "Club Name", "Sponsor Brand", "Budget");
        System.out.println("----------------------------------------------------------------------------------");
        
        for (Club club : clubList) {
            if (club.getBudget() <= maxBudget) {
                System.out.println(club);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No club found with budget <= " + maxBudget);
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    // Function 13: Load data from file
    public boolean loadData(String filename) {
        clubList.clear(); // Clear current data
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length != 4) {
                    System.out.println("Load data failed! Invalid format in line: " + line);
                    return false;
                }
                String id = parts[0].trim();
                if (!id.matches(Acceptable.CLUB_ID_VALID)) {
                    System.out.println("Load data failed! Invalid Club ID format: " + line);
                    return false;
                }
                String name = parts[1].trim();
                String sponsor = parts[2].trim();
                double budget;
                try {
                    budget = Double.parseDouble(parts[3].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Load data failed! Budget must be a number: " + line);
                    return false;
                }
                if (budget <= 0) {
                    System.out.println("Load data failed! Budget must be > 0: " + line);
                    return false;
                }
                if (searchById(id) != null) {
                    System.out.println("Load data failed! Duplicate Club ID: " + line);
                    return false;
                }
                clubList.add(new Club(id, name, sponsor, budget));
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
            for (Club club : clubList) {
                pw.println(club.getClubId() + "," + club.getClubName() + "," + club.getSponsorBrand() + "," + club.getBudget());
            }
        } catch (Exception e) {
            System.out.println("Error saving clubs to " + filename);
        }
    }
}
