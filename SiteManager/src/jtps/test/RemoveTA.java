/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps.test;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import jtps.jTPS_Transaction;
import main.workspace.TabWorkspace;
import main.workspace.TempData;
import tam.data.TeachingAssistant;

/**
 *
 * @author Home
 */
public class RemoveTA implements jTPS_Transaction {
    ObservableList<TeachingAssistant> list;
   private TeachingAssistant ta;
   private TempData data;
   private TabWorkspace workspace;
   private HashMap<String, Label> labels;
   private HashMap<String, Label> labels2;
   private ArrayList<String> stringArray;
   
   
   public RemoveTA(ObservableList<TeachingAssistant> list, TeachingAssistant ta, TempData data, TabWorkspace workspace, HashMap<String, Label> labels){
       this.list = list;
       this.ta = ta;
       this.data = data;
       this.workspace = workspace;
       this.labels = labels;
      
   }

    @Override
    public void doTransaction() {
        String taName = ta.getName();
        //data.removeTA(ta.getName());
        list.remove(ta);
       
        
        HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
        labels2 = labels;
        stringArray = new ArrayList<>();
                for (Label label : labels.values()) {
                    
                  //  System.out.println(label.getId().toString());
                    if (label.getText().equals(taName)
                            || (label.getText().contains(taName + "\n"))
                            || (label.getText().contains("\n" + taName))) {
                        stringArray.add(label.getId().toString());
                        data.removeTAFromCell(label.textProperty(), taName);
                    }
                }
                
                
                
    }

    @Override
    public void undoTransaction() {
        list.add(ta);
        for(int i = 0; i < stringArray.size(); i ++){
            data.toggleTAOfficeHours(stringArray.get(i), ta.getName());
        }
    
    }
   
   
   
   
    
}
