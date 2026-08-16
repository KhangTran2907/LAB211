package model;

public class Player extends Person {
    private String clubId;
    private String position;
    private int shirtNumber;

    public Player() {
        super();
    }

    public Player(String id, String clubId, String name, String position, int shirtNumber) {
        super(id, name);
        this.clubId = clubId;
        this.position = position;
        this.shirtNumber = shirtNumber;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(int shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-20s | %-10s | %-15s | %d", id, name, clubId, position, shirtNumber);
    }
}
