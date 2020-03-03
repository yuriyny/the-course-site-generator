/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.workspace;

/**
 *
 * @author Home
 */
public class Student {
    private String fName;
    private String lName;
    private String team;
    private String role;
    
    
    public Student(String fName, String lName, String team, String role){
        this.fName = fName;
        this.lName = lName;
        this.team = team;
        this.role = role;
    }

    /**
     * @return the fName
     */
    public String getFName() {
        return fName;
    }

    /**
     * @param fName the fName to set
     */
    public void setFName(String fName) {
        this.fName = fName;
    }

    /**
     * @return the lName
     */
    public String getLName() {
        return lName;
    }

    /**
     * @param lName the lName to set
     */
    public void setLName(String lName) {
        this.lName = lName;
    }

    /**
     * @return the team
     */
    public String getTeam() {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    
}
