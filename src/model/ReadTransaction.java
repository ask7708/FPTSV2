 package model;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Portfolio.AccountIterator;
import model.Portfolio.EquityIterator;

public class ReadTransaction {
	public ReadTransaction(){
		
	}
	
	//Transaction Format
	//Transaction id, Ticker (Equity or Account), Name of Equity/Account
	//CurrentValue, #Shares (amount for account), Date, bankName
	

	public Transaction readTransaction(String[] temp, ObservableList<Holdings> holdings ){
		Object reciever = null;
		Object transfer = null;
		AccountIterator Aitr = new AccountIterator(holdings);
		EquityIterator Eitr = new EquityIterator(holdings);
		if(temp[1] != "!BANK"){
			for(Eitr.first(); !Eitr.isDone(); Eitr.next()){
				if(Eitr.currentItem().getName() == temp[2]){
					if(Integer.parseInt(temp[4]) < 0){
						transfer = Eitr.currentItem();
						for(Aitr.first(); !Aitr.isDone(); Aitr.next()){
							if(Aitr.currentItem().getAccountName() == temp[6]){
								reciever = Aitr.currentItem();
							}
						}
					}
					else{
						reciever = Eitr.currentItem();
						for(Aitr.first(); !Aitr.isDone(); Aitr.next()){
							if(Aitr.currentItem().getAccountName() == temp[6]){
								transfer = Aitr.currentItem();
							}
						}
					}
				}
			}
		}
		else{
			for(Aitr.first(); !Aitr.isDone(); Aitr.next()){
				if(Aitr.currentItem().getAccountName() == temp[2]){
					if(Integer.parseInt(temp[4]) < 0){
						transfer = Aitr.currentItem();
						if(temp[6] == "User"){
							reciever = "User";
						}
						else{
							for(Aitr.first(); !Aitr.isDone(); Aitr.next()){
								if(Aitr.currentItem().getAccountName() == temp[6]){
									reciever = Aitr.currentItem();
								}
							}
						}
					}
					else{
						reciever = Aitr.currentItem();
						if(temp[6] == "User"){
							transfer = "User";
						}
						else{
							for(Aitr.first(); !Aitr.isDone(); Aitr.next()){
								if(Aitr.currentItem().getAccountName() == temp[6]){
									transfer = Aitr.currentItem();
								}
							}
						}
					}
				}
			}
		}
		
		Transaction data = new Transaction(temp[5], (Integer.parseInt(temp[4])*Integer.parseInt(temp[3])), reciever, transfer);
		return data;
	}
	
	public static void main(String args[]){
		String[] anArray = new String[7];
		anArray[0] = "!T";
		anArray[1] = "!BANK";
		anArray[2] = ":D";
		anArray[3] = "50";
		anArray[4] = "1";
		anArray[5] = "20151104";
		anArray[6] = "User";
		
		Account Hey = new BankAccount("Bank", ":D", 0, "20141104", "1162", "11234");
		Account Bye = new MarketAccount("Market", "D:", 0, "20141104", "1163", "11235");
		Equity Greetings = new Equity("GREET", "Communication", 50);
		ObservableList<Holdings> holdings = FXCollections.observableArrayList();
		holdings.add(Greetings);
		holdings.add(Hey);
		holdings.add(Bye);
		ReadTransaction stuff = new ReadTransaction();
		Transaction done = stuff.readTransaction(anArray, holdings);
		System.out.println(done.getAmount());
	}
}
