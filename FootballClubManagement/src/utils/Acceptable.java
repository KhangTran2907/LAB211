package utils;

public interface Acceptable {
    public final String DOUBLE_VALID = "^[+-]?([0-9]*[.])?[0-9]+$";
    public final String INTEGER_VALID = "\\d+";
    public final String CLUB_ID_VALID = "^CL-\\d{4}$";
    public final String PLAYER_ID_VALID = "^P\\d{4}$";
    
    public static boolean isValid(String data, String pattern){
        return data.matches(pattern);
    }
}
