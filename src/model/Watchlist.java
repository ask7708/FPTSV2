package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Watchlist {
   
   /**
    * an observable list of equities the user is currently watching
    */
   private ObservableList<Equity> equities;
   
   /**
    * an observable list of triggers fired from the user's watchlist
    */
   private ObservableList<Trigger> triggersFired;
   
   /**
    * Constructs a Watchlist object by initializing the equities and triggers lists
    */
   public Watchlist() {
      
      this.equities = FXCollections.observableArrayList();
      this.triggersFired = FXCollections.observableArrayList();
   }
    
   /**
    * Adds an Equity to the watchlist
    * @param e the equity to be added
    */
   public void addEquity(Equity e) { this.equities.add(e); }
   
   /**
    * Adds a Trigger to the watchlist
    * @param trigger the trigger that was fired
    */
   public void addTrigger(Trigger trigger) { this.triggersFired.add(trigger); }
   
   /**
    * Returns the observable list of equities
    * @return the list of equities
    */
   public ObservableList<Equity> getEquities() { return this.equities; }
   
   /**
    * Returns the observable list of triggers
    * @return the list of triggers
    */
   public ObservableList<Trigger> getTriggersFired() { return this.triggersFired; }

   /**
    * Method to return an Equity object from the Watchlist
    * (could be useful in searching for equities)
    * @param tSymbol
    * @return the equity with the given ticker symbol, null if nothing found
    */
   public Equity findEquity(String tSymbol) {
      
      Equity e = null;
      
      for(Equity findThis : equities){
        
        if(findThis.getTickSymbol().equals(tSymbol))
           return findThis;
      }
      
      return e;
   }
}
