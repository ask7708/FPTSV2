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

public class DashboardController {
	
	public DashboardController() {
		// TODO Auto-generated constructor stub

	}
	
	private App application; 
	
	@FXML private Button logoutB;
	
	public void viewTransactionHandler() { this.application.showTransactionView(); }
	
	public void createSimulationHandler() { this.application.showSimulatorView(); }
	
	public void logoutHandler(ActionEvent even) throws IOException{
	      this.application.logout();
	    }
	
	public void setMainApp(App app) { this.application = app; }
}
