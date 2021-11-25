package bank;

import java.util.GregorianCalendar;

public interface Account extends Comparable<Account> {
	
	String getAccountID();
	double getBalance();
	String getClient();
	GregorianCalendar getOpeningDate();
	
	boolean canWithdraw(double amount);
}
