package model;

import java.util.ArrayList;
import java.util.ListIterator;

import javafx.collections.ObservableList;

/**
 * 
 */

/**
 * @author Arsh
 *
 */
public class BuyEquity implements TypeOfTransaction {

	private Equity boughtEq;
	private Portfolio port;
	private Account acc;
	private double priceBoughtAt;
	private double numberOfSharesBought;
	
	public BuyEquity(Account acc, Equity boughtEq, Portfolio port, double numberOfSharesBought){
		
		this.acc = acc;
		this.boughtEq = boughtEq;
		this.port = port;
		this.priceBoughtAt = boughtEq.getInitPrice();
		this.numberOfSharesBought = numberOfSharesBought;
		
	}
	
	public void execute(){
		
		if(this.acc.getAmount() > (this.priceBoughtAt * this.numberOfSharesBought )){
			
		this.acc.setAmount(this.acc.getAmount()-(this.priceBoughtAt * this.numberOfSharesBought));	
		
		if(port.findEquity(this.boughtEq.getTickSymbol()) != null){
			
			
			double totalShares = port.findEquity(this.boughtEq.getTickSymbol()).getShares() + this.numberOfSharesBought;
			System.out.println(this.numberOfSharesBought);
			port.findEquity(this.boughtEq.getTickSymbol()).setShares(totalShares);
			System.out.println(port.findEquity(this.boughtEq.getTickSymbol()).getShares());
			System.out.println(port.findEquity(this.boughtEq.getTickSymbol()));
		}else{
		
		port.getEquityList().add(this.boughtEq);

		}
		
	}
		
	}

	
	public String toString(){
		
		
		return "bought "+this.boughtEq.toString();
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
		backToOriginal = port.findEquity(this.boughtEq.getTickSymbol()).getShares() - this.numberOfSharesBought;
		port.findEquity(this.boughtEq.getTickSymbol()).setShares(backToOriginal);
		this.acc.setAmount(this.acc.getAmount()+(this.priceBoughtAt*numberOfSharesBought));
		
		
		
	}

	@Override
	public void redo() {
		
		this.execute();
		
	}
	
	
	
	
	
}
