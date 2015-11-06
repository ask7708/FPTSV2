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
import model.Trigger;
import model.Watchlist;

public class WatchlistController {

   /**
    * the table listing all of the equities being watched
    */
   @FXML private TableView<Equity> equityTable;
   
      /**
       * the column listing the ticker symbol of each equity
       */
      @FXML private TableColumn<Equity, String> tickerColumn;
      
      /**
       * the columns listing the current price of each equity
       */
      @FXML private TableColumn<Equity, String> priceColumn;
      
      /**
       * the column listing the high trigger price of each equity 
       */
      @FXML private TableColumn<Equity, String> htColumn;
      
      /**
       * the column listing the low trigger price of each equity
       */
      @FXML private TableColumn<Equity, String> ltColumn;
      
   /**
    * the table listing of all the triggers fired from your watchlist   
    */
   @FXML private TableView<Trigger> triggersTable;
   
      /**
       * the column listing each trigger fired in a formatted string
       */
      @FXML private TableColumn<Trigger, String> triggers;
      
   /**
    * the 'Edit Equity Triggers' button
    */
   @FXML private Button editEquityButton;
   
   /**
    * the 'Remove Equity' button
    */
   @FXML private Button removeEquityButton;
   
   /**
    * the 'Exit' button
    */
   @FXML private Button exitButton;
      
   /**
    * the reference to the running application
    */
   private App application;
   
   /**
    * the user's watchlist
    */
   private Watchlist watchlist;
      
   /**
    * called when the 'Exit' button is pressed; making a transition to the
    * dashboard view
    */
   @FXML private void exitPressed() { this.application.showDashboardView(); }
   
   /**
    * called when the 'Edit Equity Triggers' button is pressed; opening up the
    * Edit Watchlist Equity view
    */
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
   
   /**
    * called when the 'Remove Equity' button is pressed; removing the selected
    * equity from the watchlist
    */
   @FXML public void removePressed() {
      
      if(equityTable.getSelectionModel().getSelectedItem() == null) {
         System.out.println("Nothing was selected to be removed");
         return;
      }
      
      watchlist.getEquities().remove(equityTable.getSelectionModel().getSelectedIndex());
   }
   
   /**
    * Sets the reference to the running application so that it can retrieve
    * what it needs for the view
    * @param app the running application
    */
   public void setMainApp(App app) { this.application = app; }
   
   /**
    * Sets the reference to the user's watchlist so that it can get the corresponding
    * equities and triggers lists
    * @param wList the user's watchlist
    */
   public void setWatchlist(Watchlist wList) { 
      
      this.watchlist = wList; 
      equityTable.setItems(watchlist.getEquities());
      triggersTable.setItems(watchlist.getTriggersFired());
      
      tickerColumn.setCellValueFactory(cellData -> cellData.getValue().tickSymbolProperty());
      priceColumn.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());
      htColumn.setCellValueFactory(cellData -> cellData.getValue().highTriggerProperty());
      ltColumn.setCellValueFactory(cellData -> cellData.getValue().lowTriggerProperty());
      
      triggers.setCellValueFactory(cellData -> cellData.getValue().messageProperty());
   }
}
