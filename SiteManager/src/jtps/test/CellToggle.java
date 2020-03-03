/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps.test;

import jtps.jTPS_Transaction;
import main.workspace.TempData;

/**
 *
 * @author Home
 */
public class CellToggle implements jTPS_Transaction{

   // private TeachingAssistant ta;
    private String taName;
    private TempData data;
    private String cellKey;

    public CellToggle(String taName, TempData data, String cellKey) {
        //this.ta = ta;
        this.taName = taName;
        this.data = data;
        this.cellKey = cellKey;

    }

    @Override
    public void doTransaction() {
        data.toggleTAOfficeHours(cellKey, taName);
    }

    @Override
    public void undoTransaction() {
        data.toggleTAOfficeHours(cellKey, taName);
    }
    

}
