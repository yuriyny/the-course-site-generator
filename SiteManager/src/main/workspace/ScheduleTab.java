/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.workspace;

import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import tam.CourseGeneratorApp;
import tam.CourseGeneratorProp;
import static tam.style.TAStyle.CLASS_ADD_EDIT_LABEL;
import static tam.style.TAStyle.CLASS_ORDINARY_LABEL;
import static tam.style.TAStyle.CLASS_SCHEDULE_BOTTOM_PANE;
import static tam.style.TAStyle.CLASS_SCHEDULE_LABEL;
import static tam.style.TAStyle.CLASS_SCHEDULE_TAB;
import static tam.style.TAStyle.CLASS_SCHEDULE_TOP_PANE;

/**
 *
 * @author Home
 */
public class ScheduleTab {

    private Label scheduleLbl;
    // calendar boundaries
    private VBox mainPane;
    private VBox topPane;
    private Label calendarLbl;
    private Label startLbl;
    private Label endLbl;
    private DatePicker startPicker;
    private DatePicker endPicker;
    private GridPane startGP;
    private GridPane endGP;
    //schedule items
    private HBox timePane;
    private VBox bottomPane;
    private HBox schedulePane;
    private Label itemsLbl;
    private Button removeBtn;
    private TableView table;
    private TableColumn typeColumn;
    private TableColumn dateColumn;
    private TableColumn titleColumn;
    private TableColumn topicColumn;
    private Label addEditLbl;
    private GridPane addEditGP;
    private Label typeLbl;
    private Label dateLbl;
    private Label timeLbl;
    private Label titleLbl;
    private Label topicLbl;
    private Label linkLbl;
    private Label criteriaLbl;
    private ComboBox<String> typeBox;
    private ComboBox<String> dateBox;
    private TextField timeFld;
    private TextField titleFld;
    private TextField topicFld;
    private TextField linkFld;
    private TextField criteriaFld;
    private Button addUpdateBtn;
    private Button clearBtn; 
    private CourseGeneratorApp app;
    private DatePicker selectDate;
    // private HBox buttonPane;

    public ScheduleTab(CourseGeneratorApp initApp) {
        this.app = initApp;
        TempData data = (TempData) app.getDataComponent();
        ObservableList<Schedule> tableData = data.getSchedules();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String scheText = props.getProperty(CourseGeneratorProp.SCHEDULE_LBL.toString());
        scheduleLbl = new Label(scheText);
        scheduleLbl.getStyleClass().add(CLASS_SCHEDULE_LABEL);
        // calendar boundaries
        mainPane = new VBox(10);
        topPane = new VBox(10);
        String calText = props.getProperty(CourseGeneratorProp.CALENDAR_BOUNDARIES.toString());
        calendarLbl = new Label(calText);
        calendarLbl.getStyleClass().add(CLASS_ADD_EDIT_LABEL);
        String starText = props.getProperty(CourseGeneratorProp.START_M.toString());
        startLbl = new Label(starText);
        startLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String endText = props.getProperty(CourseGeneratorProp.END_F.toString());
        endLbl = new Label(endText);
        endLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        startPicker = new DatePicker();
     //   startPicker.setValue(LocalDate.of(2017, 11, 10));
        endPicker = new DatePicker();
        timePane = new HBox(30);
        startGP = new GridPane();
        startGP.add(startLbl, 0, 0);
        startGP.add(startPicker, 1, 0);
        startGP.setHgap(5);
        startGP.setVgap(5);
        endGP = new GridPane();
        endGP.add(endLbl, 0, 0);
        endGP.add(endPicker, 1, 0);
        endGP.setHgap(5);
        endGP.setVgap(5);
        timePane.getChildren().addAll(startGP, endGP);
        topPane.getChildren().addAll(calendarLbl, timePane);
        topPane.getStyleClass().add(CLASS_SCHEDULE_TOP_PANE);
        //schedule items
        bottomPane = new VBox(10);
        bottomPane.getStyleClass().add(CLASS_SCHEDULE_BOTTOM_PANE);
        schedulePane = new HBox(10);
        String itText = props.getProperty(CourseGeneratorProp.SCHEDULE_ITEMS.toString());
        itemsLbl = new Label(itText);
        itemsLbl.getStyleClass().add(CLASS_ADD_EDIT_LABEL);
        removeBtn = new Button("-");
        schedulePane.getChildren().addAll(itemsLbl, removeBtn);
        table = new TableView();
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.setItems(tableData);
        table.setMaxWidth(700);
        String typeText = props.getProperty(CourseGeneratorProp.TYPE_COL.toString());
        typeColumn = new TableColumn(typeText);
        String dateText = props.getProperty(CourseGeneratorProp.DATE_COL.toString());
        dateColumn = new TableColumn(dateText);
        String titText = props.getProperty(CourseGeneratorProp.TITLE_COL.toString());
        titleColumn = new TableColumn(titText);
        String topText = props.getProperty(CourseGeneratorProp.TOPIC_COL.toString());
        topicColumn = new TableColumn(topText);
        table.getColumns().add(typeColumn);
        table.getColumns().add(dateColumn);
        table.getColumns().add(titleColumn);
        table.getColumns().add(topicColumn);
        topicColumn.prefWidthProperty().bind(table.widthProperty().multiply(.45));
        String addedText = props.getProperty(CourseGeneratorProp.ADD_EDIT_LBL.toString());
        addEditLbl = new Label(addedText);
        addEditLbl.getStyleClass().add(CLASS_ADD_EDIT_LABEL);
        addEditGP = new GridPane();
        String typText = props.getProperty(CourseGeneratorProp.TYPE_LBL.toString());
        typeLbl = new Label(typText);
        typeLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String dText = props.getProperty(CourseGeneratorProp.DATE_LBL.toString());
        dateLbl = new Label(dText);
        dateLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String tText = props.getProperty(CourseGeneratorProp.TIME_LBL.toString());
        timeLbl = new Label(tText);
        timeLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String titleText = props.getProperty(CourseGeneratorProp.TITLE_LBL.toString());
        titleLbl = new Label(titleText);
        titleLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String topicText = props.getProperty(CourseGeneratorProp.TOPIC_LBL.toString());
        topicLbl = new Label(topicText);
        topicLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String linkText = props.getProperty(CourseGeneratorProp.LINK_LBL.toString());
        linkLbl = new Label(linkText);
        linkLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String crText = props.getProperty(CourseGeneratorProp.CRITERIA_LBL.toString());
        criteriaLbl = new Label(crText);
        criteriaLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        typeBox = new ComboBox();
        typeBox.getItems().addAll("Holiday", "Lecture", "HW");
        typeBox.getSelectionModel().selectFirst();
        typeBox.setMinWidth(200);
        dateBox = new ComboBox();
        dateBox.setMinWidth(200);
        timeFld = new TextField();
        timeFld.setMinWidth(200);
        titleFld = new TextField();
        titleFld.setMinWidth(200);
        topicFld = new TextField();
        topicFld.setMinWidth(200);
        linkFld = new TextField();
        linkFld.setMinWidth(200);
        criteriaFld = new TextField();
        criteriaFld.setMinWidth(200);
        String auText = props.getProperty(CourseGeneratorProp.ADD_UPDATE_BTN.toString());
        addUpdateBtn = new Button(auText);
        String clText = props.getProperty(CourseGeneratorProp.CLEAR_BTN.toString());
        clearBtn = new Button(clText);
        selectDate = new DatePicker();
        selectDate.setMinWidth(200);
        addEditGP.add(typeLbl, 0, 0);
        addEditGP.add(typeBox, 1, 0);
        addEditGP.add(dateLbl, 0, 1);
        addEditGP.add(selectDate, 1, 1);
        addEditGP.add(timeLbl, 0, 2);
        addEditGP.add(timeFld, 1, 2);
        addEditGP.add(titleLbl, 0, 3);
        addEditGP.add(titleFld, 1, 3);
        addEditGP.add(topicLbl, 0, 4);
        addEditGP.add(topicFld, 1, 4);
        addEditGP.add(linkLbl, 0, 5);
        addEditGP.add(linkFld, 1, 5);
        addEditGP.add(criteriaLbl, 0, 6);
        addEditGP.add(criteriaFld, 1, 6);
        addEditGP.add(addUpdateBtn, 0, 7);
        addEditGP.add(clearBtn, 1, 7);
        addEditGP.setVgap(5);
        addEditGP.setHgap(5);
        bottomPane.getChildren().addAll(schedulePane, table, addEditLbl, addEditGP);
        mainPane.getChildren().addAll(scheduleLbl, topPane, bottomPane);
        mainPane.getStyleClass().add(CLASS_SCHEDULE_TAB);
        
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<>("type")
        );
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date")
        );
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        topicColumn.setCellValueFactory(
                new PropertyValueFactory<>("topic")
        );
//        tableData.add(new Schedule("Holiday", "2/9/2017", "", "SNOW DAY", "","",""));
//        tableData.add(new Schedule("Lecture", "2/14/2017", "", "Lecture 3", "Event Programming","",""));
//        tableData.add(new Schedule("Holiday", "3/13/2017", "", "Spring Break", "","",""));
//        tableData.add(new Schedule("HW", "3/27/2017", "", "HW3", "UML","",""));
    }
    
    public VBox getMainPane(){
        return mainPane;
    }
    
    public DatePicker getStartPicker(){
        return startPicker;
    }
    
    public DatePicker getEndPicker(){
        return endPicker;
    }

}
