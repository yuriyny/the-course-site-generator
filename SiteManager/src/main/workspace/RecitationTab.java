/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.workspace;

import static djf.settings.AppPropertyType.DELETE_ICON;
import static djf.settings.AppPropertyType.DELETE_TOOLTIP;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import tam.CourseGeneratorApp;
import tam.CourseGeneratorProp;
import static tam.style.TAStyle.CLASS_ADD_EDIT_LABEL;
import static tam.style.TAStyle.CLASS_ORDINARY_LABEL;
import static tam.style.TAStyle.CLASS_RECITATION_BOTTOM_PANE;
import static tam.style.TAStyle.CLASS_RECITATION_LABEL;
import static tam.style.TAStyle.CLASS_RECITATION_REMOVE_BUTTON;
import static tam.style.TAStyle.CLASS_RECITATION_TAB;
import static tam.style.TAStyle.CLASS_REMOVE_TA_BTN;
import temp.objects.Recitation;

/**
 *
 * @author Home
 */
public class RecitationTab {

    private VBox mainPane;
    private Label recitationsLbl;
    private Button removeBtn;
    private HBox topPane;
    private TableView table;
    private TableColumn sectionColumn;
    private TableColumn instructorColumn;
    private TableColumn dayTimeColumn;
    private TableColumn locationColumn;
    private TableColumn ta1Column;
    private TableColumn ta2Column;

    private VBox subPane;
    private Label addEditLbl;
    private GridPane addEditGP;
    private Label sectionLbl;
    private Label instructorLbl;
    private Label dayTimeLbl;
    private Label locationLbl;
    private Label ta1Lbl;
    private Label ta2Lbl;
    private TextField sectionFld;
    private TextField instructorFld;
    private TextField dayTimeFld;
    private TextField locationFld;
    private ComboBox<String> ta1Box;
    private ComboBox<String> ta2Box;
    private Button addUpdateBtn;
    private Button clearBtn;
    private VBox bottomPane;
    private CourseGeneratorApp app;
    private VBox tablePane;

    public RecitationTab(CourseGeneratorApp initApp) {
        this.app = initApp;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TempData data = (TempData) app.getDataComponent();
        final ObservableList<Recitation> tableData = data.getRecitations();

        mainPane = new VBox(10);
        String resText = props.getProperty(CourseGeneratorProp.RECITATION_LBL.toString());
        recitationsLbl = new Label(resText);
        recitationsLbl.getStyleClass().add(CLASS_RECITATION_LABEL);
        removeBtn = new Button();
        removeBtn.setPadding(new Insets(10,0,0,0));
        removeBtn.getStyleClass().add(CLASS_REMOVE_TA_BTN);
        // LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(DELETE_ICON.toString());
        Image buttonImage = new Image(imagePath);

        // NOW MAKE THE BUTTON
        
        removeBtn.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(DELETE_TOOLTIP.toString()));
        removeBtn.setTooltip(buttonTooltip);
        removeBtn.getStyleClass().add(CLASS_RECITATION_REMOVE_BUTTON);
        topPane = new HBox(10);
        topPane.getChildren().addAll(recitationsLbl, removeBtn);
        // topPane.setMaxWidth(750);
        table = new TableView();
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.setItems(tableData);
        // table.setMaxWidth(750);
        String secText = props.getProperty(CourseGeneratorProp.SECTION_COLUMN.toString());
        sectionColumn = new TableColumn(secText);
        String instrText = props.getProperty(CourseGeneratorProp.INSTRUCTOR_COLUMN.toString());
        instructorColumn = new TableColumn(instrText);
        String dayText = props.getProperty(CourseGeneratorProp.DAYTIME_COLUMN.toString());
        dayTimeColumn = new TableColumn(dayText);
        String locText = props.getProperty(CourseGeneratorProp.LOCATION_COLUMN.toString());
        locationColumn = new TableColumn(locText);
        String taText = props.getProperty(CourseGeneratorProp.TA_COLUMN.toString());
        ta1Column = new TableColumn(taText);
        ta2Column = new TableColumn(taText);
        table.getColumns().add(sectionColumn);
        table.getColumns().add(instructorColumn);
        table.getColumns().add(dayTimeColumn);
        table.getColumns().add(locationColumn);
        table.getColumns().add(ta1Column);
        table.getColumns().add(ta2Column);
        sectionColumn.prefWidthProperty().bind(table.widthProperty().multiply(.1));
        instructorColumn.prefWidthProperty().bind(table.widthProperty().multiply(.25));

        tablePane = new VBox(10);
        tablePane.getChildren().add(table);
        table.setMaxWidth(750);
        subPane = new VBox(10);
        String addedText = props.getProperty(CourseGeneratorProp.ADD_EDIT_LBL.toString());
        addEditLbl = new Label(addedText);
        addEditLbl.getStyleClass().add(CLASS_ADD_EDIT_LABEL);
        addEditGP = new GridPane();
        String sectionText = props.getProperty(CourseGeneratorProp.SECTION_LBL.toString());
        sectionLbl = new Label(sectionText);
        sectionLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String inlText = props.getProperty(CourseGeneratorProp.INSTRUCTOR_LBL.toString());
        instructorLbl = new Label(inlText);
        instructorLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String dtText = props.getProperty(CourseGeneratorProp.DAYTIME_LBL.toString());
        dayTimeLbl = new Label(dtText);
        dayTimeLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String locaText = props.getProperty(CourseGeneratorProp.LOCATION_LBL.toString());
        locationLbl = new Label(locaText);
        locationLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String talText = props.getProperty(CourseGeneratorProp.TA_LBL.toString());
        ta1Lbl = new Label(talText);
        ta1Lbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        ta2Lbl = new Label(talText);
        ta2Lbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        sectionFld = new TextField();
        sectionFld.setMinWidth(200);
        instructorFld = new TextField();
        instructorFld.setMinWidth(200);
        dayTimeFld = new TextField();
        dayTimeFld.setMinWidth(200);
        locationFld = new TextField();
        locationFld.setMinWidth(200);
        ta1Box = new ComboBox();
        ta1Box.setMinWidth(200);
       // ta1Box.getItems().addAll("Joe Shmo", "Jane Doe");
        ta1Box.getSelectionModel().selectFirst();
        ta2Box = new ComboBox();
       // ta2Box.getItems().addAll("Joe Shmo", "Jane Doe");
        ta2Box.getSelectionModel().selectFirst();
        ta2Box.setMinWidth(200);
        String add_upText = props.getProperty(CourseGeneratorProp.ADD_UPDATE_BTN.toString());
        addUpdateBtn = new Button(add_upText);
        String clearText = props.getProperty(CourseGeneratorProp.CLEAR_BTN.toString());
        clearBtn = new Button(clearText);
        addEditGP.add(sectionLbl, 0, 0);
        addEditGP.add(sectionFld, 1, 0);
        addEditGP.add(instructorLbl, 0, 1);
        addEditGP.add(instructorFld, 1, 1);
        addEditGP.add(dayTimeLbl, 0, 2);
        addEditGP.add(dayTimeFld, 1, 2);
        addEditGP.add(locationLbl, 0, 3);
        addEditGP.add(locationFld, 1, 3);
        addEditGP.add(ta1Lbl, 0, 4);
        addEditGP.add(ta1Box, 1, 4);
        addEditGP.add(ta2Lbl, 0, 5);
        addEditGP.add(ta2Box, 1, 5);
        addEditGP.add(addUpdateBtn, 0, 6);
        addEditGP.add(clearBtn, 1, 6);
        addEditGP.setHgap(5);
        addEditGP.setVgap(5);
        bottomPane = new VBox(10);
        bottomPane.getChildren().addAll(addEditLbl, addEditGP);
        bottomPane.getStyleClass().add(CLASS_RECITATION_BOTTOM_PANE);
        tablePane.getStyleClass().add(CLASS_RECITATION_BOTTOM_PANE);
        //bottomPane.setMaxWidth(750);CLASS_RECITATION_BOTTOM_PANE);

        mainPane.getChildren().addAll(topPane, tablePane, bottomPane);
        mainPane.getStyleClass().add(CLASS_RECITATION_TAB);
        //  mainPane.setAlignment(Pos.CENTER);

        sectionColumn.setCellValueFactory(
                new PropertyValueFactory<>("section")
        );
        instructorColumn.setCellValueFactory(
                new PropertyValueFactory<>("instructor")
        );
        dayTimeColumn.setCellValueFactory(
                new PropertyValueFactory<>("time")
        );
        locationColumn.setCellValueFactory(
                new PropertyValueFactory<>("location")
        );
        ta1Column.setCellValueFactory(
                new PropertyValueFactory<>("ta")
        );
        ta2Column.setCellValueFactory(
                new PropertyValueFactory<>("ta2")
        );

//        tableData.add(new Recitation("R02", "McKenna", "Wed 3:30PM-4:23PM", " Old CS 2114", "Jane Doe", "Joe Shmo"));
//        tableData.add(new Recitation("R05", "Banerjee", "Tue 5:30PM-6:23PM", " Old CS 2114", "", ""));

    }

    public VBox getMainPane() {
        return mainPane;
    }

    /**
     * @return the ta1Box
     */
    public ComboBox<String> getTa1Box() {
        return ta1Box;
    }

    /**
     * @param ta1Box the ta1Box to set
     */
    public void setTa1Box(ComboBox<String> ta1Box) {
        this.ta1Box = ta1Box;
    }

    /**
     * @return the ta2Box
     */
    public ComboBox<String> getTa2Box() {
        return ta2Box;
    }

}
