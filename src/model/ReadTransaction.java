 package model;

import java.util.ArrayList;

public class ReadTransaction {
	public ReadTransaction(){
		
	}
	
	//Transaction Format
	//Transaction id, Ticker (Equity or Account), Name of Equity/Account
	//CurrentValue, #Shares (amount for account), Date, bankName
	
	
	public Transaction readTransaction(String[] temp, ArrayList<Equity> Equities, ArrayList<Account> Accounts){
		Object reciever = null;
		Object transfer = null;
		if(temp[1] != "!BANK"){
			for(int i = 0; i < Equities.size(); i++){
				if(Equities.get(i).getName() == temp[2]){
					if(Integer.parseInt(temp[4]) < 0){
						transfer = Equities.get(i);
						for(int j = 0; j < Accounts.size(); j++){
							if(Accounts.get(j).getAccountName() == temp[6]){
								reciever = Accounts.get(j);
							}
						}
					}
					else{
						reciever = Equities.get(i);
						for(int j = 0; j < Accounts.size(); j++){
							if(Accounts.get(j).getAccountName() == temp[6]){
								transfer = Accounts.get(j);
							}
						}
					}
				}
			}
		}
		else{
			for(int j = 0; j < Accounts.size(); j++){
				if(Accounts.get(j).getAccountName() == temp[2]){
					if(Integer.parseInt(temp[4]) < 0){
						transfer = Accounts.get(j);
						if(temp[6] == "User"){
							reciever = "User";
						}
						else{
							for(int i = 0; i < Accounts.size(); i++){
								if(Accounts.get(i).getAccountName() == temp[6]){
									reciever = Accounts.get(i);
								}
							}
						}
					}
					else{
						reciever = Accounts.get(j);
						if(temp[6] == "User"){
							transfer = "User";
						}
						else{
							for(int i = 0; i < Accounts.size(); i++){
								if(Accounts.get(i).getAccountName() == temp[6]){
									transfer = Accounts.get(i);
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
		ArrayList<Equity> Equities = new ArrayList<Equity>();
		ArrayList<Account> Accounts = new ArrayList<Account>();
		Equities.add(Greetings);
		Accounts.add(Hey);
		Accounts.add(Bye);
		ReadTransaction stuff = new ReadTransaction();
		Transaction done = stuff.readTransaction(anArray, Equities, Accounts);
		System.out.println(done);
	}
}
