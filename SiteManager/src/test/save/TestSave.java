/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.save;

import java.io.FileNotFoundException;
import java.io.IOException;
import main.workspace.CourseInfo;
import main.workspace.Schedule;
import main.workspace.Student;
import main.workspace.Team;
import tam.file.TimeSlot;
import temp.objects.Recitation;
import temp.objects.SiteTemplate;

/**
 *
 * @author Home
 */
public class TestSave {

//    TempData dataManager;
//    CourseGeneratorApp app;
//    String stime = "";
//    String etime = "";
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
//    static final String JSON_START_HOUR = "startHour";
//    static final String JSON_END_HOUR = "endHour";
//    static final String JSON_OFFICE_HOURS = "officeHours";
//    static final String JSON_DAY = "day";
//    static final String JSON_TIME = "time";
//    static final String JSON_NAME = "name";
//    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
//    static final String JSON_EMAIL = "email";
//    static final String JSON_UNDERGRAD = "undergrad";
    public static void main(String[] args) throws FileNotFoundException, IOException {

        TData tData = new TData();
        TFiles tFiles = new TFiles();
        
        // courseInfo
        
      //  CourseInfo ci = new CourseInfo("CSE", "Spring", "308", "2017", "Software Engineering", "Richard McKenna", "216 New Computer Science Building");

        // siteTemplate
        tData.getSiteTemplates().add(new SiteTemplate(true, "Home", "index.html", "HomeBuilder.js"));
        tData.getSiteTemplates().add(new SiteTemplate(true, "Syllabus", "syllabus.html", "SyllabusBuilder.js"));
        tData.getSiteTemplates().add(new SiteTemplate(true, "Schedule", "schedule.html", "ScheduleBuilder.js"));
        tData.getSiteTemplates().add(new SiteTemplate(true, "HWs", "hws.html", "HWsBuilder.js"));
        tData.getSiteTemplates().add(new SiteTemplate(false, "Projects", "projects.html", "ProjectsBuilder.js"));

        //addTA dfgsf 
        tData.addTA("Aarin Chase", "aarin.chase@stonybrook.edu", Boolean.TRUE);
        tData.addTA("Ali Afzal", "ali.afzal@stonybrook.edu", Boolean.TRUE);
        tData.addTA("Anthony Roy", "anthony.roy@stonybrook.edu", Boolean.TRUE);
        tData.addTA("Bryan Robicheau", "bryan.robicheau@stonybrook.edu", Boolean.TRUE);
        tData.addTA("Calvin Cheng", "calvin.cheng@stonybrook.edu", Boolean.TRUE);
        tData.addTA("Chaeyoung Lee", "chaeyoung.lee@stonybrook.edu", Boolean.TRUE);
        tData.addTA("Jacob Evans", "jacob.evans@stonybrook.edu", Boolean.TRUE);

        //add TineSlot
        tData.getTimeSlots().add(new TimeSlot("MONDAY", "9_30am", "Wonje Kang"));
        tData.getTimeSlots().add(new TimeSlot("TUESDAY", "10_30am", "Wonyong Jeong"));
        tData.getTimeSlots().add(new TimeSlot("THURSDAY", "9_30am", "Jamie Kunzmann"));
        tData.getTimeSlots().add(new TimeSlot("Monday", "12_30pm", "Serge Crane"));
        tData.getTimeSlots().add(new TimeSlot("Thursday", "9_30am", "Calvin Cheng"));
        tData.getTimeSlots().add(new TimeSlot("Friday", "9_30am", "Wonyong Jeong"));
        tData.getTimeSlots().add(new TimeSlot("Monday", "5_30pm", "Wonje Kang"));
        tData.getTimeSlots().add(new TimeSlot("Friday", "4_30pm", "Calvin Cheng"));

        //addRecitation
        tData.getRecitations().add(new Recitation("R02", "McKenna", "Wed 3:30PM-4:23PM", " Old CS 2114", "Jane Doe", "Joe Shmo"));
        tData.getRecitations().add(new Recitation("R05", "Banerjee", "Tue 5:30PM-6:23PM", " Old CS 2114", "", ""));

        //addSchedule
        tData.getSchedules().add(new Schedule("Holiday", "2/9/2017", "", "SNOW DAY", "", "", ""));
        tData.getSchedules().add(new Schedule("Lecture", "2/14/2017", "", "Lecture 3", "Event Programming", "", ""));
        tData.getSchedules().add(new Schedule("Holiday", "3/13/2017", "", "Spring Break", "", "", ""));
        tData.getSchedules().add(new Schedule("HW", "3/27/2017", "", "HW3", "UML", "", ""));

        //addTeam
//        Hyperlink link = new Hyperlink();
//        link.setText("http://atomicomic.com");
//        Hyperlink link2 = new Hyperlink();
//        link2.setText("http://c4-comics.appspot.com");
        tData.getTeams().add(new Team("Atomic Comics", "552211", "fff fff", "http://atomicomic.com"));
        tData.getTeams().add(new Team("C4 Comics", "235399", "fff fff", "http://c4-comics.appspot.com"));

        //addStudents
        tData.getStudents().add(new Student("Beau", "Brummell", "Atomic Comics", "Lead Designer"));
        tData.getStudents().add(new Student("Jane", "Doe", "C4 Comics", "Lead Programmer"));
        tData.getStudents().add(new Student("Noonian", "Soong", "Atomic Comics", "Data Desighner"));

        tFiles.saveData(tData, "work/SiteSave.json");

        System.out.println("DONE");
        // System.out.println(toRGBCode(FFD9A5));

    }

//    public static String toRGBCode( Color color )
//    {
//        return String.format( "#%02X%02X%02X",
//            (int)( color.getRed() * 255 ),
//            (int)( color.getGreen() * 255 ),
//            (int)( color.getBlue() * 255 ) );
//    }
//    **
// * 
// * @param colorStr e.g. "#FFFFFF"
// * @return 
// */
//public static Color hex2Rgb(String colorStr) {
//    return new Color(
//            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
//            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
//            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
//}
}
