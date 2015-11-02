package controllers;

import model.Transaction;
import model.Equity;
import model.MarketAccount;
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
		
		Equity bye = new Equity("AADA", "Ada", 20);
		Account goodbyte = new BankAccount("bank", "nooo", 50, "whatever", "extra", "moreextra");
		Account hello = new MarketAccount("Market", "hello", 40, "12110412", "2012", "21345");
		Transaction hi = new Transaction("20111206", 40, bye, hello);
		
		Transaction greetings = new Transaction("201111206", 40, hello, bye);
		
		Transaction grah = new Transaction("date", 40, goodbyte, hello);
		
		Transaction ford = new Transaction("date", 40, hello, goodbyte);
		
		Transaction nope = new Transaction("date", 40, "User", goodbyte);
		
		Transaction yep = new Transaction("date", 40, goodbyte, "User");
		
		
		transactions.add(hi);
		transactions.add(greetings);
		transactions.add(ford);
		transactions.add(nope);
		transactions.add(yep);
		transactions.add(grah);
		
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
