package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author dxr5716 Daniel Roach
 * @author ask7708 Arshdeep Khalsa
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
	 * A list containing the equity's market indices and industry sectors (if it
	 * has one)
	 */
	private ArrayList<String> attributes;

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

	/**
	 * The null constructor for equities
	 */
	public Equity() {
		this(null, null, 0.0);
	}

	/**
	 * Creates a equity object
	 * 
	 * @param tickSymbol
	 *            - the ticker symbol of the equity
	 * @param name
	 *            - the name of the equity
	 * @param eqPrice
	 *            - the price of the equity
	 */
	public Equity(String tickSymbol, String name, double eqPrice) {

		this.name = new SimpleStringProperty(name);
		this.tSymbol = new SimpleStringProperty(tickSymbol);
		this.initPrice = new SimpleDoubleProperty(eqPrice);

		// dummy / default values
		this.shares = new SimpleDoubleProperty(0.0);
		this.attributes = new ArrayList<String>();
		this.priceChanges = null;
		this.date = new SimpleStringProperty("");

		this.highTrigger = new SimpleDoubleProperty(0.0);
		this.lowTrigger = new SimpleDoubleProperty(0.0);

	}

	/**
	 * Creates an equity object
	 * 
	 * @param tickSymbol
	 *            - the ticker symbol for the equity
	 * @param name
	 *            - the name of the equity
	 * @param eqPrice
	 *            - the price of the equity
	 * @param shares
	 *            - the number of shares for the equity
	 * @param date
	 *            - the date of the equity retrieved
	 */
	public Equity(String tickSymbol, String name, double eqPrice, double shares, String date) {

		this.name = new SimpleStringProperty(name);
		this.tSymbol = new SimpleStringProperty(tickSymbol);
		this.initPrice = new SimpleDoubleProperty(eqPrice);

		// dummy / default values
		this.shares = new SimpleDoubleProperty(shares);
		this.attributes = new ArrayList<String>();
		this.priceChanges = null;
		this.date = new SimpleStringProperty(date);

		this.highTrigger = new SimpleDoubleProperty(0.0);
		this.lowTrigger = new SimpleDoubleProperty(0.0);

	}

	/** Getters And Setters for name property (JavaFX style) **/
	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return this.name;
	}

	/** Getters and Setters for ticker symbol property (JavaFX style) **/
	public String getTickSymbol() {
		return this.tSymbol.get();
	}

	public void setTickSymbol(String tickSymbol) {
		this.tSymbol.set(tickSymbol);
	}

	public StringProperty tickSymbolProperty() {
		return this.tSymbol;
	}

	/** Getters and Setters for shares property (JavaFX style) **/
	public double getShares() {
		return this.shares.get();
	}

	public void setShares(double shares) {
		this.shares.set(shares);
	}

	public DoubleProperty sharesProperty() {
		return this.shares;
	}

	/** Getters and Setters for initPrice property (JavaFX style) **/
	public double getInitPrice() {
		return this.initPrice.get();
	}

	public void setInitPrice(double price) {
		this.initPrice.set(price);
	}

	/** Getters and Setters for highTrigger property (JavaFX style) **/
	public double getHighTrigger() {
		return this.highTrigger.get();
	}

	public void setHighTrigger(double ht) {
		this.highTrigger.set(ht);
	}

	public StringProperty highTriggerProperty() {

		// return this.highTrigger;
		return new SimpleStringProperty("$" + String.format("%.02f", highTrigger.get()));
	}

	/** Getters and Setters for lowTrigger property (JavaFX style) **/
	public double getLowTrigger() {
		return this.lowTrigger.get();
	}

	public void setLowTrigger(double lt) {
		this.lowTrigger.set(lt);
	}

	/**
	 * Returns the low trigger as StringProperty
	 */
	public StringProperty lowTriggerProperty() {

		// return this.lowTrigger;
		return new SimpleStringProperty("$" + String.format("%.02f", lowTrigger.get()));
	}

	/**
	 * Returns the date the equity was retrieved
	 * 
	 * @return
	 */
	public String getTime() {
		return this.date.get();
	}

	/**
	 * Sets the time the equity was retrieved
	 * 
	 * @param time
	 */
	public void setTime(String time) {
		this.date.set(time);
	}

	/**
	 * Returns the sectors the equity has
	 * 
	 * @return
	 */
	public ArrayList<String> getSectors() {
		return this.attributes;
	}

	/**
	 * Returns the date as StringProperty
	 * 
	 * @return
	 */
	public StringProperty dateProperty() {
		// TODO Auto-generated method stub
		return this.date;
	}

	/**
	 * Adds the attribute to the sector or indexes
	 * 
	 * @param Attribute
	 */
	public void addAttribute(String Attribute) {
		this.attributes.add(Attribute);
	}

	/**
	 * Returns the price of the equity
	 * 
	 * @return
	 */
	public StringProperty initPriceProperty() {

		// return this.initPrice;
		return new SimpleStringProperty("$" + String.format("%.02f", initPrice.get()));
	}

	/**
	 * Turns the simulation on
	 */
	public void putSimulationOn() {

		this.priceChanges = new Stack<DoubleProperty>();
	}

	/**
	 * Turns the simulation off
	 */
	public void putSimulationOff() {

		this.priceChanges.removeAllElements();
		this.priceChanges = null;
	}

	/**
	 * Adds the price changed to the price changes stack
	 * 
	 * @param newPrice
	 */
	public void addPriceChange(double newPrice) {

		DoubleProperty price = new SimpleDoubleProperty(newPrice);
		this.priceChanges.push(price);
	}

	/**
	 * Removes the price change from the stack
	 */
	public void removePriceChange() {

		if (this.priceChanges != null) {

			if (!this.priceChanges.empty())
				this.priceChanges.pop();
		}
	}

	/**
	 * Returns the simulated price property as a String
	 * 
	 * @return
	 */
	public StringProperty simPriceProperty() {

		System.out.println("Getting the simulation price");
		StringProperty simPrice = new SimpleStringProperty("$" + String.format("%.02f", initPrice.doubleValue()));

		if (this.priceChanges != null) {

			if (!this.priceChanges.empty())
				simPrice = new SimpleStringProperty("$" + String.format("%.02f", priceChanges.peek().doubleValue()));
		}

		return simPrice;
	}

	/**
	 * Returns the simulated price of the equity
	 * 
	 * @return
	 */
	public double getSimPrice() {

		Double simPrice = getInitPrice();

		if (this.priceChanges != null) {

			if (!this.priceChanges.empty())
				simPrice = priceChanges.peek().doubleValue();
		}

		return simPrice;
	}

	/**
	 * Creates a toString method to print out an Equity Object
	 * 
	 * @param the
	 *            String representation of Equity
	 */
	public String toString() {

		String newS = new String();
		newS += "\"" + this.getTickSymbol() + "\"";
		newS += ",\"" + this.getName() + "\"";
		newS += ",\"" + (this.getInitPrice() + "\",");
		if (this.getShares() != 0.0) {
			newS += "\"" + (this.getShares() + "\",");
		}
		if (this.getTime() != null) {
			newS += (this.getTime() + ",");
		}

		ArrayList<String> sectors = this.getSectors();

		if (!sectors.isEmpty()) {

			for (String o : sectors) {
				newS += "\"" + o + "\"" + ", ";
			}
		}

		newS += "\n";
		newS = newS.substring(0, newS.length() - 3);
		return newS;
	}

}
