/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.workspace;

import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import jtps.test.AddToList;
import jtps.test.CellToggle;
import jtps.test.ComboButton;
import jtps.test.UpdateTA;
import static main.workspace.TempData.getjTPS;
import properties_manager.PropertiesManager;
import tam.CourseGeneratorApp;
import tam.CourseGeneratorProp;
import static tam.CourseGeneratorProp.MISSING_TA_EMAIL_MESSAGE;
import static tam.CourseGeneratorProp.MISSING_TA_EMAIL_TITLE;
import static tam.CourseGeneratorProp.MISSING_TA_NAME_MESSAGE;
import static tam.CourseGeneratorProp.MISSING_TA_NAME_TITLE;
import static tam.CourseGeneratorProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE;
import static tam.CourseGeneratorProp.TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE;
import tam.data.TeachingAssistant;
import static tam.style.TAStyle.CLASS_HIGHLIGHTED_GRID_CELL;
import static tam.style.TAStyle.CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN;
import static tam.style.TAStyle.CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE;

/**
 *
 * @author Home
 */
public class TAController {
    
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    CourseGeneratorApp app;
    static jTPS jTPS;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TAController(CourseGeneratorApp initApp) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        // KEEP THIS FOR LATER
        app = initApp;
    }

    /**
     * This helper method should be called every time an edit happens.
     */
    private void markWorkAsEdited() {
        // MARK WORK AS EDITED
        AppGUI gui = app.getGUI();
        gui.getFileController().markAsEdited(gui);
    }

    private File getFile() {
        AppGUI gui = app.getGUI();
        return gui.getFileController().getCurrentWorkFile();
    }

    /**
     * This method responds to when the user requests to add a new TA via the
     * UI. Note that it must first do some validation to make sure a unique name
     * and email address has been provided.
     */
    public void handleUpdateTA(String initName, String initEmail) {
        TabWorkspace workspace = (TabWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        TempData data = (TempData) app.getDataComponent();

        if (validate(email) == false) {
            invalidEmail(email);
        } else {

          jTPS_Transaction transaction = new UpdateTA(initName, initEmail, name, email, data, workspace);
          getjTPS().addTransaction(transaction);
        }

        if (!(initName.equals(name)) || !(initEmail.equals(email))) {


            if (getFile() != null) {
                markWorkAsEdited();
            }
        }

    }

    public void handleClearButton() {
        TabWorkspace workspace = (TabWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        nameTextField.setText("");
        emailTextField.setText("");
        nameTextField.requestFocus();
        workspace.getButtonBox().getChildren().clear();
        workspace.getButtonBox().getChildren().add(workspace.getAddButton());
        workspace.getButtonBox().getChildren().add(workspace.getClearButton());

    }
    
    public void handleRemoveTA(){
           
            // GET THE TABLE
            TabWorkspace workspace = (TabWorkspace) app.getWorkspaceComponent();
            TableView taTable = workspace.getTATable();

            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant) selectedItem;
                String taName = ta.getName();
                TempData data = (TempData) app.getDataComponent();
                HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();

                data.removeTA(taName, labels);

                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
//                HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
//                for (Label label : labels.values()) {
//                    if (label.getText().equals(taName)
//                            || (label.getText().contains(taName + "\n"))
//                            || (label.getText().contains("\n" + taName))) {
//                        data.removeTAFromCell(label.textProperty(), taName);
//                    }
//                }
                // WE'VE CHANGED STUFF
                if (getFile() != null) {
                    markWorkAsEdited();
                }
            }
        

    }

    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        TabWorkspace workspace = (TabWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getNameTextField();
        TextField emailTextField = workspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        TempData data = (TempData) app.getDataComponent();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
        } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
        } else if (validate(email) == false) {
            invalidEmail(email);
        } // EVERYTHING IS FINE, ADD A NEW TA
        else {
            // ADD THE NEW TA TO THE DATA

            jTPS_Transaction transaction = new AddToList(name, email, data, false );
            getjTPS().addTransaction(transaction);

            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();

            // WE'VE CHANGED STUFF
            if (getFile() != null) {
                markWorkAsEdited();
            }
        }
    }

    /**
     * This function provides a response for when the user presses a keyboard
     * key. Note that we're only responding to Delete, to remove a TA.
     *
     * @param code The keyboard code pressed.
     */
    public void handleKeyPress(KeyCode code) {
        // DID THE USER PRESS THE DELETE KEY?
        if (code == KeyCode.DELETE || code == KeyCode.D) {
            // GET THE TABLE
            TabWorkspace workspace = (TabWorkspace) app.getWorkspaceComponent();
            TableView taTable = workspace.getTATable();

            // IS A TA SELECTED IN THE TABLE?
            Object selectedItem = taTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                // GET THE TA AND REMOVE IT
                TeachingAssistant ta = (TeachingAssistant) selectedItem;
                String taName = ta.getName();
                TempData data = (TempData) app.getDataComponent();
                HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();

                data.removeTA(taName, labels);

                // AND BE SURE TO REMOVE ALL THE TA'S OFFICE HOURS
//                HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
//                for (Label label : labels.values()) {
//                    if (label.getText().equals(taName)
//                            || (label.getText().contains(taName + "\n"))
//                            || (label.getText().contains("\n" + taName))) {
//                        data.removeTAFromCell(label.textProperty(), taName);
//                    }
//                }
                // WE'VE CHANGED STUFF
                if (getFile() != null) {
                    markWorkAsEdited();
                }
            }
        }
    }

    /**
     * This function provides a response for when the user clicks on the office
     * hours grid to add or remove a TA to a time slot.
     *
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        TabWorkspace workspace = (TabWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // GET THE TA
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
            String taName = ta.getName();
            TempData data = (TempData) app.getDataComponent();
            String cellKey = pane.getId();

          jTPS_Transaction transaction = new CellToggle(taName, data, cellKey);
           getjTPS().addTransaction(transaction);

            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            //  data.toggleTAOfficeHours(cellKey, taName);
            // WE'VE CHANGED STUFF
            if (getFile() != null) {
                markWorkAsEdited();
            }
        }
    }

    void handleGridCellMouseExited(Pane pane) {
        String cellKey = pane.getId();
        TempData data = (TempData) app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        TabWorkspace workspace = (TabWorkspace) app.getWorkspaceComponent();

        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().remove(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
            cell.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        }
    }

    void handleGridCellMouseEntered(Pane pane) {
        String cellKey = pane.getId();
        TempData data = (TempData) app.getDataComponent();
        int column = Integer.parseInt(cellKey.substring(0, cellKey.indexOf("_")));
        int row = Integer.parseInt(cellKey.substring(cellKey.indexOf("_") + 1));
        TabWorkspace workspace = (TabWorkspace) app.getWorkspaceComponent();

        // THE MOUSED OVER PANE
        Pane mousedOverPane = workspace.getTACellPane(data.getCellKey(column, row));
        mousedOverPane.getStyleClass().clear();
        mousedOverPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_CELL);

        // THE MOUSED OVER COLUMN HEADER
        Pane headerPane = workspace.getOfficeHoursGridDayHeaderPanes().get(data.getCellKey(column, 0));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // THE MOUSED OVER ROW HEADERS
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(0, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        headerPane = workspace.getOfficeHoursGridTimeCellPanes().get(data.getCellKey(1, row));
        headerPane.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);

        // AND NOW UPDATE ALL THE CELLS IN THE SAME ROW TO THE LEFT
        for (int i = 2; i < column; i++) {
            cellKey = data.getCellKey(i, row);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }

        // AND THE CELLS IN THE SAME COLUMN ABOVE
        for (int i = 1; i < row; i++) {
            cellKey = data.getCellKey(column, i);
            Pane cell = workspace.getTACellPane(cellKey);
            cell.getStyleClass().add(CLASS_HIGHLIGHTED_GRID_ROW_OR_COLUMN);
        }
    }

    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }

    public void doComboBox(int fSTime, int fETime, int sTime, int eTime, TempData data, TATab workspace) {

     jTPS_Transaction transaction = new ComboButton(fSTime, fETime, sTime, eTime, data, workspace);
      getjTPS().addTransaction(transaction);
        if (getFile() != null) {
            markWorkAsEdited();
        }

    }

    public void invalidEmail(String invalidEmail) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String error2Text1 = props.getProperty(CourseGeneratorProp.ERROR2_TEXT1.toString());
        String error2Text2 = props.getProperty(CourseGeneratorProp.ERROR2_TEXT2.toString());
        String error2Text3 = props.getProperty(CourseGeneratorProp.ERROR2_TEXT3.toString());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(error2Text1);
        alert.setHeaderText(error2Text2);
        alert.setContentText(invalidEmail + error2Text3);

        alert.showAndWait();
    }
    
}
