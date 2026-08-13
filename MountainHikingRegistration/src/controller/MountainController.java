/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mountain;

/**
 *
 * @author MSII
 */
public class MountainController extends ArrayList<Mountain> {
    
    // Đường dẫn tới file text/csv chứa dữ liệu
    private String pathFile = "MountainList.csv"; 

    /**
     * Đọc danh sách Mountain từ file CSV
     */
    public void readFromFile() {
        FileReader fr = null;
        try {
            File f = new File(this.pathFile);
            if (!f.exists()) {
                System.out.println("MountainList.csv file not found !.");
                return;
            }
            
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            
            while ((temp = br.readLine()) != null) {
                Mountain i = dataToObject(temp);
                if (i != null) {
                    this.add(i);
                }
            }
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MountainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MountainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MountainController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(MountainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Hàm này chuyển đổi 1 dòng text thành 1 đối tượng Mountain
     */
    private Mountain dataToObject(String temp) {
        String[] parts = temp.split(",");
        if (parts.length >= 4) {
            return new Mountain(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
        }
        return null;
    }
}
