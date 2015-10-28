/**
 * 
 */
package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Holdings;
import model.MarketAccount;

/**
 * @author Arsh
 *
 */
public abstract class Account implements Holdings {
	
	
	/**
	 * The name of an account
	 */
	private StringProperty typeOfAccount;
	
	/**
	 * The name of an account
	 */
	private StringProperty accountName;
	
	/**
	 * The amount in the type of account
	 */
	private DoubleProperty amount;
	
	/**
	 * The date the type of account was added
	 */
	private StringProperty date;
	
	/**
	 * The account number for this account
	 */
	private StringProperty accountNo;
	
	/**
	 * The routing number for this account
	 */
	private StringProperty routingNo;
	
	/**
	 * Creates an account object with the name, initial amount, 
	 * and the date it was added.
	 * 
	 * @param accountName
	 * @param amount
	 * @param date
	 */
	public Account(String typeOfAccount,String accountName, double amount, String date, String accountNum, String routingNum){
		
		this.typeOfAccount = new SimpleStringProperty(typeOfAccount);
		this.accountName = new SimpleStringProperty(accountName);
		this.amount = new SimpleDoubleProperty(amount);
		this.date= new SimpleStringProperty(date);
		this.accountNo = new SimpleStringProperty(accountNum);
		this.routingNo = new SimpleStringProperty(routingNum);
		
	}

	/**
	 * Returns the account name of the 
	 * specified account
	 * 
	 * @return accountName
	 */
	public String getAccountName() {
		return accountName.get();
	}
	
	/**
	 * gets the routing number of the
	 * specified string
	 */
	public String getRoutingNum(){
		return routingNo.get();
	}
	/**
	 * sets the routingno for the specified string
	 */
	public void setRoutingNum(String setNumber){
		this.routingNo.set(setNumber);
	}
	
	/**
	 * gets the account number of the
	 * specified String
	 */
	public String getAccountNum(){
		return accountNo.get();
	}
	/**
	 * sets the accountno for the specified int
	 */
	public void setAccountNum(String setNumber){
		this.accountNo.set(setNumber);
	}
	
	/**
	 * Set the name of an account
	 * 
	 * @param accountName
	 */
	public void setAccountName(String accountName) {
		this.accountName.set(accountName);
	}

	/**
	 * Returns the amount in the type of account
	 * 
	 * @return amount
	 */
	public double getAmount() {
		return amount.get();
	}

	/**
	 * Sets the amount for the account
	 * 
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount.set(amount);
	}

	/**
	 * Returns the date the account was added 
	 * 
	 * @return date
	 */
	public String getDate() {
		return date.get();
	}

	/**
	 * Set the date the account was added
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date.set(date);
	}
	
	/**
	 * Returns a String representation of the class in this form:
	 * accountName, balance, dateAdded
	 */
	public String toString(){
		
		String newS = new String();
        newS += this.getAccountName();
        newS += " , " + this.getAmount();
        newS += " , " + (this.getRoutingNum() +" , ");
        newS += (this.getAccountNum() +" , ");
        newS += this.getDate();
        
        newS += "\n";
        newS = newS.substring(0, newS.length()-2);
        return newS;
	}
	
	

	public static void main(String args[]){
		
		Account testAccount = new MarketAccount("","ArshBank", 100.0, "20151013", "000000000", "000000000");
		System.out.println(testAccount.toString());
		
	}

	public StringProperty accountNameProperty() {
		// TODO Auto-generated method stub
		return this.accountName;
	}

	public DoubleProperty amountProperty() {
		// TODO Auto-generated method stub
		return this.amount;
	}

	public StringProperty accountNoProperty() {
		// TODO Auto-generated method stub
		return this.accountNo;
	}

	public StringProperty routingNoProperty() {
		// TODO Auto-generated method stub
		return this.routingNo;
	}

	public StringProperty dateProperty() {
		// TODO Auto-generated method stub
		return this.date;
	}

	public StringProperty typeProperty() {
		
		return this.typeOfAccount;
	}
	
	
	
}
