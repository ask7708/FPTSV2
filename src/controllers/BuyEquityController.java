/**
 * 
 */
package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Equity;
import model.Market;

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
	
	public void initialize(){
	
		
		
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
	
}
