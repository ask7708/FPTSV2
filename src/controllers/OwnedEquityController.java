package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.filechooser.FileNameExtensionFilter;

import app.App;
import javafx.stage.Stage;
import model.Account;
import model.Equity;
import model.ReadAccountHolding;
import model.ReadHoldingsContext;
import model.ReadOwnedEquities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

public class OwnedEquityController {

	/**
	 * The column for the ticker symbols
	 */
	@FXML
	private TableColumn<Equity, String> tickCol;
	/**
	 * The column for the names
	 */
	@FXML
	private TableColumn<Equity, String> nameCol;
	/**
	 * The column for the prices
	 */
	@FXML
	private TableColumn<Equity, String> priceCol;
	/**
	 * The column for the shares
	 */
	@FXML
	private TableColumn<Equity, Number> shareCol;
	/**
	 * The column for the dates
	 */
	@FXML
	private TableColumn<Equity, String> dateCol;
	/**
	 * The table that holds all the cols
	 */
	@FXML
	private TableView<Equity> ownedTable;

	/**
	 * Button to import equities
	 */
	@FXML
	private Button importButton;
	
	/**
	 * Button to go back to home view
	 */
	@FXML
	private Button backButton;

	/**
	 * Button to export equities
	 */
	@FXML
	private Button exportButton;
	
	/**
	 * Button to sell equities
	 */
	@FXML
	private Button sellButton;

	/**
	 * List of all owned equities
	 */
	ObservableList<Equity> ownedEquities = FXCollections.observableArrayList();;

	/**
	 * Reference to the main application
	 */
	private App application;

	/**
	 * Sets the table to have list of equities
	 * @param user
	 */
	public void readOwnedEquities() {

		
		ownedTable.setItems(application.getPortfolio().getEquityList());
		
	}

	/**
	 * Returns the list of equities
	 * @return
	 */
	public ObservableList<Equity> getEquityArray() {

		return this.ownedEquities;
	}

	/**
	 * Returns the table data
	 * @return
	 */
	public ObservableList getTableData() {

		return ownedTable.getItems();
	}

	/**
	 * Import the equities from a user specified file
	 */
	public void importEquities() {

	
		FileChooser fileChooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			Scanner dataRead = null;
			try {
				dataRead = new Scanner(selectedFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String line;
			String temp[];
			// StringBuffer sb = new StringBuffer();


			while (dataRead.hasNextLine()) {
				line = dataRead.nextLine();
				// System.out.println(line);
				if (line.startsWith("\"!OWNED\"")) {

					line = line.replace("\"", "");
					line = line.replace(", ", "");
					temp = line.split(",");
					ReadHoldingsContext readEquity = new ReadHoldingsContext(new ReadOwnedEquities());
					Equity addEquity = (Equity) readEquity.executeStrategy(temp);
					this.application.getPortfolio().getEquityList().add(addEquity);

				}

			}

			dataRead.close();
		}

	}

	/**
	 * The method to export equities to a 
	 * user specified text file
	 */
	public void exportEquities() {

		StringBuilder writeToFile = new StringBuilder();

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);
	

		PrintWriter outExport = null;

		try {
			outExport = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			for (Equity e : this.application.getPortfolio().getEquityList()) {
				outExport.println("\"!OWNED\"," + e.toString());

			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		outExport.close();

	}

	/**
	 * Performs the action when the sell button is pressed
	 */
	 @FXML
	 public void sellPressed() {


		 	
	        try {

	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(App.class.getResource("../views/SellEquityView" +
	                    ".fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("FPTS - Equities - Sell an Equity");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);
	            
	            
	            SellEquityController controller = loader.getController();
	            controller.setDialogStage(dialogStage);
	            Equity selectedEq = getSelectedEquity();
	            if(selectedEq != null){
	            controller.setTickSymbolLabel(selectedEq.getTickSymbol());
	            controller.setEquityNameLabel(selectedEq.getName());
	            controller.setPriceLabel(Double.toString(selectedEq.getInitPrice()));
	            controller.setMainApp(this.application);
	            controller.setAccounts(this.application.getPortfolio().getAccounts());
	            dialogStage.showAndWait();
	            }
	            
	        } catch (IOException e) {

	            e.printStackTrace();

	        }
		 

	    }
   
	
	 /**
	  * Initializes the cols to respective elements
	  */
	@FXML
	public void initialize() {


		tickCol.setCellValueFactory(cellData -> cellData.getValue().tickSymbolProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		priceCol.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());
		shareCol.setCellValueFactory(cellData -> cellData.getValue().sharesProperty());
		dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

	}

	/**
	 * Sets the reference to the main application
	 * @param app
	 */
	public void setMainApp(App app) {
		this.application = app;
	}

	/**
	 * Returns the user back to the dashboard view
	 */
	@FXML
	public void backHandler() {
		this.application.showDashboardView(); }
	
	/**
	 * Returns the selected equity in the table
	 * @return
	 */
	public Equity getSelectedEquity(){
		 
		 
		 Equity selecEq = ownedTable.getSelectionModel().getSelectedItem();
		 
		 return selecEq;
	 }	 
	
}
