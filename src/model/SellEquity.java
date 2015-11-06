package model;
import java.util.ListIterator;

import javafx.collections.ObservableList;

/**
 * 
 */

/**
 * @author Arsh
 *
 */
public class SellEquity implements TypeOfTransaction{

	/**
	 * The equity that is sold
	 */
	private Equity soldEq;
	/**
	 * The portfolio the equity is sold
	 */
	private Portfolio port;
	/**
	 * The account used get the money
	 */
	private Account acc;
	/**
	 * The number of shares sold
	 */
	private double numberOfSharesSold;
	/**
	 * The price the equity is sold at
	 */
	private double priceSoldAt;
	
	/**
	 * The constructor to create selling an object
	 * 
	 * @param acc - account money is transferred to
	 * @param soldEq - the equity that is sold
	 * @param port - portfolio that sells the equity
	 * @param numberOfSharesSold - the number of shares sold
	 */
	public SellEquity(Account acc, Equity soldEq, Portfolio port, double numberOfSharesSold){
		
		this.acc = acc;
		this.soldEq = soldEq;
		this.port = port;
		this.numberOfSharesSold = numberOfSharesSold;
		this.priceSoldAt = soldEq.getInitPrice();
		
	}
	
	/**
	 * Executes the sell function. Sells the equity
	 * and add (shares*price) to chosen account
	 */
	public void execute(){
		
		
		
		if(port.findEquity(this.soldEq.getTickSymbol()) != null){
			
			if(port.findEquity(this.soldEq.getTickSymbol()).getShares() > numberOfSharesSold ){
			
			double totalShares = port.findEquity(this.soldEq.getTickSymbol()).getShares() - this.numberOfSharesSold;
			System.out.println(this.numberOfSharesSold);
			port.findEquity(this.soldEq.getTickSymbol()).setShares(totalShares);
			System.out.println(port.findEquity(this.soldEq.getTickSymbol()).getShares());
			System.out.println(port.findEquity(this.soldEq.getTickSymbol()));
			this.acc.setAmount(this.acc.getAmount()+(this.priceSoldAt * this.numberOfSharesSold));	
			
			}
			
			
		}
		
	}
		
	

	/**
	 * Returns the SellEquity object as a String
	 */
	public String toString(){
		
		
		return "Sold "+this.soldEq.toString();
	}
	
	/**
	 * Undoes selling an equity
	 */
	@Override
	public void undo() {
		

		double backToOriginal = 0.0;
		backToOriginal = port.findEquity(this.soldEq.getTickSymbol()).getShares() + this.numberOfSharesSold;
		port.findEquity(this.soldEq.getTickSymbol()).setShares(backToOriginal);
		this.acc.setAmount(this.acc.getAmount()-(this.priceSoldAt*numberOfSharesSold));
		
		
		
	}

	/**
	 * Redoes the buying of an equity
	 */
	@Override
	public void redo() {
		
		this.execute();
		
	}
	
	
	
	

}
