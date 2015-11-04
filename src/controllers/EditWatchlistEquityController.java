package controllers;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Equity;

public class EditWatchlistEquityController {

   @FXML private Label nameText;
   @FXML private Label tSymText;
   @FXML private Label currentPriceText;
   
   @FXML private TextField highTField;
   @FXML private TextField lowTField;
   
   @FXML private Button makeChangeButton;
   @FXML private Button cancelButton;
   
   private Stage dialogStage;
   
   private App application;
   
   private Equity eq;
   
   private void initialize() {}
   
   @FXML public void setNameText(String txt) { this.nameText.setText(txt); }

   @FXML public void setTSymbolText(String txt) { this.tSymText.setText(txt); }
   
   @FXML public void setcPriceText(String txt) { this.currentPriceText.setText(txt); }
   
   @FXML public void setHTField(String txt) { this.highTField.setText(txt); }
   
   @FXML public void setLTField(String txt) { this.lowTField.setText(txt); }
   
   @FXML private void makeChangePressed() {
      
      String ht = highTField.getText();
      String lt = lowTField.getText();
      
      eq.setLowTrigger(Double.parseDouble(lt));
      eq.setHighTrigger(Double.parseDouble(ht));
      
      dialogStage.close();
   }
   
   @FXML private void cancelPressed() { dialogStage.close(); }
   
   public void setMainApp(App app) { this.application = app; }
   
   public void setDialogStage(Stage dialogStage) { this.dialogStage = dialogStage; }
   
   public void setEquity(Equity e) { this.eq = e; }
}
