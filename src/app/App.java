package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

   private static final String base = "http://query.yahooapis" +
         ".com/v1/public/yql?q=select%20LastTradePriceOnly%20from" +
         "%20yahoo.finance.quotes%20where%20symbol%20in%20(%22";

   private static final String end = "%22)&env=store://datatables" +
         ".org/alltableswithkeys";
   
   
   @Override
   public void start(Stage primaryStage) throws Exception {
      
      // you can change this line to start the app with the fxml file you've made
      // it helps for testing your individual subsystem
      Parent root = FXMLLoader.load(getClass().getResource("../views/AccountView.fxml"));
     // primaryStage.setTitle("FPTS - Simulation");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
   }
   
   public static void main(String[] args) { launch(args); }

}
