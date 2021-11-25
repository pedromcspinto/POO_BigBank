package bank;

import java.util.GregorianCalendar;

public interface Transaction {
	GregorianCalendar getDate();
	double getValue();
	boolean isDeposit();  
}
