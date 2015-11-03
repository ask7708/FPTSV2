package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Portfolio {



   private ObservableList<Equity> equityList;
   private ObservableList<Transaction> transactions;
   private Watchlist watchlist;
   
   public Portfolio() {
      
      this.equityList = FXCollections.observableArrayList();
      this.transactions = FXCollections.observableArrayList();
      this.watchlist = new Watchlist();
   }
   
   
   
   public ObservableList<Equity> getHoldings() { return this.equityList; }

    public void removeEquity(String tickSymbol) {
	
    	for(int i = this.equityList.size()-1; i >= 0; i--){
    		
    		if(this.equityList.get(i).getTickSymbol() == tickSymbol){
    			
    			this.equityList.remove(i);
    		}
    		
    	}
    	
	
    }
}
