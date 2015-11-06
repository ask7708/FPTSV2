package model;

import java.util.ArrayList;
import java.util.ListIterator;

import javafx.collections.ObservableList;

/**
 * 
 */

/**
 * @author Arsh
 *
 */
public class BuyEquity implements TypeOfTransaction {

	/**
	 * The equity that is bought
	 */
	private Equity boughtEq;
	/**
	 * The portfolio the equity is bought from
	 */
	private Portfolio port;
	/**
	 * The account used to buy the equity
	 */
	private Account acc;
	/**
	 * The price the equity was bought at
	 */
	private double priceBoughtAt;
	/**
	 * The number of shares bought
	 */
	private double numberOfSharesBought;

	/**
	 * The constructor to create a buying object
	 * 
	 * @param acc
	 *            - account used to buy
	 * @param boughtEq
	 *            - bought equity
	 * @param port
	 *            - portfolio bought into
	 * @param numberOfSharesBought
	 *            - number of shares bought
	 */
	public BuyEquity(Account acc, Equity boughtEq, Portfolio port, double numberOfSharesBought) {

		this.acc = acc;
		this.boughtEq = boughtEq;
		this.port = port;
		this.priceBoughtAt = boughtEq.getInitPrice();
		this.numberOfSharesBought = numberOfSharesBought;

	}

	/**
	 * Executes the buy function. Buy the equity if it already hasnt been bought
	 * otherwise increases shares
	 */
	public void execute() {

		if (this.acc.getAmount() > (this.priceBoughtAt * this.numberOfSharesBought)) {

			this.acc.setAmount(this.acc.getAmount() - (this.priceBoughtAt * this.numberOfSharesBought));

			if (port.findEquity(this.boughtEq.getTickSymbol()) != null) {

				double totalShares = port.findEquity(this.boughtEq.getTickSymbol()).getShares()
						+ this.numberOfSharesBought;
				port.findEquity(this.boughtEq.getTickSymbol()).setShares(totalShares);
			} else {
				
				
				this.boughtEq.setShares(this.numberOfSharesBought);
				port.getEquityList().add(this.boughtEq);

			}

		}

	}

	/**
	 * Return the buy object as String
	 */
	public String toString() {

		return "bought " + this.boughtEq.toString();
	}

	/**
	 * Undoes the buying of an equity
	 */
	@Override
	public void undo() {

		double backToOriginal = 0.0;
		backToOriginal = port.findEquity(this.boughtEq.getTickSymbol()).getShares() - this.numberOfSharesBought;
		port.findEquity(this.boughtEq.getTickSymbol()).setShares(backToOriginal);
		this.acc.setAmount(this.acc.getAmount() + (this.priceBoughtAt * numberOfSharesBought));

	}

	/**
	 * Redoes the action of buying
	 */
	@Override
	public void redo() {

		this.execute();

	}

}
