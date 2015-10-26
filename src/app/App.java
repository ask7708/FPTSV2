package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

   @Override
   public void start(Stage primaryStage) throws Exception {
      
      // you can change this line to start the app with the fxml file you've made
      // it helps for testing your individual subsystem
      Parent root = FXMLLoader.load(getClass().getResource("../views/ViewOfEquity.fxml"));
     // primaryStage.setTitle("FPTS - Simulation");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
   }
   
   public static void main(String[] args) { launch(args); }

}
