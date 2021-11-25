package bank;

import java.util.*;

public interface Checking extends Account {
	Iterator<Transaction> getTransactions();
	void addTransaction(GregorianCalendar date, double amount);
}
