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
	
	@FXML private TableView<Transaction> Transactiontable;
	
	   @FXML private TableColumn<Transaction, String> typeColumn;
	   @FXML private TableColumn<Transaction, String> TransferColumn;
	   @FXML private TableColumn<Transaction, String> ReceiverColumn;
	   @FXML private TableColumn<Transaction, Number> AmountColumn;
	   @FXML private TableColumn<Transaction, String> DateColumn;
	
	@FXML private Button backButton;
	
	private App application;
	
	private ObservableList<Transaction> transactions;
	
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
	
	public void backHandler() { this.application.showDashboardView(); }
	
	public void importHandler() {

		this.application.setReadAccounts(false);	
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
	
	public void exportHandler() {

		StringBuilder writeToFile = new StringBuilder();

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);
		ArrayList<String> data = new ArrayList<String>();
	
		for (Transaction t : transactions) {
			data.add(t.toString());

		}
		ParseTransaction read = new ParseTransaction();
		read.WriteList(data);
	}
	
	public void setMainApp(App app) { this.application = app; } 
	
	public void setinformation(String user){
		String name = "";
		name = name + user + ".txt"; 
		
		File data = new File(user+".txt");
		ParseTransaction build = new ParseTransaction();
		ArrayList<String[]> temp = build.ParseFile(data);
		for(int i = 0; i < temp.size(); i++){
			Transaction TransactionTemp = new Transaction(temp.get(i));
			transactions.add(TransactionTemp);
		}
		
	}
	
}
