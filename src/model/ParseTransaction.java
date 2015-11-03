package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ParseTransaction {
	
	public ParseTransaction(){
		
	}
	
	public ArrayList<String[]> ParseFile(File file){
		ArrayList<String[]> allTransactions = new ArrayList<String[]>();
		String[] temp;
		Scanner numscan = null;
		String line;
		try {
			numscan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (numscan.hasNextLine()){
				
			line = numscan.nextLine();
			temp = line.split("\",\"");
			if(temp[0].equals("\"!T")){
				temp[6] = temp[6].replace("\"", "");
				allTransactions.add(temp);
			}
		}
		return allTransactions;
	}
	
	public static void WriteList(ArrayList<String> allTransactions){
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("Transaction.txt", true)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int x = 0; x < allTransactions.size(); x++){
			out.println(allTransactions.get(x));
		}
		out.close();
	}
	
	public static ArrayList<String> ReadtoCSV(ArrayList<String[]> Read){
		//Transaction Format
		//Transaction id, Ticker (Equity or Account), Name of Equity/Account
		//CurrentValue, #Shares, Date, bankName
		String cutter = "\",\"";
		String ReadFinal = "";
		ArrayList<String> change = new ArrayList<String>();
		for(int x = 0; x < Read.size(); x++){
			ReadFinal = Read.get(x)[0] + cutter + Read.get(x)[1] + cutter + Read.get(x)[2] + cutter + Read.get(x)[3] +
					cutter + Read.get(x)[4] + cutter + Read.get(x)[5] + cutter + Read.get(x)[6];
			change.add(ReadFinal);
		}
		return change;
		
	}
	
	

	public static void main(String[] args){
		File file = new File("Transaction.txt");
		ParseTransaction parse = new ParseTransaction();
		ArrayList<String[]> data = parse.ParseFile(file);
		ArrayList<String> Data = ReadtoCSV(data);
		WriteList(Data);
		
		Account hi = new BankAccount("hi", "hi", 22, "hi", "hi", "hi");
		Equity bye = new Equity("bye", "bye", 22);
		ArrayList<Equity> Equities = new ArrayList<Equity>();
		ArrayList<Account> accounts = new ArrayList<Account>();
		Equities.add(bye);
		accounts.add(hi);
		
		ReadTransaction stuffs = new ReadTransaction();
		
		Transaction stuff = stuffs.readTransaction(data.get(0), Equities, accounts);
		System.out.print(stuff.getType());
	}


}
