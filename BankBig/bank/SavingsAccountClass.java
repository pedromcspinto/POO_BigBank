package bank;

import java.util.GregorianCalendar;

public class SavingsAccountClass extends AbstractAccount implements Savings {

	private static int PAY_DAYS_YEAR = 360;

	private int savingDays;
	private double interestRate;
	private GregorianCalendar lastSettlementDate;

	public SavingsAccountClass(String client, GregorianCalendar openingDate, double amount, int savingDays, double interestRate) {
		super(client, openingDate, amount);
		this.savingDays = savingDays;
		this.interestRate = interestRate;
		lastSettlementDate = openingDate;
	}

	public int getSavingDays() {
		return savingDays;
	}

	public double getAnnualInterestRate() {
		return interestRate;
	}


	private static long daysBetween(GregorianCalendar from, GregorianCalendar to) {
		long oneHour = 3600000L;
		return ( (to.getTimeInMillis() - from.getTimeInMillis() ) / (oneHour * 24));
	}


	@Override
	public void updateBalance(double amount) {
		balance += amount;
	}

	public boolean dueToday(GregorianCalendar today) {
		return (daysBetween(lastSettlementDate,today) == savingDays );
	}
	
	@Override
	public void updateInterests(GregorianCalendar today) {
		if (daysBetween(lastSettlementDate,today) >= savingDays ) {
			double interest = balance * (savingDays / PAY_DAYS_YEAR) * interestRate;
			balance +=interest;
			lastSettlementDate = today;
		}
	}

}
