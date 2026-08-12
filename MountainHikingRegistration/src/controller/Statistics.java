/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.HashMap;
import java.util.List;
import model.StatisticalInfo;
import model.Student;

/**
 *
 * @author MSII
 */
public class Statistics extends HashMap<String, StatisticalInfo>{
    private final String HEADER_TABLE =
            "|--------------------------------------------------------------|\n"+
            "|    PEAK NAME      | Number of Participants |     Total Cost  |\n"+
            "|-------------------|------------------------|-----------------|";
    private final String FOOTER_TABLE = 
            "|--------------------------------------------------------------|";

    public Statistics() {
        super();
    }
    
    public Statistics(List<Student> students){
        super();
        statiticalize(students);
    }
    
    public final void statiticalize(List<Student> students){
        for(Student i : students)
            if(this.containsKey(i.getMountainCode())){
                StatisticalInfo x = this.get(i.getMountainCode());
                x.setNumOfStudent(x.getNumOfStudent() +1);
                x.setTotalCost(x.getTotalCost() +i.getTutionFee());
            }else{
                StatisticalInfo z = new StatisticalInfo(i.getMountainCode(),1, i.getTutionFee());
                this.put(i.getMountainCode(), z);
            }
    }
    public void show(){
        System.out.println(HEADER_TABLE);
        for(StatisticalInfo i : this.values())
            System.out.println(i);
        System.out.println(FOOTER_TABLE);
    }
}
