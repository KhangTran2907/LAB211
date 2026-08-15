package utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.Scanner;

/**
 *
 * @author MSII
 */
public class Inputter {
    private Scanner ndl;
    
    public Inputter(){
        this.ndl = new Scanner(System.in);
    }
    
    public String getString(String mess){
        System.out.print(mess);
        return ndl.nextLine();
    }
    
    public int getInt(String mess){
        int result = 0;
        String temp = getString(mess);
        if(Acceptable.isValid(temp, Acceptable.INTEGER_VALID))
            result = Integer.parseInt(temp);
        return result;
    }
    
    public double getDouble(String mess){
        double result = 0;
        String temp = getString(mess);
        if(Acceptable.isValid(temp, Acceptable.DOUBLE_VALID))
            result = Double.parseDouble(temp);
        return result;
    }
    
    public String inputAndLoop(String mess, String pattern){
        String result = "";
        boolean more = true;
        do{
            result = getString(mess);
            more = !Acceptable.isValid(result, pattern);
            if(more) System.out.println("Data is invalid!. Re-enter ...");
        }while(more);
        return result.trim();
    }
    
    public String getStudentName(boolean isUpdate) {
        while (true) {
            String name = this.getString(isUpdate ? "Update Student name (or Enter to skip): " : "Student name: ");
            if (isUpdate && name.trim().isEmpty()) return "";
            if (Acceptable.isValid(name, Acceptable.NAME_VALID)) {
                return name;
            }
            System.out.println("Data is invalid!. Re-enter ...");
        }
    }
    
    public String getStudentPhone(boolean isUpdate) {
        while (true) {
            String phone = this.getString(isUpdate ? "Update Phone number (or Enter to skip): " : "Phone number [10 digits]: ");
            if (isUpdate && phone.trim().isEmpty()) return "";
            if (Acceptable.isValid(phone, Acceptable.PHONE_VALID)) {
                return phone;
            }
            System.out.println("Data is invalid!. Re-enter ...");
        }
    }
    
    public String getStudentEmail(boolean isUpdate) {
        while (true) {
            String email = this.getString(isUpdate ? "Update Email address (or Enter to skip): " : "Email address: ");
            if (isUpdate && email.trim().isEmpty()) return "";
            if (Acceptable.isValid(email, Acceptable.EMAIL_VALID)) {
                return email;
            }
            System.out.println("Data is invalid!. Re-enter ...");
        }
    }
    
    public String getMountainCode(boolean isUpdate, controller.MountainController mountainController) {
        while (true) {
            String mountainCode = this.getString(isUpdate ? "Update Mountain Code (or Enter to skip): " : "Enter Mountain Code: ");
            if (isUpdate && mountainCode.trim().isEmpty()) return "";
            
            boolean validMountain = false;
            for (model.Mountain m : mountainController) {
                if (m.getMountainCode().equalsIgnoreCase(mountainCode)) {
                    validMountain = true;
                    break;
                }
            }
            if (validMountain) {
                return mountainCode;
            }
            System.out.println("Invalid Mountain Code! Please check MountainList.csv.");
        }
    }
}
