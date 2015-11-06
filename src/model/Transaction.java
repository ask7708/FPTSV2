package model;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Account;
import model.BankAccount;
import model.CashException;
import model.Equity;
import model.MarketAccount;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {
	/**
	 * Date - the date and time the transaction was made
	 * cash - the amount being transferred
	 * Receiver - the object that receives the money
	 * transfer - the object that gives the money
	 */
	private StringProperty time;
	private DoubleProperty cash;
	private Object receiver;
	private Object transfer;
	private StringProperty typeString;
	private String EquityTicker;
	
	/**
	 * 
	 * @param time - time of transaction
	 * @param amount - amount being transferred
	 * @param receiever - the object getting the money
	 * @param transfer - the object giving the money
	 * @throws CashException - not enough money 
	 * 
	 *Builder function of Transaction, uses the receiver and transfer object
	 *to figure out which function to run by finding the instance of the objects
	 *
	 *It also raises exceptions if there isn't enough in the account or equity
	 */
	
	
	private Equity equity;
	
	private Account account;
	
	private double shares;
	
	public Transaction(double shares, Equity equity, Account account){
	
		this.shares = shares;
		this.equity = equity;
		this.account = account;
		
		
	}
	
	
	
	//Amount = user specified shares * price
	//Use this for fuctionalitiy purposes 
	public Transaction(String time, double amount, Object receiver, Object transfer){
		this.time = new SimpleStringProperty(time);
		this.cash = new SimpleDoubleProperty(amount);
		this.receiver = receiver;
		this.transfer = transfer;
		
		if(transfer instanceof Account){
			
			if(receiver instanceof Equity){
				typeString = new SimpleStringProperty("AccountToEquity");
			}
			else if(receiver instanceof String){
				typeString = new SimpleStringProperty("Withdraw");
			}
			else{
				typeString = new SimpleStringProperty("AccountToAccount");
			}
		}
		else if(transfer instanceof String){
			typeString = new SimpleStringProperty("Deposit");
		}
		else{
			typeString = new SimpleStringProperty("EquityToAccount");
		}
		
	}
	
	//Use this for display purposes only
	/**
	 * A transaction constructor that will construct an object using a String[]
	 * This will not generate the objects of the reciever and transfer, so it's
	 * not as useful for computing buy and sell
	 * 
	 * @param temp
	 */
	public Transaction(String[] temp){
		this.EquityTicker = temp[1];
		this.time = new SimpleStringProperty(temp[5]);
		this.cash = new SimpleDoubleProperty(Double.parseDouble(temp[4]));
		this.shares = Double.parseDouble(temp[3]);
		if(this.cash.get() >= 0){
			this.receiver = temp[6];
			this.transfer = temp[2];
			if(temp[6].equals("User")){
				typeString = new SimpleStringProperty("Deposit");
			}
			else if(!temp[1].equals("!BANK")){
				typeString = new SimpleStringProperty("EquityToAccount");
			}
			else{
				typeString = new SimpleStringProperty("AccountToAccount");
			}
		}
		else{
			this.receiver = temp[2];
			this.transfer = temp[6];
			if(temp[6].equals("User")){
				typeString = new SimpleStringProperty("Withdraw");
			}
			else if(!temp[1].equals("!BANK")){
				typeString = new SimpleStringProperty("AccountToEquity");
			}
			else{
				typeString = new SimpleStringProperty("AccountToAccount");
			} 
		}
	}
	
	/**
	 * returns the time of transaction
	 */
	public String getTime(){
		return time.get();
	}
	
	public StringProperty frontTime(){
		return time;
	}
	
	/**
	 * returns the amount being transferred
	 */
	
	public double getAmount(){
		return cash.get();
	}
	
	public DoubleProperty frontAmount(){
		return cash;
	}
	
	/**
	 * returns the object that receives the money
	 */
	public Object getReceiver(){
		return receiver;
	}
	
	public StringProperty frontReceiver(){
		StringProperty name = null;
		if(this.receiver instanceof String){
			name = new SimpleStringProperty((String) this.receiver);
			return name;
		}
		if(this.getType().equals("AccountToAccount") || this.getType().equals("Deposit") || this.getType().equals("EquityToAccount")){
			Account receivers = (Account) receiver;
			name = new SimpleStringProperty(receivers.getAccountName());
		}
		else if(this.getType().equals("AccountToEquity")){
			Equity receivers = (Equity) receiver;
			name = new SimpleStringProperty(receivers.getTickSymbol());
		}
		else{
			name = new SimpleStringProperty("User");
		}
		return name;
	}
	
	/**
	 * returns the object that transfers the money
	 */
	public Object getTransfer(){
		return transfer;
	}
	
	public StringProperty frontTransfer(){
		StringProperty name = null;
		if(this.transfer instanceof String){
			name = new SimpleStringProperty((String) this.transfer);
			return name;
		}
		if(this.getType().equals("AccountToAccount") || this.getType().equals("Withdraw") ||this.getType().equals("AccountToEquity")){
			Account transfers = (Account) transfer;
			name = new SimpleStringProperty(transfers.getAccountName());
		}
		else if(this.getType().equals("EquityToAccount")){
			Equity transfers = (Equity) transfer;
			name = new SimpleStringProperty(transfers.getTickSymbol());
		}
		else{
			name = new SimpleStringProperty("User");
		}
		return name;
	}
	
	public String getType(){
		return typeString.get();
	}
	
	public StringProperty frontType(){
		return typeString;
	}
	
	/**
	 * takes the objects and makes sure that the monetary changes occur between
	 * account and Equity
	 */
	public void AccountToEquity(){
		if(((Account) transfer).getAmount() < cash.get()){
			try {
				throw new CashException();
			} catch (CashException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Account transfers = (Account) getTransfer(); //Transfers is the Account version of transfer
		Equity receivers = (Equity) getReceiver(); //Receivers is the Equity version of Receiver
		double total = getAmount()/receivers.getInitPrice();
		transfers.setAmount(transfers.getAmount() - getAmount());
		receivers.setShares((int) (receivers.getShares() + total));
		transfer = transfers;
		receiver = receivers;
		
	}
	
	/**
	 * takes the objects and makes sure that the monetary changes occur between
	 * Equity and Account
	 */
	public void EquityToAccount(){
		Equity transfers = (Equity) getTransfer(); //transfers is the Equity version of transfer
		Account receivers = (Account) getReceiver(); //receivers is the Account version of receiver
		double totalexp = transfers.getShares() * transfers.getInitPrice();
		if(totalexp < cash.get()){
			try {
				throw new CashException();
			} catch (CashException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double total = (getAmount()/transfers.getInitPrice());
		transfers.setShares(transfers.getShares() - total);
		receivers.setAmount(receivers.getAmount() + (total*transfers.getInitPrice()));
		transfer = transfers;
		receiver = receivers;
		
	}
	
	/**
	 * takes the objects and makes sure that the monetary changes occur
	 * between Equity and Account
	 */
	public void AccountToAccount(){
		if(((Account) transfer).getAmount() < cash.get()){
			try {
				throw new CashException();
			} catch (CashException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Account transfers = (Account) this.getTransfer(); //transfers is the account version of transfer
		Account recivers = (Account) this.getReceiver(); //receivers is the account version of receivers
		recivers.setAmount(recivers.getAmount() + getAmount());
		transfers.setAmount(transfers.getAmount() - getAmount());
		transfer = transfers;
		receiver = recivers;
		
		
	}
	
	/**
	 * takes the objects and makes sure that the monetary changes occur between
	 * the account and user
	 */
	public void Withdraw(){
		if(((Account) transfer).getAmount() < cash.get()){
			try {
				throw new CashException();
			} catch (CashException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Account transfers = (Account) getTransfer(); //transfers is the account version of transfer
		transfers.setAmount(transfers.getAmount() - getAmount());
		transfer = transfers;
	}
	
	/**
	 * takes the objects and makes sure that the monetary changes occur between
	 * the user and account
	 */
	public void Deposit(){
		Account recivers = (Account) getReceiver(); //receivers is the account version of receiver
		recivers.setAmount(recivers.getAmount() + getAmount());
		receiver = recivers;
	}
	
	/**
	 * This generates an array String of the current Transaction
	 * @return - the array String
	 */
	public String[] toStringArray(){
		String[] temp = {null, null, null, null, null, null, null};
		temp[0] = "\"!T";
		temp[5] = this.getTime();
		if(this.receiver instanceof String){
			if(this.getType().equals("AccountToAccount") || this.getType().equals("Deposit") || this.getType().equals("Withdraw")){
				temp[1] = "!BANK";
				if(this.getType().equals("Deposit") || this.getType().equals("AccountToAccount")){
					temp[2] = (String) this.receiver;
					temp[3] = "" + this.getAmount();
					temp[4] = "1";
					temp[5] = this.getTime();
					temp[6] = (String) this.getTransfer();
				}
				else{
					temp[2] = (String) this.transfer;
					temp[3] = "-" + this.getAmount();
					temp[4] = "-1";
					temp[5] = this.getTime();
					temp[6] = (String) this.getReceiver();
					
				}
			}
			else{
				temp[4] = "" + this.getAmount();
				if(this.getAmount() < 0){
					temp[6] = (String) this.transfer;
					temp[1] = this.EquityTicker;
					temp[3] = "" + this.shares;
					temp[2] = (String) this.receiver;
				}
				else{
					temp[3] = "" + this.shares;
					temp[6] = (String) this.receiver;
					temp[1] = this.EquityTicker;
					temp[2] = (String) this.transfer;
				}
			}
		}
		else{
			if(this.getType().equals("AccountToAccount") || this.getType().equals("Deposit") || this.getType().equals("Withdraw")){
				temp[1] = "!BANK";
				if(this.getAmount() > 0){
					temp[4] = "1";
					Account temp2 = (Account) this.getReceiver();
					temp[2] = temp2.getAccountName();
					temp[3] = "" + temp2.getAmount();
					if(this.getType().equals("Deposit")){
						temp[6] = "User";
					}
					else{
						Account temp6 = (Account) this.getTransfer();
						temp[6] = temp6.getAccountName();
					}
				}
				else{
					temp[4] = "-1";
					Account temp2 = (Account) this.getTransfer();
					temp[2] = temp2.getAccountName();
					temp[3] = "" + temp2.getAmount() * -1;
					temp[6] = "User";
				}
			}
			else{
				if(this.getAmount() < 0){
					Equity tempEquity = (Equity) this.getTransfer();
					temp[1] = tempEquity.getTickSymbol();
					temp[2] = tempEquity.getName();
					temp[3] = "" + tempEquity.getInitPrice();
					Account tempAccount = (Account) this.getReceiver();
					temp[6] = tempAccount.getAccountName();
					temp[4] = "" + tempEquity.getShares();
				}
				else{
					Equity tempEquity = (Equity) this.getReceiver();
					temp[1] = tempEquity.getTickSymbol();
					temp[2] = tempEquity.getName();
					temp[3] = "" + tempEquity.getInitPrice();
					Account tempAccount = (Account) this.getTransfer();
					temp[6] = tempAccount.getAccountName();
					temp[4] = "" + tempEquity.getShares();
				}
			}
		}
		temp[6] = temp[6] + "\"";
		return temp;
	}
	
	
	/**
	 * This should generate the csv format of a Transaction
	 */
	public String toString(){
		
		 String newS = new String();
         newS += ",\"" + this.getEquity().getTickSymbol()+"\"";
         newS += ",\"" + (this.getEquity().getName() +"\",");
         newS += ",\"" + (this.getEquity().getInitPrice() +"\",");
         if(this.getShares() != 0.0){
        	 newS += "\"" + (this.getShares() +"\",");
         }
         if(this.getTime() != null){
        	 newS += (this.getTime() +",");
         }
         
         newS += newS + this.getAccount().getAccountName();
         
         newS += "\n";
         newS = newS.substring(0, newS.length()-3);
         return newS;
		}	
		

		
		
	
	
	/**
	 * This takes the type of the current transaction and then decides what type
	 * of transfer should occur and then alters the object with it
	 */
	public void Transfer(){
		if(typeString.get().equals("Deposit")){
			Deposit();
		}
		else if(typeString.get().equals("Withdraw")){
			Withdraw();
		}
		else if(typeString.get().equals("AccountToAccount")){
			AccountToAccount();
		}
		else if(typeString.get().equals("EquityToAccount")){
			EquityToAccount();
		}
		else if(typeString.get().equals("AccountToEquity")){
			AccountToEquity();
		}
	}
	
	/**
	 * Gets the account for the EquitytoAccount constructor method
	 * @return
	 */
	public Account getAccount(){
	
		return this.account;
	}
	/**
	 * gets the equity for the equity to account Constructor method
	 * @return
	 */
	public Equity getEquity(){
	
		return this.equity;
	}
	/**
	 * gets the shraes for the equity to Account Constructor method and
	 * String[] constructor method
	 * @return
	 */
	public double getShares(){
	
		return this.shares;
	}
	
	public static void main(String[] args){
		/*
		Account things = new BankAccount("!BANK", "Dolla", 5000, "20150002", "1004", "99541");
		Account thingsExtra = new MarketAccount("!MM", "Dollas", 5000, "20150002", "1005", "99542");
		Equity stuff = new Equity("Stuff", "YaY", 40);
		System.out.print("" + things.getAmount() + '\n');
		System.out.print("" + stuff.getShares() + '\n');
		Transaction done = new Transaction("20150103", 120, stuff, things);
		done.Transfer();
		System.out.print("" + things.getAmount()+ '\n');
		System.out.print("" + stuff.getShares() + '\n');
		Transaction dones = new Transaction("20150103", 80, thingsExtra, things);
		dones.Transfer();
		System.out.print("" + things.getAmount()+ '\n');
		System.out.print("" + thingsExtra.getAmount()+ '\n');
		Transaction Extradone = new Transaction("20150103", 60, things, stuff);
		Extradone.Transfer();
		System.out.print("" + things.getAmount() + '\n');
		System.out.print("" + stuff.getShares() + '\n');
		Transaction Withdrawdone = new Transaction("20150103", 100, "User", things);
		Withdrawdone.Transfer();
		System.out.print("" + things.getAmount() + '\n');
		Transaction Depositdone = new Transaction("20150103", 10000.50, things, "User");
		Depositdone.Transfer();
		System.out.print("" + things.getAmount() + '\n');
		*/

	}
}

