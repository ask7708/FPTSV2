package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.filechooser.FileNameExtensionFilter;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import model.Account;
import model.Equity;
import model.ReadAccountHolding;
import model.ReadHoldingsContext;
import model.ReadOwnedEquities;

public class AccountController {
	
	
	@FXML
	private TableColumn<Account, String> typeCol;
	@FXML
	private TableColumn<Account, String> nameCol;
	@FXML
	private TableColumn<Account, Number> amountCol;
	@FXML
	private TableColumn<Account, String> routingCol;
	@FXML
	private TableColumn<Account, String> accountNumCol;
	@FXML
	private TableColumn<Account, String> dateCol;
	
	@FXML
	private TableView<Account> accountTable;
	
	@FXML
	private Button backButton;
	
	@FXML
	private Button importButton;
	
	private App application;
	
	ObservableList<Account> accounts = FXCollections.observableArrayList();
	
   
	
	
	public void viewAccount(String username) {

		/*
		if(application.getPortfolio().getAccounts().size() == 0){
		System.out.println("AccountController");
		this.application.setReadAccounts(true);	
		File data = new File(username+".txt");
		Scanner dataRead = null;

		try {
			dataRead = new Scanner(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line;
		String[] temp;

		while (dataRead.hasNextLine()) {
			line = dataRead.nextLine();
			line = line.replace("\"", "");
			line = line.replace(", ", "");
			temp = line.split(",");
			ReadHoldingsContext readAccountHolding = new ReadHoldingsContext(new ReadAccountHolding());

			if (temp[0].equals("!MM") || temp[0].equals("!BANK")) {
				Account accountInfo = (Account) readAccountHolding.executeStrategy(temp);

				accounts.add(accountInfo);
				
			}

		}

		dataRead.close();
		application.getPortfolio().setAccounts(accounts);
		accountTable.setItems(application.getPortfolio().getAccounts());
		}else{
			
			accountTable.setItems(application.getPortfolio().getAccounts());
		}*/
		accountTable.setItems(application.getPortfolio().getAccounts());
	}

	public void importAccounts() {

		this.application.setReadAccounts(false);	
		FileChooser fileChooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			Scanner dataRead = null;
			try {
				dataRead = new Scanner(selectedFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String line;
			// StringBuffer sb = new StringBuffer();
			PrintWriter out;

			while (dataRead.hasNextLine()) {
				line = dataRead.nextLine();
				// System.out.println(line);
				if (line.startsWith("\"!MM\"") || line.startsWith("\"!BANK\"")) {
					// sb.append(line+"\n");
					File writeFile = new File(this.application.getUserName()+".txt");
					writeFile.setWritable(true);
					try {
						out = new PrintWriter(
								new BufferedWriter(new FileWriter(this.application.getUserName() + ".txt", true)));
						out.println(line);
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			dataRead.close();
		}


	}
	
	
	public ObservableList<Account> getAccountArray() {

		return this.accounts;
	}


	public ObservableList getTableData(){
		
		return accountTable.getItems();
	}
	
	

	public static void main(String args[]) {

		AccountController evc = new AccountController();
		evc.viewAccount("itnks");
		System.out.println(evc.getAccountArray().get(1).toString());

	}

	@FXML
	public void initialize() {
		
		
		typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().accountNameProperty());
		amountCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
		accountNumCol.setCellValueFactory(cellData -> cellData.getValue().accountNoProperty());
		routingCol.setCellValueFactory(cellData -> cellData.getValue().routingNoProperty());
		dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
	
		
	}

	public void setMainApp(App app) {
		
		this.application = app;
		
	}
	
	@FXML
	public void backHandler() { this.application.showDashboardView(); }

}
