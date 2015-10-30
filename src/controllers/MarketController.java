package controllers;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Equity;
import model.Market;
import model.Watchlist;

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

   public void setMainApp(App app) { this.application = app; }
   
   public void exitHandler() { this.application.showDashboardView(); }
}
