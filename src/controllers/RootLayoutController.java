package controllers;

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
      
   @FXML private Menu helpMenu;
      
      @FXML private MenuItem aboutItem;
      @FXML private MenuItem settingsItem;
      
  public void disableEntireMenu() {
     
     accountMenu.setVisible(false);
     equityMenu.setVisible(false);
     helpMenu.setVisible(false);
  }

  public void enableEntireMenu() {
     
     accountMenu.setVisible(true);
     equityMenu.setVisible(true);
     helpMenu.setVisible(true);
  }
  
  public void disableViewAItem() { viewAItem.setVisible(false); }
  
  public void enableViewAItem() { viewAItem.setVisible(true); }
  
  public void disableImportAItem() { importAItem.setVisible(false); }
  
  public void enableImportAItem() { importAItem.setVisible(true); }
  
}
