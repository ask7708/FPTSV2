package model;

import java.util.ArrayList;

public class Watchlist {
   
   private ArrayList<String> tickerSymbols;
   private ArrayList<Equity> equities;
   private ArrayList<Double> lastTradePrices;
   
   public Watchlist() {
      
      this.tickerSymbols = new ArrayList<String>();
      this.lastTradePrices = new ArrayList<Double>();
      this.equities = new ArrayList<Equity>();
   }
   
   public void addTickerSymbol(String tSym) { this.tickerSymbols.add(tSym); }
   
   // this method (and maybe class) may involve threading of some sort 
   public void getLastTradePrices() {
      
      lastTradePrices.clear();
      
   }
   
   public void addEquity(Equity e) { this.equities.add(e); }

}
