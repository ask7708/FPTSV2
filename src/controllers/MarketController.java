/**
 * 
 */
package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Equity;
import model.EquityStrategy;
import model.Market;
import model.ReadEquitiesRaw;
import model.ReadHoldingsContext;
import model.ReadOwnedEquities;
import model.Watchlist;
import model.YahooContext;
import model.YahooStrategy;
import app.App;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Equity;
import model.Market;
import model.Watchlist;

/**
 * @author Arsh
 *
 */
/*
public class MarketController {
	
	@FXML
	private TableColumn<Equity, String> tickerCol;
	@FXML
	private TableColumn<Equity, String> nameCol;
	@FXML
	private TableColumn<Equity, String> priceCol;
	@FXML
	private TableColumn<Equity, String> indexSecCol;
	
	@FXML
	private TableView marketTable;
	
	ArrayList<Equity> marketEquities = new ArrayList<Equity>();
	
	private App application;
	   
	private Market market;
	   
	private Watchlist watchlist;
	
	
	public void setUpEquities() {

		

	}

	public ArrayList<Equity> getEquityArray() {

		return this.marketEquities;
	}


	public ObservableList getTableData(){
		
		return marketTable.getItems();
	}
	
	

	public static void main(String args[]) {

		MarketController evc = new MarketController();
		//evc.viewEquities();
		System.out.println(evc.getEquityArray().toString());
		
	}

	@FXML
	public void initialize() {
		
		//viewEquities();
		
		//ObservableList<Equity> data = FXCollections.observableArrayList(market.getMarketEquities());
		
		//data.add(new Equity("ASSD","Assistance Co.",12.37,23.48,"20151013"));
		//System.out.println(getEquityArray());
		
		//marketTable.getItems().clear();
		System.out.println("Before marketTable"+marketTable.getItems());
		//marketTable.setItems(data);
		System.out.println("After marketTable"+marketTable.getItems());
		
		tickerCol.setCellValueFactory(cellData -> cellData.getValue().tickSymbolProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		priceCol.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());
		
		
		
	}

	
	   public void setMainApp(App app) { this.application = app; }
	   
	   
	   
	   public void exitHandler() { this.application.showDashboardView(); }

	public void setMarket(Market market) {
		
		this.market = market;
		marketTable.setItems(this.market.getMarketEquities());
		
	}
	
	

}*/



public class MarketController {
   
   @FXML private TableView<Equity> marketTable;
      @FXML private TableColumn<Equity, String> nameColumn;
      @FXML private TableColumn<Equity, String> priceColumn;
      
   @FXML private Label nameText;   
   @FXML private Label tickerText;
   @FXML private Label priceText;
   @FXML private Label attributes;
   
   @FXML private Button addButton;
   @FXML private Button exitButton;
   
   private App application;
   
   private Market market;
   
   private Watchlist watchlist;
   
	@FXML
	public void initialize() {
	   
		//viewEquities();
		
		//ObservableList<Equity> data = FXCollections.observableArrayList(market.getMarketEquities());
		
		//data.add(new Equity("ASSD","Assistance Co.",12.37,23.48,"20151013"));
		//System.out.println(getEquityArray());
		
		//marketTable.getItems().clear();
		System.out.println("Before marketTable"+marketTable.getItems());
		//marketTable.setItems(data);
		System.out.println("After marketTable"+marketTable.getItems());
		
		//tickerCol.setCellValueFactory(cellData -> cellData.getValue().tickSymbolProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		priceColumn.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());
		
		
		
	}

	
	public void setMainApp(App app) { this.application = app; }
	   
	@FXML public void addToWatchlist() {
	   
	   System.out.println("Add to watchlist");
	   try {
	      
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(App.class.getResource("../views/AddtoWatchlist.fxml"));
	      AnchorPane page = (AnchorPane) loader.load();
	      
	      Stage dialogStage = new Stage();
	      dialogStage.setTitle("FPTS - username - Add to Watchlist");
	      dialogStage.initModality(Modality.WINDOW_MODAL);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
	      
	      AddToWatchlistController controller = loader.getController();	     
	      controller.setMainApp(application);
	      controller.setDialogStage(dialogStage);
	      controller.setWatchlist(watchlist);
	      controller.setEquity(marketTable.getSelectionModel().getSelectedItem());
	      
	      controller.setNameText(marketTable.
	            getSelectionModel().getSelectedItem().getName());
	      
	      controller.setTSymbolText(marketTable.
	            getSelectionModel().getSelectedItem().getTickSymbol());
	      
	      double price = marketTable.getSelectionModel().getSelectedItem().getInitPrice();
	      String priceStr = String.format("%.02f", price);
	      controller.setcPriceText(priceStr);
	      
	      addButton.setDisable(true);
	      dialogStage.showAndWait();
	      addButton.setDisable(false);
	      	      
	   } catch (IOException e) {
	      
	   }
	}
	   
	public void exitHandler() { this.application.showDashboardView(); }

	public void setMarket(Market market) {
		
		this.market = market;
		marketTable.setItems(this.market.getMarketEquities());
		
	}
   
	public void setWatchlist(Watchlist wList) { this.watchlist = wList; }

}

