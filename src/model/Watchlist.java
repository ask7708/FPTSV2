package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Watchlist {
   
   private ObservableList<Equity> equities;
   private ObservableList<Trigger> triggersFired;
   
   public Watchlist() {
      
      this.equities = FXCollections.observableArrayList();
      this.triggersFired = FXCollections.observableArrayList();
   }
      
   public void addEquity(Equity e) { this.equities.add(e); }
   
   public void addTrigger(Trigger trigger) { this.triggersFired.add(trigger); }
   
   public ObservableList<Equity> getEquities() { return this.equities; }
   
   public ObservableList<Trigger> getTriggersFired() { return this.triggersFired; }

   /**
    * Method to return an Equity object from the Market
    * (could be useful in searching for equities)
    * @param tSymbol
    * @return
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
