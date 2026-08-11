
package view;

import controller.Inputter;
import controller.StudentController;
import java.util.Scanner;

public class Main {
    
    public static void menu(){
        System.out.println("1.New Registration");
        System.out.println("2.Update Registration Information");
        System.out.println("3.Display Registered List");
        System.out.println("4.Delete Registration Information");
        System.out.println("5.Search Participants by Name");
        System.out.println("6.Filter Data by Campus");
        System.out.println("7.Statistics of Registration Numbers by Location");   
        System.out.println("8.Save Data to File");
        System.out.println("9.Exit the Program");
    }
    public static void main(String[] args) {
        StudentController student = new StudentController();
        Scanner sc = new Scanner(System.in);
        student.readMountainList("MountainList.csv");
        String choice = " ";
        do{
            menu();
            choice = Inputter.input("Enter your choice: ").trim();
            switch(choice){
                case "1": 
                    student.addNewRegistration();
                    break;
                case "2": 
                    student.updateRegistration();
                    break;
                case "3":
                    student.displayRegisteredList();
                    break;
                case "4":
                    student.deleteRegistrationInformation();
                    break;
                case "5":
                    student.searchParticipantsByName();
                    break;
                case "6":
                    student.filterDataByCampus();
                    break;
                case "7":
                    student.statisticsOfRegistrationNumberByLocation();
                    break;
                case "8":
                    student.saveRegistrationToFile();
                    break;
                case "9":
                    if(student.hasChange){
                        String confirm = Inputter.input("Do you want to save the changes before exiting? (Y/N): ").trim();
                        if(confirm.equalsIgnoreCase("Y")){
                            student.saveRegistrationToFile();
                        }
                    }
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("This function is not available.");
                    break;
            }
        }while(!choice.equals("9"));
    }
}
