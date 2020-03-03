package main.workspace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import jtps.test.RemoveTA;
import properties_manager.PropertiesManager;
import tam.CourseGeneratorApp;
import tam.CourseGeneratorProp;
import tam.data.TeachingAssistant;
import temp.objects.Recitation;
import temp.objects.SiteTemplate;

/**
 * This is the data component for CourseGeneratorApp. It has all the data needed to be
 * set by the user via the User Interface and file I/O can set and get all the
 * data from this object
 *
 * @author Richard McKenna
 */
public class TempData implements AppDataComponent {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    CourseGeneratorApp app;
    private static jTPS jTPS;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<TeachingAssistant> teachingAssistants;
    ObservableList<SiteTemplate> siteTemplates;
    ObservableList<Recitation> recitations;
    ObservableList<Schedule> schedules;
    ObservableList<Team> teams;
    ObservableList<Student> students;

    // THIS WILL STORE ALL THE OFFICE HOURS GRID DATA, WHICH YOU
    // SHOULD NOTE ARE StringProperty OBJECTS THAT ARE CONNECTED
    // TO UI LABELS, WHICH MEANS IF WE CHANGE VALUES IN THESE
    // PROPERTIES IT CHANGES WHAT APPEARS IN THOSE LABELS
    HashMap<String, StringProperty> officeHours;

    // THESE ARE THE LANGUAGE-DEPENDENT VALUES FOR
    // THE OFFICE HOURS GRID HEADERS. NOTE THAT WE
    // LOAD THESE ONCE AND THEN HANG ON TO THEM TO
    // INITIALIZE OUR OFFICE HOURS GRID
    ArrayList<String> gridHeaders;

    // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
    // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
    // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
    // NO MEANS FOR CHANGING THESE VALUES
    int startHour;
    int endHour;

    // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public static final int MIN_START_HOUR = 9;
    public static final int MAX_END_HOUR = 20;

    /**
     * This constructor will setup the required data structures for use, but
     * will have to wait on the office hours grid, since it receives the
     * StringProperty objects from the Workspace.
     *
     * @param initApp The application this data manager belongs to.
     */
    public TempData(CourseGeneratorApp initApp) {
        jTPS = new jTPS();
        // KEEP THIS FOR LATER
        app = initApp;

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();
        siteTemplates = FXCollections.observableArrayList();
        recitations = FXCollections.observableArrayList();
        schedules = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;

        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();

        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(CourseGeneratorProp.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(CourseGeneratorProp.DAYS_OF_WEEK);
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);
    }
    
//    public void removeTA(TeachingAssistant o){
//        teachingAssistants.remove(o);
//        
//        for(int i = 1; i < 23; i ++){
//            for(int j = 2; j < 7; j ++){
//                String cellKey = getCellKey(j, i);
//                String name = o.getName();
//                if(isInCell(cellKey, name)){
//                    StringProperty cellProp = officeHours.get(cellKey);
//                    removeTAFromCell(cellProp, name);
//                }
//            }
//        }
//    }


    /**
     * Called each time new work is created or loaded, it resets all data and
     * data structures such that they can be used for new values.
     */
    @Override
    public void resetData() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        officeHours.clear();
        jTPS = new jTPS();
    }

    // ACCESSOR METHODS
    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setStartHour(int x) {
        startHour = x;
    }

    public void setEndHour(int x) {
        endHour = x;
    }

    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }

    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }
    public ObservableList getSiteTemplates() {
        return siteTemplates;
    }
    public ObservableList getRecitations() {
        return recitations;
    }
    
    public ObservableList getSchedules() {
        return schedules;
    }
    public ObservableList getTeams() {
        return teams;
    }
    public ObservableList getStudents() {
        return students;
    }

    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }

    public int getNumRows() {
        return ((endHour - startHour) * 2) + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));

        int milHour = hour;
        //
        if (time.contains("am") && time.contains("12")) {
            milHour = 0;
        } else if (time.contains("pm")) {
            milHour += 12;
            if (milHour == 24) {
                milHour = 12;
            }
        }
        //
//        if (hour < startHour)
//            milHour += 12;
        row += (milHour - startHour) * 2;
        if (time.contains("_30")) {
            row += 1;
        }
        if (row < 1) {
            row = -1;
        }
        // System.out.println("Row: " + row);
        return getCellKey(col, row);
    }

    public TeachingAssistant getTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }

    public boolean findTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is for giving this data manager the string property for a
     * given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);
    }

    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
            int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }

    private void initOfficeHours(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;

        // EMPTY THE CURRENT OFFICE HOURS VALUES
        officeHours.clear();

        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        TabWorkspace workspaceComponent = (TabWorkspace) app.getWorkspaceComponent();
        workspaceComponent.reloadOfficeHoursGrid(this);
    }

    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
//        if ((initStartHour >= MIN_START_HOUR)
//                && (initEndHour <= MAX_END_HOUR)
//                && (initStartHour <= initEndHour)) {
        if ((initStartHour >= 0)
                && (initEndHour <= 23)
                && (initStartHour <= initEndHour)) {
            // THESE ARE VALID HOURS SO KEEP THEM

            initOfficeHours(initStartHour, initEndHour);
        }
    }

    public boolean containsTA(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsTAName(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return true;
            }

        }
        return false;
    }

    public boolean containsTAEmail(String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getEmail().equals(testEmail)) {
                return true;
            }

        }
        return false;
    }

    public void addTA(String initName, String initEmail, Boolean initUndergrad) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail, initUndergrad);
//        jTPS_Transaction transaction = new AddToList(teachingAssistants, ta);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            teachingAssistants.add(ta);
//            jTPS_Transaction transaction = new AddToList(teachingAssistants, ta);
//            getjTPS().addTransaction(transaction);
        }

        // SORT THE TAS
        Collections.sort(teachingAssistants);
    }

    public void undoTransactionTA() {
        getjTPS().undoTransaction();

    }

    public void doTransactionTA() {
        getjTPS().doTransaction();

    }

    public void removeTA(String name, HashMap<String, Label> labels) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (name.equals(ta.getName())) {
                TabWorkspace workspace = (TabWorkspace) app.getWorkspaceComponent();
                TempData data = (TempData) app.getDataComponent();

                jTPS_Transaction transaction = new RemoveTA(teachingAssistants, ta, data, workspace, labels);
                getjTPS().addTransaction(transaction);

                // teachingAssistants.remove(ta);
                return;
            }
        }
    }

    public void addOfficeHoursReservation(String day, String time, String taName) {
        String cellKey = getCellKey(day, time);
        //  System.out.println("HOW IT SHOULD LOOK: "+cellKey);
        toggleTAOfficeHours(cellKey, taName);
    }

    public void updateTA(TeachingAssistant o, TeachingAssistant o2) {
        //  teachingAssistants.remove(o);

        for (int i = 1; i < getNumRows(); i++) {
            for (int j = 2; j < 7; j++) {
                String cellKey = getCellKey(j, i);
                String name = o.getName();
                if (isInCell(cellKey, name)) {
                    StringProperty cellProp = officeHours.get(cellKey);
                    replaceTAFromCell(cellProp, name, o2.getName());
                }
            }
        }
    }

    public boolean isInCell(String cellKey, String name) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();
        String[] cellArr = cellText.split("\n");
        for (int i = 0; i < cellArr.length; i++) {
            if (cellArr[i].equals(name)) {
                return true;
            }
        }
        return false;

    }

    /**
     * This function toggles the taName in the cell represented by cellKey.
     * Toggle means if it's there it removes it, if it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        if (cellProp != null) {
            String cellText = cellProp.getValue();

            // IF IT ALREADY HAS THE TA, REMOVE IT
            if (cellText.contains(taName)) {
                removeTAFromCell(cellProp, taName);
            } // OTHERWISE ADD IT
            else if (cellText.length() == 0) {
                cellProp.setValue(taName);
            } else {
                cellProp.setValue(cellText + "\n" + taName);
            }
        }
    }

    /**
     * This method removes taName from the office grid cell represented by
     * cellProp.
     */
    public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // System.out.println("Cell text is:" +cellText);
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        } // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(cellText);
        } // IS IT IN THE MIDDLE OF A LIST OF TAs
        else if (cellText.indexOf(taName) < cellText.indexOf("\n", cellText.indexOf(taName))) {
            int startIndex = cellText.indexOf("\n" + taName);
            int endIndex = startIndex + taName.length() + 1;
            cellText = cellText.substring(0, startIndex) + cellText.substring(endIndex);
            cellProp.setValue(cellText);
        } // IT MUST BE THE LAST TA
        else {
            int startIndex = cellText.indexOf("\n" + taName);
            cellText = cellText.substring(0, startIndex);
            cellProp.setValue(cellText);
        }
    }

    public void replaceTAFromCell(StringProperty cellProp, String taName, String newName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue(newName);
        } // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(newName + "\n" + cellText);
        } // IS IT IN THE MIDDLE OF A LIST OF TAs
        else if (cellText.indexOf(taName) < cellText.indexOf("\n", cellText.indexOf(taName))) {
            int startIndex = cellText.indexOf("\n" + taName);
            int endIndex = startIndex + taName.length() + 1;
            cellText = cellText.substring(0, startIndex) + cellText.substring(endIndex);
            cellProp.setValue(newName + "\n" + cellText);
        } // IT MUST BE THE LAST TA
        else {
            int startIndex = cellText.indexOf("\n" + taName);
            cellText = cellText.substring(0, startIndex);
            cellProp.setValue(newName + "\n" + cellText);
        }
    }

    /**
     * @return the jTPS
     */
    public static jTPS getjTPS() {
        return jTPS;
    }

}
