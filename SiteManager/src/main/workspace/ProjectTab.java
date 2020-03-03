/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.workspace;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import properties_manager.PropertiesManager;
import tam.CourseGeneratorApp;
import tam.CourseGeneratorProp;
import static tam.style.TAStyle.CLASS_ADD_EDIT_LABEL;
import static tam.style.TAStyle.CLASS_COURSE_SCROLL_PANE;
import static tam.style.TAStyle.CLASS_COURSE_TAB_PANE;
import static tam.style.TAStyle.CLASS_ORDINARY_LABEL;
import static tam.style.TAStyle.CLASS_PROJECT_BOTTOM_PANE;
import static tam.style.TAStyle.CLASS_PROJECT_LABEL;
import static tam.style.TAStyle.CLASS_PROJECT_TAB;
import static tam.style.TAStyle.CLASS_PROJECT_TOP_PANE;

/**
 *
 * @author Home
 */
public class ProjectTab {

    private VBox mainPane;
    private Label projectLbl;
    private VBox topPane;
    private HBox teamsPane;
    private Label teamsLbl;
    private Button removeTeamBtn;
    private TableView teamTable;
    private TableColumn nameColumn;
    private TableColumn colorColumn;
    private TableColumn textColorColumn;
    private TableColumn linkColumn;
    private Label addEditLbl;
    private HBox namePane;
    private Label nameLbl;
    private TextField nameFld;
    private HBox colorPane;
    private Label colorLbl;
    private Label image1Lbl;
    private Label textColorLbl;
    private Label image2Lbl;
    private GridPane linkPane;
    private Label linkLbl;
    private TextField linkFld;
    private Button addUpdateTeamBtn;
    private Button clearTeamBtn;
    //students
    private VBox bottomPane;
    private HBox studentsPane;
    private Label studentsLbl;
    private Button removeStudentBtn;
    private TableView studentTable;
    private TableColumn fNameColumn;
    private TableColumn lNameColumn;
    private TableColumn teamColumn;
    private TableColumn roleColumn;
    private Label addEdit2Lbl;
    private GridPane addEditGP;
    private Label fNameLbl;
    private Label lNameLbl;
    private Label teamLbl;
    private Label roleLbl;
    private TextField fNameFld;
    private TextField lNameFld;
    private ComboBox<String> teamBox;
    private TextField roleFld;
    private Button addUpdateStudentBtn;
    private Button clearStudentBtn;
    private CourseGeneratorApp app;
    private String link;
    private String link2;
    private VBox forScroll;
    private ScrollPane sPane;

    public ProjectTab(CourseGeneratorApp initApp) {
        this.app = initApp;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        forScroll = new VBox(20);
        sPane = new ScrollPane(forScroll);
        forScroll.prefWidthProperty().bind(sPane.widthProperty());
        TempData data = (TempData) app.getDataComponent();
        ObservableList<Team> tableData = data.getTeams();
        ObservableList<Student> tableData2 = data.getStudents();
        mainPane = new VBox(10);
        String prText = props.getProperty(CourseGeneratorProp.PROJECTS_LBL.toString());
        projectLbl = new Label(prText);
        projectLbl.getStyleClass().add(CLASS_PROJECT_LABEL);
        topPane = new VBox(10);
        teamsPane = new HBox(10);
        String teaText = props.getProperty(CourseGeneratorProp.TEAMS_LBL.toString());
        teamsLbl = new Label(teaText);
        teamsLbl.getStyleClass().add(CLASS_ADD_EDIT_LABEL);
        removeTeamBtn = new Button("-");
        teamsPane.getChildren().addAll(teamsLbl, removeTeamBtn);
        teamTable = new TableView();
        teamTable.setMaxHeight(260);
        teamTable.setEditable(true);
        teamTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        teamTable.setItems(tableData);
        String nameText = props.getProperty(CourseGeneratorProp.NAME_COL.toString());
        nameColumn = new TableColumn(nameText);
        String colText = props.getProperty(CourseGeneratorProp.COLOR_COL.toString());
        colorColumn = new TableColumn(colText);
        String tcolText = props.getProperty(CourseGeneratorProp.TEXT_COLOR_COL.toString());
        textColorColumn = new TableColumn(tcolText);
        String linkText = props.getProperty(CourseGeneratorProp.LINK_COL.toString());
        linkColumn = new TableColumn(linkText);
        teamTable.getColumns().add(nameColumn);
        teamTable.getColumns().add(colorColumn);
        teamTable.getColumns().add(textColorColumn);
        teamTable.getColumns().add(linkColumn);
        teamTable.setMaxWidth(700);
       textColorColumn.prefWidthProperty().bind(teamTable.widthProperty().multiply(.25));
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        colorColumn.setCellValueFactory(
                new PropertyValueFactory<>("color")
        );
        textColorColumn.setCellValueFactory(
                new PropertyValueFactory<>("textColor")
        );
        linkColumn.setCellValueFactory(
                new PropertyValueFactory<>("link")
        );
        link = "http://atomicomic.com";
        //link.setText("http://atomicomic.com");
        link2 = "http://c4-comics.appspot.com";
        //link2.setText("http://c4-comics.appspot.com");
//        tableData.add(new Team("Atomic Comics", "552211", "fff fff", link ));
//        tableData.add(new Team("C4 Comics", "235399", "fff fff", link2 ));
        String addedText = props.getProperty(CourseGeneratorProp.ADD_EDIT_LBL.toString());
        addEditLbl = new Label(addedText);
        addEditLbl.getStyleClass().add(CLASS_ADD_EDIT_LABEL);
        namePane = new HBox(5);
        String nlText = props.getProperty(CourseGeneratorProp.NAME_LBL.toString());
        nameLbl = new Label(nlText);
        nameLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        nameLbl.setMinWidth(150);
        nameFld = new TextField();
        nameFld.setMinWidth(241);
        namePane.getChildren().addAll(nameLbl, nameFld);
        colorPane = new HBox(10);
        final ColorPicker colorPicker1 = new ColorPicker();
        colorPicker1.setValue(Color.BLUE);
 
        final Circle circle1 = new Circle(30);
        circle1.setFill(colorPicker1.getValue());
 
        colorPicker1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                circle1.setFill(colorPicker1.getValue());
            }
        });
        
        final ColorPicker colorPicker2 = new ColorPicker();
        colorPicker2.setValue(Color.WHITE);
 
        final Circle circle2 = new Circle(30);
        circle2.setFill(colorPicker2.getValue());
 
        colorPicker2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                circle2.setFill(colorPicker2.getValue());
            }
        });
 
        
        String colorText = props.getProperty(CourseGeneratorProp.COLOR_LBL.toString());
        colorLbl = new Label(colorText);
        colorLbl.setMinWidth(150);
        colorLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        image1Lbl = new Label("Image");
        String tcolorText = props.getProperty(CourseGeneratorProp.TEXT_COLOR_LBL.toString());
        textColorLbl = new Label(tcolorText);
        textColorLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        image2Lbl = new Label("Image");
        colorPane.getChildren().addAll(colorLbl, circle1, colorPicker1, textColorLbl, circle2, colorPicker2);
        linkPane = new GridPane();
        String llText = props.getProperty(CourseGeneratorProp.LINK_LBL.toString());
        linkLbl = new Label(llText);
        linkLbl.setMinWidth(150);
        linkLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        linkFld = new TextField();
        linkFld.setMinWidth(350);
        String auText = props.getProperty(CourseGeneratorProp.ADD_UPDATE_BTN.toString());
        addUpdateTeamBtn = new Button(auText);
        String cText = props.getProperty(CourseGeneratorProp.CLEAR_BTN.toString());
        clearTeamBtn = new Button(cText);
        linkPane.add(linkLbl, 0, 0);
        linkPane.add(linkFld, 1, 0);
        linkPane.add(addUpdateTeamBtn, 0, 1);
        linkPane.add(clearTeamBtn, 1, 1);
        linkPane.setVgap(5);
        linkPane.setHgap(5);
        topPane.getChildren().addAll(teamsPane, teamTable, addEditLbl, namePane, colorPane, linkPane);
        topPane.getStyleClass().add(CLASS_PROJECT_TOP_PANE);
        
        
        
        //students
        bottomPane = new VBox(10);
        studentsPane = new HBox(10);
        String studentsText = props.getProperty(CourseGeneratorProp.STUDENTS_LBL.toString());
        studentsLbl = new Label(studentsText);
        studentsLbl.getStyleClass().add(CLASS_ADD_EDIT_LABEL);
        removeStudentBtn = new Button("-");
        studentsPane.getChildren().addAll(studentsLbl, removeStudentBtn);
        studentTable = new TableView();
        studentTable.setMaxWidth(700);
        studentTable.setMaxHeight(260);
        String fText = props.getProperty(CourseGeneratorProp.FNAME_COL.toString());
        fNameColumn = new TableColumn(fText);
        String lText = props.getProperty(CourseGeneratorProp.LNAME_COL.toString());
        lNameColumn = new TableColumn(lText);
        String tText = props.getProperty(CourseGeneratorProp.TEAM_COL.toString());
        teamColumn = new TableColumn(tText);
        String rText = props.getProperty(CourseGeneratorProp.ROLE_COL.toString());
        roleColumn = new TableColumn(rText);
        studentTable.getColumns().add(fNameColumn);
        studentTable.getColumns().add(lNameColumn);
        studentTable.getColumns().add(teamColumn);
        studentTable.getColumns().add(roleColumn);
        studentTable.setEditable(true);
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentTable.setItems(tableData2);
        fNameColumn.prefWidthProperty().bind(studentTable.widthProperty().multiply(.2));
        lNameColumn.prefWidthProperty().bind(studentTable.widthProperty().multiply(.2));
        
        
        fNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("fName")
        );
        lNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("lName")
        );
        teamColumn.setCellValueFactory(
                new PropertyValueFactory<>("team")
        );
        roleColumn.setCellValueFactory(
                new PropertyValueFactory<>("role")
        );
//        tableData2.add(new Student("Beau", "Brummell", "Atomic Comics", "Lead Designer"));
//        tableData2.add(new Student("Jane", "Doe", "C4 Comics", "Lead Programmer"));
//        tableData2.add(new Student("Noonian", "Soong", "Atomic Comics", "Data Desighner"));
        String aeText = props.getProperty(CourseGeneratorProp.ADD_EDIT_LBL.toString());
        addUpdateTeamBtn = new Button(auText);
        addEdit2Lbl = new Label(aeText);
        addEdit2Lbl.getStyleClass().add(CLASS_ADD_EDIT_LABEL);
        addEditGP = new GridPane();
        String fnameText = props.getProperty(CourseGeneratorProp.FNAME_LBL.toString());
        fNameLbl = new Label(fnameText);
        fNameLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String lnameText = props.getProperty(CourseGeneratorProp.SCHEDULE_LBL.toString());
        lNameLbl = new Label(lnameText);
        lNameLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String teamText = props.getProperty(CourseGeneratorProp.TEAM_LBL.toString());
        teamLbl = new Label(teamText);
        teamLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String roleText = props.getProperty(CourseGeneratorProp.ROLE_LBL.toString());
        roleLbl = new Label(roleText);
        roleLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        fNameFld = new TextField();
        fNameFld.setMinWidth(200);
        lNameFld = new TextField();
        lNameFld.setMinWidth(200);
        teamBox = new ComboBox();
        teamBox.setMinWidth(200);
        roleFld = new TextField();
        roleFld.setMinWidth(200);
        String adduText = props.getProperty(CourseGeneratorProp.ADD_UPDATE_BTN.toString());
        addUpdateStudentBtn = new Button(adduText);
        
        clearStudentBtn = new Button(cText);
        addEditGP.add(fNameLbl, 0, 0);
        addEditGP.add(fNameFld, 1, 0);
        addEditGP.add(lNameLbl, 0, 1);
        addEditGP.add(lNameFld, 1, 1);
        addEditGP.add(teamLbl, 0, 2);
        addEditGP.add(teamBox, 1, 2);
        addEditGP.add(roleLbl, 0, 3);
        addEditGP.add(roleFld, 1, 3);
        addEditGP.add(addUpdateStudentBtn, 0, 4);
        addEditGP.add(clearStudentBtn, 1, 4);
        addEditGP.setHgap(5);
        addEditGP.setVgap(5);
        bottomPane.getChildren().addAll(studentsPane, studentTable, addEdit2Lbl, addEditGP);
        bottomPane.getStyleClass().add(CLASS_PROJECT_BOTTOM_PANE);
        //mainPane
        forScroll.getChildren().addAll(projectLbl, topPane, bottomPane);
        
        mainPane.getChildren().add(sPane);
        mainPane.getStyleClass().add(CLASS_COURSE_TAB_PANE);
        forScroll.getStyleClass().add(CLASS_COURSE_SCROLL_PANE);
        sPane.getStyleClass().add(CLASS_COURSE_SCROLL_PANE);

    }
    
    public VBox getMainPane(){
        return mainPane;
    }

    /**
     * @return the teamBox
     */
    public ComboBox getTeamBox() {
        return teamBox;
    }

}
