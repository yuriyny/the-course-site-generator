/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

/**
 *
 * @author Yuriy Burshtyko
 */
public class GridTime {

    private int militaryTime;
    private String americanTime;

    public GridTime(int militaryTime, String americanTime) {
        this.militaryTime = militaryTime;
        this.americanTime = americanTime;
    }

    /**
     * @return the militaryTime
     */
    public int getMilitaryTime() {
        return militaryTime;
    }

    /**
     * @param militaryTime the militaryTime to set
     */
    public void setMilitaryTime(int militaryTime) {
        this.militaryTime = militaryTime;
    }

    /**
     * @return the americanTime
     */
    public String getAmericanTime() {
        return americanTime;
    }

    /**
     * @param americanTime the americanTime to set
     */
    public void setAmericanTime(String americanTime) {
        this.americanTime = americanTime;
    }
    

    
  
}
