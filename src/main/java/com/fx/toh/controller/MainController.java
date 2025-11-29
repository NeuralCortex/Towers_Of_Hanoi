/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fx.toh.controller;

import com.fx.toh.Globals;
import com.fx.toh.diagrams.TohAnchorPane;
import com.fx.toh.pojo.DiskPOJO;
import com.fx.toh.pojo.StepPOJO;
import com.fx.toh.tools.HelperFunctions;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pscha
 */
public class MainController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lbStatus;
    @FXML
    private HBox hboxStatus;
    @FXML
    private Label lbInfo;
    @FXML
    private Menu menuFile;
    @FXML
    private Menu menuHelp;
    @FXML
    private MenuItem miClose;
    @FXML
    private MenuItem miAbout;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnCSV;
    @FXML
    private HBox hBoxTop;
    @FXML
    private TableView<StepPOJO> table;
    @FXML
    private Label lbSteps;
    @FXML
    private ComboBox<Integer> cbSize;

    private static final Logger _log = LogManager.getLogger(MainController.class);
    private final Stage stage;
    private List<List<DiskPOJO>> stateList;
    private List<StepPOJO> stepList;

    public MainController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle bundle) {
        borderPane.setPrefSize(Globals.WIDTH, Globals.HEIGHT);

        init(bundle);

        TableColumn colStep = new TableColumn(bundle.getString("col.step"));
        TableColumn colDisk = new TableColumn(bundle.getString("col.disk"));
        TableColumn colFrom = new TableColumn(bundle.getString("col.from"));
        TableColumn colTo = new TableColumn(bundle.getString("col.to"));

        colStep.setCellValueFactory(new PropertyValueFactory("step"));
        colDisk.setCellValueFactory(new PropertyValueFactory("disk"));
        colFrom.setCellValueFactory(new PropertyValueFactory("from"));
        colTo.setCellValueFactory(new PropertyValueFactory("to"));

        table.getColumns().addAll(colStep, colDisk, colFrom, colTo);

        TohAnchorPane tohAnchorPane = new TohAnchorPane(bundle);
        AnchorPane.setBottomAnchor(tohAnchorPane, 0.0);
        AnchorPane.setTopAnchor(tohAnchorPane, 0.0);
        AnchorPane.setRightAnchor(tohAnchorPane, 0.0);
        AnchorPane.setLeftAnchor(tohAnchorPane, 0.0);

        anchorPane.setId("blue");
        anchorPane.getChildren().add(tohAnchorPane);

        miClose.setOnAction(e -> {
            System.exit(0);
        });

        miAbout.setOnAction(e -> {
            showAboutDlg(bundle);
        });

        initAllData(bundle, tohAnchorPane, 3);

        table.getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            int idx = table.getSelectionModel().getSelectedIndex();

            createStateList(cbSize.getSelectionModel().getSelectedItem());

            for (int i = 0; i <= idx; i++) {
                StepPOJO stepPOJO = stepList.get(i);

                stateList.get(stepPOJO.getFrom()).remove(stateList.get(stepPOJO.getFrom()).size() - 1);
                stateList.get(stepPOJO.getTo()).add(new DiskPOJO(i, stepPOJO.getDisk()));
            }

            int size = cbSize.getSelectionModel().getSelectedItem();
            tohAnchorPane.setStateList(stateList, size);
            //tohAnchorPane.initColorList(size);
            tohAnchorPane.redraw();
        });

        cbSize.getSelectionModel().selectedItemProperty().addListener((ov, o, n) -> {
            table.getSelectionModel().clearSelection();
            initAllData(bundle, tohAnchorPane, n);
        });

        btnReset.setOnAction(e -> {
            table.getSelectionModel().clearSelection();
            cbSize.getSelectionModel().selectFirst();
        });

        btnCSV.setOnAction(e -> {
            int size = cbSize.getSelectionModel().getSelectedItem();

            stepList = new ArrayList<>();
            StepPOJO start = new StepPOJO(1, 0, 0);
            stepList.add(start);
            hanoi(size, 0, 1, 2, stepList);

            int step = 1;
            for (int i = 0; i < stepList.size(); i++) {
                stepList.get(i).setStep(step++);
                //System.out.println(stepList.get(i));
            }

            for (int idx = 0; idx < stepList.size(); idx++) {
                createStateList(size);
                for (int i = 0; i <= idx; i++) {
                    StepPOJO stepPOJO = stepList.get(i);

                    stateList.get(stepPOJO.getFrom()).remove(stateList.get(stepPOJO.getFrom()).size() - 1);
                    stateList.get(stepPOJO.getTo()).add(new DiskPOJO(i, stepPOJO.getDisk()));
                }
                stepList.get(idx).setStateList(stateList);
            }

            FileChooser fileChooser = new FileChooser();
            String fileName = bundle.getString("app.name") + " N" + String.format("%02d", size) + ".csv";
            fileChooser.setInitialFileName(fileName);
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV-Files", "*.csv"));
            String csvDirString = System.getProperty("user.dir") + "/csv/";
            File csvDir = new File(csvDirString);
            if (!csvDir.exists()) {
                csvDir.mkdir();
            }
            fileChooser.setInitialDirectory(new File(Globals.propman.getProperty(Globals.CSV_DIR, csvDirString)));
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                    String header = bundle.getString("col.step") + ";"
                            + bundle.getString("col.disk") + ";"
                            + bundle.getString("col.from") + ";"
                            + bundle.getString("col.to") + ";"
                            + bundle.getString("tower") + " 0;"
                            + bundle.getString("tower") + " 1;"
                            + bundle.getString("tower") + " 2;";
                    bw.write(header);
                    bw.newLine();
                    for (StepPOJO stepPOJO : stepList) {
                        String line = stepPOJO.getStep() + ";"
                                + stepPOJO.getDisk() + ";"
                                + stepPOJO.getFrom() + ";"
                                + stepPOJO.getTo() + ";"
                                + stepPOJO.getTowerStack(0) + ";"
                                + stepPOJO.getTowerStack(1) + ";"
                                + stepPOJO.getTowerStack(2) + ";";
                        bw.write(line);
                        bw.newLine();
                    }
                    bw.flush();
                    bw.close();
                } catch (Exception ex) {
                    _log.error(ex.getMessage());
                }
                Globals.propman.setProperty(Globals.CSV_DIR, file.getParent());
                Globals.propman.save();
            }
        });

        cbSize.getSelectionModel().selectFirst();
    }

    private void initAllData(ResourceBundle bundle, TohAnchorPane tohAnchorPane, int size) {
        stepList = new ArrayList<>();
        stepList.add(new StepPOJO(1, 0, 0));
        hanoi(size, 0, 1, 2, stepList);
        createStateList(size);

        int step = 1;
        for (int i = 1; i < stepList.size(); i++) {
            stepList.get(i).setStep(step++);
        }

        table.getItems().clear();
        table.setItems(FXCollections.observableArrayList(stepList));

        tohAnchorPane.setStateList(stateList, size);
        tohAnchorPane.initColorList(size);
        tohAnchorPane.redraw();

        lbSteps.setText(bundle.getString("lb.solution") + ": " + stepList.size());
    }

    private void createStateList(int stackSize) {
        stateList = new ArrayList<>();
        stateList.add(new ArrayList<>());
        for (int i = 0; i < stackSize; i++) {
            stateList.get(0).add(new DiskPOJO(0, stackSize - i));
        }
        stateList.add(new ArrayList<>());
        stateList.add(new ArrayList<>());
    }

    private void hanoi(int disks, int from, int inter, int to, List<StepPOJO> list) {
        if (disks == 1) {
            list.add(new StepPOJO(1, from, to));
        } else {
            hanoi(disks - 1, from, to, inter, list);
            list.add(new StepPOJO(disks, from, to));
            hanoi(disks - 1, inter, from, to, list);
        }
    }

    private void showAboutDlg(ResourceBundle bundle) {
        Alert alert = new Alert(AlertType.INFORMATION);
        HelperFunctions.centerWindow(alert.getDialogPane().getScene().getWindow());

        Stage stageDlg = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.getDialogPane().getStylesheets().add(Globals.CSS_PATH);
        try {
            stageDlg.getIcons().add(new Image(new FileInputStream(new File(Globals.APP_LOGO_PATH))));
        } catch (Exception ex) {
            _log.error(ex.getMessage());
        }

        alert.setTitle(bundle.getString("dlg.about.info"));
        alert.setHeaderText(bundle.getString("dlg.about.header"));
        String programmer = bundle.getString("dlg.about.content");
        alert.setContentText(MessageFormat.format(programmer, LocalDate.now().getYear()));

        alert.showAndWait();
    }

    private void init(ResourceBundle bundle) {
        hboxStatus.setId("blue");
        hBoxTop.setId("blue");

        menuFile.setText(bundle.getString("menu.file"));
        menuHelp.setText(bundle.getString("menu.help"));

        miAbout.setText(bundle.getString("mi.about"));
        miClose.setText(bundle.getString("mi.close"));

        btnReset.setText(bundle.getString("btn.reset"));
        btnCSV.setText(bundle.getString("btn.csv"));

        lbSteps.setText("");

        String programmer = bundle.getString("dlg.about.content");
        lbInfo.setText(MessageFormat.format(programmer, LocalDate.now().getYear()));

        cbSize.getItems().clear();
        for (int i = 3; i < 11; i++) {
            cbSize.getItems().add(i);
        }
    }
}
