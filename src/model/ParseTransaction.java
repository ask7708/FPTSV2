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
	
	/*
	 * this functions takes a file that it will parse the information from
	 * Then once this information is parsed it will place the parsed String[] information into
	 * the given Observable list
	 * 
	 * file - the file the information is taken form
	 * ObservableList<String[]> - a list that contains file information
	 * 
	 * Pre-conditions = the file's transaction fromat must be correct
	 */
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
	
	/**
	 * This methods takes an observableList that contains the csv
	 * format and a string that is the file that is going to be written to
	 * then it will write the csv information to the file
	 * 
	 * @param allTransactions - a list of strings that represent transactions in csv format
	 * @param fileName - A string name of the file that will be written too
	 */
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
	
	/**
	 * This function is used to take an observable list of String[] that is generated using the
	 * toStringArray transaction function and then placing those arrays in an observable list
	 * This list is then looped through an they are all converted into a sring
	 * 
	 * @param Read - an observable list of string[] that will be converted into csv format
	 * @return - an observable list of Strings in csv format
	 */
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
	
	
	/**
	 * main methods for testing purposes
	 * @param args
	 */
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
