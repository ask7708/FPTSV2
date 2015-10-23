package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Portfolio {

   private ObservableList<Holdings> holdings;
   private ObservableList<Transaction> transactions;
   
   public Portfolio() {
      
      this.holdings = FXCollections.observableArrayList();
      this.transactions = FXCollections.observableArrayList();
   }
}
