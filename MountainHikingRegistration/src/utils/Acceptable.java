/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author MSII
 */
public interface Acceptable {
    public final String STU_ID_VALID = "^[CcDdHhSsQq][Ee]\\d{6}$";
    public final String NAME_VALID = "^.{2,20}$";
    public final String DOUBLE_VALID = "^[+-]?([0-9]*[.])?[0-9]+$";
    public final String INTEGER_VALID = "\\d+";
    public final String VIETTEL_VALID = "^(086|096|097|098|032|033|034|035|036|037|038|039)\\d{7}$";
    public final String VNPT_VALID= "^(081|082|083|084|085|088|091|094)\\d{7}$";
    public final String PHONE_VALID= "^(03|05|07|08|09)\\d{8}$";
    public final String EMAIL_VALID= "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    
    public static boolean isValid(String data, String pattern){
        return data.matches(pattern);
    }
}
