package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Portfolio {

   private ObservableList<Holdings> holdings;
   private ObservableList<Transaction> transactions;
   private Watchlist watchlist;
   
   public Portfolio() {
      
      this.holdings = FXCollections.observableArrayList();
      this.transactions = FXCollections.observableArrayList();
      this.watchlist = new Watchlist();
   }
}
