package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Market {
   
   private ObservableList<StringProperty> tSymbols;
   private ObservableList<Equity> equities;
   
   private static final String base = "http://query.yahooapis" +
         ".com/v1/public/yql?q=select%20LastTradePriceOnly%20from" +
         "%20yahoo.finance.quotes%20where%20symbol%20in%20(%22";
   
   private static final String end = "%22)&env=store://datatables" +
         ".org/alltableswithkeys";
   
   public Market(String filename) {
      
      // fill in this object based on the filename passed
      this.tSymbols = FXCollections.observableArrayList();
      this.equities = FXCollections.observableArrayList();
   }
   
   public void addEquity(Equity e) {
      
      this.tSymbols.add(new SimpleStringProperty(e.getTickSymbol()));
      this.equities.add(e);
   }

}
