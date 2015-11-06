/**
 * 
 */
package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Equity;
import model.EquityStrategy;
import model.Market;
import model.ReadEquitiesRaw;
import model.ReadHoldingsContext;
import model.ReadOwnedEquities;
import model.Watchlist;
import model.YahooContext;
import model.YahooStrategy;
import app.App;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Equity;
import model.Market;
import model.Watchlist;

/**
 * @author Arsh
 *
 */

public class MarketController {

	/**
	 * The table that holds all equities
	 */
	@FXML
	private TableView<Equity> marketTable;

	/**
	 * The column that holds all names of equities
	 */
	@FXML
	private TableColumn<Equity, String> nameColumn;

	/**
	 * The price of each equity
	 */
	@FXML
	private TableColumn<Equity, String> priceColumn;

	/**
	 * The name of all equities
	 */
	@FXML
	private Label nameText;

	/**
	 * The ticker symbol of each equity
	 */
	@FXML
	private Label tickerText;

	/**
	 * The price of each equity
	 */
	@FXML
	private Label priceText;

	/**
	 * The attributes of each equity
	 */
	@FXML
	private Label attributes;

	/**
	 * Button to add equity to watchlist
	 */
	@FXML
	private Button addButton;

	/**
	 * The button to exit the system
	 */
	@FXML
	private Button exitButton;

	/**
	 * Button to buy equities
	 */
	@FXML
	private Button buyEquityButton;

	/**
	 * The reference to the application
	 */
	private App application;

	/**
	 * The reference to the market
	 */
	private Market market;

	/**
	 * The reference to the watchlist
	 */
	private Watchlist watchlist;

	/**
	 * Initializes the cols to display equities
	 */
	@FXML
	public void initialize() {

		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		priceColumn.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());

	}

	/**
	 * Sets the application reference to the main one
	 * 
	 * @param app
	 */
	public void setMainApp(App app) {
		this.application = app;
	}

	/**
	 * The action to add equities to the watchlist
	 */
	@FXML
	public void addToWatchlist() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../views/AddtoWatchlist.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("FPTS - username - Add to Watchlist");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AddToWatchlistController controller = loader.getController();
			controller.setMainApp(application);
			controller.setDialogStage(dialogStage);
			controller.setWatchlist(watchlist);
			controller.setEquity(marketTable.getSelectionModel().getSelectedItem());

			controller.setNameText(marketTable.getSelectionModel().getSelectedItem().getName());

			controller.setTSymbolText(marketTable.getSelectionModel().getSelectedItem().getTickSymbol());

			double price = marketTable.getSelectionModel().getSelectedItem().getInitPrice();
			String priceStr = String.format("%.02f", price);
			controller.setcPriceText(priceStr);

			addButton.setDisable(true);
			dialogStage.showAndWait();
			addButton.setDisable(false);

		} catch (IOException e) {

		}
	}

	/**
	 * The action to go back to the dashboard view
	 */
	public void exitHandler() {
		this.application.showDashboardView();
	}

	/**
	 * Sets the market reference to current market
	 * 
	 * @param market
	 */
	public void setMarket(Market market) {

		this.market = market;
		marketTable.setItems(this.market.getMarketEquities());

	}

	/**
	 * Sets the reference to watchlist to the user specific one
	 * 
	 * @param wList
	 */
	public void setWatchlist(Watchlist wList) {
		this.watchlist = wList;
	}

	/**
	 * Action to buy equity that display the buying modal and allows respective
	 * actions
	 */
	@FXML
	public void buyEquityViaMarket() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(App.class.getResource("../views/BuyEquityView" + ".fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("FPTS - Market - Buy an Equity");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			BuyEquityController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			Equity selectedEq = getSelectedEquity();
			if (selectedEq != null) {
				controller.setTickSymbolLabel(selectedEq.getTickSymbol());
				controller.setEquityNameLabel(selectedEq.getName());
				controller.setPriceLabel(Double.toString(selectedEq.getInitPrice()));
				controller.setMarket(this.market);
				controller.setMainApp(this.application);
				controller.setAccounts(this.application.getPortfolio().getAccounts());
				dialogStage.showAndWait();
			}

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	/**
	 * Returns the selected equity in the table
	 * 
	 * @return Equity - selecte equity object
	 */
	public Equity getSelectedEquity() {

		Equity selecEq = marketTable.getSelectionModel().getSelectedItem();

		return selecEq;
	}
}