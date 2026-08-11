package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Mountain;
import model.Student;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class StudentController {
    
    List<Student> listStudent = new ArrayList<>();
    List<Mountain> listMountain = new ArrayList<>();

    Scanner sc = new Scanner(System.in);
    public boolean hasChange =false;
    private Student getStudentByID(String studentId) {
        for (Student student : listStudent) {
            if (student.getStudentID().equals(studentId)) {
                return student;
            }
        }
        return null;

    }

    private Mountain getMountainByID(String mountainId) {
        for (Mountain mountain : listMountain) {
            if (mountain.getMountainCode().equals(mountainId)) {
                return mountain;
            }
        }
        return null;

    }

    public void readMountainList(String fileName) {
        try {
            Scanner rf = new Scanner(new File(fileName));
            if (rf.hasNext()) {
                rf.nextLine();
            }
            while (rf.hasNext()) {
                String[] lines = rf.nextLine().split(",");
                Mountain mountain = new Mountain(lines[0], lines[1], lines[2], lines[3]);
                listMountain.add(mountain);
            }
            rf.close();
        } catch (FileNotFoundException e) {
            System.out.println("File invalid");
        }

    }

    private String convertMountainCode(String mtCode) {
        String mountain = "MT";
        try {
            if (Integer.parseInt(mtCode) >= 10) {
                mountain += mtCode;
            } else {
                mountain += "0" + mtCode;
            }
        } catch (Exception e) {
            return mtCode;
        }
        return mountain;
    }

    //Function 1
    public void addNewRegistration() {
        String studentId, name, phoneNumber, email, mountainCode;
        double tuitionFee = 6000000;
        while (true) {
            studentId = Inputter.inputRequired("Enter Student ID: ", Inputter.STUDENT_ID_VALIDATE);
            if (getStudentByID(studentId) != null) {
                System.out.println("Student ID is already existed!");
            } else {
                break;
            }
        }
        name = Inputter.inputRequired("Enter Name: ", Inputter.NAME_VALIDATE);
        phoneNumber = Inputter.inputRequired("Enter Phone: ", Inputter.PHONE_NUMBER_VALIDATE);
        email = Inputter.inputRequired("Enter Email: ", Inputter.EMAIL_VALIDATE);

        while (true) {
            mountainCode = Inputter.inputRequired("Enter Mountain Code: ", Inputter.MOUNTAIN_CODE_VALIDATE);
            if (getMountainByID(mountainCode) == null) {
                System.out.println("Mountain Code does not existed in File Mountain List!");
            } else {
                break;
            }
        }
        if (phoneNumber.matches(Inputter.TUITION_FEE_VALIDATE)) {
            tuitionFee = tuitionFee - (0.35 * tuitionFee);
        }
        Student newStudent = new Student(studentId, name, phoneNumber, email, mountainCode, tuitionFee);
        listStudent.add(newStudent);
        System.out.println("Registration successfully added!");
        hasChange = true;
    }

    //Function 2
    public void updateRegistration() {
        String studentId, name, phoneNumber, email, mountainCode;
        studentId = Inputter.inputRequired("Enter Student ID: ", Inputter.STUDENT_ID_VALIDATE);

        Student std = getStudentByID(studentId);
        if (std == null) {
            System.out.println("This student has not registered yet.");
            return;
        }

        name = Inputter.inputOptional("Enter Name: ", Inputter.NAME_VALIDATE);
        if (!name.isEmpty()) {
            std.setName(name);
        }
        phoneNumber = Inputter.inputOptional("Enter Phone Number: ", Inputter.PHONE_NUMBER_VALIDATE);
        if (!phoneNumber.isEmpty()) {
            std.setPhoneNumber(phoneNumber);
        }
        email = Inputter.inputOptional("Enter Email: ", Inputter.EMAIL_VALIDATE);
        if (!email.isEmpty()) {
            std.setEmail(email);
        }

        while (true) {
            mountainCode = Inputter.inputOptional("Enter Mountain Code: ", Inputter.MOUNTAIN_CODE_VALIDATE);
            if (mountainCode.isEmpty()) {
                break;
            }
            if (getMountainByID(mountainCode) == null) {
                System.out.println("Mountain Code does not exist in File Mountain List!");
            } else {
                std.setMountainCode(mountainCode);
                break;
            }
        }
        System.out.println("Update student information successfully!");
        hasChange = true;
    }

    //Function 3
    public void displayRegisteredList() {
        if (listStudent.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }
        System.out.println("Registered Students:");
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-12s | %-9s | %s\n", "Student ID", "Name", "Phone", "Peak Code", "Fee");
        System.out.println("-----------------------------------------------------------------------");
        for (Student std : listStudent) {
            System.out.printf("%-10s | %-20s | %-12s | %-9s | %,.0f\n", std.getStudentID(), std.getName(), std.getPhoneNumber(), convertMountainCode(std.getMountainCode()), std.getTuitionFee());
        }
        System.out.println("-----------------------------------------------------------------------");
    }

    //Function 4
    public void deleteRegistrationInformation() {
        String studentId;
        studentId = Inputter.inputRequired("Enter Student ID: ", Inputter.STUDENT_ID_VALIDATE);
        Student std = getStudentByID(studentId);
        if (std == null) {
            System.out.println("This student has not registered yet.");
            return;
        }
        System.out.println("Student Details:");
        System.out.println("---------------------------------------------------------");
        System.out.printf("%-10s: %s\n", "Student ID", std.getStudentID());
        System.out.printf("%-10s: %s\n", "Name", std.getName());
        System.out.printf("%-10s: %s\n", "Phone", std.getPhoneNumber());
        System.out.printf("%-10s: %s\n", "Mountain", convertMountainCode(std.getMountainCode()));
        System.out.printf("%-10s: %,.0f\n", "Fee", std.getTuitionFee());
        System.out.println("---------------------------------------------------------");

        String confirm;
        System.out.print("Are you sure you want to delete this registration?(Y/N): ");
        confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            listStudent.remove(std);
            System.out.println("The registration has been successfully deleted.");
        }
        hasChange = true;
    }
//Function 5

    public void searchParticipantsByName() {
        boolean isFound = false;
        String name = Inputter.input("Enter Name: ").trim();

        for (Student student : listStudent) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                if (isFound == false) {
                    System.out.println("Matching Students:");
                    System.out.println("----------------------------------------------------------------------------------");
                    System.out.printf("%-10s | %-20s | %-12s | %-9s | %s\n", "Student ID", "Name", "Phone", "Peak Code", "Fee");
                    System.out.println("----------------------------------------------------------------------------------");

                    isFound = true;
                }
                System.out.printf("%-10s | %-20s | %-12s | %-9s | %,.0f\n", student.getStudentID(), student.getName(), student.getPhoneNumber(), convertMountainCode(student.getMountainCode()), student.getTuitionFee());

            }

        }
        if (isFound == true) {
            System.out.println("----------------------------------------------------------------------------------");
        } else {
            System.out.println("No one matches the search criteria!");
        }
    }

    public void  filterDataByCampus() {
        String campusCode = Inputter.input("Enter Campus Code: ").toUpperCase();
        String campusName = " ";
        boolean isFound = false;
        switch (campusCode) {
            case "CE":
                campusName = "Can Tho";
                break;
            case "HE":
                campusName = "Ha Noi";
                break;
            case "QE":
                campusName = "Quy Nhon";
                break;
            case "SE":
                campusName = "Ho Chi Minh";
                break;
            case "DE":
                campusName = "Da Nang";
                break;
            default:
                System.out.println("Invalid Campus Code!");
                return;
        }
        for (Student student : listStudent) {
            if (student.getStudentID().startsWith(campusCode)) {
                if (isFound == false) {
                    System.out.println("Registered Students Under " + campusName + " Campus (" + campusCode + "):");
                    System.out.println("----------------------------------------------------------------------------------");
                    System.out.printf("%-10s | %-20s | %-12s | %-9s | %s\n", "Student ID", "Name", "Phone", "Mountain", "Fee");
                    System.out.println("----------------------------------------------------------------------------------");
                    isFound = true;
                }
                System.out.printf("%-10s | %-20s | %-12s | %-9s | %,.0f\n",
                        student.getStudentID(), student.getName(), student.getPhoneNumber(),
                        convertMountainCode(student.getMountainCode()), student.getTuitionFee());
            }
        }
        if (isFound == true) {
            System.out.println("----------------------------------------------------------------------------------");
        } else {
            System.out.println("No students have registered under this campus.");
        }
    }
    public void statisticsOfRegistrationNumberByLocation(){
        if(listStudent.isEmpty()){
            System.out.println("No students have registered yet.");
            return;
        }
        System.out.println("Statistics of Registration by Mountain Peak:");
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-15s | %-22s | %s\n", "Peak Name", "Number of Participants", "Total Cost");
        System.out.println("-----------------------------------------------------------------------");
        
        for(Mountain mt : listMountain){
            int count = 0;
            double totalCost = 0;
            for(Student student : listStudent){
                if(student.getMountainCode().equals(mt.getMountainCode())){
                    count++;
                    totalCost += student.getTuitionFee();
                }
            }
            if(count > 0){
                System.out.printf("%-15s | %-22d | %,.0f\n", convertMountainCode(mt.getMountainCode()), count, totalCost);
            }
        }
        System.out.println("-----------------------------------------------------------------------");
    }
 
public void saveRegistrationToFile() {

    if (listStudent.isEmpty()) {
        System.out.println("No students have registered yet. Nothing to save!");
        return;
    }
    try {
        FileOutputStream fos = new FileOutputStream("registrations.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(listStudent);
        oos.close();
        fos.close();

        System.out.println("Registration data has been successfully saved to 'registrations.dat'.");
        hasChange = false;

    } catch (Exception e) {
        System.out.println("Error saving file: " + e.getMessage());
    }
}

}
