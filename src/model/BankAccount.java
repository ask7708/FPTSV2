/**
 * 
 */
package model;

/**
 * @author Arsh
 *
 */
public class BankAccount extends Account {

   /**
    * Constructor for a BankAccount object
    * @param accountName the account owner's name
    * @param amount the account's balance
    * @param date the date the account was made 
    * @param accountNum the account's identification number
    * @param routingNum the account's routing number
    */
	public BankAccount(String type, String accountName, double amount, String date, String accountNum, String routingNum) {

		super(type, accountName, amount, date, accountNum, routingNum);
		// TODO Auto-generated constructor stub
	}
	
	

}
