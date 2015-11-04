package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Watchlist {
   
   private ObservableList<Equity> equities;
   private ObservableList<String> triggersFired;
   
   public Watchlist() {
      
      this.equities = FXCollections.observableArrayList();
   }
      
   public void addEquity(Equity e) { this.equities.add(e); }
   
   public void addTrigger(String trigger) { this.triggersFired.add(trigger); }
   
   public ObservableList<Equity> getEquities() { return this.equities; }
   
   public ObservableList<String> getTriggersFired() { return this.triggersFired; }

}
