package controllers;

import java.time.LocalDateTime;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Account;
import model.BuyEquity;
import model.Equity;
import model.Market;
import model.SellEquity;
import model.TypeOfTransactionManager;

public class SellEquityController {
	
	@FXML
	private TextField sharesToSellTextField;
	
	@FXML
	private Button sellButton;
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private Button undoButton;
	
	@FXML
	private Label tickSymbolToBeSetLabel;
	
	@FXML
	private Label equityNameToSetLabel;
	
	@FXML
	private Label priceToSetLabel;
	
	private ObservableList<String> accountChoices = FXCollections.observableArrayList();
	
	 @FXML
	private ChoiceBox<String> accountChoiceComboBox;
	
	ObservableList<Account> accounts = FXCollections.observableArrayList();
	  
	private Stage dialogStage;
	
	
	private Equity selectedEq;
	
	private App application;
	
	TypeOfTransactionManager tm = new TypeOfTransactionManager();
	
	Account ac;
	
	public void initialize(){
	
		sharesToSellTextField.setText("0.0");
		
	}
	
	@FXML
	public void sellPressed(){
		
		LocalDateTime now = LocalDateTime.now();
		
		if(accountChoiceComboBox.getSelectionModel().getSelectedIndex() != -1){
			
			if(Double.parseDouble(sharesToSellTextField.getText()) > 0.0){
				
				
		selectedEq = this.application.getPortfolio().findEquity(this.tickSymbolToBeSetLabel.getText());
		selectedEq.setTime(Integer.toString(now.getYear())+Integer.toString(now.getMonthValue())+Integer.toString(now.getDayOfMonth()));
		accountChoiceComboBox.getSelectionModel().getSelectedIndex();
		ac = this.application.getPortfolio().getAccounts().get(accountChoiceComboBox.getSelectionModel().getSelectedIndex());
		
		
		SellEquity be = new SellEquity(ac, selectedEq, this.application.getPortfolio(), Double.parseDouble(sharesToSellTextField.getText()));
		
		tm.executeTransaction(be);
		sellButton.setDisable(true);
		accountChoiceComboBox.setDisable(true);
		sharesToSellTextField.setDisable(true);
		
			
			}else{
				Alert alert = new Alert(AlertType.CONFIRMATION);
	    		alert.setTitle("Number of Shares Error");
	    		alert.setContentText("Invalid transcation. Please select a valid shares amount.");
	    		alert.showAndWait();
			}
		}else{
			
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Account Error");
    		alert.setContentText("Invalid transcation. Please select an account.");
    		alert.showAndWait();
		}
	
	}
	
	@FXML
	public void undoPressed(){
		
		if(tm.isUndoAvailable()){
		tm.undo();
		}
	}
	
	@FXML
	public void redoPressed(){
		
		if(tm.isRedoAvailable()){
		tm.redo();
		}
	}
	
	
	
	@FXML
	public void cancelPressed(){
		
		dialogStage.close();
	}
	
	public void setDialogStage(Stage dialogStage){
		
		this.dialogStage = dialogStage;
	}

	
	public void setTickSymbolLabel(String ticker){
		
		tickSymbolToBeSetLabel.setText(ticker);
	}
	
	public void setEquityNameLabel(String name){
		
		equityNameToSetLabel.setText(name);
	}

	public void setPriceLabel(String price){
	
	priceToSetLabel.setText(price);
	}
	
	
	public void setMainApp(App app) {
		this.application = app;
	}
	
	public void setAccounts(ObservableList<Account> accounts){
		
		this.accounts = accounts;
		for(Account e: accounts){
			
			accountChoices.add(e.getAccountName());
		}
		accountChoiceComboBox.getItems().addAll(accountChoices);
	}
}
