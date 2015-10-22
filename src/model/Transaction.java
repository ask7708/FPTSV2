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
	final private ObjectProperty<Object> receiver;
	final private ObjectProperty<Object> transfer;
	
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
		this.receiver = new SimpleObjectProperty<Object>(receiver);
		this.transfer = new SimpleObjectProperty<Object>(transfer);
		
		if(transfer instanceof Account){
			if(((Account) transfer).getAmount() < cash.get()){
				try {
					throw new CashException();
				} catch (CashException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(receiver instanceof Equity){
				AccountToEquity();
			}
			else if(receiver instanceof String){
				Withdraw();
			}
			else{
				AccountToAccount();
			}
		}
		else if(transfer instanceof String){
			Deposit();
		}
		else{
			Equity transfers = (Equity) transfer;
			double total = transfers.getShares() * transfers.getInitPrice();
			if(total < cash.get()){
				try {
					throw new CashException();
				} catch (CashException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			EquityToAccount();
		}
		
	}
	
	/**
	 * returns the time of transaction
	 */
	public String getTime(){
		return time.get();
	}
	
	/**
	 * returns the amount being transferred
	 */
	
	public double getAmount(){
		return cash.get();
	}
	
	/**
	 * returns the object that receives the money
	 */
	public Object getReceiver(){
		return receiver.get();
	}
	
	/**
	 * returns the object that transfers the money
	 */
	public Object getTransfer(){
		return transfer.get();
	}
	
	/**
	 * takes the objects and makes sure that the monetary changes occur between
	 * account and Equity
	 */
	public void AccountToEquity(){
		Account transfers = (Account) getTransfer(); //Transfers is the Account version of transfer
		Equity receivers = (Equity) getReceiver(); //Receivers is the Equity version of Receiver
		double total = getAmount()/receivers.getInitPrice();
		transfers.setAmount(transfers.getAmount() - total);
		receivers.setShares((int) (receivers.getShares() + total));
		transfer.set(transfers);
		receiver.set(receiver);
		
	}
	
	/**
	 * takes the objects and makes sure that the monetary changes occur between
	 * Equity and Account
	 */
	public void EquityToAccount(){
		Equity transfers = (Equity) getTransfer(); //transfers is the Equity version of transfer
		Account receivers = (Account) getReceiver(); //receivers is the Account version of receiver
		double total = (getAmount()/transfers.getInitPrice());
		transfers.setShares(transfers.getShares() - total);
		receivers.setAmount(getAmount() + (total*transfers.getInitPrice()));
		transfer.set(transfers);
		receiver.set(receivers);
		
	}
	
	/**
	 * takes the objects and makes sure that the monetary changes occur
	 * between Equity and Account
	 */
	public void AccountToAccount(){
		Account transfers = (Account) getTransfer(); //transfers is the account version of transfer
		Account recivers = (Account) getReceiver(); //receivers is the account version of receivers
		recivers.setAmount(recivers.getAmount() + getAmount());
		transfers.setAmount(transfers.getAmount() - getAmount());
		transfer.set(transfers);
		receiver.set(recivers);
		
		
	}
	
	/**
	 * takes the objects and makes sure that the monetary changes occur between
	 * the account and user
	 */
	public void Withdraw(){
		Account transfers = (Account) getTransfer(); //transfers is the account version of transfer
		transfers.setAmount(transfers.getAmount() - getAmount());
		transfer.set(transfers);
	}
	
	/**
	 * takes the objects and makes sure that the monetary changes occur between
	 * the user and account
	 */
	public void Deposit(){
		Account recivers = (Account) getReceiver(); //receivers is the account version of receiver
		recivers.setAmount(recivers.getAmount() + getAmount());
		receiver.set(recivers);
	}
	
	public static void main(String[] args){
		Account things = new BankAccount("Dolla", 5000, "20150002", 1004, 99541);
		Account thingsExtra = new MarketAccount("Dollas", 5000, "20150002", 1005, 99542);
		Equity stuff = new Equity("Stuff", "YaY", 40);
		System.out.print("" + things.getAmount() + '\n');
		System.out.print("" + stuff.getShares() + '\n');
		Transaction done = new Transaction("20150103", 120, stuff, things);
		System.out.print("" + things.getAmount()+ '\n');
		System.out.print("" + stuff.getShares() + '\n');
		Transaction dones = new Transaction("20150103", 80, thingsExtra, things);
		System.out.print("" + things.getAmount()+ '\n');
		System.out.print("" + thingsExtra.getAmount()+ '\n');
		Transaction Extradone = new Transaction("20150103", 60, things, stuff);
		System.out.print("" + things.getAmount() + '\n');
		System.out.print("" + stuff.getShares() + '\n');
		Transaction Withdrawdone = new Transaction("20150103", 100, "User", things);
		System.out.print("" + things.getAmount() + '\n');
		Transaction Depositdone = new Transaction("20150103", 10000.50, things, "User");
		System.out.print("" + things.getAmount() + '\n');
		
	}
	

}