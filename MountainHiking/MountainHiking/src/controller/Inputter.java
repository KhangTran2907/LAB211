/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Scanner;

/**
 *
 * @author ACER
 */
public class Inputter {

    /*
^: bắt đầu chuỗi regex
$: đánh dấu là kết thúc chuỗi regex
.: cho phép dùng tất cả các loại kí tự
*: lặp 0 hoặc nhiều lần
+: lặp ít nhất 1 lần
?: lặp 0 hoặc 1 lần
{n}: Yêu cầu phải đủ n kí tự 
{n,m}: Lặp từ n đến m lần
[]: tập hợp các kí tự cho phép
(): Nhóm
|: Hoặc
[\w.-]: có nghĩa là có thể cho phép nhập chữ cái, số, dấu _, dấu ., dấu gạch ngang -
\d: cho phép nhập số
\D: cho phép nhập bất kì kí tự gì trừ số
     */

    // Validate Rule
    public static final String STUDENT_ID_VALIDATE = "^(HE|SE|CE|DE|QE)\\d{6}$";
    public static final String NAME_VALIDATE = "^[A-Za-zÀ-ỹ\\s]{2,20}$";
    public static final String PHONE_NUMBER_VALIDATE = "^(03[2-9]|05[689]|07[06-9]|08[1-9]|09\\d)\\d{7}$";
    public static final String EMAIL_VALIDATE = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,}$";
    public static final String MOUNTAIN_CODE_VALIDATE = "^\\d+$";
    public static final String TUITION_FEE_VALIDATE = "^(03[2-9]|086|09[6-8]|08[1-5]|088|09[14])\\d{7}$";
    static Scanner sc = new Scanner(System.in);

    public static String input(String label) {
        System.out.print(label);
        String input = sc.nextLine();
        return input;
    }
    public static String inputRequired(String label, String regex){
        String input;
        do{
            System.out.print(label);
            input = sc.nextLine();
        }while(!input.matches(regex));
        return input;
    }
    public static String inputOptional(String label, String regex){
        String input;
        while(true){
            System.out.print(label);
            input = sc.nextLine();
            if(input == null|| input.isEmpty()){
                return " ";
            }
            if(input.matches(regex)){
                return input;
            }
        }
    }
}
