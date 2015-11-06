/**
 * 
 */
package model;

/**
 * @author Arsh
 *
 */
public class ReadAccountHolding extends ReadHoldingsStrategy {

	/**
	 * Reads the string array for an account and returns the 
	 * respective type of account
	 */
	@Override
	public Holdings readHolding(String[] temp) {
		String temp1 = temp[1];
		double temp2 = Double.parseDouble(temp[2]);
		String temp3 = (temp[3]);
		String temp4 = (temp[4]);
		String temp5 = temp[5];
		
		Account AccountInfo = null;
		if(temp[0].equals("!MM")){
			AccountInfo = new MarketAccount("Money Market", temp1, temp2, temp5, temp4, temp3);
			return AccountInfo;
		}
		else{
			AccountInfo = new BankAccount("Bank Account",temp1, temp2, temp5, temp4, temp3);
			return AccountInfo;
		}
	}

}


