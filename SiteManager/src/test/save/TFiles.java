package test.save;

import tam.file.*;
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
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import main.workspace.CourseInfo;
import main.workspace.Schedule;
import main.workspace.Student;
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
public class TFiles implements AppFileComponent {

    // THIS IS THE APP ITSELF
    CourseGeneratorApp app;
    String stime = "";
    String etime = "";

    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_MONDAY_MONTH = "startingMondayMonth";
    static final String JSON_MONDAY_DAY = "startingMondayDay";
    static final String JSON_MONDAY_YEAR = "syear";
    static final String JSON_FRIDAY_MONTH = "endingFridayMonth";
    static final String JSON_FRIDAY_DAY = "endingFridayDay";
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

    public TFiles(CourseGeneratorApp initApp) {
        app = initApp;
    }

    public TFiles() {

    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        // CLEAR THE OLD DATA OUT
        TempData dataManager = (TempData) data;

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        // LOAD THE START AND END HOURS
        String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            dataManager.addTA(name, email, true);
        }

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
//        // recitations
//         JsonArray jsonRecArray = json.getJsonArray(JSON_RECITATIONS);
//        for (int i = 0; i < jsonRecArray.size(); i++) {
//            JsonObject jsonRec = jsonRecArray.getJsonObject(i);
//            String section = jsonRec.getString(JSON_REC_SECTION);
//            String instructor = jsonRec.getString(JSON_REC_INSTRUCTOR);
//            String time = jsonRec.getString(JSON_REC_TIME);
//            String location = jsonRec.getString(JSON_REC_LOCATION);
//            String ta1 = jsonRec.getString(JSON_REC_TA1);
//            String ta2 = jsonRec.getString(JSON_REC_TA2);
//            dataManager.getRecitations.add(section, instructor, time, location, ta1, ta2);
//        }

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
        TData dataManager = (TData) data;

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        //   System.out.println("L" + tas.size());
        for (TeachingAssistant ta : tas) {
            JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail())
                    .add(JSON_UNDERGRAD, ta.getUndergrad())
                    .build();
            taArrayBuilder.add(taJson);
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();

        // NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
//        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
        ObservableList<TimeSlot> officeHours = dataManager.getTimeSlots();
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY, ts.getDay())
                    .add(JSON_TIME, ts.getTime())
                    .add(JSON_NAME, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        // courseInfo
        JsonArrayBuilder cInfoArrayBuilder = Json.createArrayBuilder();
        CourseInfo ci = dataManager.getCi();
        // for (CourseInfo ci : course) {
        JsonObject coJson = Json.createObjectBuilder()
                .add(JSON_COURSE_SUBJECT, ci.getSubject())
                .add(JSON_COURSE_SEMESTER, ci.getSemester())
                .add(JSON_COURSE_NUMBER, ci.getSemester())
                .add(JSON_COURSE_YEAR, ci.getYear())
                .add(JSON_COURSE_TITLE, ci.getTitle())
                .add(JSON_COURSE_INAME, ci.getInsName())
                .add(JSON_COURSE_IHOME, ci.getInsHome())
                .build();
        cInfoArrayBuilder.add(coJson);
        // }
        JsonArray courseArray = cInfoArrayBuilder.build();

        // SiteTemplate
        JsonArrayBuilder siteArrayBuilder = Json.createArrayBuilder();
        ObservableList<SiteTemplate> site = dataManager.getSiteTemplates();
        for (SiteTemplate template : site) {
            JsonObject temJson = Json.createObjectBuilder()
                    .add(JSON_SITE_USE, template.isUse())
                    .add(JSON_SITE_TITLE, template.getTitle())
                    .add(JSON_SITE_NAME, template.getName())
                    .add(JSON_SITE_SCRIPT, template.getScript())
                    .build();
            siteArrayBuilder.add(temJson);
        }
        JsonArray templateArray = siteArrayBuilder.build();

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

        // Schedule
        JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
        ObservableList<Schedule> schedule = dataManager.getSchedules();
        //   System.out.println("res:" + res.size());
        for (Schedule sh : schedule) {
            JsonObject scheJson = Json.createObjectBuilder()
                    .add(JSON_SCHE_TYPE, sh.getType())
                    .add(JSON_SCHE_DATE, sh.getDate())
                    .add(JSON_SCHE_TIME, sh.getTime())
                    .add(JSON_SCHE_TITLE, sh.getTitle())
                    .add(JSON_SCHE_TOPIC, sh.getTopic())
                    .add(JSON_SCHE_LINK, sh.getLink())
                    .add(JSON_SCHE_CRITERIA, sh.getCriteria())
                    .build();
            scheduleArrayBuilder.add(scheJson);
        }
        JsonArray scheduleArray = scheduleArrayBuilder.build();

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
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_MONDAY_DAY, "" + dataManager.getStartMondayDay())
                .add(JSON_MONDAY_MONTH, "" + dataManager.getStartMondayMonth())
                .add(JSON_MONDAY_YEAR, "" + dataManager.getStartMondayYear())
                .add(JSON_FRIDAY_DAY, "" + dataManager.getEndFridayDay())
                .add(JSON_FRIDAY_MONTH, "" + dataManager.getEndFridayMonth())
                .add(JSON_FRIDAY_YEAR, "" + dataManager.getEndFridayYear())
                .add(JSON_COURSE_INFO, courseArray)
                .add(JSON_SITE_TEMPLATE, templateArray)
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .add(JSON_RECITATIONS, recitationsArray)
                .add(JSON_SCHEDULE, scheduleArray)
                .add(JSON_TEAM, teamArray)
                .add(JSON_STUDENT, studentArray)
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
//        ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
//        for (TimeSlot ts : officeHours) {
//            JsonObject tsJson = Json.createObjectBuilder()
//                    .add(JSON_DAY, ts.getDay())
//                    .add(JSON_TIME, ts.getTime())
//                    .add(JSON_NAME, ts.getName()).build();
//            timeSlotArrayBuilder.add(tsJson);
//        }
//        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
//
//        // THEN PUT IT ALL TOGETHER IN A JsonObject
//        JsonObject dataManagerJSO = Json.createObjectBuilder()
//                .add(JSON_START_HOUR, "" + dataManager.getStartHour())
//                .add(JSON_END_HOUR, "" + dataManager.getEndHour())
//                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
//                .add(JSON_OFFICE_HOURS, timeSlotsArray)
//                .build();
//
//        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
//        Map<String, Object> properties = new HashMap<>(1);
//        properties.put(JsonGenerator.PRETTY_PRINTING, true);
//        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
//        StringWriter sw = new StringWriter();
//        JsonWriter jsonWriter = writerFactory.createWriter(sw);
//        jsonWriter.writeObject(dataManagerJSO);
//        jsonWriter.close();
//
//        // INIT THE WRITER
//        OutputStream os = new FileOutputStream(filePath + File.separator + "js" + File.separator + "OfficeHoursGridData.json");
//        JsonWriter jsonFileWriter = Json.createWriter(os);
//        jsonFileWriter.writeObject(dataManagerJSO);
//        String prettyPrinted = sw.toString();
//        PrintWriter pw = new PrintWriter(filePath + File.separator + "js" + File.separator + "OfficeHoursGridData.json");
//        pw.write(prettyPrinted);
//        pw.close();
//        infoAlert();
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
