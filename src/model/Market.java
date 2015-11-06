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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Market {
   
	/**
	 * The list of tick symbols for each equity
	 */
   private ObservableList<StringProperty> tSymbols;
   /**
    * The list of equities existing in portfolio
    */
   private ObservableList<Equity> equities;
   /**
    * The name of the file used to set up the market
    */
   private String fileName;
   
   /**
    * Sets up the equity list in the market by a 
    * specified file name
    * @param fileName
    */
   public Market(String fileName) {
      
	  this.fileName = fileName;
      // fill in this object based on the filename passed
      this.tSymbols = FXCollections.observableArrayList();
      this.equities = FXCollections.observableArrayList();
      populateEquities();
   }
   
   /**
    * Adds an equity into the market
    * @param e
    */
   public void addEquity(Equity e) {
      
      this.tSymbols.add(new SimpleStringProperty(e.getTickSymbol()));
      this.equities.add(e);
   }

   /**
    * Returns the list of market equities
    * @return
    */
   public ObservableList<Equity> getMarketEquities(){
	   
	   return this.equities;
   }
   
   /**
    * Populates the equities into the market object
    */
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
   
   /**
    * The class used differentiate the equities 
    * for retrieval of DOW equities
    *
    */
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
   
   /**
    * Returns the dow iterator to seperate
    * dow equities
    * @return
    */
   public DowIterator getDowIterator() { return new DowIterator(equities); }
}
