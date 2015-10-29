package controllers;

import model.Transaction;
import model.Equity;
import model.MarketAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Account;

public class TransactionController {
	
	@FXML
	private TableView<Transaction> Transactiontable;
	@FXML
	private TableColumn<Transaction, String> typeColumn;
	@FXML
	private TableColumn<Transaction, String> TransferColumn;
	@FXML
	private TableColumn<Transaction, String> ReceiverColumn;
	@FXML
	private TableColumn<Transaction, Number> AmountColumn;
	@FXML
	private TableColumn<Transaction, String> DateColumn;
	
	@FXML
	private Button backButton;
	
	private ObservableList<Transaction> transactions;
	
	@FXML
	private void initialize(){
		transactions = FXCollections.observableArrayList();
		
		Equity bye = new Equity("AADA", "Ada", 20);
		Account hello = new MarketAccount("Market", "hello", 40, "12110412", "2012", "21345");
		Transaction hi = new Transaction("20111206", 40, bye, hello);
		
		transactions.add(hi);
		
		typeColumn.setCellValueFactory(cellData -> cellData.getValue().frontType());
		TransferColumn.setCellValueFactory(cellData -> cellData.getValue().frontTransfer());
		ReceiverColumn.setCellValueFactory(cellData -> cellData.getValue().frontReceiver());
		AmountColumn.setCellValueFactory(cellData -> cellData.getValue().frontAmount());
		DateColumn.setCellValueFactory(cellData -> cellData.getValue().frontTime());
		
		Transactiontable.setItems(transactions);
	}
	

}
