package app;

import java.io.IOException;

import controllers.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Holdings;
import model.Market;
import model.Portfolio;
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
   
   private String username;

   public App() {
      
      this.portfolio = new Portfolio();
      this.market = new Market("nameoftxtfilehere.txt");
      this.watchlist = new Watchlist();
   }
   
   @Override
   public void start(Stage primaryStage) throws Exception {
      
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

     } catch (IOException e) {
         e.printStackTrace();
     }
   }
   
   /**
    * Makes a transition to the simulator view
    */
   public void showDashboardView() {
      
      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/TheOtherScene.fxml"));
         Pane loginView = (Pane) loader.load();
         rootLayout.setCenter(loginView);

         primaryStage.setTitle("FPTS - " + username + " - Dashboard");
         
         rootController.resetDashboardMenu();
         DashboardController dashboardController  = loader.getController();
         dashboardController.setMainApp(this);

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

     } catch (IOException e) {
         e.printStackTrace();
     }
   }
   
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

}
