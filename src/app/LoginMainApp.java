package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//import  ch.makery.login.LoginOverviewController;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginMainApp extends Application {
	//private LoginOverviewController talal = new LoginOverviewController();
    private Stage primaryStage;
    private ActionEvent even;
	@FXML private TextField usernameText;
	@FXML private PasswordField passwordText;
	  //@FXML private Button loginButton;
	private Scanner numScan;
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        
        
        /**
        this.primaryStage.setTitle("FPTS");
        Parent loginView = FXMLLoader.load(getClass().getResource("../views/LoginView.fxml"));
        Parent otherView = FXMLLoader.load(getClass().getResource("../views/TheOtherScene.fxml"));
        Scene scene = new Scene(loginView);
        
        this.primaryStage.setTitle("FPTS");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        **/
        

        
        showLogin();
    	//showOther();
    }
    
    public void showLogin() throws IOException{

           Parent loginView = FXMLLoader.load(getClass().getResource("../views/LoginView.fxml"));
           Parent otherView = FXMLLoader.load(getClass().getResource("../views/TheOtherScene.fxml"));
           Scene scene = new Scene(loginView);
           
           this.primaryStage.setTitle("FPTS");
           this.primaryStage.setScene(scene);
           this.primaryStage.show();
        
    }
    public void showOther() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../views/TheOtherScene.fxml"));
        Scene scene = new Scene(root);
        this.primaryStage.setTitle("FPTS");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }


    public boolean trueOr(ActionEvent even) throws FileNotFoundException{
    	System.out.println("again!");
 
    	try{
        	if(userExists(usernameText.getText().toString(),passwordText.getText().toString() ) == true){
                return true;
/**
        		Alert alert = new Alert(AlertType.CONFIRMATION);
        		alert.setTitle("Information Dialog");
        		alert.setContentText("It Looks like you are in the system!");
        		alert.showAndWait();
        		
**/        		
        	}else{
        	return false;
    		
    	}

    	}catch(Exception e){
    		System.out.println(e.toString());
    		
    	}
		return false;
    	
    }
	public boolean userExists(String usernamem, String password) throws FileNotFoundException {

		File f = new File("users.txt");

		numScan = new Scanner(f);
		numScan.useDelimiter(",");
		String line;
		String[] temp;

		while (numScan.hasNextLine()) {
			line = numScan.nextLine();
			temp = line.split(",");
			if (temp[0].equals(usernamem)) {

				if (temp[1].equals(password)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}


    public static void main(String[] args) {
        launch(args);
    }
}