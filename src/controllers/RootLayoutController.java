package controllers;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class RootLayoutController {
   
   @FXML private Menu accountMenu;
      
      @FXML private MenuItem viewAItem;
      @FXML private MenuItem importAItem;
      @FXML private MenuItem exportAItem;
      
   @FXML private Menu equityMenu;
      
      @FXML private MenuItem viewEItem;
      @FXML private MenuItem importEItem;
      @FXML private MenuItem exportEItem;
      
   @FXML private Menu marketMenu;
      
      @FXML private MenuItem simMarketItem;
      
   @FXML private Menu helpMenu;
      
      @FXML private MenuItem aboutItem;
      @FXML private MenuItem settingsItem;
      
   private App application;
   
  public void disableEntireMenu() {
     
     accountMenu.setVisible(false);
     equityMenu.setVisible(false);
     helpMenu.setVisible(false);
     marketMenu.setVisible(false);
  }

  public void enableEntireMenu() {
     
     accountMenu.setVisible(true);
     equityMenu.setVisible(true);
     helpMenu.setVisible(true);
     marketMenu.setVisible(true);
  }
  
  public void disableAccountMenu() { accountMenu.setVisible(false); }
  
  public void disableViewAItem() { viewAItem.setVisible(false); }
  
  public void enableViewAItem() { viewAItem.setVisible(true); }
  
  public void disableImportAItem() { importAItem.setVisible(false); }
  
  public void enableImportAItem() { importAItem.setVisible(true); }
  
  public void disableSimMarketItem() { simMarketItem.setVisible(false); }
  
  public void enableSimMarketItem() { simMarketItem.setVisible(true); }
  
  public void simulateMarketHandler() { this.application.showSimulatorView(); }
  
  public void setMainApp(App app) { this.application = app; }
  
}
