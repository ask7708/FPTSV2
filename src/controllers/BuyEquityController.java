/**
 * 
 */
package controllers;

import java.time.LocalDateTime;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
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
	private Button buyButton;
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private Label tickSymbolToBeSetLabel;
	
	@FXML
	private Label equityNameToSetLabel;
	
	@FXML
	private Label priceToSetLabel;
	
	private Stage dialogStage;
	
	private MarketController markControl;
	
	private Market market;
	
	private Equity selectedEq;
	
	private App application;
	
	public void initialize(){
	
		
	}
	
	@FXML
	public void buyPressed(){
		
		LocalDateTime now = LocalDateTime.now();
		System.out.println(this.tickSymbolToBeSetLabel.getText());
		selectedEq = this.market.findEquity(this.tickSymbolToBeSetLabel.getText());
		selectedEq.setTime(Integer.toString(now.getYear())+Integer.toString(now.getMonthValue())+Integer.toString(now.getDayOfMonth()));
		BuyEquity be = new BuyEquity(selectedEq, this.application.getPortfolio());
		TypeOfTransactionManager tm = new TypeOfTransactionManager();
		tm.executeTransaction(be);
	
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
}
