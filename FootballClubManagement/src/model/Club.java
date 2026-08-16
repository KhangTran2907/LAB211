package model;

public class Club {
    private String clubId;
    private String clubName;
    private String sponsorBrand;
    private double budget;

    public Club() {
    }

    public Club(String clubId, String clubName, String sponsorBrand, double budget) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.sponsorBrand = sponsorBrand;
        this.budget = budget;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getSponsorBrand() {
        return sponsorBrand;
    }

    public void setSponsorBrand(String sponsorBrand) {
        this.sponsorBrand = sponsorBrand;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        // You can format this better later for tabular display
        return String.format("%-10s | %-20s | %-20s | %.2f", clubId, clubName, sponsorBrand, budget);
    }
}
