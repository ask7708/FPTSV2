package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class RootLayoutController {
   
   @FXML private Menu accountMenu;
      
      @FXML private MenuItem viewAccountsItem;
      @FXML private MenuItem importAccountItem;
      @FXML private MenuItem exportAccountItem;
      
   @FXML private Menu equityMenu;
      
      @FXML private MenuItem viewEquitiesItem;
      @FXML private MenuItem importEquitiesItem;
      @FXML private MenuItem exportEquitiesItem;
      
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
}
