package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author dxr5716 Daniel Roach
 */
public class Equity implements Holdings {

    /**
     * the equity's name
     */
    private final StringProperty name;

    /**
     * the equity's ticker symbol
     */
    private final StringProperty tSymbol;

    /**
     * Number of shares the user holds
     *
     */
    private final DoubleProperty shares;

    /**
     * The price of the equity
     */
    private final DoubleProperty initPrice;

    /**
     * A list containing the equity's market indices and industry sectors (if
     * it has one)
     */
    private ArrayList<StringProperty> attributes;

    /**
     * The date the Equity was acquired
     */
    private final StringProperty date;

    /**
     * a stack used to simulate price changes
     */
    private Stack<DoubleProperty> priceChanges;

    /**
     * the high trigger price 
     */
    private DoubleProperty highTrigger;
    
    /**
     * the low trigger price
     */
    private DoubleProperty lowTrigger;
    
    public Equity() { this(null, null, 0.0); }

    public Equity(String name, String tickSymbol, double eqPrice) {

        this.name = new SimpleStringProperty(name);
        this.tSymbol = new SimpleStringProperty(tickSymbol);
        this.initPrice = new SimpleDoubleProperty(eqPrice);

        // dummy / default values
        this.shares = new SimpleDoubleProperty(0.0);
        this.attributes = new ArrayList<StringProperty>();
        this.priceChanges = null;
        this.date = new SimpleStringProperty("");

    }

    /** Getters And Setters for name property (JavaFX style) **/
    public String getName() { return this.name.get(); }

    public void setName(String name) { this.name.set(name); }

    public StringProperty nameProperty() { return this.name; }

    /** Getters and Setters for ticker symbol property (JavaFX style) **/
    public String getTickSymbol() { return this.tSymbol.get(); }

    public void setTickSymbol(String tickSymbol) { this.tSymbol.set(tickSymbol);}

    public StringProperty tickSymbolProperty() { return this.tSymbol; }

    /** Getters and Setters for shares property (JavaFX style) **/
    public double getShares() { return this.shares.get(); }

    public void setShares(double shares) {this.shares.set(shares);}

    public DoubleProperty sharesProperty(){ return this.shares; }

    /** Getters and Setters for initPrice property (JavaFX style) **/
    public double getInitPrice() { return this.initPrice.get(); }

    public void setInitPrice(double price) { this.initPrice.set(price); }
    
    public String getTime(){ return this.date.get();}
    
    public void setTime(String time) { this.date.set(time);}
    
    public ArrayList<StringProperty> getAttributes(){
    	return this.attributes;
    }
    
    public void addAttribute(String Attribute){
    	SimpleStringProperty Attributes = new SimpleStringProperty("Attribute");
    	getAttributes().add(Attributes);
    }

    public StringProperty initPriceProperty() { 
       
       //return this.initPrice;
       return new SimpleStringProperty("$" + String.format("%.02f", initPrice.get()));
    }

    public void putSimulationOn() {

        this.priceChanges = new Stack<DoubleProperty>();
    }

    public void putSimulationOff() {

        this.priceChanges.removeAllElements();
        this.priceChanges = null;
    }

    public void addPriceChange(double newPrice) {

        DoubleProperty price = new SimpleDoubleProperty(newPrice);
        this.priceChanges.push(price);
    }

    public void removePriceChange() {

        if(this.priceChanges != null) {

            if(!this.priceChanges.empty())
                this.priceChanges.pop();
        }
    }

    public StringProperty simPriceProperty() {

       System.out.println("Getting the simulation price");
       StringProperty simPrice = new SimpleStringProperty("$" + 
             String.format("%.02f", initPrice.doubleValue()));

        if(this.priceChanges != null) {

            if(!this.priceChanges.empty())
               simPrice = new SimpleStringProperty("$" + 
                       String.format("%.02f", priceChanges.peek().doubleValue()));
        }

        return simPrice;
    }

    public double getSimPrice() {

        Double simPrice = getInitPrice();

        if(this.priceChanges != null) {

        if(!this.priceChanges.empty())
            simPrice = priceChanges.peek().doubleValue();
    }

        return simPrice;
    }
}
