package controllers;

import java.io.IOException;
import java.time.LocalDate;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Equity;
import model.Simulation;

/**
 * @author dxr5716 Daniel Roach
 * @author ask7708 Arshdeep Khalsa
 * @author tna3531 Talal Alsarrani
 * @author dcc7331 Daniel Cypher
 */
public class SimulatorController {

   /**
    * the table listing all of the user's equities
    */
    @FXML private TableView<Equity> equitiesTable;

        /**
         * the column listing the ticker symbol of each equity
         */
        @FXML private TableColumn<Equity, String> tickSymbolColumn;
        
        /**
         * the column listing the name of each equity
         */
        @FXML private TableColumn<Equity, String> nameColumn;
        
        /**
         * the column listing the price of each equity
         */
        @FXML private TableColumn<Equity, String> priceColumn;
        
        /**
         * the column listing the simulation price of each equity
         */
        @FXML private TableColumn<Equity, String> simPriceColumn;

    /**
     * the table listing all of the simulations made    
     */
    @FXML private TableView<Simulation> simulationsTable;

        /**
         * the column listing each simulation made in a formatted string
         */
        @FXML private TableColumn<Simulation, String> simColumn;

    /**
     * the 'Create Sim' button    
     */
    @FXML private Button addNewSimButton;
    
    /**
     * the 'Remove Sim' button
     */
    @FXML private Button resetOneButton;
    
    /**
     * the 'Remove All' button
     */
    @FXML private Button resetAllButton;
    
    /**
     * the 'Exit' button
     */
    @FXML private Button exitButton;

    /**
     * the ImageView displaying an up or down arrow
     */
    @FXML private ImageView arrowView;
    
    /**
     * the Label displaying the percent change
     */
    @FXML private Label percentChange;

    /**
     * the Label displaying the date of the Simulator
     */
    @FXML private Label simulatorDate;

    /**
     * the Label displaying the value of the user's portfolio
     */
    @FXML private Label portfolioValue;

    /**
     * the reference to the running application
     */
    private App application;
    
    /**
     * the list of simulations made in the Simulator
     */
    private ObservableList<Simulation> simulations;
        
    /**
     * the list of the user's owned equities
     */
    private ObservableList<Equity> equities;
    
    /**
     * called when the corresponding FXML file is loaded, setting up the view
     * and connecting it to the model
     */
    @FXML private void initialize() {

        simulations = FXCollections.observableArrayList();
        equities = FXCollections.observableArrayList();
                
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

    /**
     * called when the 'Create Sim' button is pressed
     */
    @FXML public void addNewSimulation() {

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
               simulations.get(0).changeHoldingPrice(holding);

            equitiesTable.setItems(equities);
            
            checkForResets();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    /**
     * removes one simulation from the Simulator
     */
    @FXML private void removeOneSimulation() {

        simulations.remove(0);
        
        for(Equity obj: this.equities) 
           obj.removePriceChange(); 
        
        checkForResets();
    }

    /**
     * removes all simulations made in the Simulator
     */
    @FXML private void removeAllSimulation() {

        while(!simulations.isEmpty()) 
           removeOneSimulation();
        
        checkForResets();
    }

    /**
     * transitions back to the dashboard, putting simulation mode off for
     * each of the user's equities
     * @throws IOException
     */
    @FXML private void exitHandler() throws IOException { 
       
       for(Equity obj: equities)
          obj.putSimulationOff();
       
       this.application.showDashboardView(); 
    }
    
    /**
     * checks to see if there are simulations to remove
     * if there are none, 'Remove Sim' and 'Remove All' are disabled
     * if there is one or more, 'Remove Sim' and 'Remove All' are enabled
     */
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
    
    /**
     * Sets the reference to the running application so that it can retrieve
     * what it needs for the view
     * @param app the running application
     */
    public void setMainApp(App app) { this.application = app; }
    
    /**
     * Gets the list of the user's equities, putting simulation mode on for each
     * @param e the user's equities
     */
    public void getEquities(ObservableList<Equity> e) { 
       
       this.equities = e;
       
       for(Equity obj: this.equities)
          obj.putSimulationOn();
    } 
}