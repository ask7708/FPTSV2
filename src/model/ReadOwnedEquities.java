/**
 * 
 */
package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.Equity;
import model.ReadHoldingsStrategy;

/**
 * @author Arsh
 *
 */
public class ReadOwnedEquities extends ReadHoldingsStrategy {

	@Override
	/**
	 * String[] temp -> the array of Strings that contian the parameters to create an owned Equity
	 * 
	 * creates an Equity object that has been made for a user (number of shares is there)
	 * using an array list that contains its parameters
	 */
	public Holdings readHolding(String[] temp) {
		String temp1 = temp[1]; //ticker Symbol
		String temp2 = temp[2]; //name
		double temp3 = Double.parseDouble(temp[3]); //Number of shares
		double temp4 = Double.parseDouble(temp[4]); //Price of Shares
		Equity EquityInfo = new Equity(temp1, temp2, temp3); //creating Equity
		for(int x = 6; x < temp.length; x++){
			EquityInfo.addAttribute(temp[x]); //adding the number of Sec according the length of the Equity
		}
		EquityInfo.setShares(temp4); //Setting number of shares
		EquityInfo.setTime(temp[5]);; //Setting the date 
		return EquityInfo;  //returning Equity
	}
	
	

}
