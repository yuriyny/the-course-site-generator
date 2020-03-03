/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tam.data.TeachingAssistant;
import test.save.TData;

/**
 *
 * @author Home
 */
public class TAFilesJUnit3Test {

    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_RECITATIONS = "recitations";
    static final String JSON_SCHEDULE = "schedule";
    static final String JSON_TEAM = "teams";
    static final String JSON_STUDENT = "students";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_EMAIL = "email";
    static final String JSON_UNDERGRAD = "undergrad";
    static final String JSON_REC_SECTION = "recitation_section";
    static final String JSON_REC_INSTRUCTOR = "recitation_instructor";
    static final String JSON_REC_TIME = "recitation_time";
    static final String JSON_REC_LOCATION = "recitation_location";
    static final String JSON_REC_TA1 = "recitation_ta1";
    static final String JSON_REC_TA2 = "recitation_ta2";
    static final String JSON_SCHE_TYPE = "schedule_type";
    static final String JSON_SCHE_DATE = "schedule_date";
    static final String JSON_SCHE_TIME = "schedule_time";
    static final String JSON_SCHE_TITLE = "schedule_title";
    static final String JSON_SCHE_TOPIC = "schedule_topic";
    static final String JSON_SCHE_LINK = "schedule_link";
    static final String JSON_SCHE_CRITERIA = "schedule_criteria";
    static final String JSON_TEAM_NAME = "team_name";
    static final String JSON_TEAM_COLOR = "team_color";
    static final String JSON_TEAM_TCOLOR = "team_tcolor";
    static final String JSON_TEAM_LINK = "team_link";
    static final String JSON_STUDENT_FNAME = "student_fname";
    static final String JSON_STUDENT_LNAME = "student_lname";
    static final String JSON_STUDENT_ROLE = "student_role";
    static final String JSON_STUDENT_TEAM = "student_team";
    static final String JSON_MONDAY_MONTH = "smonth";
    static final String JSON_MONDAY_DAY = "sday";
    static final String JSON_MONDAY_YEAR = "syear";
    static final String JSON_FRIDAY_MONTH = "emonth";
    static final String JSON_FRIDAY_DAY = "eday";
    static final String JSON_FRIDAY_YEAR = "eyear";
    static final String JSON_SITE_TEMPLATE = "site_template";
    public final String JSON_SITE_USE = "use";
    public final String JSON_SITE_TITLE = "title";
    public final String JSON_SITE_NAME = "name";
    public final String JSON_SITE_SCRIPT = "script";

    public TAFilesJUnit3Test() {
    }

    /**
     * Test of loadData method, of class TAFiles.
     */
    @Test
    public void testLoadData() throws Exception {
        System.out.println("loadData");
        //   AppDataComponent data = null;
        String filePath = "work/SiteSave.json";
//        TAFiles instance = null;
//        instance.loadData(data, filePath);

        JsonObject json = loadJSONFile(filePath);

        // LOAD THE START AND END HOURS
        String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        // checking starting/ending time for the ta grid pane 
        assertEquals("9", startHour);
        assertEquals("20", endHour);

        // dataManager.initHours(startHour, endHour);
        int stDay = Integer.parseInt(json.getString(JSON_MONDAY_DAY));
        int stMonth = Integer.parseInt(json.getString(JSON_MONDAY_MONTH));
        int stYear = Integer.parseInt(json.getString(JSON_MONDAY_YEAR));
        int enDay = Integer.parseInt(json.getString(JSON_FRIDAY_DAY));
        int enMonth = Integer.parseInt(json.getString(JSON_FRIDAY_MONTH));
        int enYear = Integer.parseInt(json.getString(JSON_FRIDAY_YEAR));
        assertEquals(10, stDay);
        assertEquals(11, stMonth);
        assertEquals(2016, stYear);
        assertEquals(11, enDay);
        assertEquals(06, enMonth);
        assertEquals(2018, enYear);

        //test TA Data
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);

        JsonObject jsonTA = jsonTAArray.getJsonObject(0);
        String name0 = jsonTA.getString(JSON_NAME);
        String email0 = jsonTA.getString(JSON_EMAIL);;
        assertEquals("Aarin Chase", name0);
        assertEquals("aarin.chase@stonybrook.edu", email0);

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        // oha = new OfficeHoursArray();
        for (int i = 0; i < 1; i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            assertEquals("MONDAY", day);
            assertEquals("9_30am", time);
            assertEquals("Wonje Kang", name);
        }

        //siteTemplate
        JsonArray jsonSiteArray = json.getJsonArray(JSON_SITE_TEMPLATE);
        for (int i = 0; i < 1; i++) {
            JsonObject jsonSite = jsonSiteArray.getJsonObject(i);
            boolean use = jsonSite.getBoolean(JSON_SITE_USE);
            String title = jsonSite.getString(JSON_SITE_TITLE);
            String name = jsonSite.getString(JSON_SITE_NAME);
            String script = jsonSite.getString(JSON_SITE_SCRIPT);
            assertEquals(true, use);
            assertEquals("Home", title);
            assertEquals("index.html", name);
            assertEquals("HomeBuilder.js", script);

        }

        // recitations
        JsonArray jsonRecArray = json.getJsonArray(JSON_RECITATIONS);
        for (int i = 0; i < 1; i++) {
            JsonObject jsonRec = jsonRecArray.getJsonObject(i);
            String section = jsonRec.getString(JSON_REC_SECTION);
            String instructor = jsonRec.getString(JSON_REC_INSTRUCTOR);
            String time = jsonRec.getString(JSON_REC_TIME);
            String location = jsonRec.getString(JSON_REC_LOCATION);
            String ta1 = jsonRec.getString(JSON_REC_TA1);
            String ta2 = jsonRec.getString(JSON_REC_TA2);
            assertEquals("R02", section);
            assertEquals("McKenna", instructor);
            assertEquals("Wed 3:30PM-4:23PM", time);
            assertEquals(" Old CS 2114", location);
            assertEquals("Jane Doe", ta1);
            assertEquals("Joe Shmo", ta2);
        }

        // Schedule
        JsonArray jsonScheArray = json.getJsonArray(JSON_SCHEDULE);
        for (int i = 0; i < 1; i++) {
            JsonObject jsonSche = jsonScheArray.getJsonObject(i);
            String type = jsonSche.getString(JSON_SCHE_TYPE);
            String date = jsonSche.getString(JSON_SCHE_DATE);
            String time = jsonSche.getString(JSON_SCHE_TIME);
            String title = jsonSche.getString(JSON_SCHE_TITLE);
            String topic = jsonSche.getString(JSON_SCHE_TOPIC);
            String link = jsonSche.getString(JSON_SCHE_LINK);
            String criteria = jsonSche.getString(JSON_SCHE_CRITERIA);
            assertEquals("Holiday", type);
            assertEquals("2/9/2017", date);
            assertEquals("", time);
            assertEquals("SNOW DAY", title);
            assertEquals("", topic);
            assertEquals("", link);
            assertEquals("", criteria);
        }

        // teams
        JsonArray jsonTeaArray = json.getJsonArray(JSON_TEAM);
        for (int i = 0; i < 1; i++) {
            JsonObject jsonTeam = jsonTeaArray.getJsonObject(i);
            String name = jsonTeam.getString(JSON_TEAM_NAME);
            String color = jsonTeam.getString(JSON_TEAM_COLOR);
            String tcolor = jsonTeam.getString(JSON_TEAM_TCOLOR);
            String link = jsonTeam.getString(JSON_TEAM_LINK);
            assertEquals("Atomic Comics", name);
            assertEquals("552211", color);
            assertEquals("fff fff", tcolor);
            assertEquals("http://atomicomic.com", link);
        }

        // Students
        JsonArray jsonStuArray = json.getJsonArray(JSON_STUDENT);
        for (int i = 0; i < 1; i++) {
            JsonObject jsonStudent = jsonStuArray.getJsonObject(i);
            String fname = jsonStudent.getString(JSON_STUDENT_FNAME);
            String lname = jsonStudent.getString(JSON_STUDENT_LNAME);
            String team = jsonStudent.getString(JSON_STUDENT_TEAM);
            String role = jsonStudent.getString(JSON_STUDENT_ROLE);
            assertEquals("Beau", fname);
            assertEquals("Brummell", lname);
            assertEquals("Atomic Comics", team);
            assertEquals("Lead Designer", role);

        }
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

}
