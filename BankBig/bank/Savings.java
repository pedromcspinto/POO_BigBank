package bank;

import java.util.GregorianCalendar;

public interface Savings extends Account {
	int getSavingDays();
	double getAnnualInterestRate();
	void updateBalance(double amount);
	boolean dueToday(GregorianCalendar today);
	void updateInterests(GregorianCalendar today);
}
