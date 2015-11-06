package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import controllers.AccountController;
import controllers.DashboardController;
import controllers.LoginOverviewController;
import controllers.MarketController;
import controllers.OwnedEquityController;
import controllers.RootLayoutController;
import controllers.SimulatorController;
import controllers.TransactionController;
import controllers.WatchlistController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Account;
import model.Equity;
import model.Holdings;
import model.Market;
import model.ParseTransaction;
import model.Market.DowIterator;
import model.Portfolio;
import model.UpdateTimer;
import model.Watchlist;

public class App extends Application {

   private static final String base = "http://query.yahooapis" +
         ".com/v1/public/yql?q=select%20LastTradePriceOnly%20from" +
         "%20yahoo.finance.quotes%20where%20symbol%20in%20(%22";

   private static final String end = "%22)&env=store://datatables" +
         ".org/alltableswithkeys";

   private Stage primaryStage;
   private BorderPane rootLayout;

   // the logged in user's portfolio object
   private Portfolio portfolio;


   // the market object holding all the equities we can buy
   private Market market;

   // the Watchlist object holding the equities the user would 
   // like to monitor???
   private Watchlist watchlist;

   // a reference to the root layout's controller (used to manipulate menus)
   private RootLayoutController rootController;

   // the logged in user's username
   private String username;

   // the update timer we will use to refresh stock prices at a certain interval
   private UpdateTimer timer;

   // a list of the 30 Dow Jones Equities
   private ObservableList<Equity> dowCompanies;

   // if we can get price updates happening, I'd like to think I could store 
   // the DJIA values here for comparision
   private ArrayList<Double> DJIAvals;

   public App() {

      this.market = new Market("shorteqs.txt");
      this.watchlist = new Watchlist();
      this.portfolio = null;


      dowCompanies = FXCollections.observableArrayList();

      DowIterator dowFinder = market.getDowIterator();

      for(dowFinder.first(); !dowFinder.isDone(); dowFinder.next()) {
         dowCompanies.add(dowFinder.currentItem());
      }

      timer = new UpdateTimer();
      timer.scheduleUpdate(1, 15, this);
   }


   @Override
   public void start(Stage primaryStage) throws Exception {

      // you can change this line to start the app with the fxml file you've made
      // it helps for testing your individual subsystem
      Parent root = FXMLLoader.load(getClass().getResource("../views/RootLayout.fxml"));
      // primaryStage.setTitle("FPTS - Simulation");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();

      this.primaryStage = primaryStage;
      initRoot();
      showLoginView();

   }

   public void initRoot() {

      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/RootLayout.fxml"));
         this.rootLayout = (BorderPane) loader.load();

         this.rootController = loader.getController();
         rootController.setMainApp(this);

         Scene scene = new Scene(rootLayout);
         primaryStage.setScene(scene);
         primaryStage.show();

      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   /**
    * Makes a transition to the login view
    */
   public void showLoginView() {

      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/LoginView.fxml"));
         AnchorPane loginView = (AnchorPane) loader.load();
         this.rootLayout.setCenter(loginView);

         primaryStage.setTitle("FPTS - Login");

         rootController.disableEntireMenu();

         LoginOverviewController loginController = loader.getController();
         loginController.setMainApp(this);
      }
      
      catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Makes a transition to the simulator view
    */
   public void showDashboardView() {

      try {



    	 if(this.portfolio == null){
    	 this.portfolio = new Portfolio(getUserName());
    	 }
    	 
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/TheOtherScene.fxml"));
         Pane loginView = (Pane) loader.load();
         rootLayout.setCenter(loginView);

         primaryStage.setTitle("FPTS - " + username + " - Dashboard");

         rootController.resetDashboardMenu();
         DashboardController dashboardController  = loader.getController();
         dashboardController.setMainApp(this);
         dashboardController.setDowCompanies(dowCompanies);
         dashboardController.setPortfolioValue(this.portfolio.getPortfolioValue());
         System.out.println(this.portfolio.getPortfolioValue());
         
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Makes a transition to the simulator view
    */
   public void showSimulatorView() {

      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/Simulator.fxml"));
         Pane simulatorView = (Pane) loader.load();
         rootLayout.setCenter(simulatorView);

         rootController.resetDashboardMenu();
         rootController.disableSimMarketItem();

         primaryStage.setTitle("FPTS - " + username + " - Simulator");
         SimulatorController simController = loader.getController();
         simController.setMainApp(this);
         simController.getEquities(this.portfolio.getEquityList());

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Makes a transition to the market view
    */
   public void showMarketView() {

      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/ViewMarket.fxml"));
         Pane marketView = (Pane) loader.load();
         rootLayout.setCenter(marketView);

         rootController.resetDashboardMenu();
         rootController.disableViewMarketItem();

         primaryStage.setTitle("FPTS - " + username + " - View Market");
         MarketController marketController = loader.getController();
         marketController.setMainApp(this);
         marketController.setMarket(this.market);
         marketController.setWatchlist(this.watchlist);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Makes a transition to the transaction view
    */
   public void showTransactionView() {

      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/TransactionView.fxml"));
         SplitPane transactionView = (SplitPane) loader.load();
         rootLayout.setCenter(transactionView);

         rootController.resetDashboardMenu();
         //rootController.disableViewMarketItem();

         primaryStage.setTitle("FPTS - " + username + " - View Transactions");
         TransactionController transactionController = loader.getController();
         transactionController.setMainApp(this);
         transactionController.setinformation(username);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void showWatchlistView() {

      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/ViewWatchlist.fxml"));
         Pane watchlistView = (Pane) loader.load();
         rootLayout.setCenter(watchlistView);

         primaryStage.setTitle("FPTS - " + username + " - Manage Watchlist");
         WatchlistController watchlistController = loader.getController();
         watchlistController.setMainApp(this);
         watchlistController.setWatchlist(watchlist);

      } catch(IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Makes a transition to the equity view
    */
   public void showOwnedEquitiesView() {

      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/ViewOfEquity.fxml"));
         Pane ownedEquityView = (Pane) loader.load();
         rootLayout.setCenter(ownedEquityView);

         rootController.resetDashboardMenu();
         //rootController.disableViewMarketItem();

         primaryStage.setTitle("FPTS - " + username + " - View Owned Equities");
         OwnedEquityController ownedEquityController = loader.getController();
         ownedEquityController.setMainApp(this);
         ownedEquityController.readOwnedEquities(username);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }


   /**
    * Makes a transition to the accounts view
    */
   public void showAccountsView() {

      try {


         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/AccountView.fxml"));
         Pane accountView = (Pane) loader.load();
         rootLayout.setCenter(accountView);

         rootController.resetDashboardMenu();
         //rootController.disableViewMarketItem();

         primaryStage.setTitle("FPTS - " + username + " - Accounts");
         AccountController accountController = loader.getController();
         accountController.setMainApp(this);
         accountController.viewAccount(username);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Makes a transition back to the login view by logging out 
    * (maybe prompt for saving the user's portfolio, cancel any scheduled price
    * updates, etc.)
    */
   public void logout() {

	  
	  File writeFile = new File(getUserName()+".txt");
	  writeFile.setWritable(true);
      PrintWriter out;
      try {
		out = new PrintWriter(
					new BufferedWriter(new FileWriter(getUserName() + ".txt", false)));

		for(Equity e: this.portfolio.getEquityList()){
			
			out.println("\"!OWNED\","+e.toString());
		}
		for(Account a: this.portfolio.getAccounts()){
			
			if(a.getTypeOfAccount().equals("Money Market")){
			out.println("\"!MM\"," + a.toString());
			}
			else{
			out.println("\"!BANK\"," + a.toString());
			}
		}
		
	
		
		out.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
     
      
      this.portfolio = null;
      this.market = null;
      this.watchlist = null;
      this.username = null;
      showLoginView();   
   }
   
   public void setUsername(String user) { this.username = user; }

   public static void main(String[] args) { launch(args); }

   public ObservableList<Holdings> getHoldings() { return this.portfolio.getHoldings(); }

   public String getBaseURL() {return this.base; }

   public String getEnduRL() { return this.end; }

   public Market getMarket(){

      return this.market;
   }

   public String getUserName(){

      return this.username;
   }

   public Watchlist getWatchlist() { return this.watchlist; }
   
   public Portfolio getPortfolio(){

      return this.portfolio;
   }

   @Override
   public void stop(){
	
	   System.exit(0);
   }
   
   
}