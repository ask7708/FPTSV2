/**
 * 
 */
package controllers;

import java.time.LocalDateTime;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import model.BuyEquity;
import model.Equity;
import model.Market;
import model.Transaction;
import model.TypeOfTransactionManager;

/**
 * @author Arsh
 *
 */
public class BuyEquityController {

	@FXML
	private TextField sharesToBuyTextField;
	
	@FXML
	private Button buyButton;
	
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
	
	private MarketController markControl;
	
	private Market market;
	
	private Equity selectedEq;
	
	private App application;
	
	TypeOfTransactionManager tm = new TypeOfTransactionManager();
	
	Account ac;
	
	public void initialize(){
	
	
		
	}
	
	@FXML
	public void buyPressed(){
		
		LocalDateTime now = LocalDateTime.now();

		
		selectedEq = this.market.findEquity(this.tickSymbolToBeSetLabel.getText());
		selectedEq.setTime(Integer.toString(now.getYear())+Integer.toString(now.getMonthValue())+Integer.toString(now.getDayOfMonth()));
		ac = this.application.getPortfolio().getAccounts().get(accountChoiceComboBox.getSelectionModel().getSelectedIndex());
		BuyEquity be = new BuyEquity(ac, selectedEq, this.application.getPortfolio());
		
		tm.executeTransaction(be);
		buyButton.setDisable(true);
		accountChoiceComboBox.setDisable(true);
		sharesToBuyTextField.setDisable(true);
		
	
	}
	
	@FXML
	public void undoPressed(){
		
		if(tm.isUndoAvailable())
		tm.undo();
		
	}
	
	@FXML
	public void redoPressed(){
		
		
		tm.redo();
		
	}
	
	
	
	@FXML
	public void cancelPressed(){
		
		dialogStage.close();
	}
	
	public void setDialogStage(Stage dialogStage){
		
		this.dialogStage = dialogStage;
	}

	public void setMarketController(MarketController marketController) {
		
		this.markControl = marketController;
		
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
	
	public void setMarket(Market market){
		
		this.market = market;
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
