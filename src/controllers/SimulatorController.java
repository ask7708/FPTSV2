package controllers;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Equity;
import model.Simulation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Stack;

/**
 * @author dxr5716 Daniel Roach
 */
public class SimulatorController {

    @FXML
    private TableView<Equity> equitiesTable;

        @FXML
        private TableColumn<Equity, String> tickSymbolColumn;
        @FXML
        private TableColumn<Equity, String> nameColumn;
        @FXML
        private TableColumn<Equity, Double> priceColumn;
        @FXML
        private TableColumn<Equity, Double> simPriceColumn;

    @FXML
    private TableView<Simulation> simulationsTable;

        @FXML
        private TableColumn<Simulation, String> simColumn;

    @FXML
    private Button addNewSimButton;
    @FXML
    private Button resetOneButton;
    @FXML
    private Button resetAllButton;
    @FXML
    private Button exitButton;

    @FXML
    private ImageView arrowView;
    @FXML
    private Label percentChange;

    @FXML
    private Label simulatorDate;

    @FXML
    private Label portfolioValue;

    private ObservableList<Simulation> simulations =
            FXCollections.observableArrayList();

    public SimulatorController() {}

    @FXML
    private void initialize() {

        simulationsTable.setItems(simulations);
        tickSymbolColumn.setCellValueFactory(cellData -> cellData.getValue().tickSymbolProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    //    priceColumn.setCellValueFactory(cellData -> cellData.getValue()
    //            .initPriceProperty());
        simColumn.setCellValueFactory(cellData -> cellData.getValue().shortVersionProperty());
        resetOneButton.setDisable(true);
        resetAllButton.setDisable(true);

        simColumn.setVisible(true);
        simulatorDate.setText(LocalDate.now().toString());
        percentChange.setText("0.0%");
    }

    @FXML
    public void addNewSimulation() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("../views/AddNewSimulation" +
                    ".fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("FPTS - Simulator - Add New Simulation");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddNewSimController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            Simulation newSim = new Simulation();
            System.out.println("Simulation given");
            controller.setSimulation(newSim);
            simulations.add(newSim);
            addNewSimButton.setDisable(true);
            dialogStage.showAndWait();
            addNewSimButton.setDisable(false);

            checkForResets();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    @FXML
    private void removeOneSimulation() {

        System.out.println("Reset One pressed");
        simulations.remove(0);
        checkForResets();
    }

    @FXML
    private void removeAllSimulation() {

        System.out.println("Reset All pressed");

        while(!simulations.isEmpty()) { removeOneSimulation(); }

        checkForResets();
    }

    private void checkForResets() {

        System.out.println("Checks for resets called");
        if(!simulations.isEmpty()) {

            resetOneButton.setDisable(false);
            resetAllButton.setDisable(false);
        }

        else {

            resetOneButton.setDisable(true);
            resetAllButton.setDisable(true);
        }
    }
}

