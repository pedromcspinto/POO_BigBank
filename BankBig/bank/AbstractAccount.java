package bank;

import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class AbstractAccount implements Account {
	
	private static long seed = 1234;
	
	private String accountID;
	private String client;
	private GregorianCalendar openingDate;
	protected double balance;
	
	public AbstractAccount(String client, GregorianCalendar openingDate, double amount) {
		this.accountID = generateAccountID();
		this.client = client;	
		int day = openingDate.get(Calendar.DAY_OF_MONTH);
		int month = openingDate.get(Calendar.MONTH);
		int year = openingDate.get(Calendar.YEAR);
		this.openingDate = new GregorianCalendar(year,month,day);
		this.balance = amount;
		
	}
	
	
	private static String generateAccountID() {
		seed++;
		return "" + seed;
	}
	public String getAccountID() {	
		return accountID;
	}
	
	public double getBalance() {
		return balance;
	}

	
	public String getClient() {	
		return client;
	}

	public GregorianCalendar getOpeningDate() {
		return openingDate;
	}
		
	


	public boolean canWithdraw(double amount) {
		if (amount < 0)
			amount *= -1;
		return balance > amount;
	}

	public int compareTo(Account a) {
		return accountID.compareTo(a.getAccountID());
	}
	
	public String toString() {
		return this.getAccountID() + " " + this.getClient();
	}
}
