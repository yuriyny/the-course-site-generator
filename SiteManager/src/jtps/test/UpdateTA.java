/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps.test;

import jtps.jTPS_Transaction;
import main.workspace.TabWorkspace;
import main.workspace.TempData;
import tam.data.TeachingAssistant;

/**
 *
 * @author Home
 */
public class UpdateTA implements jTPS_Transaction {

    private TabWorkspace workspace;
    private TempData data;
    private String initName;
    private String initEmail;
    private String name;
    private String email;
    private Boolean undergrad;
    private TeachingAssistant tq;
    private TeachingAssistant tz;

    public UpdateTA(String initName, String initEmail, String name, String email, TempData data, TabWorkspace workspace) {
        this.initName = initName;
        this.initEmail = initEmail;
        this.name = name;
        this.email = email;
        this.data = data;
        this.workspace = workspace;
    }

    @Override
    public void doTransaction() {
        if (!data.containsTAName(name)) {
            data.getTA(initName).setName(name);
        } else if (!data.containsTAEmail(email)) {
            data.getTA(name).setEmail(email);
        }

        if (!(initName.equals(name)) || !(initEmail.equals(email))) {
            tq = new TeachingAssistant(initName, initEmail, true);
            tz = new TeachingAssistant(data.getTA(name).getName(), data.getTA(name).getEmail(), true);
            workspace.getTATable().getItems().remove(data.getTA(name));
            workspace.getTATable().getItems().add(tz);

            data.updateTA(tq, tz);

        }
    }

    @Override
    public void undoTransaction() {
        if (!data.containsTAName(initName)) {
            data.getTA(name).setName(initName);
        } else if (!data.containsTAEmail(initEmail)) {
            data.getTA(initName).setEmail(initEmail);
        }

        if (!(name.equals(initName)) || !(email.equals(initEmail))) {
            tq = new TeachingAssistant(name, email,true);
            tz = new TeachingAssistant(data.getTA(initName).getName(), data.getTA(initName).getEmail(),true);
            workspace.getTATable().getItems().remove(data.getTA(initName));
            workspace.getTATable().getItems().add(tz);

            data.updateTA(tq, tz);

        }
        
    }

}
