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
	
	public BuyEquity(Account acc, Equity boughtEq, Portfolio port){
		
		this.acc = acc;
		this.boughtEq = boughtEq;
		this.port = port;
		this.priceBoughtAt = boughtEq.getInitPrice();
		
	}
	
	public void execute(){
		
		if(this.acc.getAmount() > priceBoughtAt){
			
		this.acc.setAmount(this.acc.getAmount()-priceBoughtAt);	
		port.getEquityList().add(this.boughtEq);
		}
		
	}

	
	public String toString(){
		
		
		return "bought "+this.boughtEq.toString();
	}
	
	@Override
	public void undo() {
		
		ObservableList<Equity> findBoughtEquity =  this.port.getEquityList();
		ListIterator li = findBoughtEquity.listIterator(findBoughtEquity.size());

		// Iterate in reverse.
		while(li.hasPrevious()) {
			
			if((((Equity) li.previous()).getTickSymbol()) == boughtEq.getTickSymbol()){
				
				port.removeEquity(boughtEq.getTickSymbol());
				this.acc.setAmount(this.acc.getAmount()+priceBoughtAt);
			}
			
		  
		
		}
		
		
	}

	@Override
	public void redo() {
		
		this.execute();
		
	}
	
	
	
	
	
}
