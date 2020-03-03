/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps.test;

import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import jtps.jTPS_Transaction;
import main.workspace.TATab;
import main.workspace.TempData;
import properties_manager.PropertiesManager;
import tam.CourseGeneratorProp;
import tam.file.TimeSlot;

/**
 *
 * @author Home
 */
public class ComboButton implements jTPS_Transaction {

    private int fSTime;
    private int fETime;
    private int sTime;
    private int eTime;
    private TempData data;
    private TATab workspace;
    private ArrayList<TimeSlot> officeHours;
    private ArrayList<TimeSlot> t;

    public ComboButton(int fSTime, int fETime, int sTime, int eTime, TempData data, TATab workspace) {
        this.fSTime = fSTime;
        this.fETime = fETime;
        this.sTime = sTime;
        this.eTime = eTime;
        this.data = data;
        this.workspace = workspace;
        officeHours = TimeSlot.buildOfficeHoursList(data);

    }

    @Override
    public void doTransaction() {
        t = officeHours;
        ArrayList<TimeSlot> arr = new ArrayList<>();

        for (int i = 0; i < officeHours.size(); i++) {
            String day = officeHours.get(i).getDay();
            String time = officeHours.get(i).getTime();
            String name = officeHours.get(i).getName();
            String mainTime = "";
            int finalTime;
            String newTime[] = time.split("_");

            if (!time.contains("12")) {
                if (time.contains("_00am")) {
                    mainTime = newTime[0] + "00";
                } else if (time.contains("_30am")) {
                    mainTime = newTime[0] + "30";
                } else if (time.contains("_00pm")) {
                    mainTime = Integer.toString(Integer.parseInt(newTime[0]) + 12) + "00";
                } else if (time.contains("_30pm")) {
                    mainTime = Integer.toString(Integer.parseInt(newTime[0]) + 12) + "30";
                }

            } else if (time.contains("00am")) {
                mainTime = Integer.toString(Integer.parseInt(newTime[0]) - 12) + "00";
            } else if (time.contains("30am")) {
                mainTime = Integer.toString(Integer.parseInt(newTime[0]) - 12) + "00";
            } else if (time.contains("00pm")) {
                mainTime = Integer.toString(Integer.parseInt(newTime[0])) + "00";
            } else if (time.contains("30pm")) {
                mainTime = Integer.toString(Integer.parseInt(newTime[0])) + "30";
            }

            finalTime = Integer.parseInt(mainTime);

            if (finalTime >= sTime * 100 && finalTime < eTime * 100) {
                arr.add(officeHours.get(i));
            }

        }

        if (arr.size() < officeHours.size()) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String info2Text1 = props.getProperty(CourseGeneratorProp.INFO2_TEXT1.toString());
            String info2Text2 = props.getProperty(CourseGeneratorProp.INFO2_TEXT2.toString());
            String info2Text3 = props.getProperty(CourseGeneratorProp.INFO2_TEXT3.toString());
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(info2Text1);
            alert.setHeaderText(info2Text2);
            alert.setContentText(info2Text3);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                workspace.resetWorkspace();
                data.initHours(Integer.toString(sTime), Integer.toString(eTime));
                workspace.getStartTime().getSelectionModel().select(workspace.getGc().getTime(sTime).getAmericanTime());
                workspace.getEndTime().getSelectionModel().select(workspace.getGc().getTime(eTime).getAmericanTime());

                for (int i = 0; i < arr.size(); i++) {
                    String day1 = arr.get(i).getDay();
                    String time1 = arr.get(i).getTime();
                    String name1 = arr.get(i).getName();
                    data.addOfficeHoursReservation(day1, time1, name1);
                }
            } else {
                infoAlert();
            }
        } else {
            workspace.resetWorkspace();
            data.initHours(Integer.toString(sTime), Integer.toString(eTime));
            workspace.getStartTime().getSelectionModel().select(workspace.getGc().getTime(sTime).getAmericanTime());
            workspace.getEndTime().getSelectionModel().select(workspace.getGc().getTime(eTime).getAmericanTime());
            for (int i = 0; i < arr.size(); i++) {
                String day1 = arr.get(i).getDay();
                String time1 = arr.get(i).getTime();
                String name1 = arr.get(i).getName();
                data.addOfficeHoursReservation(day1, time1, name1);

            }
        }

    }

    @Override
    public void undoTransaction() {
        workspace.resetWorkspace();
        data.initHours(Integer.toString(fSTime), Integer.toString(fETime));
        workspace.getStartTime().getSelectionModel().select(workspace.getGc().getTime(fSTime).getAmericanTime());
        workspace.getEndTime().getSelectionModel().select(workspace.getGc().getTime(fETime).getAmericanTime());

        for (int i = 0; i < t.size(); i++) {
            String day1 = t.get(i).getDay();
            String time1 = t.get(i).getTime();
            String name1 = t.get(i).getName();
            data.addOfficeHoursReservation(day1, time1, name1);
        }

    }

    public void infoAlert() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String infoText1 = props.getProperty(CourseGeneratorProp.INFO_TEXT1.toString());
        String infoText2 = props.getProperty(CourseGeneratorProp.INFO_TEXT2.toString());
        String infoText3 = props.getProperty(CourseGeneratorProp.INFO_TEXT3.toString());
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(infoText1);
        alert.setHeaderText(infoText2);
        alert.setContentText(infoText3);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        }
    }

}
