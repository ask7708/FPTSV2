package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParseTransaction {
	
	public ParseTransaction(){
		
	}
	
	public ObservableList<String[]> ParseFile(File file){
		ObservableList<String[]> allTransactions = FXCollections.observableArrayList();
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
	
	public void WriteList(ObservableList<String> allTransactions, String fileName){
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int x = 0; x < allTransactions.size(); x++){
			out.println(allTransactions.get(x));
		}
		out.close();
	}
	
	public ObservableList<String> ReadtoCSV(ObservableList<String[]> Read){
		//Transaction Format
		//Transaction id, Ticker (Equity or Account), Name of Equity/Account
		//CurrentValue, #Shares, Date, bankName
		String cutter = "\",\"";
		String ReadFinal = "";
		ObservableList<String> change = FXCollections.observableArrayList();
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
		ObservableList<String[]> data = parse.ParseFile(file);
		ObservableList<String> Data = parse.ReadtoCSV(data);
		parse.WriteList(Data, "Transaction.txt");
		
		Account hi = new BankAccount("hi", "hi", 22, "hi", "hi", "hi");
		Equity bye = new Equity("bye", "bye", 22);
		ObservableList<Holdings> holdings = FXCollections.observableArrayList();
		holdings.add(bye);
		holdings.add(hi);
		
		ReadTransaction stuffs = new ReadTransaction();
		
		Transaction stuff = stuffs.readTransaction(data.get(0),holdings);
		System.out.print(stuff.getType());
	}


}
