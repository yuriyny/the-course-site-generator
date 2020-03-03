/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.workspace;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import properties_manager.PropertiesManager;
import tam.CourseGeneratorApp;
import tam.CourseGeneratorProp;
import static tam.style.TAStyle.CLASS_BANNER_LABEL;
import static tam.style.TAStyle.CLASS_COURSE_INFO_LABEL;
import static tam.style.TAStyle.CLASS_COURSE_INFO_PANE;
import static tam.style.TAStyle.CLASS_COURSE_SCROLL_PANE;
import static tam.style.TAStyle.CLASS_COURSE_TAB_PANE;
import static tam.style.TAStyle.CLASS_DIRECTORY_LABEL;
import static tam.style.TAStyle.CLASS_ORDINARY_LABEL;
import static tam.style.TAStyle.CLASS_PAGE_STYLE_LABEL;
import static tam.style.TAStyle.CLASS_PAGE_STYLE_PANE;
import static tam.style.TAStyle.CLASS_SITE_PAGES_LABEL;
import static tam.style.TAStyle.CLASS_SITE_TEMPLATE_LABEL;
import static tam.style.TAStyle.CLASS_SITE_TEMPLATE_PANE;
import temp.objects.SiteTemplate;

/**
 *
 * @author Home
 */
public class CourseTab {


    private VBox mainPane;
    // building course info pane
    private VBox courseInfoPane;
    private Label courseInfoLabel;
    private GridPane courseInfoGP1;
    private GridPane courseInfoGP1_1;
    private HBox gridPane;
    private Label subjectLbl;
    private Label semesterLbl;
    private Label numberLbl;
    private Label yearLbl;
    private ComboBox<String> subjectBox;
    private ComboBox<String> semesterBox;
    private ComboBox<String> numberBox;
    private ComboBox<String> yearBox;
    // second gridpane for course info section
    private GridPane courseInfoGP2;
    private Label titleLbl;
    private Label instructorNameLbl;
    private Label instructorHomeLbl;
    private TextField titleFld;
    private TextField instructorNameFld;
    private TextField instructorHomeFld;
    //export dir section
    private HBox exportPane;
    private Label exportDirLbl;
    private Label exportInfoLbl;
    private Button exportChangeBtn;

    //Site Template pane
    private VBox siteTemplatePane;
    private Label siteTemplateLbl;
    private Label templateInfoLbl;
    private Label directoryLbl;
    private Button selectTemplateBtn;
    private Label sitePagesLbl;
    private TableView<SiteTemplate> table;
    private TableColumn useColumn;
    private TableColumn navbarColumn;
    private TableColumn fileColumn;
    private TableColumn scriptColumn;

    //page style
    private VBox pageStylePane;
    private Label pageStyleLbl;
    private GridPane pageStyleGP;
    private Label bannerLbl;
    private Label leftFooterLbl;
    private Label rightFooterLbl;
    private Label bannerTxt;
    private Label leftTxt;
    private Label rightTxt;
    private Button bannerBtn;
    private Button leftBtn;
    private Button rightBtn;
    private HBox stylePane;
    private Label styleSheet;
    private ComboBox<String> styleBox;
    private Label noteLbl;
    private CourseGeneratorApp app;
    private VBox forScroll;
    private ScrollPane sPane;

    public CourseTab(CourseGeneratorApp initApp) {
        this.app = initApp;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TempData data = (TempData) app.getDataComponent();
        ObservableList<SiteTemplate> tableData = data.getSiteTemplates();
        mainPane = new VBox(20);
        forScroll = new VBox(20);
        sPane = new ScrollPane(forScroll);
        forScroll.prefWidthProperty().bind(sPane.widthProperty());
        // building course info pane
        courseInfoPane = new VBox(10);
        //  courseInfoPane.setPadding(new Insets(20,20,20,20));
        courseInfoPane.getStyleClass().add(CLASS_COURSE_INFO_PANE);
        String courseInfoText = props.getProperty(CourseGeneratorProp.COURSE_INFO.toString());
        courseInfoLabel = new Label(courseInfoText);
        courseInfoLabel.getStyleClass().add(CLASS_COURSE_INFO_LABEL);
        courseInfoGP1 = new GridPane();
        courseInfoGP1.setHgap(75);
        courseInfoGP1.setVgap(5);
        courseInfoGP1_1 = new GridPane();
        courseInfoGP1_1.setHgap(10);
        courseInfoGP1_1.setVgap(5);
        String subjectText = props.getProperty(CourseGeneratorProp.SUBJECT_LBL.toString());
        subjectLbl = new Label(subjectText);
        subjectLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String semesterText = props.getProperty(CourseGeneratorProp.SEMESTER_LBL.toString());
        semesterLbl = new Label(semesterText);
        semesterLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String numberText = props.getProperty(CourseGeneratorProp.NUMBER_LBL.toString());
        numberLbl = new Label(numberText);
        numberLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String yearText = props.getProperty(CourseGeneratorProp.YEAR_LBL.toString());
        yearLbl = new Label(yearText);
        yearLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        subjectBox = new ComboBox();
        subjectBox.setMinWidth(120);
       // subjectBox.getItems().addAll("CSE", "MAT", "PHY");
       // subjectBox.getSelectionModel().selectFirst();
        semesterBox = new ComboBox();
        semesterBox.setMinWidth(120);
      //  semesterBox.getItems().addAll("Fall", "Winter", "Spring", "Summer");
     //   semesterBox.getSelectionModel().selectFirst();
        numberBox = new ComboBox();
        numberBox.setMinWidth(120);
       // numberBox.getItems().addAll("219","101","214", "215");
      //  numberBox.getSelectionModel().selectFirst();
        yearBox = new ComboBox();
        yearBox.setMinWidth(120);
//        for(int i = 2017; i < 2025; i++){
//            yearBox.getItems().add(Integer.toString(i));
//        }
        yearBox.getSelectionModel().selectFirst();
        courseInfoGP1.add(subjectLbl, 0, 0);
        courseInfoGP1.add(subjectBox, 1, 0);
        courseInfoGP1.add(semesterLbl, 0, 1);
        courseInfoGP1.add(semesterBox, 1, 1);
        courseInfoGP1_1.add(numberLbl, 0, 0);
        courseInfoGP1_1.add(numberBox, 1, 0);
        courseInfoGP1_1.add(yearLbl, 0, 1);
        courseInfoGP1_1.add(yearBox, 1, 1);
        gridPane = new HBox(50);
        gridPane.getChildren().addAll(courseInfoGP1, courseInfoGP1_1);

        // second gridpane for course info section
        courseInfoGP2 = new GridPane();
        String titleText = props.getProperty(CourseGeneratorProp.TITLE_LBL.toString());
        titleLbl = new Label(titleText);
        titleLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String inText = props.getProperty(CourseGeneratorProp.INSTRUCTOR_NAME.toString());
        instructorNameLbl = new Label(inText);
        instructorNameLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String ihText = props.getProperty(CourseGeneratorProp.INSTRUCTOR_HOME.toString());
        instructorHomeLbl = new Label(ihText);
        instructorHomeLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        titleFld = new TextField();
        titleFld.setPrefWidth(360);
        instructorNameFld = new TextField();
        instructorHomeFld = new TextField();
        courseInfoGP2.add(titleLbl, 0, 0);
        courseInfoGP2.add(titleFld, 1, 0);
        courseInfoGP2.add(instructorNameLbl, 0, 1);
        courseInfoGP2.add(instructorNameFld, 1, 1);
        courseInfoGP2.add(instructorHomeLbl, 0, 2);
        courseInfoGP2.add(instructorHomeFld, 1, 2);
        courseInfoGP2.setHgap(10);
        courseInfoGP2.setVgap(5);
        //export dir section
        exportPane = new HBox(50);
        exportPane.setPadding(new Insets(10,0,0,0));
        String dirText = props.getProperty(CourseGeneratorProp.EXPORT_DIR.toString());
        exportDirLbl = new Label(dirText);
        exportDirLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        exportDirLbl.setMinWidth(70);
        exportInfoLbl = new Label("..........");
        exportInfoLbl.setMinWidth(180);
        String changeText = props.getProperty(CourseGeneratorProp.CHANGE_BTN.toString());
        exportChangeBtn = new Button(changeText);
        exportPane.getChildren().addAll(exportDirLbl, exportInfoLbl, exportChangeBtn);
        courseInfoPane.getChildren().addAll(courseInfoLabel, gridPane, courseInfoGP2, exportPane);
        // courseInfoPane.getStyleClass().add(CLASS_COURSE_INFO_PANE);

        //Site Template pane
        siteTemplatePane = new VBox(5);
        String stText = props.getProperty(CourseGeneratorProp.SITE_TEMPLATE.toString());
        siteTemplateLbl = new Label(stText);
        siteTemplateLbl.getStyleClass().add(CLASS_SITE_TEMPLATE_LABEL);
        String stiText = props.getProperty(CourseGeneratorProp.SITE_TEMPLATE_INFO.toString());
        templateInfoLbl = new Label(stiText);
        directoryLbl = new Label("templates\\CSE219");
        directoryLbl.getStyleClass().add(CLASS_DIRECTORY_LABEL);
        String selectText = props.getProperty(CourseGeneratorProp.SELECT_TEMPLATE_BTN.toString());
        selectTemplateBtn = new Button(selectText);
        String sitePagesText = props.getProperty(CourseGeneratorProp.SITE_PAGES.toString());
        sitePagesLbl = new Label(sitePagesText);
        sitePagesLbl.getStyleClass().add(CLASS_SITE_PAGES_LABEL);
        table = new TableView();
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.setItems(tableData);
        String useText = props.getProperty(CourseGeneratorProp.USE_COLUMN.toString());
        useColumn = new TableColumn(useText);
        String navText = props.getProperty(CourseGeneratorProp.NAVBAR_COLUMN.toString());
        navbarColumn = new TableColumn(navText);
        String fileText = props.getProperty(CourseGeneratorProp.FILE_COLUMN.toString());
        fileColumn = new TableColumn(fileText);
        String scriptText = props.getProperty(CourseGeneratorProp.SCRIPT_COLUMN.toString());
        scriptColumn = new TableColumn(scriptText);
        table.getColumns().add(useColumn);
        table.getColumns().add(navbarColumn);
        table.getColumns().add(fileColumn);
        table.getColumns().add(scriptColumn);
        siteTemplatePane.getChildren().addAll(siteTemplateLbl, templateInfoLbl, directoryLbl, selectTemplateBtn,
                sitePagesLbl, table);
        siteTemplatePane.getStyleClass().add(CLASS_SITE_TEMPLATE_PANE);
        //page style
        pageStylePane = new VBox(5);
        String pageStyleText = props.getProperty(CourseGeneratorProp.PAGE_STYLE.toString());
        pageStyleLbl = new Label(pageStyleText);
        pageStyleLbl.getStyleClass().add(CLASS_PAGE_STYLE_LABEL);
        pageStyleGP = new GridPane();
        String bannerText = props.getProperty(CourseGeneratorProp.BANNER_LBL.toString());
        bannerLbl = new Label(bannerText);
        bannerLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String leftText = props.getProperty(CourseGeneratorProp.LEFT_FOOTER.toString());
        leftFooterLbl = new Label(leftText);
        leftFooterLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        String rightText = props.getProperty(CourseGeneratorProp.RIGHT_FOOTER.toString());
        rightFooterLbl = new Label(rightText);
        rightFooterLbl.getStyleClass().add(CLASS_ORDINARY_LABEL);
        bannerTxt = new Label("Yale Univercity");
        bannerTxt.getStyleClass().add(CLASS_BANNER_LABEL);
        leftTxt = new Label("Yale Univercity");
        leftTxt.getStyleClass().add(CLASS_BANNER_LABEL);
        rightTxt = new Label("Yale CS");
        rightTxt.getStyleClass().add(CLASS_BANNER_LABEL);
        
        bannerBtn = new Button(changeText);
        leftBtn = new Button(changeText);
        rightBtn = new Button(changeText);
        pageStyleGP.add(bannerLbl, 0, 0);
        pageStyleGP.add(bannerTxt, 1, 0);
        pageStyleGP.add(bannerBtn, 2, 0);
        pageStyleGP.add(leftFooterLbl, 0, 1);
        pageStyleGP.add(leftTxt, 1, 1);
        pageStyleGP.add(leftBtn, 2, 1);
        pageStyleGP.add(rightFooterLbl, 0, 2);
        pageStyleGP.add(rightTxt, 1, 2);
        pageStyleGP.add(rightBtn, 2, 2);
        pageStyleGP.setHgap(10);
        pageStyleGP.setVgap(5);
        stylePane = new HBox(60);
        String sheetText = props.getProperty(CourseGeneratorProp.STYLESHEET.toString());
        styleSheet = new Label(sheetText);
        styleSheet.getStyleClass().add(CLASS_ORDINARY_LABEL);
        styleBox = new ComboBox();
        stylePane.getChildren().addAll(styleSheet, styleBox);
        styleBox.setMinWidth(200);
        styleBox.getItems().add("sea_wolf.css");
        styleBox.getSelectionModel().selectFirst();
        String sheetInfoText = props.getProperty(CourseGeneratorProp.STYLESHEET_INFO.toString());
        noteLbl = new Label(sheetInfoText);
        pageStylePane.getChildren().addAll(pageStyleLbl, pageStyleGP, stylePane, noteLbl);
        pageStylePane.getStyleClass().add(CLASS_PAGE_STYLE_PANE);
        //and add all mini panes into one big one
        forScroll.getChildren().addAll(courseInfoPane, siteTemplatePane, pageStylePane);
       // mainPane.getChildren().addAll(courseInfoPane, siteTemplatePane, pageStylePane);
        mainPane.getChildren().add(sPane);
        mainPane.getStyleClass().add(CLASS_COURSE_TAB_PANE);
        forScroll.getStyleClass().add(CLASS_COURSE_SCROLL_PANE);
        sPane.getStyleClass().add(CLASS_COURSE_SCROLL_PANE);
        
        useColumn.setCellValueFactory(
                new PropertyValueFactory<>("use")
        );
        navbarColumn.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        fileColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        scriptColumn.setCellValueFactory(
                new PropertyValueFactory<>("script")
        );
        
        
 //       tableData.add(new SiteTemplate(true, "Home", "index.html", "HomeBuilder.js"));
//        tableData.add(new SiteTemplate(true, "Syllabus", "syllabus.html", "SyllabusBuilder.js"));
//        tableData.add(new SiteTemplate(true, "Schedule", "schedule.html", "ScheduleBuilder.js"));
//        tableData.add(new SiteTemplate(true, "HWs", "hws.html", "HWsBuilder.js"));
//        tableData.add(new SiteTemplate(false, "Projects", "projects.html", "ProjectsBuilder.js"));
        
        
      //  table.prefHeightProperty().bind(table.fixedCellSizeProperty().multiply(Bindings.size(table.getItems()).add(1.01)));
        table.setMaxWidth(600);
        table.setMaxHeight(180);
        navbarColumn.prefWidthProperty().bind(table.widthProperty().multiply(.3));
        fileColumn.prefWidthProperty().bind(table.widthProperty().multiply(.3));
        scriptColumn.prefWidthProperty().bind(table.widthProperty().multiply(.3));
     useColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SiteTemplate, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<SiteTemplate, Boolean> param) {
                SiteTemplate person = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.isUse());

                // Note: singleCol.setOnEditCommit(): Not work for
                // CheckBoxTableCell.
                // When "Single?" column change.
                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) {
                        // person.setSingle(newValue);
                        person.setUse(newValue);
                    }
                });
                return booleanProp;
            }
        });
        useColumn.setCellFactory(new Callback<TableColumn<SiteTemplate, Boolean>, //
                TableCell<SiteTemplate, Boolean>>() {
            @Override
            public TableCell<SiteTemplate, Boolean> call(TableColumn<SiteTemplate, Boolean> p) {
                CheckBoxTableCell<SiteTemplate, Boolean> cell = new CheckBoxTableCell<SiteTemplate, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
//        mainPane.setMaxWidth(750);
//        mainPane.setAlignment(Pos.CENTER);

    }

    public VBox getMainPane() {
        return mainPane;
    }
    
    
    /**
     * @return the subjectLbl
     */
    public Label getSubjectLbl() {
        return subjectLbl;
    }

    /**
     * @return the semesterLbl
     */
    public Label getSemesterLbl() {
        return semesterLbl;
    }

    /**
     * @return the numberLbl
     */
    public Label getNumberLbl() {
        return numberLbl;
    }

    /**
     * @return the yearLbl
     */
    public Label getYearLbl() {
        return yearLbl;
    }

    /**
     * @return the subjectBox
     */
    public ComboBox<String> getSubjectBox() {
        return subjectBox;
    }

    /**
     * @return the semesterBox
     */
    public ComboBox<String> getSemesterBox() {
        return semesterBox;
    }

    /**
     * @return the numberBox
     */
    public ComboBox<String> getNumberBox() {
        return numberBox;
    }

    /**
     * @return the yearBox
     */
    public ComboBox<String> getYearBox() {
        return yearBox;
    }

    /**
     * @return the titleFld
     */
    public TextField getTitleFld() {
        return titleFld;
    }

    /**
     * @return the instructorNameFld
     */
    public TextField getInstructorNameFld() {
        return instructorNameFld;
    }

    /**
     * @return the instructorHomeFld
     */
    public TextField getInstructorHomeFld() {
        return instructorHomeFld;
    }

}
