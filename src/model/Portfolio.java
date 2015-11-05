package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Portfolio {


   private ObservableList<Holdings> holdings;
   private ObservableList<Account> accounts;
   private ObservableList<Equity> equityList;
   private ObservableList<Transaction> transactions;
   private Watchlist watchlist;
   private String userName;

   public Portfolio(String userName) {
      
      this.equityList = FXCollections.observableArrayList();
      this.accounts = FXCollections.observableArrayList();
      this.holdings = FXCollections.observableArrayList();
      this.transactions = FXCollections.observableArrayList();
      this.watchlist = new Watchlist();
      this.userName = userName;
      populateAccountsFromFile();
      populateEquitiesFromFile();
      populateTransactions();
   }

   
   public ObservableList<Transaction> getTransactions(){
	   return this.transactions;
   }
   
   public void addTransaction(Transaction data){
	   this.transactions.add(data);
   }
   
   public void populateTransactions(){
	   ParseTransaction data = new ParseTransaction();
	      String usernameFile = userName + ".txt";
	      File userFile = new File(usernameFile);
	      ArrayList<String[]> info = data.ParseFile(userFile);
	      for(int i = 0; i < info.size(); i++){
	    	  Transaction temp = new Transaction(info.get(i));
	    	  transactions.add(temp);
	      }
   }
   
   public ObservableList<Equity> getEquityList() { return this.equityList; }

    public void removeEquity(String tickSymbol) {
	
    	for(int i = this.equityList.size()-1; i >= 0; i--){
    		
    		if(this.equityList.get(i).getTickSymbol() == tickSymbol){
    			
    			this.equityList.remove(i);
    		}
    		
    	}
    	
	
    }
    
    public void setEquityList(ObservableList<Equity> eqList){
    	
    	this.equityList= eqList;
    }

   public ObservableList<Holdings> getHoldings() { return this.holdings; }

   public static class EquityIterator {

      private ObservableList<Holdings> list;
      private Iterator<Holdings> iter;
      private Equity first;
      private Equity current;

      public EquityIterator(ObservableList<Holdings> holdings) { this.list = holdings; }

      public Equity first() {

         if(first != null)
            return first;

         else {

            iter = list.iterator();

            while(iter.hasNext()) {

               try {

                  Holdings nextObj = iter.next();

                  if(nextObj instanceof Equity) {

                     first = (Equity) nextObj;
                     current = (Equity) nextObj;
                     return first;
                  }

               } catch (NoSuchElementException e) {
                  current = null;
               }
            }
            return first;
         }
      }

      public boolean isDone() { return current == null; }

      public Equity currentItem() { return current; }

      public void next() {

         while(iter.hasNext()) {

            try {

               Holdings nextObj = iter.next();

               if(nextObj instanceof Equity) {

                  current = (Equity) nextObj;
                  return;
               }

            } catch (NoSuchElementException e) {
               current = null;
            }
         }
         current = null;
      }
   }

   public static class AccountIterator {

      private ObservableList<Holdings> list;
      private Iterator<Holdings> iter;
      private Account first;
      private Account current;

      public AccountIterator(ObservableList<Holdings> holdings) { this.list = holdings; }

      public Account first() {

         if(first != null)
            return first;

         else {
            iter = list.iterator();

            while(iter.hasNext()) {

               try {

                  Holdings nextObj = iter.next();

                  if(nextObj instanceof Account) {

                     first = (Account) nextObj;
                     current = (Account) nextObj;
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

      public Account currentItem() { return current; }

      public void next() {

         while(iter.hasNext()) {

            try {

               Holdings nextObj = iter.next();

               if(nextObj instanceof Account) {

                  current = (Account) nextObj;
                  return;
               }

            } catch (NoSuchElementException e) {
               current = null;
            }
         }

         current = null;
      }
   }

   public EquityIterator getEquityIterator() { return new EquityIterator(holdings); }

   public AccountIterator getAccountIterator() { return new AccountIterator(holdings); }

   public static void main(String[] args) {

      Portfolio myPortfolio = new Portfolio("itnks");
      ObservableList<Holdings> holdings = FXCollections.observableArrayList();

      holdings.add(new MarketAccount("!MM", "First Element", 123.45, "11/2/15", "0101010", "5434512"));
      holdings.add(new BankAccount("!BANK", "Second Element", 500.21, "11/2/15", "0101010", "5434512"));
      holdings.add(new Equity("3RD", "Third Element", 200.15));
      holdings.add(new Equity("4TH", "Fourth Element", 243.00));
      holdings.add(new Equity("5TH", "Fifth Element", 500.00));
      holdings.add(new MarketAccount("!MM", "Sixth Element", 1500.21, "11/2/15", "0101010", "5434512"));
      holdings.add(new Equity("7TH", "Seventh Element", 1000.00));
      holdings.add(new Equity("8TH", "Eighth Element", 550.00));
      holdings.add(new BankAccount("!BANK", "Ninth Element", 300.21, "11/2/15", "0101010", "5434512"));
      holdings.add(new Equity("10TH", "Tenth Element", 1000.00));

      myPortfolio.holdings = holdings;

      AccountIterator aIter = myPortfolio.getAccountIterator();
      EquityIterator eIter = myPortfolio.getEquityIterator();

      System.out.println("AccountIterator (only the 1st, 2nd, 6th, and 9th element should be shown)");

      for(aIter.first(); !aIter.isDone(); aIter.next())
         System.out.println(aIter.currentItem());

      System.out.println("\nEquityIterator (only the 3rd, 4th, 5th, 7th, 8th, and 10th element should be shown)");

      for(eIter.first(); !eIter.isDone(); eIter.next())
         System.out.println(eIter.currentItem());

   }

   
   	public void populateAccountsFromFile(){
   		
   		
   			File data = new File(this.userName+".txt");
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
   				line = line.replace(", ", "");
   				temp = line.split(",");
   				ReadHoldingsContext readAccountHolding = new ReadHoldingsContext(new ReadAccountHolding());

   				if (temp[0].equals("!MM") || temp[0].equals("!BANK")) {
   					
   					Account accountInfo = null;

   					accountInfo = (Account) readAccountHolding.executeStrategy(temp);
   					
   					accounts.add(accountInfo);
   					
   				}

   			}

   			dataRead.close();
   	
   			
   	}
   	
   	public void populateEquitiesFromFile(){
   		

   			YahooStrategy company = new EquityStrategy();
   			YahooContext context = new YahooContext(company);
   		
   			File data = new File(userName+".txt");
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
   				ReadHoldingsContext readOwnedEquity = new ReadHoldingsContext(new ReadOwnedEquities());

   				if (temp[0].equals("!OWNED")) {
   					Equity OwnedEquityInfo = (Equity) readOwnedEquity.executeStrategy(temp);

   					OwnedEquityInfo.setInitPrice((context.executeStrategy(OwnedEquityInfo.getTickSymbol())));
   					equityList.add(OwnedEquityInfo);

   				}

   			}

   			dataRead.close();
   			
   		
   			
   	}


   	public ObservableList<Account> getAccounts() {
   		return this.accounts;
   	}




   	public void setAccounts(ObservableList<Account> accounts) {
   		this.accounts = accounts;
   	}
   	

    public Equity findEquity(String tSymbol) {
        
        Equity e = null;
        
        for(Equity findThis : equityList){
      	  
      	  if(findThis.getTickSymbol().equals(tSymbol))
      		  return findThis;
        }
        
        return e;
     }
   	
}
