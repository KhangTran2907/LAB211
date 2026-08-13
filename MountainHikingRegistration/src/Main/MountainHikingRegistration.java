/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

/**
 *
 * @author MSII
 */
import controller.MountainController;
import controller.StudentController;
import utils.Inputter;

public class MountainHikingRegistration {

    public static void main(String[] args) {
        Inputter inputter = new Inputter();
        MountainController mountainController = new MountainController();
        StudentController studentController = new StudentController();
        
        // Đọc dữ liệu từ file khi khởi động chương trình
        mountainController.readFromFile();
        studentController.readFromFile();
        
        int choice;
        do {
            System.out.println("\n====== MOUNTAIN HIKING REGISTRATION ======");
            System.out.println("1. New Registration");
            System.out.println("2. Update Registration Information");
            System.out.println("3. Display Registered List");
            System.out.println("4. Delete Registration Information");
            System.out.println("5. Search Participants by Name");
            System.out.println("6. Filter Data by Campus");
            System.out.println("7. Statistics of Registration Numbers by Location");
            System.out.println("8. Save Data to File");
            System.out.println("9. Exit the Program");
            System.out.println("============================================");
            
            choice = inputter.getInt("Enter your choice (1-9): ");
            
            switch (choice) {
                case 1:
                    studentController.addNewRegistration(mountainController);
                    break;
                case 2:
                    studentController.updateRegistration(mountainController);
                    break;
                case 3:
                    studentController.showAll();
                    break;
                case 4:
                    studentController.deleteRegistration();
                    break;
                case 5:
                    studentController.searchByName();
                    break;
                case 6:
                    studentController.filterByCampus();
                    break;
                case 7:
                    studentController.showStatistics();
                    break;
                case 8:
                    studentController.saveToFile();
                    System.out.println("Registration data has been successfully saved to registrations.dat.");
                    break;
                case 9:
                    studentController.exitProgram(inputter);
                    break;
                default:
                    System.out.println("This function is not available.");
            }
        } while (choice != 9);
    }
}
