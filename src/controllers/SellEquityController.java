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
	
	/**
	 * Text field representing the shares to sell
	 */
	@FXML
	private TextField sharesToSellTextField;
	
	/**
	 * The button to sell equities
	 */
	@FXML
	private Button sellButton;
	
	/**
	 * Button to cancel out of buy menu
	 */
	@FXML
	private Button cancelButton;
	
	/**
	 * Button to display undoing of buy
	 */
	@FXML
	private Button undoButton;
	
	/**
	 * The ticker symbol displayed to the user
	 */
	@FXML
	private Label tickSymbolToBeSetLabel;
	
	/**
	 * The equity name label
	 */
	@FXML
	private Label equityNameToSetLabel;
	
	/**
	 * The price of the equity
	 */
	@FXML
	private Label priceToSetLabel;
	
	/**
	 * List of account choices user can pick from
	 */
	private ObservableList<String> accountChoices = FXCollections.observableArrayList();
	
	/**
	 * Drop down combo box for user accounts
	 */
	 @FXML
	private ChoiceBox<String> accountChoiceComboBox;
	
	/**
	 *The list of user accounts
	 */ 
	ObservableList<Account> accounts = FXCollections.observableArrayList();
	  
	/**
	 * The main stage to be displayed
	 */
	private Stage dialogStage;
	
	/**
	 * The selected equity by the user
	 */
	private Equity selectedEq;
	
	/**
	 * The reference to the main application
	 */
	private App application;
	
	/**
	 * The typeoftranscation object used to accomplish buy task
	 */
	TypeOfTransactionManager tm = new TypeOfTransactionManager();
	
	/**
	 * Reference to an account
	 */
	Account ac;
	
	/**
	 * Initializes first time UI specifics
	 */
	public void initialize(){
	
		sharesToSellTextField.setText("0.0");
		
	}
	
	/**
	 * Performs the sell action when the user presses the sell button
	 * 
	 */
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
	

	/**
	 * Performs the undo option of selling an equity
	 */
	@FXML
	public void undoPressed(){
		
		if(tm.isUndoAvailable()){
		tm.undo();
		}
	}
	
	/**
	 * Performs the redo option of selling an equity
	 */
	@FXML
	public void redoPressed(){
		
		if(tm.isRedoAvailable()){
		tm.redo();
		}
	}
	
	
	/**
	 * Closes the window when cancel is pressed in view
	 */
	@FXML
	public void cancelPressed(){
		
		dialogStage.close();
	}
	
	/**
	 * Sets the stage for a new stage
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage){
		
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the label of the equity tick symbol
	 * 
	 * @param ticker
	 */
	public void setTickSymbolLabel(String ticker){
		
		tickSymbolToBeSetLabel.setText(ticker);
	}
	
	/**
	 * Sets the label of the equity's name
	 * 
	 * @param name
	 */
	public void setEquityNameLabel(String name){
		
		equityNameToSetLabel.setText(name);
	}

	/**
	 * Sets the price label of the equity
	 * 
	 * @param price
	 */
	public void setPriceLabel(String price){
	
	priceToSetLabel.setText(price);
	}
	
	/**
	 * Sets the reference to the main application
	 * 
	 * @param app
	 */
	public void setMainApp(App app) {
		this.application = app;
	}
	
	/**
	 * Sets the accounts to the respective user accounts
	 * 
	 * @param accounts
	 */
	public void setAccounts(ObservableList<Account> accounts){
		
		this.accounts = accounts;
		for(Account e: accounts){
			
			accountChoices.add(e.getAccountName());
		}
		accountChoiceComboBox.getItems().addAll(accountChoices);
	}
}
