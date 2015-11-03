import java.util.ListIterator;

import javafx.collections.ObservableList;
import model.Equity;
import model.Portfolio;

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
	
	public SellEquity(Equity soldEq, Portfolio port){
		
		this.soldEq = soldEq;
		this.port = port;
	}

	public String toString(){
		
		
		return "Sold "+this.soldEq.toString();
	}
	
	@Override
	public void execute() {
		
		ObservableList<Equity> findBoughtEquity =  port.getEquityList();
		ListIterator li = findBoughtEquity.listIterator(findBoughtEquity.size());
		
			for(int i = findBoughtEquity.size()-1; i >= 0; i--){
    		
    		if(findBoughtEquity.get(i).getTickSymbol() == soldEq.getTickSymbol()){
    			
    			port.removeEquity(soldEq.getTickSymbol());
    		}
    		
    	}
    	
			
	}
			
		  
	@Override
	public void undo() {
		
		this.port.getHoldings().add(this.soldEq);
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
