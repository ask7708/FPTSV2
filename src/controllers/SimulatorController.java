package controllers;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        private TableColumn<Equity, String> priceColumn;
        @FXML
        private TableColumn<Equity, String> simPriceColumn;

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

    private App application;
    
    private ObservableList<Simulation> simulations;
            
    private ObservableList<Equity> equities;
    
    @FXML
    private void initialize() {

        simulations = FXCollections.observableArrayList();
        equities = FXCollections.observableArrayList();
        
        Equity e1 = new Equity("GOOG", "Google Inc.", 50.00);
        e1.putSimulationOn();
        
        Equity e2 = new Equity("AAPL", "Apple Inc.", 100.00);
        e2.putSimulationOn();
        
        Equity e3 = new Equity("MSFT", "Microsoft Corporation", 150.00);
        e3.putSimulationOn();
        
        equities.add(e1);
        equities.add(e2);
        equities.add(e3);
        
        tickSymbolColumn.setCellValueFactory(cellData -> cellData.getValue().tickSymbolProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());
        simPriceColumn.setCellValueFactory(cellData -> cellData.getValue().simPriceProperty());
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
            controller.getSimulations(simulations);
            addNewSimButton.setDisable(true);
            dialogStage.showAndWait();
            addNewSimButton.setDisable(false);
            
            simulationsTable.setItems(simulations);
            
            for(Equity holding: equities)
               //simulations.get(simulations.size()-1).changeHoldingPrice(holding);
               simulations.get(0).changeHoldingPrice(holding);

            
            equitiesTable.setItems(equities);
            
            checkForResets();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    @FXML
    private void removeOneSimulation() {

        simulations.remove(0);
        
        for(Equity obj: this.equities) { obj.removePriceChange(); }
        //simPriceColumn.setCellValueFactory(cellData -> cellData.getValue().getSimPriceProperty().asObject());
        checkForResets();
    }

    @FXML
    private void removeAllSimulation() {

        while(!simulations.isEmpty()) { removeOneSimulation(); }
        checkForResets();
    }

    @FXML
    private void exitHandler() throws IOException { 
       
       for(Equity obj: equities)
          obj.putSimulationOff();
       
       this.application.showDashboardView(); 
    }
    
    private void checkForResets() {

        if(!simulations.isEmpty()) {

            resetOneButton.setDisable(false);
            resetAllButton.setDisable(false);
        }

        else {

            resetOneButton.setDisable(true);
            resetAllButton.setDisable(true);
        }
    }
    
    public void setMainApp(App app) { this.application = app; }
    
    public void getEquities(ObservableList<Equity> e) { this.equities = e; } 
}