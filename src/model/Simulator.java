//package model;
//
//import javafx.beans.property.DoubleProperty;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Scanner;
//import java.util.Stack;
//
///**
// * @author dxr5716 Daniel Roach
// */
//public class Simulator {
//
//    protected ArrayList<Equity> holdings;
//    protected Stack<Simulation> simulations;
//    protected StringProperty username;
////    protected DoubleProperty portfolioVal;
////    protected ObjectProperty<LocalDate> currentDate;
//
//    public Simulator(String username) {
//
//        this.username = new SimpleStringProperty(username);
//        this.holdings = new ArrayList<Equity>();
//
//        String fName = username.concat(".txt");
//
//        //
//        File data = new File(fName);
//        Scanner dataRead = null;
//        try {
//            dataRead = new Scanner(data);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        String line;
//        String[] temp;
//
//        while (dataRead.hasNextLine())
//        {
//            line = dataRead.nextLine();
//            line = line.replace("\"", "");
//            line = line.replace(", ", "");
//            temp = line.split(",");
//            ReadHoldingsContext readOwnedEquity = new ReadHoldingsContext(new ReadOwnedEquities());
//
//            if(temp[0].equals("!OWNED")){
//                Equity OwnedEquityInfo = (Equity) readOwnedEquity.executeStrategy(temp);
//                holdings.add(OwnedEquityInfo);
//            }
//        }
//
//        dataRead.close();
//
//        for(Equity obj: holdings)
//            obj.putSimulationOn();
//
//    }
//}
