package tam.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import main.workspace.CourseTab;
import main.workspace.ProjectTab;
import main.workspace.RecitationTab;
import main.workspace.Schedule;
import main.workspace.ScheduleTab;
import main.workspace.Student;
import main.workspace.TabWorkspace;
import main.workspace.Team;
import main.workspace.TempData;
import tam.CourseGeneratorApp;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;
import tam.CourseGeneratorProp;

import tam.data.TeachingAssistant;
import temp.objects.Recitation;
import temp.objects.SiteTemplate;

/**
 * This class serves as the file component for the TA manager app. It provides
 * all saving and loading services for the application.
 *
 * @author Richard McKenna
 */
public class TAFiles implements AppFileComponent {

    // THIS IS THE APP ITSELF
    CourseGeneratorApp app;
    String stime = "";
    String etime = "";

    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_MONDAY_MONTH = "smonth";
    static final String JSON_MONDAY_DAY = "sday";
    static final String JSON_MONDAY_YEAR = "syear";
    static final String JSON_FRIDAY_MONTH = "emonth";
    static final String JSON_FRIDAY_DAY = "eday";
    static final String JSON_FRIDAY_YEAR = "eyear";
    static final String JSON_COURSE_INFO = "course_info";
    static final String JSON_COURSE_SUBJECT = "subject";
    static final String JSON_COURSE_SEMESTER = "semester";
    static final String JSON_COURSE_NUMBER = "number";
    static final String JSON_COURSE_YEAR = "year";
    static final String JSON_COURSE_TITLE = "title";
    static final String JSON_COURSE_INAME = "insName";
    static final String JSON_COURSE_IHOME = "insHome";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_SITE_TEMPLATE = "site_template";
    public final String JSON_SITE_USE = "use";
    public final String JSON_SITE_TITLE = "title";
    public final String JSON_SITE_NAME = "name";
    public final String JSON_SITE_SCRIPT = "script";
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
    static final String JSON_REC_SECTION = "section";
    static final String JSON_REC_INSTRUCTOR = "recitation_instructor";
    static final String JSON_REC_TIME = "day_time";
    static final String JSON_REC_LOCATION = "location";
    static final String JSON_REC_TA1 = "ta_1";
    static final String JSON_REC_TA2 = "ta_2";
    static final String JSON_SCHE_TYPE = "schedule_type";
    static final String JSON_SCHE_DATE = "schedule_date";
    static final String JSON_SCHE_TIME = "schedule_time";
    static final String JSON_SCHE_TITLE = "schedule_title";
    static final String JSON_SCHE_TOPIC = "schedule_topic";
    static final String JSON_SCHE_LINK = "schedule_link";
    static final String JSON_SCHE_CRITERIA = "schedule_criteria";
    static final String JSON_TEAM_NAME = "name";
    static final String JSON_TEAM_COLOR = "color";
    static final String JSON_TEAM_TCOLOR = "text_color";
    static final String JSON_TEAM_LINK = "team_link";
    static final String JSON_STUDENT_FNAME = "first_name";
    static final String JSON_STUDENT_LNAME = "last_name";
    static final String JSON_STUDENT_ROLE = "role";
    static final String JSON_STUDENT_TEAM = "team";
    public TAFiles(CourseGeneratorApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        // CLEAR THE OLD DATA OUT
        TempData dataManager = (TempData) data;
        TabWorkspace tw = (TabWorkspace) app.getWorkspaceComponent();
        ScheduleTab scheduleTab = tw.getSt();
        RecitationTab recitationTab = tw.getRt();
        ProjectTab projectTab = tw.getPt();
        CourseTab ct = tw.getCd();
        //  scheduleTab.getEndPicker().setValue(LocalDate.of(Integer.parseInt(JSON_MONDAY_YEAR), 11, 10));
//        scheduleTab.getStartPicker().setValue(LocalDate.of(2012, 11, 10));

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        // LOAD THE START AND END HOURS
        String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        Integer stDay = Integer.parseInt(json.getString(JSON_MONDAY_DAY));
        Integer stMonth = Integer.parseInt(json.getString(JSON_MONDAY_MONTH));
        Integer stYear = Integer.parseInt(json.getString(JSON_MONDAY_YEAR));
        Integer enDay = Integer.parseInt(json.getString(JSON_FRIDAY_DAY));
        Integer enMonth = Integer.parseInt(json.getString(JSON_FRIDAY_MONTH));
        Integer enYear = Integer.parseInt(json.getString(JSON_FRIDAY_YEAR));

        scheduleTab.getStartPicker().setValue(LocalDate.of(stYear, stMonth, stDay));
        scheduleTab.getEndPicker().setValue(LocalDate.of(enYear, enMonth, enDay));

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            dataManager.addTA(name, email, true);
            // TA comboboxes
            recitationTab.getTa1Box().getItems().add(name);
            recitationTab.getTa2Box().getItems().add(name);
        }
//        recitationTab.getTa1Box().getSelectionModel().selectFirst();
//        recitationTab.getTa2Box().getSelectionModel().selectFirst();

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        // oha = new OfficeHoursArray();
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
        //courseInfo
        JsonArray jsoncArray = json.getJsonArray(JSON_COURSE_INFO);
        for (int i = 0; i < jsoncArray.size(); i++) {
            JsonObject jsonSite = jsoncArray.getJsonObject(i);
            
            ct.getSubjectBox().getItems().add(jsonSite.getString(JSON_COURSE_SUBJECT));
            ct.getSubjectBox().getSelectionModel().selectFirst();
            ct.getSemesterBox().getItems().add(jsonSite.getString(JSON_COURSE_SEMESTER));
            ct.getSemesterBox().getSelectionModel().selectFirst();
            ct.getNumberBox().getItems().add(jsonSite.getString(JSON_COURSE_NUMBER));
            ct.getNumberBox().getSelectionModel().selectFirst();
            ct.getYearBox().getItems().add(jsonSite.getString(JSON_COURSE_YEAR));
            ct.getYearBox().getSelectionModel().selectFirst();
            ct.getTitleFld().setText(jsonSite.getString(JSON_COURSE_TITLE));
            ct.getInstructorNameFld().setText(jsonSite.getString(JSON_COURSE_INAME));
            ct.getInstructorHomeFld().setText(jsonSite.getString(JSON_COURSE_IHOME));
            
          

        }

        //siteTemplate
        JsonArray jsonSiteArray = json.getJsonArray(JSON_SITE_TEMPLATE);
        for (int i = 0; i < jsonSiteArray.size(); i++) {
            JsonObject jsonSite = jsonSiteArray.getJsonObject(i);
            boolean use = jsonSite.getBoolean(JSON_SITE_USE);
            String title = jsonSite.getString(JSON_SITE_TITLE);
            String name = jsonSite.getString(JSON_SITE_NAME);
            String script = jsonSite.getString(JSON_SITE_SCRIPT);

            dataManager.getSiteTemplates().add(new SiteTemplate(use, title, name, script));

        }

        // recitations
        JsonArray jsonRecArray = json.getJsonArray(JSON_RECITATIONS);
        for (int i = 0; i < jsonRecArray.size(); i++) {
            JsonObject jsonRec = jsonRecArray.getJsonObject(i);
            String section = jsonRec.getString(JSON_REC_SECTION);
            String instructor = jsonRec.getString(JSON_REC_INSTRUCTOR);
            String time = jsonRec.getString(JSON_REC_TIME);
            String location = jsonRec.getString(JSON_REC_LOCATION);
            String ta1 = jsonRec.getString(JSON_REC_TA1);
            String ta2 = jsonRec.getString(JSON_REC_TA2);
            dataManager.getRecitations().add(new Recitation(section, instructor, time, location, ta1, ta2));

        }

        // Schedule
        JsonArray jsonScheArray = json.getJsonArray(JSON_SCHEDULE);
        for (int i = 0; i < jsonScheArray.size(); i++) {
            JsonObject jsonSche = jsonScheArray.getJsonObject(i);
            String type = jsonSche.getString(JSON_SCHE_TYPE);
            String date = jsonSche.getString(JSON_SCHE_DATE);
            String time = jsonSche.getString(JSON_SCHE_TIME);
            String title = jsonSche.getString(JSON_SCHE_TITLE);
            String topic = jsonSche.getString(JSON_SCHE_TOPIC);
            String link = jsonSche.getString(JSON_SCHE_LINK);
            String criteria = jsonSche.getString(JSON_SCHE_CRITERIA);
            dataManager.getSchedules().add(new Schedule(type, date, time, title, topic, link, criteria));
        }

        // teams
        JsonArray jsonTeaArray = json.getJsonArray(JSON_TEAM);
        for (int i = 0; i < jsonTeaArray.size(); i++) {
            JsonObject jsonTeam = jsonTeaArray.getJsonObject(i);
            String name = jsonTeam.getString(JSON_TEAM_NAME);
            String color = jsonTeam.getString(JSON_TEAM_COLOR);
            String tcolor = jsonTeam.getString(JSON_TEAM_TCOLOR);
            String link = jsonTeam.getString(JSON_TEAM_LINK);
            //  System.out.println(name);
            dataManager.getTeams().add(new Team(name, color, tcolor, link));
            // team combobox
            projectTab.getTeamBox().getItems().add(name);
        }

        // Students
        JsonArray jsonStuArray = json.getJsonArray(JSON_STUDENT);
        for (int i = 0; i < jsonStuArray.size(); i++) {
            JsonObject jsonStudent = jsonStuArray.getJsonObject(i);
            String fname = jsonStudent.getString(JSON_STUDENT_FNAME);
            String lname = jsonStudent.getString(JSON_STUDENT_LNAME);
            String team = jsonStudent.getString(JSON_STUDENT_TEAM);
            String role = jsonStudent.getString(JSON_STUDENT_ROLE);
            // System.out.println(name);
            dataManager.getStudents().add(new Student(fname, lname, team, role));
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

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        // GET THE DATA
        TempData dataManager = (TempData) data;

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail()).build();
            taArrayBuilder.add(taJson);
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();

        // NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY, ts.getDay())
                    .add(JSON_TIME, ts.getTime())
                    .add(JSON_NAME, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .build();

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(dataManagerJSO);
        jsonWriter.close();

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath);
        pw.write(prettyPrinted);
        pw.close();
    }

    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        String currentPath = System.getProperty("user.dir");
        File file = new File(currentPath + File.separator + "public_html");
        File distination = new File(filePath);
        FileUtils.copyDirectory(file, distination);

        TempData dataManager = (TempData) data;

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail()).build();
            taArrayBuilder.add(taJson);
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();

        // NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY, ts.getDay())
                    .add(JSON_TIME, ts.getTime())
                    .add(JSON_NAME, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .build();
        //Team JSON 
        
        // Team
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        ObservableList<Team> team = dataManager.getTeams();
        for (Team te : team) {
            JsonObject tJson = Json.createObjectBuilder()
                    .add(JSON_TEAM_NAME, te.getName())
                    .add(JSON_TEAM_COLOR, te.getColor())
                    .add(JSON_TEAM_TCOLOR, te.getTextColor())
                    .add(JSON_TEAM_LINK, te.getLink())
                    .build();
            teamArrayBuilder.add(tJson);
        }
        JsonArray teamArray = teamArrayBuilder.build();

        // Students
        JsonArrayBuilder studentsArrayBuilder = Json.createArrayBuilder();
        ObservableList<Student> students = dataManager.getStudents();
        //   System.out.println("res:" + res.size());
        for (Student st : students) {
            JsonObject sJson = Json.createObjectBuilder()
                    .add(JSON_STUDENT_FNAME, st.getFName())
                    .add(JSON_STUDENT_LNAME, st.getLName())
                    .add(JSON_STUDENT_TEAM, st.getTeam())
                    .add(JSON_STUDENT_ROLE, st.getRole())
                    .build();
            studentsArrayBuilder.add(sJson);
        }
        JsonArray studentArray = studentsArrayBuilder.build();

        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO2 = Json.createObjectBuilder()
                
                .add(JSON_TEAM, teamArray)
                .add(JSON_STUDENT, studentArray)
                .build();
        
        ////
        // Recitations
        JsonArrayBuilder resArrayBuilder = Json.createArrayBuilder();
        ObservableList<Recitation> res = dataManager.getRecitations();
        System.out.println("res:" + res.size());
        for (Recitation recitation : res) {
            JsonObject resJson = Json.createObjectBuilder()
                    .add(JSON_REC_SECTION, recitation.getSection())
                    .add(JSON_REC_INSTRUCTOR, recitation.getInstructor())
                    .add(JSON_REC_TIME, recitation.getTime())
                    .add(JSON_REC_LOCATION, recitation.getLocation())
                    .add(JSON_REC_TA1, recitation.getTa())
                    .add(JSON_REC_TA2, recitation.getTa2())
                    .build();
            resArrayBuilder.add(resJson);
        }
        JsonArray recitationsArray = resArrayBuilder.build();
        
        // THEN PUT IT ALL TOGETHER IN A JsonObject
        JsonObject dataManagerJSO3 = Json.createObjectBuilder()
                
                .add(JSON_RECITATIONS, recitationsArray)
                
                .build();
        
        ////

        

        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(dataManagerJSO);
        jsonWriter.close();

        // INIT THE WRITER
        OutputStream os = new FileOutputStream(filePath + File.separator + "js" + File.separator + "OfficeHoursGridData.json");
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(dataManagerJSO);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(filePath + File.separator + "js" + File.separator + "OfficeHoursGridData.json");
        pw.write(prettyPrinted);
        pw.close();
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties2 = new HashMap<>(1);
        properties2.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory2 = Json.createWriterFactory(properties2);
        StringWriter sw2 = new StringWriter();
        JsonWriter jsonWriter2 = writerFactory2.createWriter(sw2);
        jsonWriter2.writeObject(dataManagerJSO2);
        jsonWriter2.close();

        // INIT THE WRITER
        OutputStream os2 = new FileOutputStream(filePath + File.separator + "js" + File.separator + "TeamsAndStudents.json");
        JsonWriter jsonFileWriter2 = Json.createWriter(os2);
        jsonFileWriter2.writeObject(dataManagerJSO2);
        String prettyPrinted2 = sw2.toString();
        PrintWriter pw2 = new PrintWriter(filePath + File.separator + "js" + File.separator + "TeamsAndStudents.json");
        pw2.write(prettyPrinted2);
        pw2.close();
        
        ///
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
        Map<String, Object> properties3 = new HashMap<>(1);
        properties3.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory3 = Json.createWriterFactory(properties3);
        StringWriter sw3 = new StringWriter();
        JsonWriter jsonWriter3 = writerFactory3.createWriter(sw3);
        jsonWriter3.writeObject(dataManagerJSO3);
        jsonWriter3.close();

        // INIT THE WRITER
        OutputStream os3 = new FileOutputStream(filePath + File.separator + "js" + File.separator + "RecitationsData.json");
        JsonWriter jsonFileWriter3 = Json.createWriter(os3);
        jsonFileWriter3.writeObject(dataManagerJSO3);
        String prettyPrinted3 = sw3.toString();
        PrintWriter pw3 = new PrintWriter(filePath + File.separator + "js" + File.separator + "RecitationsData.json");
        pw3.write(prettyPrinted3);
        pw3.close();
        
        
        infoAlert();
    }

    private void infoAlert() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String error3Text1 = props.getProperty(CourseGeneratorProp.ERROR3_TEXT1.toString());
        String error3Text2 = props.getProperty(CourseGeneratorProp.ERROR3_TEXT2.toString());
        String error3Text3 = props.getProperty(CourseGeneratorProp.ERROR3_TEXT3.toString());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(error3Text1);
        alert.setHeaderText(error3Text2);
        alert.setContentText(error3Text3);

        alert.showAndWait();
    }

}
