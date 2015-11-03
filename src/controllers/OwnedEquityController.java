package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.stage.Stage;

import model.Equity;
import model.ReadHoldingsContext;
import model.ReadOwnedEquities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;


public class OwnedEquityController {
	
	
	@FXML
	private TableColumn<Equity, String> tickCol;
	@FXML
	private TableColumn<Equity, String> nameCol;
	@FXML
	private TableColumn<Equity, String> priceCol;
	@FXML
	private TableColumn<Equity, Number> shareCol;
	@FXML
	private TableColumn<Equity, String> dateCol;
	
	@FXML
	private TableView ownedTable;
	
	@FXML
	private Button importButton;
	
	@FXML
	private Button exportButton;
	
	ArrayList<Equity> ownedEquities = new ArrayList<Equity>();
	
	
	public void viewOwnedEquities() {

		//

		File data = new File("itnks.txt");
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
			ReadHoldingsContext readOwnedEquity = new ReadHoldingsContext(new ReadOwnedEquities());

			if (temp[0].equals("!OWNED")) {
				Equity OwnedEquityInfo = (Equity) readOwnedEquity.executeStrategy(temp);

				ownedEquities.add(OwnedEquityInfo);
				
			}

		}

		dataRead.close();

	}

	public ArrayList<Equity> getEquityArray() {

		return this.ownedEquities;
	}


	public ObservableList getTableData(){
		
		return ownedTable.getItems();
	}
	
	public void importEquities(){
		
		
		
		FileChooser fileChooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = fileChooser.showOpenDialog(null);

		if(selectedFile != null){
		   Scanner dataRead = null;
			try {
				dataRead = new Scanner(selectedFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String line;
			//StringBuffer sb = new StringBuffer();
			PrintWriter out;
			
			while (dataRead.hasNextLine())
			{
	     	line = dataRead.nextLine();
	     	//System.out.println(line);
	     	if(line.startsWith("\"!OWNED\"")){
	     		//sb.append(line+"\n");
	     		System.out.println("Import Equities");
	    		File writeFile = new File("itnks.txt");
	    		writeFile.setWritable(true);
	    		try {
					out = new PrintWriter(new BufferedWriter(new FileWriter("itnks.txt", true)));
					out.println(line);
					out.close();
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
	    		
	     	}
	     	
	     		
	     	}
			
			dataRead.close();
		}
		
		initialize();
		
	}
	
	public void exportEquities(){
		
		System.out.println("Export Clicked");
	}

	public static void main(String args[]) {

		OwnedEquityController evc = new OwnedEquityController();
		evc.viewOwnedEquities();
		System.out.println(evc.getEquityArray().get(1).toString());

	}

	@FXML
	public void initialize() {
		
		viewOwnedEquities();
		ObservableList<Equity> data = FXCollections.observableArrayList(getEquityArray());
		
		//data.add(new Equity("ASSD","Assistance Co.",12.37,23.48,"20151013"));
		//System.out.println(getEquityArray());
		
		ownedTable.getItems().clear();
		System.out.println("Before OwnedTable"+ownedTable.getItems());
		ownedTable.setItems(data);
		System.out.println("After OwnedTable"+ownedTable.getItems());
		
		tickCol.setCellValueFactory(cellData -> cellData.getValue().tickSymbolProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		priceCol.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());
		shareCol.setCellValueFactory(cellData -> cellData.getValue().sharesProperty());
		dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		
		
	}

}
