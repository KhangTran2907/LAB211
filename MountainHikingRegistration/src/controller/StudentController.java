/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Student;

/**
 *
 * @author MSII
 */
public class StudentController extends ArrayList<Student> {
    
    private String pathFile = "Registration.dat";
    private boolean saved = true;

    /**
     * Đọc và nạp dữ liệu từ file Registration.dat vào danh sách sinh viên đã đăng ký
     */
    public void readFromFile() {
        FileInputStream fis = null;
        try {
            //--- 1. Tạo File object để ánh xạ lên thiết bị
            File f = new File(this.pathFile);
            if (!f.exists()) {
                System.out.println("registration.dat file not found !.");
                return;
            }
            //--- 2. Tạo luồng ánh xạ tới file để đọc dữ liệu từ thiết bị
            fis = new FileInputStream(f);
            //--- 3. Tạo đối tượng mang dữ liệu từ luồng đã tạo ở trên
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            //--- 4. Lặp và đọc dữ liệu từ file, gắn vào đối tượng hiện hành khi còn dữ liệu
            while (fis.available() > 0) {
                Student x = (Student) ois.readObject();
                this.add(x);
            }
            //--- 5. Đóng đối tượng, sau khi đọc xong
            ois.close();
            this.saved = true;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Phương thức phục vụ cho việc lưu dữ liệu xuống file ở trên đĩa
     */
    public void saveToFile() {
        //--- 0. Nếu đã lưu rồi thì thôi, không ghi nữa
        if (this.saved) return;
        
        FileOutputStream fos = null;
        try {
            //--- 1. Tạo File object
            File f = new File(this.pathFile);
            //--- 2. Tạo FileOutputStream ánh xạ tới File object
            fos = new FileOutputStream(f);
            //--- 3. Tạo ObjectOutputStream để chuyển dữ liệu xuống thiết bị
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            //--- 4. Lặp để ghi dữ liệu
            for (Student i : this) {
                oos.writeObject(i);
            }
            
            //--- 5. Đóng các object tương ứng sau khi xử lý
            oos.close();
            //--- 6. Ghi nhận trạng thái là lưu thành công
            this.saved = true;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addNewRegistration(MountainController mountainController) {
        utils.Inputter inputter = new utils.Inputter();
        String id;
        
        while (true) {
            id = inputter.inputAndLoop("Student ID: ", utils.Acceptable.STU_ID_VALID).toUpperCase();
            boolean isUnique = true;
            for (Student s : this) {
                if (s.getID().equalsIgnoreCase(id)) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) break;
            System.out.println("ID already exists! Please re-enter.");
        }
        
        Student student = new Student();
        student.setID(id);
        inputter.enterStudentInfo(student, mountainController, false);
        this.add(student);
        this.saved = false;
        System.out.println("New registration added successfully!");
    }

    public void updateRegistration(MountainController mountainController) {
        utils.Inputter inputter = new utils.Inputter();
        String id = inputter.inputAndLoop("Enter Student ID to update: ", utils.Acceptable.STU_ID_VALID).toUpperCase();
        
        Student target = null;
        for (Student s : this) {
            if (s.getID().equalsIgnoreCase(id)) {
                target = s;
                break;
            }
        }
        
        if (target == null) {
            System.out.println("This student has not registered yet.");
            return;
        }
        
        inputter.enterStudentInfo(target, mountainController, true);
        
        this.saved = false;
        System.out.println("Update registration successfully!");
    }
    
        public void showAll() {
        showAll(this);
    }

    public void showAll(java.util.List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }
        
        String HEADER_TABLE = "Student ID | Name                 | Phone      | Peak Code| Fee";
        String FOOTER_TABLE = "-------------------------------------------------------------------------";
        
        System.out.println("Registered Students:");
        System.out.println(FOOTER_TABLE);
        System.out.println(HEADER_TABLE);
        System.out.println(FOOTER_TABLE);
        
        for (Student i : list) {
            System.out.println(i);
        }
        
        System.out.println(FOOTER_TABLE);
    }
    

    public void deleteRegistration() {
        utils.Inputter inputter = new utils.Inputter();
        String id = inputter.inputAndLoop("Enter Student ID to delete: ", utils.Acceptable.STU_ID_VALID).toUpperCase();
        
        Student target = null;
        for (Student s : this) {
            if (s.getID().equalsIgnoreCase(id)) {
                target = s;
                break;
            }
        }
            if (target == null) {
            System.out.println("This student has not registered yet.");
            return; 
        }
        System.out.println("Student Details:");
        System.out.println("Student ID: " + target.getID());
        System.out.println("Name : " + target.getName());
        System.out.println("Phone : " + target.getPhone());
        System.out.println("Mountain : " + target.getMountainCode());
        System.out.println("Fee : " + String.format("%,.0f", target.getTutionFee()));
        
        String confirm = inputter.getString("Are you sure you want to delete this registration? (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            this.remove(target);
            this.saved = false;
            System.out.println("The registration has been successfully deleted.");
        }
    }
    
    public void searchByName() {
        utils.Inputter inputter = new utils.Inputter();
        String searchStr = inputter.getString("Enter name to search: ").toLowerCase();
        
        boolean found = false;
        for (Student s : this) {
            if (s.getName().toLowerCase().contains(searchStr)) {
                if (!found) {
                    System.out.println("Matching Students:");
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println("Student ID | Name                 | Phone      | Peak Code| Fee");
                    System.out.println("-------------------------------------------------------------------------");
                    found = true;
                }
                System.out.println(s);
            }
        }
        
        if (found) {
            System.out.println("-------------------------------------------------------------------------");
        } else {
            System.out.println("No one matches the search criteria!");
        }
    }

    
            public void filterByCampus() {
        utils.Inputter inputter = new utils.Inputter();
        String code = inputter.getString("Enter campus code (CE, DE, HE, SE, QE): ").toUpperCase();
        
        String campusName = "";
        switch (code) {
            case "CE": campusName = "Can Tho"; break;
            case "DE": campusName = "Da Nang"; break;
            case "HE": campusName = "Ha Noi"; break;
            case "SE": campusName = "Ho Chi Minh"; break;
            case "QE": campusName = "Quy Nhon"; break;
            default:
                System.out.println("Invalid campus code!");
                return;
        }
        
        boolean found = false;
        for (Student s : this) {
            if (s.getID().toUpperCase().startsWith(code)) {
                if (!found) {
                    System.out.println("Registered Students Under " + campusName + " Campus (" + code + "):");
                    System.out.println("-------------------------------------------------------------------------");
                    System.out.println("Student ID | Name                 | Phone      | Peak Code| Fee");
                    System.out.println("-------------------------------------------------------------------------");
                    found = true;
                }
                System.out.println(s);
            }
        }
        
        if (found) {
            System.out.println("-------------------------------------------------------------------------");
        } else {
            System.out.println("No students have registered under this campus.");
        }
    }

    public void showStatistics() {
        if (this.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }
        controller.Statistics stats = new controller.Statistics(this);
        stats.show();
    }
    
        public boolean isSaved() {
        return this.saved;
    }

    public void exitProgram(utils.Inputter inputter) {
        if (!this.isSaved()) {
            System.out.println("You have unsaved changes.");
            String confirm = inputter.getString("Do you want to save the changes before exiting? (Y/N): ");
            if (confirm.equalsIgnoreCase("Y")) {
                this.saveToFile();
                System.out.println("Registration data has been successfully saved to registrations.dat.");
            }
        }
        System.out.println("Exiting the program...");
    }

}
