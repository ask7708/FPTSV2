package controllers;


import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JOptionPane;

import app.App;
import app.LoginMainApp;
import model.DecryptStrategy;
import model.EncryptStrategy;
import model.User;
//import ch.makery.login.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Talal Alsarrani, Daniel Roach, Arshdeep Khalsa , Daniel Cyphe
 *
 */

public class LoginOverviewController {

   private App application;
   
   @FXML private TextField usernameText;
	@FXML private PasswordField passwordText;
	@FXML private Button loginButton;
	
	private Scanner numScan;
	
	public LoginOverviewController() {
	    
	}
	


	/**
	 * checks if the user exists in the system or not.
	 * @param usernamem 
	 * @param password
	 * @return
	 * @throws FileNotFoundException
	 */
	public boolean userExists(String usernamem, String password) throws FileNotFoundException {

		File f = new File("users.txt");
		
		numScan = new Scanner(f);
		numScan.useDelimiter(",");
		String line;
		String[] temp;

		DecryptStrategy ds = new DecryptStrategy();
		
		while (numScan.hasNextLine()) {
			line = numScan.nextLine();
			temp = line.split(",");
			if (temp[0].equals(usernamem)) {

				if (password.equalsIgnoreCase(ds.assessPassword(temp[1]))) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
	/*
	 * register the user into the system if it does not exist.
	 */
	public void registerHandler(ActionEvent even) throws IOException{
		System.out.println("yes1");
		
		EncryptStrategy es = new EncryptStrategy();
		
		String tempPassword = passwordText.getText().toString() ;
		String encrypted = es.assessPassword(tempPassword);
		String tempName = usernameText.getText().toString();
		User theUserModel = new User(tempName, tempPassword);
		
		File newfile = new File(tempName+".txt");
		boolean blnCreated = false;
		  
		  
		File file = new File("users.txt");
		if (!theUserModel.userExists(tempName)) {
			//System.out.println("there no one with this username " + tempName);
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("users.txt", true)));
			out.println(tempName + "," + encrypted);
			out.close();
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Information Dialog");
    		alert.setContentText("you have registered successfully!/n Now,hit the login button.");
    		alert.showAndWait();
    		
    		try{
    			 blnCreated = newfile.createNewFile();
    			 System.out.print("the file is created");
    		}catch(IOException e){
    			System.out.println(e.toString());
    		}
			
		}else{
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Information Dialog");
    		alert.setContentText("The username is alreay taken");
    		alert.showAndWait();
			System.out.println("there is someone with this username " + tempName);
		}
		

	}
	
		
     /*
     * login the user to the dash boars if the user exist. 
     */
    public void loginHandler(ActionEvent even ) throws FileNotFoundException{
       
       if(userExists(usernameText.getText().toString(), passwordText.getText().toString())) {  
          
          this.application.setUsername(usernameText.getText().toString());
          this.application.showDashboardView();
       }
       
       else {
          System.out.println("Did not get the right password ");
          Alert alert = new Alert(AlertType.ERROR);
          alert.setTitle("Information Dialog");
          alert.setContentText("Invalid Password or Username");
          alert.showAndWait();
       }	
    }

    public void setMainApp(App app) { this.application = app; }
}
