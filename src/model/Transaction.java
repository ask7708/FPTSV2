package model;
import java.time.LocalDate;

import model.Account;
import model.BankAccount;
import model.CashException;
import model.Equity;
import model.MarketAccount;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {
	/**
	 * Date - the date and time the transaction was made
	 * cash - the amount being transferred
	 * Receiver - the object that receives the money
	 * transfer - the object that gives the money
	 */
	final private StringProperty time;
	final private DoubleProperty cash;
	private Object receiver;
	private Object transfer;
	final private StringProperty typeString;
	
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
	
	public Transaction(String time, double amount, Object receiver,Object transfer){
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
		if(typeString.get() == "AccounToAccount" || typeString.get() == "Deposit" || typeString.get() == "AccountToEquity"){
			Account receivers = (Account) receiver;
			name = new SimpleStringProperty(receivers.getAccountName());
		}
		if(typeString.get() == "EquityToAccount"){
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
		if(typeString.get() == "AccounToAccount" || typeString.get() == "Withdraw" || typeString.get() == "EquityToAccount"){
			Account receivers = (Account) receiver;
			name = new SimpleStringProperty(receivers.getAccountName());
		}
		if(typeString.get() == "AccountToEquity" ){
			Equity receivers = (Equity) receiver;
			name = new SimpleStringProperty(receivers.getTickSymbol());
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
		Account transfers = (Account) getTransfer(); //transfers is the account version of transfer
		Account recivers = (Account) getReceiver(); //receivers is the account version of receivers
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
	
	public void Transfer(){
		if(typeString.get() == "Deposit"){
			Deposit();
		}
		else if(typeString.get() == "Withdraw"){
			Withdraw();
		}
		else if(typeString.get() == "AccountToAccount"){
			AccountToAccount();
		}
		else if(typeString.get() == "EquityToAccount"){
			EquityToAccount();
		}
		else if(typeString.get() == "AccountToEquity"){
			AccountToEquity();
		}
	}
	
	public static void main(String[] args){
		Account things = new BankAccount("Dolla", 5000, "20150002", 1004, 99541);
		Account thingsExtra = new MarketAccount("Dollas", 5000, "20150002", 1005, 99542);
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
		
	}
	

}