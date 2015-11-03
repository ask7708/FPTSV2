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

import app.App;
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
	private TableView<Equity> ownedTable;

	@FXML
	private Button importButton;
	
	@FXML
	private Button backButton;

	@FXML
	private Button exportButton;

	ObservableList<Equity> ownedEquities = FXCollections.observableArrayList();;

	private App application;

	public void readOwnedEquities(String user) {

		// System.out.println("UserName"+this.application.getUserName());
		File data = new File(user + ".txt");
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
		ownedTable.setItems(ownedEquities);

	}

	public ObservableList<Equity> getEquityArray() {

		return this.ownedEquities;
	}

	public ObservableList getTableData() {

		return ownedTable.getItems();
	}

	public void importEquities() {

		FileChooser fileChooser = new FileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			Scanner dataRead = null;
			try {
				dataRead = new Scanner(selectedFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String line;
			// StringBuffer sb = new StringBuffer();
			PrintWriter out;

			while (dataRead.hasNextLine()) {
				line = dataRead.nextLine();
				// System.out.println(line);
				if (line.startsWith("\"!OWNED\"")) {
					// sb.append(line+"\n");
					File writeFile = new File("itnks.txt");
					writeFile.setWritable(true);
					try {
						out = new PrintWriter(
								new BufferedWriter(new FileWriter(this.application.getUserName() + ".txt", true)));
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

	}

	public void exportEquities() {

		StringBuilder writeToFile = new StringBuilder();

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);
	

		PrintWriter outExport = null;

		try {
			outExport = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			for (Equity e : ownedEquities) {
				outExport.println("\"!OWNED\"," + e.toString());

			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		outExport.close();

	}

	public static void main(String args[]) {

		OwnedEquityController evc = new OwnedEquityController();
		evc.readOwnedEquities("itnks");
		System.out.println(evc.getEquityArray().get(1).toString());

	}

	@FXML
	public void initialize() {

		// data.add(new Equity("ASSD","Assistance Co.",12.37,23.48,"20151013"));
		// System.out.println(getEquityArray());
		/*
		 * ObservableList<Equity> data = getEquityArray();
		 * ownedTable.getItems().clear(); System.out.println("Before OwnedTable"
		 * +ownedTable.getItems()); ownedTable.setItems(data);
		 * System.out.println("After OwnedTable"+ownedTable.getItems());
		 */

		tickCol.setCellValueFactory(cellData -> cellData.getValue().tickSymbolProperty());
		nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		priceCol.setCellValueFactory(cellData -> cellData.getValue().initPriceProperty());
		shareCol.setCellValueFactory(cellData -> cellData.getValue().sharesProperty());
		dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

	}

	public void setMainApp(App app) {
		this.application = app;
	}

	public void backHandler() { this.application.showDashboardView(); }
	
}
