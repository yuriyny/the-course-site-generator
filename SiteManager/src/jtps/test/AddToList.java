/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps.test;

import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;
import main.workspace.TempData;
import tam.data.TeachingAssistant;

/**
 *
 * @author yury
 */
public class AddToList implements jTPS_Transaction{
   private String name;
   private String email;
   private TempData data;
   private boolean undergrad;
   private ObservableList<TeachingAssistant> teachingAssistants;
    
    
    public AddToList(String name, String email, TempData data, boolean undergrad ){
        this.name = name;
        this.email = email;
        this.data = data;
        this.undergrad = undergrad;
        this.teachingAssistants = data.getTeachingAssistants();
    }

    @Override
    public void doTransaction() {
        data.addTA(name, email, undergrad);
    }

    @Override
    public void undoTransaction() {
        for(int i = 0; i < teachingAssistants.size(); i++){
            if(teachingAssistants.get(i).getName().equals(name)){
                teachingAssistants.remove(i);
            }
        }
    }
    
    
}
