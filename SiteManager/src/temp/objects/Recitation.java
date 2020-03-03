/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.objects;

/**
 *
 * @author yuriy
 */
public class Recitation {
    private String section;
    private String instructor;
    private String time;
    private String location;
    private String ta;
    private String ta2;
    
    
    public Recitation(String section, String instructor, String time, String location, String ta, String ta2 ){
        this.section = section;
        this.instructor = instructor;
        this.time = time;
        this.location = location;
        this.ta = ta;
        this.ta2 = ta2;
        
    }

    /**
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the instructor
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * @param instructor the instructor to set
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the ta1
     */
    public String getTa() {
        return ta;
    }

    /**
     * @param ta1 the ta1 to set
     */
    public void setTa1(String ta1) {
        this.ta = ta1;
    }

    /**
     * @return the ta2
     */
    public String getTa2() {
        return ta2;
    }

    /**
     * @param ta2 the ta2 to set
     */
    public void setTa2(String ta2) {
        this.ta2 = ta2;
    }
    
    
}
