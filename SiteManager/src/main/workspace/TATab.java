package main.workspace;

import djf.components.AppDataComponent;
import static djf.settings.AppPropertyType.DELETE_ICON;
import static djf.settings.AppPropertyType.DELETE_TOOLTIP;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import tam.CourseGeneratorApp;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import properties_manager.PropertiesManager;
import tam.CourseGeneratorProp;
import tam.data.GridClass;
import tam.data.GridTime;
import tam.style.TAStyle;
import tam.data.TeachingAssistant;
import tam.file.TimeSlot;
import static tam.style.TAStyle.CLASS_REMOVE_TA_BTN;
import static tam.style.TAStyle.CLASS_RIGHT_PANE;
import static tam.style.TAStyle.CLASS_TA_SCROLL_PANE;
import static tam.style.TAStyle.CLASS_TA_SPLIT_PANE;
import static tam.style.TAStyle.CLASS_TA_TAB;

/**
 * This class serves as the workspace component for the TA Manager application.
 * It provides all the user interface controls in the workspace area.
 *
 * @author Richard McKenna
 */
public class TATab {

    private BorderPane workspace;
    TeachingAssistant tt;
    private GridClass gc;

    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    CourseGeneratorApp app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    TAController controller;

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;

    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, Boolean> undergradColumn;
    // TableColumn<TeachingAssistant, Boolean> undergradColumn;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;

    // THE TA INPUT
    HBox addBox;
    HBox addPanel;
    HBox buttonBox;
    private TextField nameTextField;
    private TextField emailTextField;
    Button addButton;
    Button updateButton;
    Button clearButton;
    HBox timepane;

    //ComboBoxes for Start and End Time
    private ComboBox<String> startTime;
    private ComboBox<String> endTime;
    private Label startLabel;
    private Label endLabel;
    private Button ok;
    private Button removeBtn;

    // Alert Info
    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;

    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;
    TableRow<TeachingAssistant> rr;
    TableView<String> tv;
    TableColumn startColumn;
    TableColumn endColumn;
    TableColumn monColumn;
    TableColumn tueColumn;
    TableColumn wedColumn;
    TableColumn thuColumn;
    TableColumn friColumn;

    /**
     * The contstructor initializes the user interface, except for the full
     * office hours grid, since it doesn't yet know what the hours will be until
     * a file is loaded or a new one is created.
     */
    public TATab(CourseGeneratorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(CourseGeneratorProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        tasHeaderBox.getChildren().add(tasHeaderLabel);
        String startLabelText = props.getProperty(CourseGeneratorProp.START_LABEL_TEXT.toString());
        startLabel = new Label(startLabelText);

        String endLabelText = props.getProperty(CourseGeneratorProp.END_LABEL_TEXT.toString());
        endLabel = new Label(endLabelText);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TempData data = (TempData) app.getDataComponent();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String undergradColumnText = props.getProperty(CourseGeneratorProp.UNDERGRAD_COLUMN_TEXT.toString());
        String nameColumnText = props.getProperty(CourseGeneratorProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(CourseGeneratorProp.EMAIL_COLUMN_TEXT.toString());
        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
//        );
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
//        taTable.getColumns().add(undergradColumn);
//        taTable.getColumns().add(nameColumn);
//        taTable.getColumns().add(emailColumn);
//        undergradColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(.2));
//        nameColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(.3));
//        emailColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(.4));

        ////////
        undergradColumn = new TableColumn<>(undergradColumnText);

        undergradColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TeachingAssistant, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TeachingAssistant, Boolean> param) {
                TeachingAssistant person = param.getValue();

                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.getUndergrad());

                // Note: singleCol.setOnEditCommit(): Not work for
                // CheckBoxTableCell.
                // When "Single?" column change.
                booleanProp.addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) {
                        // person.setSingle(newValue);
                        person.setUndergrad(newValue);
                    }
                });
                return booleanProp;
            }
        });
        undergradColumn.setCellFactory(new Callback<TableColumn<TeachingAssistant, Boolean>, //
                TableCell<TeachingAssistant, Boolean>>() {
            @Override
            public TableCell<TeachingAssistant, Boolean> call(TableColumn<TeachingAssistant, Boolean> p) {
                CheckBoxTableCell<TeachingAssistant, Boolean> cell = new CheckBoxTableCell<TeachingAssistant, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        taTable.getColumns().add(undergradColumn);
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);
        undergradColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(.2));
        nameColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(.3));
        emailColumn.prefWidthProperty().bind(taTable.widthProperty().multiply(.4));
        ////////
        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(CourseGeneratorProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(CourseGeneratorProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(CourseGeneratorProp.ADD_BUTTON_TEXT.toString());
        String updateButtonText = props.getProperty(CourseGeneratorProp.UPDATE_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(CourseGeneratorProp.CLEAR_BUTTON_TEXT.toString());
        String okButtonText = props.getProperty(CourseGeneratorProp.OK_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        emailTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        updateButton = new Button(updateButtonText);
        addBox = new HBox(5);
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);

        buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(5, 10, 10, 5));
        buttonBox.getChildren().add(addButton);

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(CourseGeneratorProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);

        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();

        // ORGANIZE THE LEFT AND RIGHT PANES
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tasHeaderBox);
        leftPane.getChildren().add(taTable);
        addPanel = new HBox(addBox, buttonBox);
        leftPane.getChildren().addAll(addPanel);
        nameTextField.prefWidthProperty().bind(addPanel.widthProperty().multiply(.3));
        emailTextField.prefWidthProperty().bind(addPanel.widthProperty().multiply(.3));
        addButton.prefWidthProperty().bind(addPanel.widthProperty().multiply(.2));
        VBox rightPane = new VBox();
        VBox forGridHeader = new VBox();
        forGridHeader.getChildren().add(officeHoursHeaderBox);
        
        VBox forGrid = new VBox();
        forGrid.getChildren().add(officeHoursGridPane);
        
        rightPane.getChildren().addAll(forGridHeader, forGrid);
        rightPane.getStyleClass().add(CLASS_RIGHT_PANE);
        forGrid.getStyleClass().add(CLASS_RIGHT_PANE);
        //  officeHoursGridPane.prefWidthProperty().bind(rightPane.widthProperty());
        leftPane.setPadding(new Insets(0, 10, 0, 0));
        VBox testPane = new VBox();
        testPane.getStyleClass().add(CLASS_TA_SCROLL_PANE);
        
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        ScrollPane scroll = new ScrollPane(forGrid);
        testPane.getChildren().addAll(forGridHeader,scroll);
        HBox sPane = new HBox(20);//(leftPane, testPane);
        sPane.getChildren().addAll(leftPane, testPane);
        sPane.getStyleClass().add(CLASS_TA_SPLIT_PANE);
        scroll.getStyleClass().add(CLASS_TA_SCROLL_PANE);
        workspace = new BorderPane();
        workspace.getStyleClass().add(CLASS_TA_TAB);
        leftPane.minWidthProperty().bind(sPane.widthProperty().multiply(.4));
        leftPane.maxWidthProperty().bind(sPane.widthProperty().multiply(.4));
        // AND PUT EVERYTHING IN THE WORKSPACE
        workspace.setCenter(sPane);

        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));

        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new TAController(app);

        // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        emailTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            controller.handleAddTA();
        });

        taTable.setFocusTraversable(true);
        taTable.setOnKeyPressed(e -> {
            controller.handleKeyPress(e.getCode());
        });
        taTable.setEditable(true);

        taTable.setRowFactory(tv -> {
            TableRow<TeachingAssistant> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    setRow(row);
                    tt = row.getItem();
                    //System.out.println(tt.getEmail());
                    nameTextField.setText(tt.getName());
                    emailTextField.setText(tt.getEmail());
                    buttonBox.getChildren().clear();
                    buttonBox.getChildren().add(updateButton);
                    buttonBox.getChildren().add(clearButton);

                }
            });
            return row;
        });

        updateButton.setOnAction(e -> {
            controller.handleUpdateTA(tt.getName(), tt.getEmail());
        });
        clearButton = new Button(clearButtonText);
        buttonBox.getChildren().add(clearButton);
        clearButton.prefWidthProperty().bind(addPanel.widthProperty().multiply(.15));

        clearButton.setOnAction(e -> {
            taTable.getSelectionModel().clearSelection();
            controller.handleClearButton();
        });

        startTime = new ComboBox();
        endTime = new ComboBox();

        // officeHoursHeaderBox.getChildren().addAll(startLabel, startTime, endTime);
        ok = new Button(okButtonText);
        timepane = new HBox(5);
        timepane.setAlignment(Pos.CENTER);
        timepane.getChildren().addAll(startLabel, startTime, endLabel, endTime, ok);
        officeHoursHeaderBox.getChildren().add(timepane);
        timepane.setPadding(new Insets(10, 10, 10, 50));
        gc = new GridClass();
        for (int i = 0; i < 24; i++) {
            gc.addTime(new GridTime(i, getStandartTime(i)));
            startTime.getItems().add(gc.getTime(i).getAmericanTime());
            endTime.getItems().add(gc.getTime(i).getAmericanTime());
        }

        // officeHoursHeaderBox.getChildren().add(ok);
        String errorText1 = props.getProperty(CourseGeneratorProp.ERROR_TEXT1.toString());
        String errorText2 = props.getProperty(CourseGeneratorProp.ERROR_TEXT2.toString());
        String errorText3 = props.getProperty(CourseGeneratorProp.ERROR_TEXT3.toString());
        ok.setOnAction(e -> {
            int firstStime = data.getStartHour();
            int firstEtime = data.getEndHour();
            int sTime = gc.getATime(startTime.getValue()).getMilitaryTime();
            int eTime = gc.getATime(endTime.getValue()).getMilitaryTime();
            if (eTime <= sTime) {
                errorWindow(errorText1, errorText2, errorText3);
            } else {
                ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(data);
                controller.doComboBox(firstStime, firstEtime, sTime, eTime, data, this);
            }
        });

        app.getGUI().getPrimaryScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Y && e.isControlDown()) {
                data.doTransactionTA();
            } else if (e.getCode() == KeyCode.Z && e.isControlDown()) {
                data.undoTransactionTA();
            }
        });
        nameTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Y && e.isControlDown()) {
                data.doTransactionTA();
            } else if (e.getCode() == KeyCode.Z && e.isControlDown()) {
                data.undoTransactionTA();

            }
        });
        emailTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.Y && e.isControlDown()) {
                data.doTransactionTA();
            } else if (e.getCode() == KeyCode.Z && e.isControlDown()) {
                data.undoTransactionTA();

            }
        });
        removeBtn = new Button();
        removeBtn.setPadding(new Insets(15,0,0,0));
        removeBtn.getStyleClass().add(CLASS_REMOVE_TA_BTN);
        // PropertiesManager props = PropertiesManager.getPropertiesManager();

        // LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(DELETE_ICON.toString());
        Image buttonImage = new Image(imagePath);

        // NOW MAKE THE BUTTON
        
        removeBtn.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(DELETE_TOOLTIP.toString()));
        removeBtn.setTooltip(buttonTooltip);
        tasHeaderBox.getChildren().add(removeBtn);
        removeBtn.setOnAction(e -> {
            controller.handleRemoveTA();
        });

        tv = new TableView();
        startColumn = new TableColumn();
        endColumn = new TableColumn();
        monColumn = new TableColumn();
        tueColumn = new TableColumn();
        wedColumn = new TableColumn();
        thuColumn = new TableColumn();
        friColumn = new TableColumn();
        tv.getColumns().add(startColumn);
        tv.getColumns().add(endColumn);
        tv.getColumns().add(monColumn);
        tv.getColumns().add(tueColumn);
        tv.getColumns().add(wedColumn);
        tv.getColumns().add(thuColumn);
        tv.getColumns().add(friColumn);

      //  taTable.getSelectionModel().setCellSelectionEnabled(true);
//
//        taTable.setOnMouseClicked(e -> {
//            TablePosition pos = tv.getSelectionModel().getSelectedCells().get(0);
//            
//            
//            taTable.setOnMouseClicked(ev -> {
//            int row = pos.getRow();
//
//// Item here is the table view type:
//            String item = tv.getItems().get(row);
//
//            TableColumn col = pos.getTableColumn();
//
//// this gives the value in the selected cell:
//            String d = (String) col.getCellObservableValue(item).getValue();
//            System.out.println(d);
//            });
//
//        });

    }

    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT
    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    //@Override
    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();

        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
    }

    //@Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        TempData taData = (TempData) dataComponent;
        getNameTextField().setText("");
        getEmailTextField().setText("");
        getStartTime().getSelectionModel().select(gc.getTime(taData.getStartHour()).getAmericanTime());
        getEndTime().getSelectionModel().select(gc.getTime(taData.getEndHour()).getAmericanTime());
        System.out.println("STEP1");
        reloadOfficeHoursGrid(taData);
    }

    public void reloadOfficeHoursGrid(TempData dataComponent) {
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();
        System.out.println("STEP2");

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row + 1);
            dataComponent.getCellTextProperty(col, row + 1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row + 1);
            dataComponent.getCellTextProperty(col, row + 1).set(buildCellText(endHour + 1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row + 1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setFocusTraversable(true);
            p.setOnKeyPressed(e -> {
                controller.handleKeyPress(e.getCode());
            });
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseExited(e -> {
                controller.handleGridCellMouseExited((Pane) e.getSource());
            });
            p.setOnMouseEntered(e -> {
                controller.handleGridCellMouseEntered((Pane) e.getSource());
            });
        }

        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        TAStyle taStyle = (TAStyle) app.getStyleComponent();
        taStyle.initOfficeHoursGridStyle();
    }

    public void addCellToGrid(TempData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
    //    cellPane.setMinSize(120, 70);
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);

        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);

        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());
    }

    public String getStandartTime(int num) {
        String newNum;
        if (num == 0) {
            return "12:00AM";
        } else if (num > 0 && num < 12) {
            newNum = num + ":00AM";
            return newNum;
        } else if (num > 12) {
            int n = num - 12;
            newNum = n + ":00PM";
            return newNum;
        } else {
            return "12:00PM";
        }
    }

    public int getMilitaryTime(String num) {
        String newNum[] = num.split(":");
        if (num.contains("AM")) {
            if (newNum[0].equals("12")) {
                return 0;
            } else {
                return Integer.parseInt(newNum[0]);
            }
        } else if (num.contains("PM")) {
            if (newNum[0].equals("12")) {
                return 12;
            } else {
                return Integer.parseInt(newNum[0]) + 12;
            }
        }
        return 0;

    }

    public void errorWindow(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    /**
     * @param nameTextField the nameTextField to set
     */
    public void setNameTextField(TextField nameTextField) {
        this.nameTextField = nameTextField;
    }

    /**
     * @param emailTextField the emailTextField to set
     */
    public void setEmailTextField(TextField emailTextField) {
        this.emailTextField = emailTextField;
    }

    /**
     * @return the startTime
     */
    public ComboBox<String> getStartTime() {
        return startTime;
    }

    /**
     * @return the endTime
     */
    public ComboBox<String> getEndTime() {
        return endTime;
    }

    /**
     * @return the gc
     */
    public GridClass getGc() {
        return gc;
    }

    /**
     * @return the startLabel
     */
    public Label getStartLabel() {
        return startLabel;
    }

    /**
     * @return the endLabel
     */
    public Label getEndLabel() {
        return endLabel;
    }

    /**
     * @return the ok
     */
    public Button getOk() {
        return ok;
    }

    public void setRow(TableRow<TeachingAssistant> row) {
        rr = row;
    }

    public TableRow<TeachingAssistant> getRow() {
        return rr;
    }

    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public HBox getButtonBox() {
        return buttonBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }

    public void setOfficeHoursGridTACellLabels(HashMap<String, Label> l) {
        this.officeHoursGridTACellLabels = l;
    }

    /**
     * @return the workspace
     */
    public BorderPane getWorkspace() {
        return workspace;
    }

}
