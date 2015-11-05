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

	private Equity soldEq;
	private Portfolio port;
	private Account acc;
	private double numberOfSharesSold;
	private double priceSoldAt;
	
	public SellEquity(Account acc, Equity soldEq, Portfolio port, double numberOfSharesSold){
		
		this.acc = acc;
		this.soldEq = soldEq;
		this.port = port;
		this.numberOfSharesSold = numberOfSharesSold;
		this.priceSoldAt = soldEq.getInitPrice();
		
	}
	
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
		
	

	
	public String toString(){
		
		
		return "Sold "+this.soldEq.toString();
	}
	
	@Override
	public void undo() {
		
		/*
		ObservableList<Equity> findBoughtEquity =  this.port.getEquityList();
		ListIterator li = findBoughtEquity.listIterator(findBoughtEquity.size());

		// Iterate in reverse.
		while(li.hasPrevious()) {
			
			if((((Equity) li.previous()).getTickSymbol()) == boughtEq.getTickSymbol()){
				
				port.removeEquity(boughtEq.getTickSymbol());
				this.acc.setAmount(this.acc.getAmount()+priceBoughtAt);
			}
			
		  
		
		}*/
		double backToOriginal = 0.0;
		backToOriginal = port.findEquity(this.soldEq.getTickSymbol()).getShares() + this.numberOfSharesSold;
		port.findEquity(this.soldEq.getTickSymbol()).setShares(backToOriginal);
		this.acc.setAmount(this.acc.getAmount()-(this.priceSoldAt*numberOfSharesSold));
		
		
		
	}

	@Override
	public void redo() {
		
		this.execute();
		
	}
	
	
	
	

}
