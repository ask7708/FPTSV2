package controllers;
import java.io.IOException;

import app.App;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import model.DJIAStrategy;
import model.Equity;

public class DashboardController {
	
   private ObservableList<Equity> dowCompanies;
   	
	private App application; 
	
	@FXML private Button logoutB;
	
	@FXML private TableView<Equity> dowTable;
	   @FXML private TableColumn<Equity, String> companyNameColumn;
	   @FXML private TableColumn<Equity, String> priceColumn;
	   
	@FXML private Label indexValue;  
	@FXML private Label percentChange;
	
	@FXML private ImageView arrow;
		
	public void viewTransactionHandler() { this.application.showTransactionView(); }
	
	public void createSimulationHandler() { this.application.showSimulatorView(); }
	
	public void logoutHandler(ActionEvent even) throws IOException{
	      this.application.logout();
	    }
	
	public void manageWatchlistHandler() { this.application.showWatchlistView(); }
	
	public void setMainApp(App app) { this.application = app; }
	
	public void setDowCompanies(ObservableList<Equity> dj) {
	   
	   this.dowCompanies = dj; 
	   dowTable.setItems(dowCompanies);
	   
	   companyNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
	   priceColumn.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());
	   
	   double total = 0;
	   
	   for(Equity obj: dowCompanies) 
	      total += obj.getInitPrice();
	   
	   this.indexValue.setText(String.format("%.02f", total / DJIAStrategy.dowDivisor));
	   this.percentChange.setText("0.0%");
	}
	   
	
}
