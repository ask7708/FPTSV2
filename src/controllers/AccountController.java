package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private TableView accountTable;
	
	@FXML
	private Button backButton;
	
	private App application;
	
	ObservableList<Account> accounts = FXCollections.observableArrayList();;;
	
	
	public void viewAccount(String username) {

		
		if(this.application.getPortfolio().getAccounts().size() == 0){
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
