package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
	
	ArrayList<Account> accounts = new ArrayList<Account>();
	
	
	public void viewAccount() {

		//

		File data = new File("itnks.txt");
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

	}

	public ArrayList<Account> getAccountArray() {

		return this.accounts;
	}


	public ObservableList getTableData(){
		
		return accountTable.getItems();
	}
	
	

	public static void main(String args[]) {

		AccountController evc = new AccountController();
		evc.viewAccount();
		System.out.println(evc.getAccountArray().get(1).toString());

	}

	@FXML
	public void initialize() {
		
		viewAccount();
		ObservableList<Account> data = FXCollections.observableArrayList(getAccountArray());
		
		//data.add(new Equity("ASSD","Assistance Co.",12.37,23.48,"20151013"));
		//System.out.println(getEquityArray());
		
		accountTable.getItems().clear();
		System.out.println("Before OwnedTable"+accountTable.getItems());
		accountTable.setItems(data);
		System.out.println("After OwnedTable"+accountTable.getItems());
		
		
		typeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().accountNameProperty());
		amountCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
		accountNumCol.setCellValueFactory(cellData -> cellData.getValue().accountNoProperty());
		routingCol.setCellValueFactory(cellData -> cellData.getValue().routingNoProperty());
		dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
	
		
	}

}
