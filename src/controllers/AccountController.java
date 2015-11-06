package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.filechooser.FileNameExtensionFilter;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import model.Account;
import model.Equity;
import model.MarketAccount;
import model.ReadAccountHolding;
import model.ReadHoldingsContext;
import model.ReadOwnedEquities;

public class AccountController {

	/**
	 * Sets up the column for the type of account
	 */
	@FXML
	private TableColumn<Account, String> typeCol;

	/**
	 * Sets up the column for the name of account
	 */
	@FXML
	private TableColumn<Account, String> nameCol;

	/**
	 * Sets up the column for the amount in account
	 */
	@FXML
	private TableColumn<Account, Number> amountCol;
	

	/**
	 * Sets up the column for the routing no. of account
	 */
	@FXML
	private TableColumn<Account, String> routingCol;
	

	/**
	 * Sets up the column for the account no. of account
	 */
	@FXML
	private TableColumn<Account, String> accountNumCol;
	

	/**
	 * Sets up the column for the date of account
	 */
	@FXML
	private TableColumn<Account, String> dateCol;

	/**
	 * The FXML table for which cols will be stored
	 */
	@FXML
	private TableView<Account> accountTable;

	/**
	 * Button to handle going back to home screen
	 * 
	 */
	@FXML
	private Button backButton;

	/**
	 * The import button to import accounts
	 */
	@FXML
	private Button importButton;

	/**
	 * The export button to handle exporting accounts
	 * 
	 */
	@FXML
	private Button exportButton;

	/**
	 * The reference to the main application
	 */
	private App application;

	/**
	 * An observable list of account objects
	 */
	ObservableList<Account> accounts = FXCollections.observableArrayList();

	/**
	 * Displays the dashboard view as the user 
	 * clicks the dashboard button
	 */
	@FXML
	public void backHandler() {
		this.application.showDashboardView();
	}

	/**
	 * Writes the accounts the user has in their portfolio
	 * to a user specified text file
	 */
	public void exportAccounts() {

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
			for (Account a : application.getPortfolio().getAccounts()) {

				if (a.getTypeOfAccount().equals("Money Market")) {
					System.out.println("Marketaccount instance");
					outExport.println("\"!MM\"," + a.toString());
				} else {
					outExport.println("\"!BANK\"," + a.toString());

				}

			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		outExport.close();

	}

	/**
	 * Return the observable list of accounts
	 * @return
	 */
	public ObservableList<Account> getAccountArray() {

		return this.accounts;
	}

	/**
	 * Returns the table data of the accounts
	 * @return
	 */
	public ObservableList getTableData() {

		return accountTable.getItems();
	}

	/**
	 * Reads in accounts from a user specified file
	 * and loads them into the user's accounts
	 * 
	 */
	public void importAccounts() {

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
			String[] temp;
			// StringBuffer sb = new StringBuffer();

			while (dataRead.hasNextLine()) {
				line = dataRead.nextLine();
				// System.out.println(line);
				if (line.startsWith("\"!MM\"") || line.startsWith("\"!BANK\"")) {

					line = line.replace("\"", "");
					line = line.replace(", ", "");
					temp = line.split(",");
					ReadHoldingsContext readAccountHolding = new ReadHoldingsContext(new ReadAccountHolding());
					Account accountInfo = (Account) readAccountHolding.executeStrategy(temp);
					this.application.getPortfolio().getAccounts().add(accountInfo);

				}

			}

			dataRead.close();
		}

	}


	/**
	 * Handles initializing the cols for display
	 */
	@FXML
	public void initialize() {

		typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().accountNameProperty());
		amountCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
		accountNumCol.setCellValueFactory(cellData -> cellData.getValue().accountNoProperty());
		routingCol.setCellValueFactory(cellData -> cellData.getValue().routingNoProperty());
		dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

	}

	/**
	 * Method to set the application 
	 * parameter to the main application running.
	 * @param app
	 */
	public void setMainApp(App app) {

		this.application = app;

	}

	/**
	 * Sets the account table to the observable list of 
	 * equities from the portfolio object
	 */
	public void viewAccount() {

		
		accountTable.setItems(this.application.getPortfolio().getAccounts());
	}

}
