package controllers;

import model.Transaction;
import model.Equity;
import model.MarketAccount;
import model.ParseTransaction;
import model.ReadTransaction;

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
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.Account;
import model.BankAccount;

public class TransactionController {
	//table that contains transaction methods
	@FXML private TableView<Transaction> Transactiontable;
		//type method
	   @FXML private TableColumn<Transaction, String> typeColumn;
	   //transfer method shows the transfers name 
	   @FXML private TableColumn<Transaction, String> TransferColumn;
	   //reciever methods shows the receiver name
	   @FXML private TableColumn<Transaction, String> ReceiverColumn;
	   //Amount method shows the amount being transferred
	   @FXML private TableColumn<Transaction, Number> AmountColumn;
	   //Date string that shows the date of Transaction
	   @FXML private TableColumn<Transaction, String> DateColumn;
	
	//Button made to go back
	@FXML private Button backButton;
	
	//The application that's being used
	private App application;
	
	//The list of transactions that will be shown
	private ObservableList<Transaction> transactions;
	/**
	 * the intialize function that creates a transaction observable array
	 * list and then creates the settings from the views using the various parameters
	 * above that hav been specified with FXML format
	 */
	@FXML
	private void initialize(){
		transactions = FXCollections.observableArrayList();
		
		typeColumn.setCellValueFactory(cellData -> cellData.getValue().frontType());
		TransferColumn.setCellValueFactory(cellData -> cellData.getValue().frontTransfer());
		ReceiverColumn.setCellValueFactory(cellData -> cellData.getValue().frontReceiver());
		AmountColumn.setCellValueFactory(cellData -> cellData.getValue().frontAmount());
		DateColumn.setCellValueFactory(cellData -> cellData.getValue().frontTime());
		
		Transactiontable.setItems(transactions);
	}
	
	//sends the user back to the dashboard
	public void backHandler() { this.application.showDashboardView(); }
	
	//allows the user to import a file
	public void importHandler() {

	
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
			// StringBuffer sb = new StringBuffer();
			PrintWriter out;

			while (dataRead.hasNextLine()) {
				line = dataRead.nextLine();
				// System.out.println(line);
				if (line.startsWith("\"!T\"")) {
					// sb.append(line+"\n");
					File writeFile = new File(this.application.getUserName()+".txt");
					writeFile.setWritable(true);
					try {
						out = new PrintWriter(
								new BufferedWriter(new FileWriter(this.application.getUserName() + ".txt", true)));
						out.println(line);
						String[] temp = line.split("\",\"");
						temp[6] = temp[6].replace("\"", "");
						Transaction data = new Transaction(temp);
						this.application.getPortfolio().addTransaction(data);
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			dataRead.close();
		}


	}
	
	//allows the user to export their informaion onto a text file on their computer
	public void exportHandler() {

		StringBuilder writeToFile = new StringBuilder();

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);
		ObservableList<String[]> data = FXCollections.observableArrayList();
 	
		for (Transaction t : transactions) {
			data.add(t.toStringArray());

		}
		ParseTransaction Read = new ParseTransaction();
		ObservableList<String> format = Read.ReadtoCSV(data);
		Read.WriteList(format, file.toString());
	}
	
	//sets the  main application
	//parameters app(this app that needs to be set)
	public void setMainApp(App app) { this.application = app; } 
	
	//this function takes a string that is the users name that is current using the application
	//Then the information of the transactions will be set so that it will be properly displayed from the view
	public void setinformation(String user){
		ObservableList<Transaction> temp = this.application.getPortfolio().getTransactions();
		for(int i = 0; i < temp.size(); i++){
			Transaction TransactionTemp = temp.get(i);
			transactions.add(TransactionTemp);
		}
		
	}
	
}