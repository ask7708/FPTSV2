package controllers;

import app.App;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class RootLayoutController {

	@FXML private Menu accountMenu;

	@FXML private MenuItem viewAItem;

	@FXML private Menu equityMenu;

	@FXML private MenuItem viewEItem;

	@FXML private Menu marketMenu;

	@FXML private MenuItem viewMarketItem;
	@FXML private MenuItem simMarketItem;

	@FXML private Menu helpMenu;

	@FXML private MenuItem aboutItem;
	@FXML private MenuItem settingsItem;
	@FXML private MenuItem logoutItem;

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

	public void resetDashboardMenu() {

		accountMenu.setVisible(true);

		for(MenuItem obj: accountMenu.getItems())
			obj.setVisible(true);

		equityMenu.setVisible(true);

		for(MenuItem obj: equityMenu.getItems())
			obj.setVisible(true);

		marketMenu.setVisible(true);

		for(MenuItem obj: marketMenu.getItems())
			obj.setVisible(true);

		helpMenu.setVisible(true);

		for(MenuItem obj: helpMenu.getItems())
			obj.setVisible(true);
	}

	public void simulateMarketHandler() { this.application.showSimulatorView(); }

	public void viewMarketHandler() { this.application.showMarketView(); }

	public void viewOwnedEquityHandler() { this.application.showOwnedEquitiesView();}

	public void viewAccountHandler() { this.application.showAccountsView();}

	public void logoutHandler() { this.application.logout(); }

	public void disableAccountMenu() { accountMenu.setVisible(false); }

	public void disableViewAItem() { viewAItem.setVisible(false); }


	public void disableSimMarketItem() { simMarketItem.setVisible(false); }

	public void disableViewMarketItem() { viewMarketItem.setVisible(false); }

	public void setMainApp(App app) { this.application = app; }

}
