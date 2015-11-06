package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Portfolio {

	/**
	 * The holdings of the portfolio
	 */
	private ObservableList<Holdings> holdings;
	/**
	 * The accounts of the portfolio
	 */
	private ObservableList<Account> accounts;
	/**
	 * List of equities in portfolio
	 */
	private ObservableList<Equity> equityList;
	/**
	 * The list of transactions
	 */
	private ObservableList<Transaction> transactions;
	/**
	 * The watchlist for the portfolio
	 */
	private Watchlist watchlist;
	/**
	 * The username of the logged in User
	 */
	private String userName;

	/**
	 * The Portfolio constructor which loads in respective holdings and
	 * transaction fromt text file
	 * 
	 * @param userName
	 */
	public Portfolio(String userName) {

		this.equityList = FXCollections.observableArrayList();
		this.accounts = FXCollections.observableArrayList();
		this.holdings = FXCollections.observableArrayList();
		this.transactions = FXCollections.observableArrayList();
		this.watchlist = new Watchlist();
		this.userName = userName;
		populateAccountsFromFile();
		populateEquitiesFromFile();
		populateTransactions();
	}

	/**
	 * Returns the list of transactions
	 * @return
	 */
	public ObservableList<Transaction> getTransactions() {
		return this.transactions;
	}

	/**
	 * Adds a transaction to the list
	 * @param data
	 */
	public void addTransaction(Transaction data) {
		this.transactions.add(data);
	}

	/**
	 * Reads in the transactions to the list
	 */
	public void populateTransactions() {
		ParseTransaction data = new ParseTransaction();
		String usernameFile = userName + ".txt";
		File userFile = new File(usernameFile);
		ObservableList<String[]> info = data.ParseFile(userFile);
		for (int i = 0; i < info.size(); i++) {
			Transaction temp = new Transaction(info.get(i));
			transactions.add(temp);
		}
	}

	/**
	 * Returns the list of equities persisted in portfolio
	 * @return
	 */
	public ObservableList<Equity> getEquityList() {
		return this.equityList;
	}

	/**
	 * Removes a specific equity
	 * @param tickSymbol
	 */
	public void removeEquity(String tickSymbol) {

		for (int i = this.equityList.size() - 1; i >= 0; i--) {

			if (this.equityList.get(i).getTickSymbol() == tickSymbol) {

				this.equityList.remove(i);
			}

		}

	}

	/**
	 * Sets the equity list to a specified list
	 * @param eqList
	 */
	public void setEquityList(ObservableList<Equity> eqList) {

		this.equityList = eqList;
	}

	/**
	 * Returns the holdings of the portfolio
	 * @return
	 */
	public ObservableList<Holdings> getHoldings() {
		return this.holdings;
	}

	/**
	 * Differentiates the holdings and helps
	 * specify which are equities
	 * @author Daniel R., Arsh K., Talal A., Daniel C.
	 *
	 */
	public static class EquityIterator {

		private ObservableList<Holdings> list;
		private Iterator<Holdings> iter;
		private Equity first;
		private Equity current;

		public EquityIterator(ObservableList<Holdings> holdings) {
			this.list = holdings;
		}

		public Equity first() {

			if (first != null)
				return first;

			else {

				iter = list.iterator();

				while (iter.hasNext()) {

					try {

						Holdings nextObj = iter.next();

						if (nextObj instanceof Equity) {

							first = (Equity) nextObj;
							current = (Equity) nextObj;
							return first;
						}

					} catch (NoSuchElementException e) {
						current = null;
					}
				}
				return first;
			}
		}

		public boolean isDone() {
			return current == null;
		}

		public Equity currentItem() {
			return current;
		}

		public void next() {

			while (iter.hasNext()) {

				try {

					Holdings nextObj = iter.next();

					if (nextObj instanceof Equity) {

						current = (Equity) nextObj;
						return;
					}

				} catch (NoSuchElementException e) {
					current = null;
				}
			}
			current = null;
		}
	}

	/**
	 * Differentiates the holdings and helps
	 * specify which are accounts
	 * @author Daniel R., Arsh K., Talal A., Daniel C.
	 *
	 */
	public static class AccountIterator {

		private ObservableList<Holdings> list;
		private Iterator<Holdings> iter;
		private Account first;
		private Account current;

		public AccountIterator(ObservableList<Holdings> holdings) {
			this.list = holdings;
		}

		public Account first() {

			if (first != null)
				return first;

			else {
				iter = list.iterator();

				while (iter.hasNext()) {

					try {

						Holdings nextObj = iter.next();

						if (nextObj instanceof Account) {

							first = (Account) nextObj;
							current = (Account) nextObj;
							return first;
						}

					} catch (NoSuchElementException e) {
						current = null;
					}
				}

				current = null;
				return first;
			}

		}

		public boolean isDone() {
			return current == null;
		}

		public Account currentItem() {
			return current;
		}

		public void next() {

			while (iter.hasNext()) {

				try {

					Holdings nextObj = iter.next();

					if (nextObj instanceof Account) {

						current = (Account) nextObj;
						return;
					}

				} catch (NoSuchElementException e) {
					current = null;
				}
			}

			current = null;
		}
	}

	/**
	 * Returns the iterator for equities
	 * @return
	 */
	public EquityIterator getEquityIterator() {
		return new EquityIterator(holdings);
	}

	/**
	 * Returns the iterator for accounts
	 * @return
	 */
	public AccountIterator getAccountIterator() {
		return new AccountIterator(holdings);
	}

	/**
	 * Populates the list of accounts from the 
	 * user file
	 */
	public void populateAccountsFromFile() {

		File data = new File(this.userName + ".txt");
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
			ReadHoldingsContext readAccountHolding = new ReadHoldingsContext(new ReadAccountHolding());

			if (temp[0].equals("!MM") || temp[0].equals("!BANK")) {

				Account accountInfo = null;

				accountInfo = (Account) readAccountHolding.executeStrategy(temp);

				accounts.add(accountInfo);

			}

		}

		dataRead.close();

	}

	/**
	 * Populates the equity list with the 
	 * equities from the user file
	 */
	public void populateEquitiesFromFile() {

		YahooStrategy company = new EquityStrategy();
		YahooContext context = new YahooContext(company);

		File data = new File(userName + ".txt");
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
			line = line.replace(", ", " ");
			temp = line.split(",");
			ReadHoldingsContext readOwnedEquity = new ReadHoldingsContext(new ReadOwnedEquities());

			if (temp[0].equals("!OWNED")) {
				Equity OwnedEquityInfo = (Equity) readOwnedEquity.executeStrategy(temp);

				OwnedEquityInfo.setInitPrice((context.executeStrategy(OwnedEquityInfo.getTickSymbol())));
				equityList.add(OwnedEquityInfo);

			}

		}

		dataRead.close();

	}

	/**
	 * Returns the list of accounts
	 * @return
	 */
	public ObservableList<Account> getAccounts() {
		return this.accounts;
	}

	/**
	 * Sets the account list to a specified list
	 * @param accounts
	 */
	public void setAccounts(ObservableList<Account> accounts) {
		this.accounts = accounts;
	}

	/**
	 * Finds an equity from a specified symbol
	 * @param tSymbol
	 * @return
	 */
	public Equity findEquity(String tSymbol) {

		Equity e = null;

		for (Equity findThis : equityList) {

			if (findThis.getTickSymbol().equals(tSymbol))
				return findThis;
		}

		return e;
	}

	/**
	 * Calculates the value of the portfolio
	 * by summing the accounts and equities
	 * @return
	 */
	public double getPortfolioValue() {

		double sumValue = 0.0;

		for (Equity e : this.equityList) {

			sumValue += e.getInitPrice() * e.getShares();
		}
		for (Account a : accounts) {

			sumValue += a.getAmount();
		}

		return sumValue;
	}

}
