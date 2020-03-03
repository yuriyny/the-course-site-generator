/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.util.ArrayList;

/**
 *
 * @author yury
 */
public class GridClass {
    
    private ArrayList<GridTime> array;
    
    public GridClass(){
        array = new ArrayList<GridTime>();
    }
    
    public void addTime(GridTime time){
        array.add(time);
    }
    public GridTime getTime(int mTime){
        for(int i = 0; i < array.size(); i++){
            if(array.get(i).getMilitaryTime() == mTime){
                return array.get(i);
            }
        }
        return null;
    }
    public GridTime getATime(String aTime){
        for(int i = 0; i < array.size(); i++){
            if(array.get(i).getAmericanTime().equals(aTime)){
                return array.get(i);
            }
        }
        return null;
    }
    
    
}
