package controllers;

import app.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Interval;
import model.Simulation;
import model.SimulationType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public class AddNewSimController {


    @FXML
    private ChoiceBox<String> marketChoiceBox;
    @FXML
    private ChoiceBox<String> intervalChoiceBox;
    @FXML
    private ComboBox<Integer> timeStepComboBox;
    @FXML
    private TextField percentageTextField;

    @FXML
    private Button createNewSimButton;
    @FXML
    private Button cancelNewSimButton;

    private final ObservableList<String> marketChoices =
            FXCollections.observableArrayList(
                    "Bull-Market",
                    "Bear-Market",
                    "No-Growth Market"
            );

    private final ObservableList<String> intervalChoices =
            FXCollections.observableArrayList(
                    "DAY",
                    "MONTH",
                    "YEAR"
            );

    private Stage dialogStage;
    
    private ObservableList<Simulation> sims;

    @FXML
    private void initialize() {

        System.out.println("Sim is made null");
//        this.sim = null;
        marketChoiceBox.getItems().addAll(marketChoices);
        intervalChoiceBox.getItems().addAll(intervalChoices);

        ArrayList<Integer> intervalNumChoices = new ArrayList<Integer>();
        for (int i = 1; i <= 50; i++) { intervalNumChoices.add(i); }

        timeStepComboBox.getItems().addAll(intervalNumChoices);
    }

    @FXML
    private void handleCreatePressed() {

        SimulationType mType = null;
        Interval iType = null;
        Integer length;
        Double percent;

        switch (marketChoiceBox.getSelectionModel().getSelectedIndex()) {

            case 0:
                mType = SimulationType.BULL;
                break;
            case 1:
                mType = SimulationType.BEAR;
                break;
            case 2:
                mType = SimulationType.NONE;
                break;
        }

        switch (intervalChoiceBox.getSelectionModel().getSelectedIndex()) {

            case 0:
                iType = Interval.DAY;
                break;
            case 1:
                iType = Interval.MONTH;
                break;
            case 2:
                iType = Interval.YEAR;
                break;
        }

        length = timeStepComboBox.getSelectionModel().getSelectedItem();
        percent = Double.parseDouble(percentageTextField.getText());

        sims.add(new Simulation(mType, iType, length, percent, LocalDate.now()));
        dialogStage.close();
    }

    @FXML
    public void handleCancelPressed() { dialogStage.close(); }
    
    public void setDialogStage(Stage dialogStage) {

        this.dialogStage = dialogStage;
    }

    public void getSimulations(ObservableList<Simulation> sims) { this.sims = sims; }

}
