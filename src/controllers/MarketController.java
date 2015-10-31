/**
 * 
 */
package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Equity;
import model.EquityStrategy;
import model.Market;
import model.ReadEquitiesRaw;
import model.ReadHoldingsContext;
import model.ReadOwnedEquities;
import model.Watchlist;
import model.YahooContext;
import model.YahooStrategy;

/**
 * @author Arsh
 *
 */
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
	
	
	public void viewEquities() {

		//

		File data = new File("equities.txt");

        YahooStrategy company = new EquityStrategy();
        YahooContext context = new YahooContext(company);

		Scanner dataRead = null;

		try {
			dataRead = new Scanner(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line;
		String[] temp;

		while (dataRead.hasNextLine()) {
			line = dataRead.nextLine();
			line = line.replace("\"", "");
			line = line.replace(", ", " ");
			temp = line.split(",");
			ReadHoldingsContext readEquity = new ReadHoldingsContext(new ReadEquitiesRaw());
			Equity EquityInfo = (Equity) readEquity.executeStrategy(temp);
			//System.out.println(EquityInfo.getTickSymbol()+""+EquityInfo.getInitPrice());
			EquityInfo.setInitPrice((context.executeStrategy(EquityInfo.getTickSymbol())));
			marketEquities.add(EquityInfo);
			

		}

		dataRead.close();

	}

	public ArrayList<Equity> getEquityArray() {

		return this.marketEquities;
	}


	public ObservableList getTableData(){
		
		return marketTable.getItems();
	}
	
	

	public static void main(String args[]) {

		MarketController evc = new MarketController();
		evc.viewEquities();
		//System.out.println(evc.getEquityArray().toString());

	}

	@FXML
	public void initialize() {
		
		viewEquities();
		ObservableList<Equity> data = FXCollections.observableArrayList(getEquityArray());
		
		//data.add(new Equity("ASSD","Assistance Co.",12.37,23.48,"20151013"));
		//System.out.println(getEquityArray());
		
		marketTable.getItems().clear();
		System.out.println("Before marketTable"+marketTable.getItems());
		marketTable.setItems(data);
		System.out.println("After marketTable"+marketTable.getItems());
		
		tickerCol.setCellValueFactory(cellData -> cellData.getValue().tickSymbolProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		priceCol.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());
		
		
		
	}

	
	   public void setMainApp(App app) { this.application = app; }
	   
	   public void exitHandler() { this.application.showDashboardView(); }
	
	

}

/* DANIELS CODE FOR MARKET CONTROLLER
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


}
>>>>>>> branch 'master' of https://github.com/ask7708/FPTSV2.git
*/
