package controllers;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Equity;
import model.Watchlist;

public class AddToWatchlistController {

   @FXML Label nameText;
   @FXML Label tSymText;
   @FXML Label currentPriceText;
   
   @FXML TextField highTField;
   @FXML TextField lowTField;
   
   @FXML Button addEquityButton;
   @FXML Button cancelButton;
   
   private Stage dialogStage;
   
   private App application;
   
   private Watchlist watchlist;
   
   private Equity eq;
   
   @FXML private void initialize() {
      setNameText("N/A");
      setcPriceText("Are you working yet?");
      setTSymbolText("Please help");
   }
   
   @FXML private void addEquityPressed() {
      
      String ht = highTField.getText();
      String lt = lowTField.getText();
      
      eq.setLowTrigger(Double.parseDouble(lt));
      eq.setHighTrigger(Double.parseDouble(ht));
      
      watchlist.addEquity(eq);
      
      dialogStage.close();
   }
   
   @FXML private void cancelPressed() { dialogStage.close(); }
   
   @FXML public void setNameText(String txt) { this.nameText.setText(txt); }
   
   @FXML public void setTSymbolText(String txt) { this.tSymText.setText(txt); }
   
   @FXML public void setcPriceText(String txt) { this.currentPriceText.setText(txt); }
   
   public void setDialogStage(Stage dialogStage) { this.dialogStage = dialogStage; }
   
   public void setMainApp(App app) { this.application = app; }
   
   public void setWatchlist(Watchlist wList) { this.watchlist = wList; }
   
   public void setEquity(Equity e) { this.eq = e; }
}
