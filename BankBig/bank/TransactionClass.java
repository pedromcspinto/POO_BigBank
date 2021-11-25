package bank;

import java.util.GregorianCalendar;

public class TransactionClass implements Transaction {
 
	private GregorianCalendar date;
	private double value;
	
	public TransactionClass(GregorianCalendar date, double value) {
		this.date = date;
		this.value = value;
	}
	
	public GregorianCalendar getDate() {
		return date;
	}

	public double getValue() {	
		return value;
	}

	public boolean isDeposit() {
		return value > 0;
	}
	
	public String toString() {
		return value + "  " + date.get(GregorianCalendar.DAY_OF_MONTH) + ":" + date.get(GregorianCalendar.MONTH) + ":" + date.get(GregorianCalendar.YEAR);
	}

}
