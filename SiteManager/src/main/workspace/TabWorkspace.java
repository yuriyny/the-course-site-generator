/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import properties_manager.PropertiesManager;
import tam.CourseGeneratorApp;
import tam.CourseGeneratorProp;
import tam.data.GridClass;
import static tam.style.TAStyle.CLASS_TAB1;
import static tam.style.TAStyle.CLASS_TAB_PANE;
import static tam.style.TAStyle.CLASS_WORKSPACE_PANE;

/**
 *
 * @author Home
 */
public class TabWorkspace extends AppWorkspaceComponent {
    private Group thePane;
    StackPane ss;
    
    CourseGeneratorApp app;
    TATab td;
    private CourseTab cd;
    private RecitationTab rt;
    private ScheduleTab st;
    private ProjectTab pt;

    public TabWorkspace(CourseGeneratorApp initApp) {
        app = initApp;
        td = new TATab(initApp);
        cd = new CourseTab(initApp);
        rt = new RecitationTab(initApp);
        st = new ScheduleTab(initApp);
        pt = new ProjectTab(initApp);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
       
       //  sp.setPadding(new Insets(200,200,200,200));
        // taTab = tt;
        thePane = new Group();
        //thePane.setPadding(new Insets(20,20,20,20));
        Group root = new Group();

        TabPane tabPane = new TabPane();
       // tabPane.getStyleClass().add(CLASS_TAB_PANE)
        tabPane.getStyleClass().add(CLASS_TAB_PANE);
        workspace = new BorderPane();
        Tab tab = new Tab();
        tab.getStyleClass().add(CLASS_TAB1);
        String tadataText = props.getProperty(CourseGeneratorProp.TA_DATA.toString());
        tab.setText(tadataText);
        tab.setContent(td.getWorkspace());
       // tabPane.getTabs().add(tab);
        Tab tab2 = new Tab();
        String cdText = props.getProperty(CourseGeneratorProp.COURSE_DETAILS.toString());
        tab2.setText(cdText);
        tab2.setContent(cd.getMainPane());
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab);
        Tab tab3 = new Tab();
        String rdText = props.getProperty(CourseGeneratorProp.RECITATION_DATA.toString());
        tab3.setText(rdText);
        tab3.setContent(rt.getMainPane());
        tabPane.getTabs().add(tab3);
        Tab tab4 = new Tab();
        String sdText = props.getProperty(CourseGeneratorProp.SCHEDULE_DATA.toString());
        tab4.setText(sdText);
        tab4.setContent(st.getMainPane());
        tabPane.getTabs().add(tab4);
        Tab tab5 = new Tab();
        String pText = props.getProperty(CourseGeneratorProp.PROJECT_DATA.toString());
        tab5.setText(pText);
        tab5.setContent(pt.getMainPane());
        tabPane.getTabs().add(tab5);

        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        root.getChildren().add(tabPane);
        tabPane.prefHeightProperty().bind(workspace.heightProperty());
        tabPane.prefWidthProperty().bind(workspace.widthProperty());
        thePane.getChildren().add(root);
        ss = new StackPane();
        ss.getChildren().add(tabPane);
        ss.setPadding(new Insets(20,20,20,20));
        
        ((BorderPane) workspace).setCenter(ss);
        
      ((BorderPane) workspace).getStyleClass().add(CLASS_WORKSPACE_PANE);

    }

    @Override
    public void resetWorkspace() {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("HERE1");
        td.resetWorkspace();
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("HERE2");
        td.reloadWorkspace(dataComponent);
    }

    public void reloadOfficeHoursGrid(TempData dataComponent) {
        td.reloadOfficeHoursGrid(dataComponent);
    }

    public GridPane getOfficeHoursGridPane() {
        return td.getOfficeHoursGridPane();
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return td.getOfficeHoursGridTimeHeaderPanes();
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return td.getOfficeHoursGridTimeHeaderLabels();
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return td.getOfficeHoursGridDayHeaderPanes();
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return td.getOfficeHoursGridDayHeaderLabels();
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return td.getOfficeHoursGridTimeCellPanes();
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return td.getOfficeHoursGridTimeCellLabels();
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return td.getOfficeHoursGridTACellPanes();
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return td.getOfficeHoursGridTACellLabels();
    }

    public HBox getTAsHeaderBox() {
        return td.getTAsHeaderBox();
    }

    public Label getTAsHeaderLabel() {
        return td.getTAsHeaderLabel();
    }

    public HBox getOfficeHoursSubheaderBox() {
        return td.getOfficeHoursSubheaderBox();
    }

    public Label getOfficeHoursSubheaderLabel() {
        return td.getOfficeHoursSubheaderLabel();
    }

    public Label getStartLabel() {
        return td.getStartLabel();
    }

    /**
     * @return the endLabel
     */
    public Label getEndLabel() {
        return td.getEndLabel();
    }

    public Button getOk() {
        return td.getOk();
    }

    public TableView getTATable() {
        return td.getTATable();
    }

    public HBox getAddBox() {
        return td.getAddBox();
    }

    public TextField getNameTextField() {
        return td.getNameTextField();
    }

    public TextField getEmailTextField() {
        return td.getEmailTextField();
    }

    public Button getAddButton() {
        return td.getAddButton();
    }

    public Button getUpdateButton() {
        return td.getUpdateButton();
    }

    public Button getClearButton() {
        return td.getClearButton();
    }

    public HBox getButtonBox() {
        return td.getButtonBox();
    }

    public Pane getTACellPane(String cellPane) {
        return td.getTACellPane(cellPane);
    }

    public ComboBox<String> getStartTime() {
        return td.getStartTime();
    }

    /**
     * @return the endTime
     */
    public ComboBox<String> getEndTime() {
        return td.getEndTime();
    }

    public GridClass getGc() {
        return td.getGc();
    }

    /**
     * @return the st
     */
    public ScheduleTab getSt() {
        return st;
    }

    /**
     * @return the rt
     */
    public RecitationTab getRt() {
        return rt;
    }

    /**
     * @return the pt
     */
    public ProjectTab getPt() {
        return pt;
    }

    /**
     * @return the cd
     */
    public CourseTab getCd() {
        return cd;
    }

}
