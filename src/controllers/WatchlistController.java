package controllers;

import java.io.IOException;

import app.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Equity;
import model.Watchlist;

public class WatchlistController {

   @FXML private TableView<Equity> equityTable;
      @FXML private TableColumn<Equity, String> tickerColumn;
      @FXML private TableColumn<Equity, String> priceColumn;
      @FXML private TableColumn<Equity, String> htColumn;
      @FXML private TableColumn<Equity, String> ltColumn;
      
   @FXML private TableView<String> triggersTable;
   
   @FXML private Button addEquityButton;
   @FXML private Button editEquityButton;
   @FXML private Button removeEquityButton;
   @FXML private Button exitButton;
      
   private App application;
   private Watchlist watchlist;
   
   @FXML private void initialize() {
      
   }
   
   @FXML private void exitPressed() { this.application.showDashboardView(); }
   
   @FXML private void editPressed() {
      
      if(equityTable.getSelectionModel().getSelectedItem() == null) {
         System.out.println("Nothing was selected to be edited");
         return;
      }
      
      try {
         
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/EditWatchlistEquity.fxml"));
         AnchorPane page = (AnchorPane) loader.load();
         
         Stage dialogStage = new Stage();
         dialogStage.setTitle("FPTS - username - Edit Equity");
         dialogStage.initModality(Modality.WINDOW_MODAL);
         Scene scene = new Scene(page);
         dialogStage.setScene(scene);
         
         EditWatchlistEquityController controller = loader.getController();
         controller.setMainApp(application);
         controller.setDialogStage(dialogStage);
         controller.setEquity(equityTable.getSelectionModel().getSelectedItem());
         
         controller.setNameText(equityTable.
               getSelectionModel().getSelectedItem().getName());
         
         controller.setTSymbolText(equityTable.
               getSelectionModel().getSelectedItem().getTickSymbol());
         
         double price = equityTable.getSelectionModel().getSelectedItem().getInitPrice();
         String priceStr = String.format("%.02f", price);
         controller.setcPriceText(priceStr);
         
         String htedit = equityTable.
               getSelectionModel().getSelectedItem().highTriggerProperty().get().substring(1);
         
         String ltedit = equityTable.
               getSelectionModel().getSelectedItem().lowTriggerProperty().get().substring(1);
         
         controller.setHTField(htedit);
         
         controller.setLTField(ltedit);
         
         editEquityButton.setDisable(true);
         dialogStage.showAndWait();
         editEquityButton.setDisable(false);
         
      } catch(IOException e) {
         
      }
   }
   
   @FXML public void removePressed() {
      
      if(equityTable.getSelectionModel().getSelectedItem() == null) {
         System.out.println("Nothing was selected to be removed");
         return;
      }
      
      watchlist.getEquities().remove(equityTable.getSelectionModel().getSelectedIndex());
   }
   public void setMainApp(App app) { this.application = app; }
   
   public void setWatchlist(Watchlist wList) { 
      
      this.watchlist = wList; 
      equityTable.setItems(watchlist.getEquities());
      triggersTable.setItems(watchlist.getTriggersFired());
      
      tickerColumn.setCellValueFactory(cellData -> cellData.getValue().tickSymbolProperty());
      priceColumn.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());
      htColumn.setCellValueFactory(cellData -> cellData.getValue().highTriggerProperty());
      ltColumn.setCellValueFactory(cellData -> cellData.getValue().lowTriggerProperty());
   }
}
