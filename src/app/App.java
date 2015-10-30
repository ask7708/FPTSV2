package app;

import java.io.IOException;

import controllers.LoginOverviewController;
import controllers.SimulatorController;
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
import model.Portfolio;

public class App extends Application {

   private static final String base = "http://query.yahooapis" +
         ".com/v1/public/yql?q=select%20LastTradePriceOnly%20from" +
         "%20yahoo.finance.quotes%20where%20symbol%20in%20(%22";

   private static final String end = "%22)&env=store://datatables" +
         ".org/alltableswithkeys";
   
   private Stage primaryStage;
   private BorderPane rootLayout;
   private Portfolio portfolio;

   public App() {
      
      System.out.println("Constructor called");
      
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
         rootLayout = (BorderPane) loader.load();

         Scene scene = new Scene(rootLayout);
         primaryStage.setScene(scene);
         primaryStage.show();

     } catch (IOException e) {
         e.printStackTrace();
     }
      
   }
   
   public void showLoginView() {
      
      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/LoginView.fxml"));
         AnchorPane loginView = (AnchorPane) loader.load();
         rootLayout.setCenter(loginView);

         LoginOverviewController loginController  = loader.getController();
         loginController.setMainApp(this);

     } catch (IOException e) {
         e.printStackTrace();
     }
   }
   
   public void showDashboardView() {
      
      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/TheOtherScene.fxml"));
         Pane loginView = (Pane) loader.load();
         rootLayout.setCenter(loginView);

         LoginOverviewController loginController  = loader.getController();
         loginController.setMainApp(this);

     } catch (IOException e) {
         e.printStackTrace();
     }
   }
   
   public void showSimulatorView() {
      
      try {

         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(App.class.getResource("../views/Simulator.fxml"));
         Pane simulatorView = (Pane) loader.load();
         rootLayout.setCenter(simulatorView);

         SimulatorController simController = loader.getController();
         simController.setMainApp(this);
         //simController.getEquities(equities);

     } catch (IOException e) {
         e.printStackTrace();
     }
   }
   
   public static void main(String[] args) { launch(args); }
   
   public ObservableList<Holdings> getHoldings() { return this.portfolio.getHoldings(); }
   
   public String getBaseURL() {return this.base; }
   
   public String getEnduRL() { return this.end; }

}
