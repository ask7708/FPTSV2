package controllers;

import model.Transaction;
import model.Equity;
import model.MarketAccount;
import model.ParseTransaction;
import model.ReadTransaction;

import java.io.File;
import java.util.ArrayList;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
		
		File data = new File("itnks.txt");
		ParseTransaction build = new ParseTransaction();
		ArrayList<String[]> temp = build.ParseFile(data);
		for(int i = 0; i < temp.size(); i++){
			Transaction TransactionTemp = new Transaction(temp.get(i));
			transactions.add(TransactionTemp);
		}
		
		typeColumn.setCellValueFactory(cellData -> cellData.getValue().frontType());
		TransferColumn.setCellValueFactory(cellData -> cellData.getValue().frontTransfer());
		ReceiverColumn.setCellValueFactory(cellData -> cellData.getValue().frontReceiver());
		AmountColumn.setCellValueFactory(cellData -> cellData.getValue().frontAmount());
		DateColumn.setCellValueFactory(cellData -> cellData.getValue().frontTime());
		
		Transactiontable.setItems(transactions);
	}
	
	public void backHandler() { this.application.showDashboardView(); }
	
	public void setMainApp(App app) { this.application = app; } 
	
}
