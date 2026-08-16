package utils;

import java.util.Scanner;

public class Inputter {
    private Scanner ndl;

    public Inputter() {
        this.ndl = new Scanner(System.in);
    }

    public String getString(String mess) {
        System.out.print(mess);
        return ndl.nextLine();
    }

    public String getStringNonEmpty(String mess) {
        String result;
        do {
            result = getString(mess).trim();
            if (result.isEmpty()) {
                System.out.println("Data cannot be empty. Please re-enter.");
            }
        } while (result.isEmpty());
        return result;
    }

    public String getStringAllowEmpty(String mess) {
        System.out.print(mess);
        return ndl.nextLine().trim();
    }

    public int getInt(String mess) {
        String temp;
        do {
            temp = getString(mess).trim();
            if (!Acceptable.isValid(temp, Acceptable.INTEGER_VALID)) {
                System.out.println("Invalid integer number. Please re-enter.");
            }
        } while (!Acceptable.isValid(temp, Acceptable.INTEGER_VALID));
        return Integer.parseInt(temp);
    }

    /**
     * Get an integer from input, allowing the user to press Enter to skip.
     * Returns -1 if the user skips.
     */
    public int getIntAllowEmpty(String mess) {
        String temp;
        do {
            temp = getString(mess).trim();
            if (temp.isEmpty()) {
                return -1; // -1 represents skipping
            }
            if (!Acceptable.isValid(temp, Acceptable.INTEGER_VALID)) {
                System.out.println("Invalid integer number. Please re-enter.");
            }
        } while (!Acceptable.isValid(temp, Acceptable.INTEGER_VALID));
        return Integer.parseInt(temp);
    }

    public double getDouble(String mess) {
        String temp;
        do {
            temp = getString(mess).trim();
            if (!Acceptable.isValid(temp, Acceptable.DOUBLE_VALID)) {
                System.out.println("Invalid double number. Please re-enter.");
            }
        } while (!Acceptable.isValid(temp, Acceptable.DOUBLE_VALID));
        return Double.parseDouble(temp);
    }

    /**
     * Get a double from input, allowing the user to press Enter to skip.
     * Returns -1.0 if the user skips.
     */
    public double getDoubleAllowEmpty(String mess) {
        String temp;
        do {
            temp = getString(mess).trim();
            if (temp.isEmpty()) {
                return -1.0; // -1.0 represents skipping
            }
            if (!Acceptable.isValid(temp, Acceptable.DOUBLE_VALID)) {
                System.out.println("Invalid double number. Please re-enter.");
            }
        } while (!Acceptable.isValid(temp, Acceptable.DOUBLE_VALID));
        return Double.parseDouble(temp);
    }

    public int getInt(String mess, int min, int max) {
        int result;
        while (true) {
            result = getInt(mess);
            if (result >= min && result <= max) {
                return result;
            }
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    public int getIntAllowEmpty(String mess, int min, int max) {
        int result;
        while (true) {
            result = getIntAllowEmpty(mess);
            if (result == -1) return -1;
            if (result >= min && result <= max) {
                return result;
            }
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    public String getPosition(String mess, boolean isUpdate) {
        String pos;
        while (true) {
            pos = isUpdate ? getStringAllowEmpty(mess) : getStringNonEmpty(mess);
            if (isUpdate && pos.isEmpty()) {
                return "";
            }
            if (pos.equalsIgnoreCase("Goalkeeper") || pos.equalsIgnoreCase("Defender") || 
                pos.equalsIgnoreCase("Midfielder") || pos.equalsIgnoreCase("Forward") || 
                pos.equalsIgnoreCase("Winger")) {
                
                // Capitalize first letter to standardize
                return pos.substring(0, 1).toUpperCase() + pos.substring(1).toLowerCase();
            }
            System.out.println("Position must be Goalkeeper, Defender, Midfielder, Forward, or Winger.");
        }
    }

    public String inputAndLoop(String mess, String pattern) {
        String result;
        boolean more = true;
        do {
            result = getString(mess);
            more = !Acceptable.isValid(result, pattern);
            if (more) {
                System.out.println("Data is invalid!. Re-enter ...");
            }
        } while (more);
        return result.trim();
    }
}
