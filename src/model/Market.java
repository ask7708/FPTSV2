package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Market {
   
   private ObservableList<StringProperty> tSymbols;
   private ObservableList<Equity> equities;
   private String fileName;
   
   private static final String base = "http://query.yahooapis" +
         ".com/v1/public/yql?q=select%20LastTradePriceOnly%20from" +
         "%20yahoo.finance.quotes%20where%20symbol%20in%20(%22";
   
   private static final String end = "%22)&env=store://datatables" +
         ".org/alltableswithkeys";
   
   public Market(String fileName) {
      
	  this.fileName = fileName;
      // fill in this object based on the filename passed
      this.tSymbols = FXCollections.observableArrayList();
      this.equities = FXCollections.observableArrayList();
      populateEquities();
   }
   
   public void addEquity(Equity e) {
      
      this.tSymbols.add(new SimpleStringProperty(e.getTickSymbol()));
      this.equities.add(e);
   }

   
   public ObservableList<Equity> getMarketEquities(){
	   
	   return this.equities;
   }
   
   public void populateEquities(){
	   
	   //

		File data = new File(this.fileName);

       YahooStrategy company = new EquityStrategy();
       YahooContext context = new YahooContext(company);

		Scanner dataRead = null;

		try {
			dataRead = new Scanner(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line;
		String[] temp;

		while (dataRead.hasNextLine()) {
			line = dataRead.nextLine();
			line = line.replace("\"", "");
			line = line.replace(", ", " ");
			temp = line.split(",");
			ReadHoldingsContext readEquity = new ReadHoldingsContext(new ReadEquitiesRaw());
			Equity EquityInfo = (Equity) readEquity.executeStrategy(temp);
			//System.out.println(EquityInfo.getTickSymbol()+""+EquityInfo.getInitPrice());
			EquityInfo.setInitPrice((context.executeStrategy(EquityInfo.getTickSymbol())));
			equities.add(EquityInfo);
			

		}

		dataRead.close();

   }
   
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
   
   public static class DowIterator {
      
      private ObservableList<Equity> list;
      private Iterator<Equity> iter;
      private Equity first;
      private Equity current;
      
      public DowIterator(ObservableList<Equity> equities) { this.list = equities; }
      
      public Equity first() {
         
         if(first != null)
            return first;
         
         else {
            
            iter = list.iterator();
            
            while(iter.hasNext()) {
               
               try {
                  
                  Equity nextObj = iter.next();
                  
                  if(nextObj.getSectors().contains("DOW")) {
                  
                     first = nextObj;
                     current = nextObj;
                     return first;
                  }
                  
               } catch (NoSuchElementException e) {
                  current = null;
               }
            }
            
            current = null;
            return first;
         }
      }
      
      public boolean isDone() { return current == null; }
      
      public Equity currentItem() { return current; }
      
      public void next() {
         
         while(iter.hasNext()) {
            
            try {
               
               Equity nextObj = iter.next();
               
               if(nextObj.getSectors().contains("DOW")) {
                  
                  current = nextObj;
                  return;
               }
            } catch (NoSuchElementException e) {
               current = null;
            }
         }
         
         current = null;
      }
   }
   
   public DowIterator getDowIterator() { return new DowIterator(equities); }
}
