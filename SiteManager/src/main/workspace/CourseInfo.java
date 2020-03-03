/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.workspace;

/**
 *
 * @author Yuriy Burshtyko
 */
public class CourseInfo {
    private String subject;
    private String semester;
    private String number;
    private String year;
    private String title;
    private String insName;
    private String insHome;
    
    public CourseInfo(String subject, String semester, String number, String year, String title, String insName, String insHome){
        this.subject = subject;
        this.semester = semester;
        this.number = number;
        this.year = year;
        this.title = title;
        this.insHome = insHome;
        this.insName = insName;
        
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * @param semester the semester to set
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the insName
     */
    public String getInsName() {
        return insName;
    }

    /**
     * @param insName the insName to set
     */
    public void setInsName(String insName) {
        this.insName = insName;
    }

    /**
     * @return the insHome
     */
    public String getInsHome() {
        return insHome;
    }

    /**
     * @param insHome the insHome to set
     */
    public void setInsHome(String insHome) {
        this.insHome = insHome;
    }
    
    
            
    
}
