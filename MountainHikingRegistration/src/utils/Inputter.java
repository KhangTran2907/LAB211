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
    
    public void enterStudentInfo(model.Student target, controller.MountainController mountainController, boolean isUpdate) {
        while (true) {
            String name = this.getString(isUpdate ? "Update Student name (or Enter to skip): " : "Student name: ");
            if (isUpdate && name.trim().isEmpty()) break;
            if (Acceptable.isValid(name, Acceptable.NAME_VALID)) {
                target.setName(name);
                break;
            }
            System.out.println("Data is invalid!. Re-enter ...");
        }
        
        while (true) {
            String phone = this.getString(isUpdate ? "Update Phone number (or Enter to skip): " : "Phone number [10 digits]: ");
            if (isUpdate && phone.trim().isEmpty()) break;
            if (Acceptable.isValid(phone, Acceptable.PHONE_VALID)) {
                target.setPhone(phone);
                break;
            }
            System.out.println("Data is invalid!. Re-enter ...");
        }
        
        while (true) {
            String email = this.getString(isUpdate ? "Update Email address (or Enter to skip): " : "Email address: ");
            if (isUpdate && email.trim().isEmpty()) break;
            if (Acceptable.isValid(email, Acceptable.EMAIL_VALID)) {
                target.setEmail(email);
                break;
            }
            System.out.println("Data is invalid!. Re-enter ...");
        }
        
        while (true) {
            String mountainCode = this.getString(isUpdate ? "Update Mountain Code (or Enter to skip): " : "Enter Mountain Code: ");
            if (isUpdate && mountainCode.trim().isEmpty()) break;
            
            boolean validMountain = false;
            for (model.Mountain m : mountainController) {
                if (m.getMountainCode().equalsIgnoreCase(mountainCode)) {
                    validMountain = true;
                    break;
                }
            }
            if (validMountain) {
                target.setMountainCode(mountainCode);
                break;
            }
            System.out.println("Invalid Mountain Code! Please check MountainList.csv.");
        }
        
        double tuitionFee = 6000000;
        if (target.getPhone().matches(Acceptable.VIETTEL_VALID) || target.getPhone().matches(Acceptable.VNPT_VALID)) {
            tuitionFee = tuitionFee * 0.65;
            System.out.println("You get a 35% discount.");
        }
        target.setTutionFee(tuitionFee);
    }
}
