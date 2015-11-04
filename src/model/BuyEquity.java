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
	
	public BuyEquity(Equity boughtEq, Portfolio port){
		
		this.boughtEq = boughtEq;
		this.port = port;
		
	}
	
	public void execute(){
		
		port.getEquityList().add(this.boughtEq);
		
	}

	
	public String toString(){
		
		
		return "bought "+this.boughtEq.toString();
	}
	
	@Override
	public void undo() {
		
		ObservableList<Equity> findBoughtEquity =  port.getEquityList();
		ListIterator li = findBoughtEquity.listIterator(findBoughtEquity.size());

		// Iterate in reverse.
		while(li.hasPrevious()) {
			
			if((((Equity) li.previous()).getTickSymbol()) == boughtEq.getTickSymbol()){
				
				port.removeEquity(boughtEq.getTickSymbol());
			}
			
		  
		
		}
		
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
