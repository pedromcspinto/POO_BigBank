package bank;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CheckingAccountClass extends AbstractAccount implements
		Checking {
	
	private List<Transaction> transactions;
	
	public CheckingAccountClass(String client, double amount, GregorianCalendar openingDate) {
		super(client, openingDate, amount);
		transactions = new LinkedList<Transaction>();
	}
	
	public Iterator<Transaction> getTransactions() {		
		return transactions.iterator();
	}

	public void addTransaction(GregorianCalendar date, double amount) {
		balance += amount;
		Transaction tr = new TransactionClass(date, amount);
		transactions.add(tr);
	}

}
